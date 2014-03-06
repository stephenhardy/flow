package org.apericore.flow.examples.futurebank.controller;

import org.apericore.flow.controller.annotations.*;
import org.apericore.flow.model.FlowContext;
import org.apericore.flow.model.Model;
import org.apericore.flow.model.ModelAndAction;
import org.apericore.flow.model.annotations.ContextParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by stephenh on 01/03/2014.
 */
@Flow("savingsAccountApplicationController")
@FlowInputs({   FlowContext.CTX_INTERVIEW_PARTIES,
                FlowContext.CTX_CURRENT_PRODUCT_SALE,
                FlowContext.CTX_TRANSACTION_CONTEXT
            })
public class SavingsAccountApplicationController {
    private final static Logger LOG = LoggerFactory.getLogger(SavingsAccountApplicationController.class);

    public static final String NAME = "savingsAccountApplicationController";

    /*
     *  State constants
     */
    public static final String STATE_INIT = "state_init";
    public static final String STATE_CAPTURE_PERSONAL_DETAILS = "state_capturePersonalDetails";
    public static final String STATE_STORE_PERSONAL_DETAILS = "state_storePersonalDetails";
    public static final String STATE_DISPLAY_CONFIRMATION_OF_UNDERSTANDING = "state_displayConfirmationOfUnderstanding";
    public static final String STATE_STORE_ACCEPTANCE = "state_storeAcceptance";
    public static final String STATE_STORE_DECLINE = "state_storeDecline";
    public static final String STATE_COMPLETE = "state_flowComplete";

    /*
     *  Flow constants
     */
    public static final String CONTROLLER_CAPTURE_PERSONAL_DETAILS = "capturePersonalDetailsController";

    /*
     *  View Constants
     */
    public static final String VIEW_DISPLAY_CCA_COMPLETE = "displayCCAComplete";

    @Init()
    @State( stateType = StateType.ACTION,
            state = STATE_INIT)
    @Transition(targetState = STATE_CAPTURE_PERSONAL_DETAILS)
    public ModelAndAction initSavingsApplication(){
        LOG.debug("SavingsAccountApplicationController: Init method called.");
        ModelAndAction output = new ModelAndAction.Builder(ModelAndAction.ACTION_CONTINUE).build();
        LOG.debug("SavingsAccountApplicationController: Init method exit.");
        return output;
    }

    @State( stateType = StateType.FLOW,
            state = STATE_CAPTURE_PERSONAL_DETAILS)
    @Flow("capturePersonalDetailsController")
    @Transition(targetState = STATE_STORE_PERSONAL_DETAILS)
    public ModelAndAction capturePersonalDetails(@ContextParam(FlowContext.CTX_TRANSACTION_CONTEXT) Object context,
                                         @ContextParam(FlowContext.CTX_CURRENT_PRODUCT_SALE) Object productSale){
        LOG.debug("SavingsAccountApplicationController: capturePersonalDetails method called.");
        ModelAndAction output = new ModelAndAction.Builder(STATE_STORE_PERSONAL_DETAILS).build();
        LOG.debug("SavingsAccountApplicationController: capturePersonalDetails method exit.");
        return output;
    }

    @State( stateType = StateType.ACTION,
            state = STATE_STORE_PERSONAL_DETAILS)
    @Transition(targetState = STATE_DISPLAY_CONFIRMATION_OF_UNDERSTANDING)
    public ModelAndAction storePersonalDetails( @ContextParam(FlowContext.CTX_TRANSACTION_CONTEXT) Object context,
                                        @ContextParam(FlowContext.CTX_CURRENT_PRODUCT_SALE) Object productSale){
        LOG.debug("SavingsAccountApplicationController: storePersonalDetails method called.");
        ModelAndAction output = new ModelAndAction.Builder(ModelAndAction.ACTION_CONTINUE)
                                        .addModelData(FlowContext.CTX_CURRENT_PRODUCT_SALE, productSale)
                                        .build();
        LOG.debug("SavingsAccountApplicationController: storePersonalDetails method exit.");
        return output;
    }

    @State( stateType = StateType.VIEW,
            state = STATE_DISPLAY_CONFIRMATION_OF_UNDERSTANDING)
    @Transitions(value = {  @Transition(event = ModelAndAction.ACTION_CONTINUE, targetState = STATE_STORE_ACCEPTANCE),
                            @Transition(event = ModelAndAction.ACTION_CANCEL, targetState = STATE_STORE_DECLINE)})
    public ModelAndAction displayConfirmationOfUnderstanding
                                (@ContextParam(FlowContext.CTX_INTERVIEW_PARTIES) Object partyList){
        LOG.debug("SavingsAccountApplicationController: displayCCA method called.");
        ModelAndAction output = new ModelAndAction.Builder(ModelAndAction.ACTION_CONTINUE).build();
        LOG.debug("SavingsAccountApplicationController: displayCCA method exit.");
        return output;
    }

    @State( stateType = StateType.ACTION,
            state = STATE_STORE_ACCEPTANCE)
    @Transition(targetState = STATE_COMPLETE)
    public ModelAndAction storeAcceptance(  @ContextParam(FlowContext.CTX_TRANSACTION_CONTEXT) Object context,
                                            @ContextParam(FlowContext.CTX_CURRENT_PRODUCT_SALE) Object productSale){
        LOG.debug("SavingsAccountApplicationController: storeAcceptance method called.");
        ModelAndAction output = new ModelAndAction.Builder(ModelAndAction.ACTION_CONTINUE)
                                        .addModelData(FlowContext.CTX_CURRENT_PRODUCT_SALE, productSale)
                                        .build();
        LOG.debug("SavingsAccountApplicationController: storeAcceptance method exit.");
        return output;
    }

    @State( stateType = StateType.ACTION,
            state = STATE_STORE_DECLINE)
    @Transition(targetState = STATE_COMPLETE)
    public ModelAndAction storeDecline( @ContextParam(FlowContext.CTX_TRANSACTION_CONTEXT) Object context,
                                        @ContextParam(FlowContext.CTX_CURRENT_PRODUCT_SALE) Object productSale){
        LOG.debug("SavingsAccountApplicationController: storeDecline method called.");
        ModelAndAction output = new ModelAndAction.Builder(ModelAndAction.ACTION_CONTINUE)
                                        .addModelData(FlowContext.CTX_CURRENT_PRODUCT_SALE, productSale)
                                        .build();
        LOG.debug("SavingsAccountApplicationController: storeDecline method exit.");
        return output;
    }

    @Complete()
    @State( stateType = StateType.ACTION,
            state = STATE_COMPLETE)
    public ModelAndAction completeSavingsApplication(){
        LOG.debug("SavingsAccountApplicationController: completeSavingsApplication method called.");
        ModelAndAction output = new ModelAndAction.Builder(ModelAndAction.ACTION_CONTINUE).build();
        LOG.debug("SavingsAccountApplicationController: completeSavingsApplication method exit.");
        return output;
    }

}
