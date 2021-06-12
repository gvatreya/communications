package in.gvatreya.communications.model;

import lombok.*;

import java.util.Date;

@Getter @Setter @Builder @NoArgsConstructor @AllArgsConstructor @ToString
public class Contact {

    // Primary Key
    private Long id;

    // For Rest API ids
    private String uuid;
    private String name;
    private String email;
    private String telegramHandle;
    private String twitterId;
    private String phoneNumber; // For now we are saving as string, can be modularized further

    // Book Keeping
    private Date created = new Date();
    private Date modified = new Date();
    private Date deleted;

}
