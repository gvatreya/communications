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
}
