package in.gvatreya.communications.controller;

import in.gvatreya.communications.model.dto.ChannelDto;
import in.gvatreya.communications.model.dto.ContactDto;
import in.gvatreya.communications.services.ChannelService;
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
import java.util.Optional;

// FIXME: This entire controller will be restricted to USER with ADMIN PRIVILEGE - handled at Service Level
@RestController
@RequestMapping("/api/v1/channels")
@Validated
@Tag( name = "Channels", description = "The Channels API")
public class ChannelController {

    private static final Logger LOG = LoggerFactory.getLogger(ChannelController.class);

    @Autowired
    private ChannelService channelService;

    @PostMapping
    @ResponseBody
    @Operation(summary = "Create a new Channel", description = "Creates new channel with given details.")
    public ResponseEntity<ChannelDto> createChannel(@RequestBody final ChannelDto channelDto) {
        final ChannelDto createdChannel = channelService.createChannel(channelDto);
        return new ResponseEntity<>(createdChannel, HttpStatus.CREATED);
    }

    @GetMapping
    @ResponseBody
    @Operation(summary = "Get all Channels", description = "Get all contacts in the system")
    public ResponseEntity<Collection<ChannelDto>> getAllContacts() {
        final Collection<ChannelDto> allChannels = channelService.getAllChannels();
        return new ResponseEntity<>(allChannels, HttpStatus.OK);
    }
}
