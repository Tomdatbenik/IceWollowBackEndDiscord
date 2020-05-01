package server.controllers;

import com.google.gson.Gson;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import serverlogic.logic.ChannelContainerLogic;
import serverlogic.logic.ServerContainerLogic;
import serverlogic.models.Channel;
import serverlogic.models.IWServer;
import serverlogic.models.TextChannel;
import serverlogic.models.VoiceChannel;
import serverlogic.models.dtomodels.ChannelDTO;
import serverlogic.models.dtomodels.ChannelType;


@RestController
@RequestMapping(path = "channel", produces = MediaType.APPLICATION_JSON_VALUE)
public class ChannelController {
    ChannelContainerLogic channelContainerLogic = new ChannelContainerLogic();
    ServerContainerLogic serverContainerLogic = new ServerContainerLogic();
    private Gson gson = new Gson();

    @PostMapping(value = "/add")
    public ChannelDTO addChannel(int server_id, String channel) {
        ChannelDTO channelDTO = gson.fromJson(channel, ChannelDTO.class);

        Channel c = null;
        IWServer iwServer = serverContainerLogic.getServerById(server_id);

        if (channelDTO.getType() == ChannelType.TEXT) {
            c = new TextChannel();
            c.setName(channelDTO.getName());
        } else if (channelDTO.getType() == ChannelType.VOICE) {
            c = new VoiceChannel();
            c.setName(channelDTO.getName());
        }

        channelContainerLogic.createChannel(iwServer, c, channelDTO.getType());

        //get updated server
        iwServer = serverContainerLogic.getServerById(server_id);

        int id = iwServer.getChannels().stream().mapToInt(Channel::getId).filter(sortChannel -> sortChannel >= 0).max().orElse(0);

        return new ChannelDTO(iwServer.getChannels().stream().filter(filterChannel-> filterChannel.getId() == id).findAny().orElse(null));
    }
}
