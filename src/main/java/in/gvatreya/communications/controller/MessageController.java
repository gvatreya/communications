package in.gvatreya.communications.controller;

import in.gvatreya.communications.model.dto.MessageDto;
import in.gvatreya.communications.services.MessageService;
import in.gvatreya.communications.utils.Utility;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/messages")
@Validated
@Tag( name = "Messages", description = "The Messages API")
public class MessageController {

    private static final Logger LOG = LoggerFactory.getLogger(MessageController.class);

    @Autowired
    private MessageService messageService;

    @PostMapping
    @ResponseBody
    @Operation(summary = "Send a new message", description = "Send a new message using the channel mentioned.")
    public ResponseEntity<MessageDto> sendMessage(@RequestBody final MessageDto messageDto) {
        final MessageDto message = messageService.createMessage(messageDto);
        return new ResponseEntity<>(message, HttpStatus.CREATED);
    }

    @PostMapping("/external")
    @ResponseBody
    @Operation(summary = "Accept external messages", description = "Accepts messages from external web services as a json")
    public ResponseEntity<MessageDto> acceptMessage(@RequestBody final Object object) {
        LOG.info("Accepted incoming message from external service. Will be processed further and saved as message in the service");
        LOG.info(Utility.printObject(object));
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }

    @GetMapping("/{uuid}")
    @ResponseBody
    @Operation(summary = "Get Messages for a given uuid", description = "Get message given messageUuid")
    public ResponseEntity<Optional<MessageDto>> getMessage(@PathVariable("uuid")final String uuid) {
        final Optional<MessageDto> message = messageService.getMessage(uuid);
        return new ResponseEntity<>(message, HttpStatus.OK);
    }

    @GetMapping("/contact/{uuid}")
    @ResponseBody
    @Operation(summary = "Get all Messages for a contact uuid", description = "Get all messages for a given contact")
    public ResponseEntity<Collection<MessageDto>> listMessages(@PathVariable("uuid")final String contactUuid) {
        final Collection<MessageDto> messages = messageService.getAllMessagesOfContact(contactUuid);
        return new ResponseEntity<>(messages, HttpStatus.OK);
    }
}
