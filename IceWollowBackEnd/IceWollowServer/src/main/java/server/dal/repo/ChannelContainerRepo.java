package server.dal.repo;

import server.interfaces.IChannelContainerRepo;
import server.interfaces.IChannelContext;
import server.models.Channel;

public class ChannelContainerRepo implements IChannelContainerRepo {
    private IChannelContext context;

    public ChannelContainerRepo(IChannelContext context) {
        this.context = context;
    }
    @Override
    public Channel createChannel(Channel channel) {
        return context.createChannel(channel);
    }
}
