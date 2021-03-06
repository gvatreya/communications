package in.gvatreya.communications.repository;

import in.gvatreya.communications.model.Channel;
import org.hibernate.annotations.Where;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
@Where(clause = "deleted IS null")
public interface ChannelRepository extends JpaRepository<Channel, Long> {

    Optional<Channel> findByUuid(String uuid);

}
