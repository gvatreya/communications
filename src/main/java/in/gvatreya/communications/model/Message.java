package in.gvatreya.communications.model;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;

@Entity
@Getter @Setter @Builder
@NoArgsConstructor @AllArgsConstructor @ToString
public class Message {

    // Primary Key
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // For Rest API ids
    private String uuid;

    // Foreign Key
    private Long senderId;
    private Long receiverId;
    private Long channelId;

    private String body;
    private String subject;

    //FIXME: can store the original message from webhook as a JSON Object

    private String deliveryStatus;

    // Book Keeping
    @Builder.Default
    private Date created = new Date();
    @Builder.Default
    private Date modified = new Date();
    private Date deleted;
}
