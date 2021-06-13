package in.gvatreya.communications.services;

import in.gvatreya.communications.model.Channel;
import in.gvatreya.communications.model.dto.ChannelDto;
import org.springframework.lang.NonNull;

import java.util.Collection;
import java.util.Map;
import java.util.Set;

public interface ChannelService {

    ChannelDto createChannel(@NonNull ChannelDto channelDto);

    Collection<ChannelDto> getAllChannels();

    Map<Long, String> getUuids(@NonNull Set<Long> ids);

}
