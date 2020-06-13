package restmodule.controllers;

import com.google.gson.Gson;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import restmodule.models.Channel;
import restmodule.models.IWServer;
import restmodule.models.TextChannel;
import restmodule.models.VoiceChannel;
import restmodule.models.dtomodels.ChannelDTO;
import restmodule.models.dtomodels.ChannelType;
import restmodule.service.ChannelService;
import restmodule.service.ServerService;

@CrossOrigin(origins = {"*"})
@RestController
@AllArgsConstructor
@RequestMapping(path = "channel", produces = MediaType.APPLICATION_JSON_VALUE)
@Log4j2
public class ChannelController {

    ChannelService channelService;
    ServerService serverService;

    @CrossOrigin(origins = {"*"})
    @PostMapping()
    public ResponseEntity addChannel(@RequestBody ChannelDTO channelDTO) {

        IWServer iwServer = serverService.getServerById(channelDTO.getServer_id());

        if(iwServer != null && channelDTO.getName().length() > 2)
        {
            Channel c = null;
            log.info("Handling add channel: " + channelDTO.getName());
            log.info("channel type: " + channelDTO.getType());

            if (channelDTO.getType() == ChannelType.TEXT) {
                c = new TextChannel();
                c.setName(channelDTO.getName());
            } else if (channelDTO.getType() == ChannelType.VOICE) {
                c = new VoiceChannel();
                c.setName(channelDTO.getName());
            }
            else {
                return new ResponseEntity(HttpStatus.UNPROCESSABLE_ENTITY);
            }

            channelService.createChannel(iwServer, c, channelDTO.getType());

            //get updated server
            iwServer = serverService.getServerById(channelDTO.getServer_id());

            int id = iwServer.getChannels().stream().mapToInt(Channel::getId).filter(sortChannel -> sortChannel >= 0).max().orElse(0);
            ChannelDTO dto = new ChannelDTO(iwServer.getChannels().stream().filter(filterChannel-> filterChannel.getId() == id).findAny().orElse(null));

            log.warn("Channel saved");

            return new ResponseEntity<>(dto, HttpStatus.OK);
        }
        else
        {
            log.warn("Dito is missing required fields");
            return new ResponseEntity(HttpStatus.UNPROCESSABLE_ENTITY);
        }

    }
}
