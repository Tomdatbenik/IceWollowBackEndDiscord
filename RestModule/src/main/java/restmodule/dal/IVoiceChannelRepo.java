package restmodule.dal;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import restmodule.models.VoiceChannel;

@Repository
public interface IVoiceChannelRepo extends JpaRepository<VoiceChannel, String> {

}
