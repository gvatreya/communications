package in.gvatreya.communications.services.impl;

import in.gvatreya.communications.model.Contact;
import in.gvatreya.communications.model.dto.ContactDto;
import in.gvatreya.communications.repository.ContactRepository;
import in.gvatreya.communications.services.ContactService;
import in.gvatreya.communications.utils.ValidationResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.UUID;

@Service
public class ContactServiceImpl implements ContactService {

    private static final Logger LOG = LoggerFactory.getLogger(ContactServiceImpl.class);

    @Autowired
    private ContactRepository contactRepository;

    @Override
    public ContactDto createContact(@NonNull final ContactDto contactDto) {

        final ValidationResponse validationResponse = contactDto.validate();

        if(validationResponse.isValid()) {
            // Setup UUID
            contactDto.setUuid(UUID.randomUUID().toString());

            final Contact contactToSave = contactDto.toModel();
            final Contact savedContact = contactRepository.save(contactToSave);
            return ContactDto.fromModel(savedContact);
        } else {
            throw new IllegalArgumentException(StringUtils
                    .collectionToCommaDelimitedString(validationResponse.getProblems()));
        }
    }
}
