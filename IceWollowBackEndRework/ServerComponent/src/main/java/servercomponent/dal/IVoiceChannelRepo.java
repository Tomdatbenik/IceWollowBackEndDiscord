package servercomponent.dal;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import servercomponent.models.IWServer;

@Repository
public interface IVoiceChannelRepo extends JpaRepository<IWServer, String> {

}
