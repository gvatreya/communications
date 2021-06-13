package in.gvatreya.communications.repository;

import in.gvatreya.communications.model.Channel;
import org.hibernate.annotations.Where;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
@Where(clause = "deleted IS null")
public interface ChannelRepository extends JpaRepository<Channel, Long> {

}
