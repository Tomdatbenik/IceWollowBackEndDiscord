package server.models.dtomodels;

import server.models.Channel;
import server.models.TextChannel;
import server.models.VoiceChannel;

public class ChannelDTO {
    private int id;
    private String name;
    private ChannelType type;

    public ChannelDTO() {
    }

    public ChannelDTO(Channel channel) {

        this.id = channel.getId();
        this.name = channel.getName();

        if(channel.getClass().getName() == TextChannel.class.getName())
        {
            type = ChannelType.TEXT;
        }
        else  if(channel.getClass().getName() == VoiceChannel.class.getName())
        {
            type = ChannelType.VOICE;
        }
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public ChannelType getType() {
        return type;
    }
}
