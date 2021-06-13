package in.gvatreya.communications.model.dto;

import in.gvatreya.communications.model.Contact;
import in.gvatreya.communications.model.Message;
import in.gvatreya.communications.utils.ValidationResponse;
import lombok.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.lang.NonNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Data transfer object for the Message model
 */
@Getter @Setter @Builder
@NoArgsConstructor @AllArgsConstructor @ToString
public class MessageDto {

    // For Rest API ids
    private String uuid;
    private String senderUuid;
    private String receiverUuid;
    private String channelUuid;
    private String body;
    private String subject;
    private String deliveryStatus;

    public static MessageDto fromModel(@NonNull final Message message, @NonNull final Map<Long, String> idUuidMap) {
        return new MessageDtoBuilder()
                .uuid(message.getUuid())
                .senderUuid(idUuidMap.get(message.getSenderId()))
                .receiverUuid(idUuidMap.get(message.getReceiverId()))
                .channelUuid(idUuidMap.get(message.getChannelId()))
                .body(message.getBody())
                .subject(message.getSubject())
                .deliveryStatus(message.getDeliveryStatus())
                .build();
    }

    public Message toModel(@NonNull final Map<String, Long> uuidIdMap){
        return Message.builder()
                .uuid(this.uuid)
                .senderId(uuidIdMap.get(this.senderUuid))
                .receiverId(uuidIdMap.get(this.receiverUuid))
                .channelId(uuidIdMap.get(this.channelUuid))
                .body(this.body)
                .subject(this.subject)
                .deliveryStatus(this.deliveryStatus)
                .build();
    }

    /**
     * Helper method that validates the ContactDto object
     * @return {@link ValidationResponse}
     */
    public ValidationResponse validate() {
        final ValidationResponse response = new ValidationResponse();
        // Set true by default
        response.setValid(true);
        final List<String> problems = new ArrayList<>();

        if(StringUtils.isBlank(this.body) ) {
            response.setValid(false);
            problems.add("body is empty/blank");
        }
        response.setProblems(problems);
        return response;
    }
}
