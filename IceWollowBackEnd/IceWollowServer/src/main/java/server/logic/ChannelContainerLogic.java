package server.logic;

import server.models.Channel;
import server.models.IWServer;
import server.models.TextChannel;
import server.models.VoiceChannel;
import server.models.dtomodels.ChannelType;

public class ChannelContainerLogic {

    public ServerLogic serverLogic = new ServerLogic();

    public Channel createChannel(IWServer server, Channel channel, ChannelType type) {
        if (type == ChannelType.TEXT) {
            server.getTextChannels().add((TextChannel) channel);
        } else if (type == ChannelType.VOICE) {
            server.getVoiceChannels().add((VoiceChannel) channel);
        }

        serverLogic.updateServer(server);

        return channel;
    }
}
