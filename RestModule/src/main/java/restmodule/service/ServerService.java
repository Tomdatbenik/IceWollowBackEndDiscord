package restmodule.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import restmodule.dal.IServerRepo;
import restmodule.models.IWServer;
import restmodule.models.TextChannel;
import restmodule.models.User;
import restmodule.models.VoiceChannel;;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Component
@AllArgsConstructor
public class ServerService {

    IServerRepo repo;

    public IWServer getServerById(int server_id) {
        return repo.findById(server_id);
    }

    public void updateServer(IWServer server) {
        repo.saveAndFlush(server);
    }

    public IWServer addServer(IWServer iwServer) {
        iwServer.getTextChannels().add(new TextChannel("General"));
        iwServer.getVoiceChannels().add(new VoiceChannel("General"));
        UUID uuid = UUID.randomUUID();
        iwServer.setCode(uuid.toString());

        return repo.save(iwServer);
    }

    public List<IWServer> getAllServersByUser(User user) {

        List<IWServer> allServers = repo.findAll();
        List<IWServer> servers = new ArrayList<>();

        allServers.stream().forEach(s -> {
            s.getUsers().stream().forEach(u -> {
                if (u.getId() == user.getId()) {
                    servers.add(s);
                }
            });
        });

        return servers;
    }

    public IWServer getServerByCode(String code)
    {
        return repo.getServerByCode(code);
    }
}
