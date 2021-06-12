package in.gvatreya.communications.controller;

import in.gvatreya.communications.model.dto.ContactDto;
import in.gvatreya.communications.services.ContactService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/contacts")
@Validated
@Tag( name = "Contacts", description = "The Contacts API")
public class ContactController {

    private static final Logger LOG = LoggerFactory.getLogger(ContactController.class);

    @Autowired
    private ContactService contactService;

    @PostMapping
    @ResponseBody
    @Operation(summary = "Create a new Contact", description = "Creates new contact with given details.")
    public ResponseEntity<ContactDto> createContact(@RequestBody final ContactDto contactDto) {
        final ContactDto createdContact = contactService.createContact(contactDto);
        return new ResponseEntity<>(createdContact, HttpStatus.CREATED);
    }

    @GetMapping("/{uuid}")
    @ResponseBody
    @Operation(summary = "Get contact with uuid", description = "Get details of the contact whose uuid is passed")
    public ResponseEntity<ContactDto> getContact(@PathVariable("uuid")final String contactUuid) {
        final Optional<ContactDto> contactDto = contactService.getContact(contactUuid);
        return contactDto.map(dto -> new ResponseEntity<>(dto, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping
    @ResponseBody
    @Operation(summary = "Get all Contacts", description = "Get all contacts in the system")
    public ResponseEntity<Collection<ContactDto>> getAllContacts() {
        final Collection<ContactDto> contacts = contactService.getAllContacts();
        return new ResponseEntity<>(contacts, HttpStatus.OK);
    }
}
