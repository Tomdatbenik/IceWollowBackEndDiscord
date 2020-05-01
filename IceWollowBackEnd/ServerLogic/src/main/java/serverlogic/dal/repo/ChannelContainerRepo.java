package serverlogic.dal.repo;

import serverlogic.interfaces.IChannelContainerRepo;
import serverlogic.interfaces.IChannelContext;
import serverlogic.models.Channel;

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
