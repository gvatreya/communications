package in.gvatreya.communications.repository;

import in.gvatreya.communications.TestUtility;
import in.gvatreya.communications.model.Contact;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;

import java.util.*;

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
    @SqlGroup({
            @Sql(scripts = "classpath:/sql/data-setup.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    })
    void findAll() {
        final Contact[] contacts = contactRepository.findAll(Sort.by("name")).toArray(Contact[]::new);

        assertNotNull(contacts, "contacts is null");
        final int countOfContacts = 5;
        assertEquals(countOfContacts, contacts.length, String.format("Expected %s accounts got %s", countOfContacts, contacts.length));

        assertNotNull(contacts[0].getId(), "id is null");
        assertEquals("test_uuid_1", contacts[0].getUuid(), "id mismatch");
        assertEquals("test_name_1", contacts[0].getName(), "name mismatch");
        assertEquals("test_name_1@test-email.com", contacts[0].getEmail(), "email mismatch");
        assertEquals("test_name_1", contacts[0].getTelegramHandle(), "telegramHandle mismatch");
        assertEquals("@testName1", contacts[0].getTwitterId(), "twitterId mismatch");
        assertEquals("+919988776655", contacts[0].getPhoneNumber(), "phoneNumber mismatch");
        assertNotNull(contacts[0].getCreated(), "created is null");
        assertNotNull(contacts[0].getModified(), "modified is null");
        assertNull(contacts[0].getDeleted(), "expected deleted to be null");

        assertNotNull(contacts[3].getId(), "id is null");
        assertEquals("test_uuid_4", contacts[3].getUuid(), "id mismatch");
        assertEquals("test_name_4", contacts[3].getName(), "name mismatch");
        assertEquals("test_name_4@test-email.com", contacts[3].getEmail(), "email mismatch");
        assertEquals("test_name_4", contacts[3].getTelegramHandle(), "telegramHandle mismatch");
        assertEquals("@testName4", contacts[3].getTwitterId(), "twitterId mismatch");
        assertEquals("+919988766755", contacts[3].getPhoneNumber(), "phoneNumber mismatch");
        assertNotNull(contacts[3].getCreated(), "created is null");
        assertNotNull(contacts[3].getModified(), "modified is null");
        assertNull(contacts[3].getDeleted(), "expected deleted to be null");

    }

    @Test
    @SqlGroup({
            @Sql(scripts = "classpath:/sql/data-setup.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD),
            @Sql(scripts = "classpath:/sql/data-teardown.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    })
    void findByUuid() {
        Optional<Contact> contactOptional = contactRepository.findByUuid("test_uuid_1");
        Contact contact = contactOptional.get();

        assertNotNull(contact, "contacts is null");

        assertNotNull(contact.getId(), "id is null");
        assertEquals("test_uuid_1", contact.getUuid(), "id mismatch");
        assertEquals("test_name_1", contact.getName(), "name mismatch");
        assertEquals("test_name_1@test-email.com", contact.getEmail(), "email mismatch");
        assertEquals("test_name_1", contact.getTelegramHandle(), "telegramHandle mismatch");
        assertEquals("@testName1", contact.getTwitterId(), "twitterId mismatch");
        assertEquals("+919988776655", contact.getPhoneNumber(), "phoneNumber mismatch");
        assertNotNull(contact.getCreated(), "created is null");
        assertNotNull(contact.getModified(), "modified is null");
        assertNull(contact.getDeleted(), "expected deleted to be null");

        contactOptional = contactRepository.findByUuid("test_uuid_4");
        contact = contactOptional.get();

        assertNotNull(contact.getId(), "id is null");
        assertEquals("test_uuid_4", contact.getUuid(), "id mismatch");
        assertEquals("test_name_4", contact.getName(), "name mismatch");
        assertEquals("test_name_4@test-email.com", contact.getEmail(), "email mismatch");
        assertEquals("test_name_4", contact.getTelegramHandle(), "telegramHandle mismatch");
        assertEquals("@testName4", contact.getTwitterId(), "twitterId mismatch");
        assertEquals("+919988766755", contact.getPhoneNumber(), "phoneNumber mismatch");
        assertNotNull(contact.getCreated(), "created is null");
        assertNotNull(contact.getModified(), "modified is null");
        assertNull(contact.getDeleted(), "expected deleted to be null");

    }

    @Test
    void findByUuidNotExisting() {
        Optional<Contact> contactOptional = contactRepository.findByUuid("test_uuid_1");
        assertTrue(contactOptional.isEmpty(), "Expected empty to be true, but got false");
    }

    @Test
    void save() {

        final String UUID_1 = UUID.randomUUID().toString();

        final Contact preBuildContact = TestUtility.getPreBuildContact(UUID_1);
        final Contact contact = contactRepository.save(preBuildContact);
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

    @Test
    void getUuids() {

        final String UUID_1 = UUID.randomUUID().toString();
        final String UUID_2 = UUID.randomUUID().toString();

        final Contact contact1 = contactRepository.save(Contact.builder().uuid(UUID_1).name("test1").email("test1@test1.com").build());
        final Contact contact2 = contactRepository.save(Contact.builder().uuid(UUID_2).name("test2").email("test2@test2.com").build());

        final Set<Long> ids = new HashSet<>();
        ids.add(contact1.getId());
        ids.add(contact2.getId());

        final List<Contact> uuids = contactRepository.findAllById(ids);
        assertEquals(2, uuids.size(), "Expected 2 uuids got " + uuids.size());
        assertEquals(UUID_1, uuids.toArray()[0], "UUID mismatch ");
        assertEquals(UUID_2, uuids.toArray()[1], "UUID mismatch");

    }

}