package servercomponent.dal;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import servercomponent.models.VoiceChannel;

@Repository
public interface ITextChannelRepo extends JpaRepository<VoiceChannel, String> {

}
