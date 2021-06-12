package in.gvatreya.communications.model;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;

@Entity
@Getter @Setter @Builder @NoArgsConstructor @AllArgsConstructor @ToString
public class Contact {

    // Primary Key
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // For Rest API ids
    private String uuid;
    private String name;
    private String email;
    private String telegramHandle;
    private String twitterId;
    private String phoneNumber; // For now we are saving as string, can be modularized further

    // Book Keeping
    @Builder.Default
    private Date created = new Date();
    @Builder.Default
    private Date modified = new Date();
    private Date deleted;

}
