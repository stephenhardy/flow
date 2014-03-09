package org.apericore.flow.examples.futurebank.beans;

import com.google.common.eventbus.Subscribe;
import org.apericore.flow.event.StateChangedEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by stephenh on 07/03/2014.
 */
public class BreadcrumbsBean {

    private final static Logger LOG = LoggerFactory.getLogger(BreadcrumbsBean.class);

    @Subscribe
    public void handle(StateChangedEvent event) {
        if (LOG.isDebugEnabled()) {
            StringBuilder sb = new StringBuilder("Handled event: ");
            sb.append(event.getOldState()).append(" -> ").append(event.getNewState());
            LOG.debug(sb.toString());
        }
    }
}
