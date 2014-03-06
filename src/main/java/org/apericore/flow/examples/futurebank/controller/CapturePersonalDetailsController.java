package org.apericore.flow.examples.futurebank.controller;

import org.apericore.flow.controller.annotations.*;
import org.apericore.flow.model.ModelAndAction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by stephenh on 02/03/2014.
 */
@Flow("capturePersonalDetailsController")
public class CapturePersonalDetailsController {

    private final static Logger LOG = LoggerFactory.getLogger(CapturePersonalDetailsController.class);

    public static final String STATE_CAPTURE_PERSONAL_DETAILS = "state_innerCapturePersonalDetails";
    public static final String STATE_DISPLAY_PREFEERENCES = "state_displayPreferences";

    @Init()
    @State(stateType = StateType.VIEW, state = STATE_CAPTURE_PERSONAL_DETAILS)
    @Transition(targetState = STATE_DISPLAY_PREFEERENCES)
    public ModelAndAction displayPersonalDetailsPage(){
        LOG.debug("CapturePersonalDetailsController: displayPersonalDetailsPage method called.");
        ModelAndAction output = new ModelAndAction.Builder(ModelAndAction.ACTION_CONTINUE).build();
        LOG.debug("CapturePersonalDetailsController: displayPersonalDetailsPage method exit.");
        return output;
    }

    @State(stateType = StateType.VIEW, state = STATE_DISPLAY_PREFEERENCES)
    public ModelAndAction displayPreferences(){
        LOG.debug("CapturePersonalDetailsController: displayPersonalDetailsPage method called.");
        ModelAndAction output = new ModelAndAction.Builder(ModelAndAction.ACTION_CONTINUE).build();
        LOG.debug("CapturePersonalDetailsController: displayPersonalDetailsPage method exit.");
        return output;
    }

}
