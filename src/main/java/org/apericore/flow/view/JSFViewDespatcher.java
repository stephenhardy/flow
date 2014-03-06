package org.apericore.flow.view;

import org.apericore.flow.model.ModelAndAction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by stephenh on 02/03/2014.
 */
public class JSFViewDespatcher implements ViewDespatcher {
    private final static Logger LOG = LoggerFactory.getLogger(JSFViewDespatcher.class);

    public void despatchView(ModelAndAction modelAndAction) {
        System.out.println("*****************************************************");
        System.out.println("This is the view for: " + modelAndAction.getAction());
        System.out.println("*****************************************************");
        System.out.println("Please enter some data: ");
    }

}
