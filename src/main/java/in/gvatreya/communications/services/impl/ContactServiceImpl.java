package in.gvatreya.communications.services.impl;

import in.gvatreya.communications.model.Channel;
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

import java.util.*;
import java.util.stream.Collectors;

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

    @Override
    public Collection<ContactDto> getAllContacts() {
        final Collection<Contact> contacts = contactRepository.findAll();
        return contacts.stream().map(ContactDto::fromModel).collect(Collectors.toList());
    }

    @Override
    public Optional<ContactDto> getContact(@NonNull final String uuid) {
        final Optional<Contact> byUuid = contactRepository.findByUuid(uuid);
        return byUuid.map(ContactDto::fromModel);
    }

    @Override
    public Map<Long, String> getUuids(@NonNull List<Long> ids) {
        final Collection<Contact> allById = contactRepository.findAllById(ids);
        assert allById.size() == ids.size();
        final Map<Long, String> idUuidMap = new HashMap<>();
        for (Contact contact : allById) {
            idUuidMap.put(contact.getId(), contact.getUuid());
        }
        return idUuidMap;
    }

    @Override
    public Map<String, Long> getIds(@NonNull List<String> uuids) {
        final Collection<Contact> allById = contactRepository.findAllByUuid(uuids);
        assert allById.size() == uuids.size();
        final Map<String, Long> uuidIdMap = new HashMap<>();
        for (Contact contact : allById) {
            uuidIdMap.put(contact.getUuid(), contact.getId());
        }
        return uuidIdMap;
    }
}
