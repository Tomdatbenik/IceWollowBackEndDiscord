package server.interfaces;

import server.models.Channel;

public interface IChannelContainerRepo {

    Channel createChannel(Channel channel);
}
