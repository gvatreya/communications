package in.gvatreya.communications.repository;

import in.gvatreya.communications.model.Contact;
import org.hibernate.annotations.Where;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@Where(clause = "deleted IS null")
public interface ContactRepository extends JpaRepository<Contact, Long> {

    Optional<Contact> findByUuid(String uuid);
}
