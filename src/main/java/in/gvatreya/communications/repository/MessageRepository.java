package in.gvatreya.communications.repository;

import in.gvatreya.communications.model.Message;
import org.hibernate.annotations.Where;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
@Where(clause = "deleted IS null")
public interface MessageRepository extends JpaRepository<Message, Long> {

    Message findByUuid(@NonNull String uuid);

    @Modifying
    @Query("update Message set deliveryStatus = :status, modified = now() where uuid = :uuid")
    int updateStatus(@Param("uuid") String uuid, @Param("status") String newStatus);

    @Query("select m from Message m where (senderId = :contactId or receiverId = :contactId) order by modified desc")
    Collection<Message> findAllBySenderIdOrReceiverId(@Param("contactId") Long contactUuid);
}
