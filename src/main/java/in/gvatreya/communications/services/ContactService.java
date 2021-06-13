package in.gvatreya.communications.services;

import in.gvatreya.communications.model.dto.ContactDto;
import org.springframework.lang.NonNull;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface ContactService {

    ContactDto createContact(@NonNull ContactDto contactDto);

    Collection<ContactDto> getAllContacts();

    Optional<ContactDto> getContact(@NonNull final String uuid);

    Map<Long, String> getUuids(@NonNull List<Long> ids);

    Map<String, Long> getIds(@NonNull List<String> ids);
}
