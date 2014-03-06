package org.apericore.flow.examples.futurebank.controller;

import org.apericore.flow.engine.FlowExecutor;
import org.apericore.flow.model.ExternalContext;
import org.apericore.flow.model.ModelAndAction;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

/**
 * Created by stephenh on 02/03/2014.
 */
@RunWith(JUnit4.class)
public class SavingsAccountApplicationControllerTest {

    @Test
    public void testInitState() {
        SavingsAccountApplicationController flow = new SavingsAccountApplicationController();
        flow.initSavingsApplication();
    }

    @Test
    public void testcapturePersonalDetails() {
        SavingsAccountApplicationController flow = new SavingsAccountApplicationController();
        flow.capturePersonalDetails(new Object(), new Object());
    }

    @Test
    public void testStorePersonalDetails() {
        SavingsAccountApplicationController flow = new SavingsAccountApplicationController();
        flow.storePersonalDetails(new Object(), new Object());
    }

    @Test
    public void testStoreAcceptance() {
        SavingsAccountApplicationController flow = new SavingsAccountApplicationController();
        flow.storeAcceptance(new Object(), new Object());
    }

    @Test
    public void testStoreDecline() {
        SavingsAccountApplicationController flow = new SavingsAccountApplicationController();
        flow.storeDecline(new Object(), new Object());
    }

    @Test
    public void testSavingsFlow(){
        ExternalContext externalContext = new ExternalContext();
        externalContext.put("currentProductSale", "currentProductSale");
        externalContext.put("transactionContext", "transactionContext");
        externalContext.put("interviewParties", "interviewParties");
        externalContext.put("donotpass", "donotpass");
        FlowExecutor flowExecutor = new FlowExecutor();
        ModelAndAction modelAndAction = flowExecutor.start(new SavingsAccountApplicationController(), externalContext);

    }

}
