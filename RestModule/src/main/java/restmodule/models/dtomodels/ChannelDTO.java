package restmodule.models.dtomodels;

import lombok.Getter;
import restmodule.models.Channel;
import restmodule.models.TextChannel;
import restmodule.models.VoiceChannel;

@Getter
public class ChannelDTO {
    private int id;
    private String name;
    private ChannelType type;
    private int server_id;

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

}
