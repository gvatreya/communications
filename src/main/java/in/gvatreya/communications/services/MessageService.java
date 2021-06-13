package in.gvatreya.communications.services;

import in.gvatreya.communications.model.dto.MessageDto;
import in.gvatreya.communications.utils.Constants;
import org.springframework.lang.NonNull;

import java.util.Collection;
import java.util.Optional;

public interface MessageService {

    MessageDto createMessage(@NonNull MessageDto messageDto);

    Collection<MessageDto> getAllMessagesOfContact(@NonNull String uuid);

    Optional<MessageDto> getMessage(@NonNull final String uuid);

    MessageDto updateStatus(@NonNull final String uuid, @NonNull String deliveryStatus);
}
