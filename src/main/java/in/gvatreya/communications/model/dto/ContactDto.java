package in.gvatreya.communications.model.dto;

import in.gvatreya.communications.model.Contact;
import in.gvatreya.communications.utils.ValidationResponse;
import lombok.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.lang.NonNull;

import java.util.ArrayList;
import java.util.List;

/**
 * Data transfer object for the Contact model
 */
@Getter @Setter @Builder @NoArgsConstructor @AllArgsConstructor @ToString
public class ContactDto {

    // For Rest API ids
    private String uuid;
    private String name;
    private String email;
    private String telegramHandle;
    private String twitterId;
    private String phoneNumber; // For now we are saving as string, can be modularized further

    public static ContactDto fromModel(@NonNull final Contact contact) {
        return new ContactDtoBuilder()
                .uuid(contact.getUuid())
                .name(contact.getName())
                .email(contact.getEmail())
                .telegramHandle(contact.getTelegramHandle())
                .twitterId(contact.getTwitterId())
                .phoneNumber(contact.getPhoneNumber())
                .build();
    }

    public Contact toModel(){
        return Contact.builder()
                .uuid(this.uuid)
                .name(this.name)
                .email(this.email)
                .telegramHandle(this.telegramHandle)
                .twitterId(this.twitterId)
                .phoneNumber(this.phoneNumber)
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

        if(StringUtils.isBlank(this.name) ) {
            response.setValid(false);
            problems.add("name is empty/blank");
        }
        if(StringUtils.isBlank(this.email) ) {
            response.setValid(false);
            problems.add("email is empty/blank");
        }
        response.setProblems(problems);
        return response;
    }

}
