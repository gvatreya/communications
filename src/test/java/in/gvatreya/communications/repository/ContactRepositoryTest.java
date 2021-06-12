package in.gvatreya.communications.repository;

import in.gvatreya.communications.TestUtility;
import in.gvatreya.communications.model.Contact;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Collection;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class ContactRepositoryTest extends AbstractRepositoryTest {

    @Autowired
    private ContactRepository contactRepository;

    @Test
    void findAllWhenEmpty() {
        final Collection<Contact> all = contactRepository.findAll();
        assertTrue(all.isEmpty(), "Expected empty list, found: " + all.size());
    }

    @Test
    void save() {

        final String UUID_1 = UUID.randomUUID().toString();

        final Contact preBuildContact = TestUtility.getPreBuildContact(UUID_1);
        System.out.println(preBuildContact);
        final Contact contact = contactRepository.save(preBuildContact);
        System.out.println(contact);
        assertNotNull(contact, "contact is null");
        assertNotNull(contact.getId(), "id is null");
        assertEquals(UUID_1, contact.getUuid(), "id mismatch");
        assertEquals(TestUtility.NAME_1, contact.getName(), "name mismatch");
        assertEquals(TestUtility.EMAIL_1, contact.getEmail(), "email mismatch");
        assertNull(contact.getTelegramHandle(), "telegramHandle is not null. It is " + contact.getTelegramHandle());
        assertNull(contact.getTwitterId(), "twitterId is not null. It is " + contact.getTwitterId());
        assertNull(contact.getPhoneNumber(), "phoneNumber is not null. It is " + contact.getPhoneNumber());
        assertNotNull(contact.getCreated(), "created is null");
        assertNotNull(contact.getModified(), "modified is null");
        assertNull(contact.getDeleted(), "expected deleted to be null");
    }

}