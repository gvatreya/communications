package in.gvatreya.communications.services;

import in.gvatreya.communications.model.dto.ChannelDto;
import org.springframework.lang.NonNull;

import java.util.Collection;

public interface ChannelService {

    ChannelDto createChannel(@NonNull ChannelDto channelDto);

    Collection<ChannelDto> getAllChannels();
}
