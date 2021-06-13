package in.gvatreya.communications.repository;

import in.gvatreya.communications.model.Message;
import org.hibernate.annotations.Where;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

@Repository
@Where(clause = "deleted IS null")
public interface MessageRepository extends JpaRepository<Message, Long> {

    @Query("update Message set deliveryStatus = :status, modified = now() where uuid = :uuid")
    Message updateStatus(@Param("uuid") String uuid, @Param("status") String newStatus);
}
