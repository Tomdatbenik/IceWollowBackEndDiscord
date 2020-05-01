package serverlogic.dal.context;

import serverlogic.dal.hibernate.HibernateFactory;
import serverlogic.interfaces.IChannelContext;
import serverlogic.models.Channel;

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
