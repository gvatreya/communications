package in.gvatreya.communications.services.impl;

import in.gvatreya.communications.model.Channel;
import in.gvatreya.communications.model.Message;
import in.gvatreya.communications.model.dto.MessageDto;
import in.gvatreya.communications.repository.ChannelRepository;
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

import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Service
public class MessageServiceImpl implements MessageService {

    private static final Logger LOG = LoggerFactory.getLogger(MessageServiceImpl.class);

    @Autowired
    private ContactService contactService;

    @Autowired
    private MessageRepository messageRepository;

    @Autowired
    private ChannelRepository channelRepository;

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
            final MessageDeliveryCallback callback = new MessageDeliverycallbackImpl();
            // Attempt Delivery
            ExecutorService messageDeliveryExecutors = MessageThreadsManager.getInstance().getMessageThreadPool();
            messageDeliveryExecutors.execute(new MessageDeliveryTask(savedContact, callback));


            return MessageDto.fromModel(savedContact, idUuidMap);
        } else {
            throw new IllegalArgumentException(StringUtils
                    .collectionToCommaDelimitedString(validationResponse.getProblems()));
        }
    }

    @Override
    public Collection<MessageDto> getAllMessages() {
        return null;
    }

    @Override
    public Optional<MessageDto> getMessage(@NonNull final String uuid) {
        return Optional.empty();
    }

    @Override
    public MessageDto updateStatus(@NonNull final String uuid, @NonNull final String deliveryStatus) {
        final Message message = messageRepository.updateStatus(uuid, deliveryStatus);

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
