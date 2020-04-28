package server.dal.context;

import server.dal.hibernate.HibernateFactory;
import server.interfaces.IChannelContext;
import server.models.Channel;

import javax.annotation.Nullable;

public class ChannelHibernateContext implements IChannelContext {
    private HibernateFactory hibernateFactory;

    public ChannelHibernateContext(@Nullable HibernateFactory hibernateFactory) {
        if (hibernateFactory == null) {
            this.hibernateFactory = HibernateFactory.getInstance();
        } else {
            this.hibernateFactory = hibernateFactory;
        }
    }

    @Override
    public Channel createChannel(Channel channel) {
        return null;
    }
}
