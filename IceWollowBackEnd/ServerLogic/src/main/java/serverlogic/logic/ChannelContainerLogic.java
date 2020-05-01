package serverlogic.logic;


import serverlogic.models.Channel;
import serverlogic.models.IWServer;
import serverlogic.models.TextChannel;
import serverlogic.models.VoiceChannel;
import serverlogic.models.dtomodels.ChannelType;

public class ChannelContainerLogic {

    public ServerLogic serverLogic = new ServerLogic();

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

        serverLogic.updateServer(server);

        return channel;
    }
}
