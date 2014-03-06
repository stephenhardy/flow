package org.apericore.flow.view;

import org.apericore.flow.model.ModelAndAction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by stephenh on 02/03/2014.
 */
public class StandardInOutViewDespatcher implements ViewDespatcher {

    private final static Logger LOG = LoggerFactory.getLogger(StandardInOutViewDespatcher.class);

    public void despatchView(ModelAndAction modelAndAction) {
        StringBuilder sb = new StringBuilder("\n");
        sb.append("*****************************************************");
        sb.append("\n");
        sb.append("This is the view for: ");
        sb.append(modelAndAction.getAction());
        sb.append("\n");
        sb.append("*****************************************************");
        sb.append("\n");
        sb.append("Please enter some data: ");
        LOG.debug(sb.toString());
    }

}
