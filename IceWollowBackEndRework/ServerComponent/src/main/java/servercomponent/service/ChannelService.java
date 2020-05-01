package servercomponent.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import servercomponent.dal.ITextChannelRepo;
import servercomponent.dal.IVoiceChannelRepo;
import servercomponent.models.Channel;
import servercomponent.models.IWServer;
import servercomponent.models.TextChannel;
import servercomponent.models.VoiceChannel;
import servercomponent.models.dtomodels.ChannelType;

@Component
@AllArgsConstructor
public class ChannelService {

    ITextChannelRepo textChannelRepo;
    IVoiceChannelRepo voiceChannelRepo;

    ServerService serverService;

    public Channel createChannel(IWServer server, Channel channel, ChannelType type) {

        if (type == ChannelType.TEXT) {
            TextChannel textChannel = (TextChannel)channel;
            //Chat needs to be saved first
            //TODO create chat logic that saves a chat.
            //textChannel.chat = new Chat();
            server.getTextChannels().add(textChannel);
        } else if (type == ChannelType.VOICE) {
            server.getVoiceChannels().add((VoiceChannel) channel);
        }

        serverService.updateServer(server);

        return channel;

    }
}
