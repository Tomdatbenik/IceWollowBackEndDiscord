package restmodule.controllers;

import com.google.gson.Gson;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import restmodule.models.Channel;
import restmodule.models.IWServer;
import restmodule.models.TextChannel;
import restmodule.models.VoiceChannel;
import restmodule.models.dtomodels.ChannelDTO;
import restmodule.models.dtomodels.ChannelType;
import restmodule.service.ChannelService;
import restmodule.service.ServerService;

@CrossOrigin
@RestController
@AllArgsConstructor
@RequestMapping(path = "channel", produces = MediaType.APPLICATION_JSON_VALUE)
public class ChannelController {

    ChannelService channelService;
    ServerService serverService;

    private Gson gson = new Gson();

    @PostMapping(value = "/add")
    public ChannelDTO addChannel(int server_id, String channel) {
        ChannelDTO channelDTO = gson.fromJson(channel, ChannelDTO.class);

        Channel c = null;
        IWServer iwServer = serverService.getServerById(server_id);

        if (channelDTO.getType() == ChannelType.TEXT) {
            c = new TextChannel();
            c.setName(channelDTO.getName());
        } else if (channelDTO.getType() == ChannelType.VOICE) {
            c = new VoiceChannel();
            c.setName(channelDTO.getName());
        }

        channelService.createChannel(iwServer, c, channelDTO.getType());

        //get updated server
        iwServer = serverService.getServerById(server_id);

        int id = iwServer.getChannels().stream().mapToInt(Channel::getId).filter(sortChannel -> sortChannel >= 0).max().orElse(0);

        return new ChannelDTO(iwServer.getChannels().stream().filter(filterChannel-> filterChannel.getId() == id).findAny().orElse(null));
    }
}
