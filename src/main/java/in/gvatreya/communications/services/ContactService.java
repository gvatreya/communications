package in.gvatreya.communications.services;

import in.gvatreya.communications.model.dto.ContactDto;
import org.springframework.lang.NonNull;

public interface ContactService {

    ContactDto createContact(@NonNull ContactDto contactDto);
}
