package in.gvatreya.communications.services.impl;

import in.gvatreya.communications.model.Channel;
import in.gvatreya.communications.model.Contact;
import in.gvatreya.communications.model.Message;
import in.gvatreya.communications.model.dto.ContactDto;
import in.gvatreya.communications.model.dto.MessageDto;
import in.gvatreya.communications.repository.ChannelRepository;
import in.gvatreya.communications.repository.ContactRepository;
import in.gvatreya.communications.repository.MessageRepository;
import in.gvatreya.communications.services.*;
import in.gvatreya.communications.utils.Constants;
import in.gvatreya.communications.utils.ValidationResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.transaction.Transactional;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class MessageServiceImpl implements MessageService {

    private static final Logger LOG = LoggerFactory.getLogger(MessageServiceImpl.class);

    @Autowired
    private ContactService contactService;

    @Autowired
    private MessageRepository messageRepository;

    @Autowired
    private ContactRepository contactRepository;

    @Autowired
    private ChannelRepository channelRepository;

    @Autowired
    private MessageDeliveryCallback messageDeliveryCallback;

    @Override
    public MessageDto createMessage(@NonNull final MessageDto messageDto) {

        final ValidationResponse validationResponse = messageDto.validate();

        if(validationResponse.isValid()) {
            // Setup UUID
            messageDto.setUuid(UUID.randomUUID().toString());

            final List<String> contactUuids = new ArrayList<>();
            contactUuids.add(messageDto.getSenderUuid());
            contactUuids.add(messageDto.getReceiverUuid());

            final Map<String, Long> uuidIdMap = contactService.getIds(contactUuids);
            assert uuidIdMap.size() == 2;

            final Optional<Channel> channel = channelRepository.findByUuid(messageDto.getChannelUuid());
            if(channel.isPresent()) {
                final Channel channel1 = channel.get();
                uuidIdMap.put(channel1.getUuid(), channel1.getId());
            } else {
                throw new IllegalArgumentException("No channel exists for given channelId");
            }

            final Map<Long, String> idUuidMap = new HashMap<>();
            for (String uuid : uuidIdMap.keySet()) {
                idUuidMap.put(uuidIdMap.get(uuid), uuid);
            }

            final Message messageToSave = messageDto.toModel(uuidIdMap);
            messageToSave.setDeliveryStatus(Constants.DELIVERY_STATUS.IN_TRANSIT.name());

            final Message savedContact = messageRepository.save(messageToSave);

            // Register delivery callback
//            final MessageDeliveryCallback callback = new MessageDeliverycallbackImpl();
            // Attempt Delivery
            ExecutorService messageDeliveryExecutors = MessageThreadsManager.getInstance().getMessageThreadPool();
            messageDeliveryExecutors.execute(new MessageDeliveryTask(savedContact, messageDeliveryCallback));


            return MessageDto.fromModel(savedContact, idUuidMap);
        } else {
            throw new IllegalArgumentException(StringUtils
                    .collectionToCommaDelimitedString(validationResponse.getProblems()));
        }
    }

    @Override
    public Collection<MessageDto> getAllMessagesOfContact(@NonNull String uuid) {
        final Optional<Contact> contact = contactRepository.findByUuid(uuid);
        if(contact.isPresent()) {
            final Collection<Message> all = messageRepository.findAllBySenderIdOrReceiverId(contact.get().getId());
            final Collection<Long> receiverIds = all.stream().map(Message::getReceiverId).collect(Collectors.toList());
            final Collection<Long> senderIds = all.stream().map(Message::getSenderId).collect(Collectors.toList());
            final Collection<Long> channelIds = all.stream().map(Message::getChannelId).collect(Collectors.toList());
            final List<Long> ids = Stream.concat(receiverIds.stream(), senderIds.stream()).collect(Collectors.toList());
            final Collection<Contact> allById = contactRepository.findAllById(ids);
            final Collection<Channel> channels = channelRepository.findAllById(channelIds);
            final Map<Long, String> idUuidMap = allById.stream()
                    .collect(Collectors.toMap(Contact::getId, Contact::getUuid));
            final Map<Long, String> channelIdUuidMap = channels.stream().collect(Collectors.toMap(Channel::getId, Channel::getUuid));
            idUuidMap.putAll(channelIdUuidMap);
            return all.stream().map(message -> MessageDto.fromModel(message, idUuidMap)).collect(Collectors.toList());
        } else {
            return Collections.emptyList();
        }
    }

    @Override
    public Optional<MessageDto> getMessage(@NonNull final String uuid) {
        final Message message = messageRepository.findByUuid(uuid);

        final List<Long> ids = new ArrayList<>();
        ids.add(message.getSenderId());
        ids.add(message.getReceiverId());

        final Map<Long, String> idUuidMap = contactService.getUuids(ids);
        assert idUuidMap.size() == 2;

        final Optional<Channel> channel = channelRepository.findById(message.getChannelId());
        channel.ifPresent(value -> idUuidMap.put(value.getId(), value.getUuid()));

        return Optional.ofNullable(MessageDto.fromModel(message, idUuidMap));
    }

    @Override
    @Transactional
    public MessageDto updateStatus(@NonNull final String uuid, @NonNull final String deliveryStatus) {
        final int i = messageRepository.updateStatus(uuid, deliveryStatus);

        final Message message = messageRepository.findByUuid(uuid);

        final List<Long> contactIds = new ArrayList<>();
        contactIds.add(message.getSenderId());
        contactIds.add(message.getReceiverId());

        final Map<Long, String> uuidIdMap = contactService.getUuids(contactIds);
        assert uuidIdMap.size() == 2;

        final Optional<Channel> channel = channelRepository.findById(message.getChannelId());
        if(channel.isPresent()) {
            final Channel channel1 = channel.get();
            uuidIdMap.put(channel1.getId(), channel1.getUuid());
        } else {
            throw new IllegalArgumentException("No channel exists for given channelId");
        }

        final MessageDto messageDto = MessageDto.fromModel(message, uuidIdMap);
        LOG.info("Updated Message Status" + messageDto);
        return messageDto;
    }
}
