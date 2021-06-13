package in.gvatreya.communications.services.impl;

import in.gvatreya.communications.model.Channel;
import in.gvatreya.communications.model.Contact;
import in.gvatreya.communications.model.dto.ChannelDto;
import in.gvatreya.communications.model.dto.ContactDto;
import in.gvatreya.communications.repository.ChannelRepository;
import in.gvatreya.communications.services.ChannelService;
import in.gvatreya.communications.utils.ValidationResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.*;
import java.util.stream.Collectors;

//FIXME This entire Service is a privileged service and requires the user to have role ROLE_ADMIN
// @Secured("ROLE_ADMIN") annotation will then go here - Spring Security
@Service
public class ChannelServiceImpl implements ChannelService {

    @Autowired
    private ChannelRepository channelRepository;

    @Override
    public ChannelDto createChannel(@NonNull final ChannelDto channelDto) {
        final ValidationResponse validationResponse = channelDto.validate();

        if(validationResponse.isValid()) {
            // Setup UUID
            channelDto.setUuid(UUID.randomUUID().toString());

            final Channel channelToSave = channelDto.toModel();
            final Channel savedChannel = channelRepository.save(channelToSave);
            return ChannelDto.fromModel(savedChannel);
        } else {
            throw new IllegalArgumentException(StringUtils
                    .collectionToCommaDelimitedString(validationResponse.getProblems()));
        }
    }

    @Override
    public Collection<ChannelDto> getAllChannels() {
        final Collection<Channel> channels = channelRepository.findAll();
        return channels.stream().map(ChannelDto::fromModel).collect(Collectors.toList());
    }

    @Override
    public Map<Long, String> getUuids(Set<Long> ids) {
        final Collection<Channel> allById = channelRepository.findAllById(ids);
        assert allById.size() == ids.size();
        final Map<Long, String> idUuidMap = new HashMap<>();
        for (Channel channel : allById) {
            idUuidMap.put(channel.getId(), channel.getUuid());
        }
        return idUuidMap;
    }
}
