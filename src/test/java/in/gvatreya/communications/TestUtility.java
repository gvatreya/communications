package in.gvatreya.communications;

import in.gvatreya.communications.model.Contact;
import org.apache.commons.lang3.RandomStringUtils;

import java.util.UUID;

/**
 * Helper class with static methods to create stubs.
 */
public class TestUtility {

    public static final String UUID_1 = UUID.randomUUID().toString();
    public static final String NAME_1 = RandomStringUtils.randomAlphabetic(3, 7);
    public static final String EMAIL_1 = NAME_1 + "@sample-email.com";

    public static Contact getPreBuildContact(final String uuid) {
        return Contact.builder()
                .uuid(uuid)
                .name(NAME_1)
                .email(EMAIL_1)
                .build();
    }
}
