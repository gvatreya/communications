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
public class Channel {

    // Primary Key
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // For Rest API ids
    private String uuid;
    private String name;

    //FIXME: This has to be a JSON Object
    private String configuration;

    // Book Keeping
    @Builder.Default
    private Date created = new Date();
    @Builder.Default
    private Date modified = new Date();
    private Date deleted;
}
