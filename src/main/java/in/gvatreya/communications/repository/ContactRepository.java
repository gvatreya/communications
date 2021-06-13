package in.gvatreya.communications.repository;

import in.gvatreya.communications.model.Contact;
import org.hibernate.annotations.Where;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

@Repository
@Where(clause = "deleted IS null")
public interface ContactRepository extends JpaRepository<Contact, Long> {

    Optional<Contact> findByUuid(String uuid);

    @Query("select c from Contact c where uuid in :uuids")
    List<Contact> findAllByUuid(@Param("uuids") List<String> uuid);
}
