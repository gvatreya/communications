package in.gvatreya.communications.controller;

import in.gvatreya.communications.model.dto.MessageDto;
import in.gvatreya.communications.services.MessageService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/messages")
@Validated
@Tag( name = "Messages", description = "The Messages API")
public class MessageController {

    @Autowired
    private MessageService messageService;

    @PostMapping
    @ResponseBody
    @Operation(summary = "Send a new message", description = "Send a new message using the channel mentioned.")
    public ResponseEntity<MessageDto> sendMessage(@RequestBody final MessageDto messageDto) {
        final MessageDto message = messageService.createMessage(messageDto);
        return new ResponseEntity<>(message, HttpStatus.CREATED);
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
