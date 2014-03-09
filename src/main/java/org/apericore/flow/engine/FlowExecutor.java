package org.apericore.flow.engine;

import com.google.common.eventbus.EventBus;
import org.apericore.flow.controller.annotations.Flow;
import org.apericore.flow.engine.repository.FlowTemplateRepository;
import org.apericore.flow.engine.template.StateMethod;
import org.apericore.flow.event.FlowEventBus;
import org.apericore.flow.model.ExternalContext;
import org.apericore.flow.model.FlowContext;
import org.apericore.flow.model.ModelAndAction;
import org.apericore.flow.view.ViewDespatcherFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Stack;

/**
 * Created by stephenh on 02/03/2014.
 */
public class FlowExecutor {
    private final static Logger LOG = LoggerFactory.getLogger(FlowExecutor.class);

    private FlowTemplateRepository templateRepos = FlowTemplateRepository.getInstance();
    private Stack<FlowInstance> flowStack = new Stack<FlowInstance>();
    private FlowEventBus flowEventBus = new FlowEventBus();

    public ModelAndAction start(Object flow, ExternalContext externalContext) {
        FlowContext flowContext = new FlowContext();
        flowContext.putAll(externalContext);
        return start(flow, flowContext);
    }

    public ModelAndAction start(Object flow, FlowContext flowContext) {
        String flowName = flow.getClass().getAnnotation(Flow.class).value();
        LOG.debug("Starting flow: " + flowName);
        ModelAndAction modelAndAction = null;
        flowStack.push(new FlowInstance(flow));
        modelAndAction = flowStack.peek().start(flowContext);
        return handleCallback(modelAndAction);
    }

    private ModelAndAction eventLoop(ModelAndAction modelAndAction) {
        while(!flowStack.isEmpty()) {
            StateMethod currentState = flowStack.peek().getCurrentState();
            modelAndAction = flowStack.peek().onEvent(modelAndAction);
            modelAndAction = handleCallback(modelAndAction);
        }
        return modelAndAction;
    }

    private ModelAndAction handleCallback(ModelAndAction modelAndAction) {
        if (!flowStack.isEmpty()) {
            StateMethod currentState = flowStack.peek().getCurrentState();
            if (FlowInstanceState.RUNNING.equals(flowStack.peek().getFlowState())) {
                modelAndAction = flowStack.peek().onEvent(modelAndAction);
            } else if (FlowInstanceState.PAUSED_VIEW.equals(flowStack.peek().getFlowState())) {
                LOG.debug("Handling view state: " + currentState.getState());
                ViewDespatcherFactory.getViewerDespatcher().despatchView(modelAndAction);
                modelAndAction = next(modelAndAction); // REMOVE WHEN REAL VIEWS POPPED IN
            } else if (FlowInstanceState.PAUSED_SUBFLOW.equals(flowStack.peek().getFlowState())) {
                LOG.debug("Handling subflow state: " + currentState.getState());
                Object subFlow = templateRepos.getFlowForName(currentState.getSubflowName());
                modelAndAction = start(subFlow, flowStack.peek().getFlowContext());
                modelAndAction = handleCallback(modelAndAction);
            } else if (FlowInstanceState.COMPLETE.equals(flowStack.peek().getFlowState())) {
                completeCurrentFlow();
            }
        }
        return modelAndAction;
    }
    public ModelAndAction next(ModelAndAction modelAndAction) {
        return eventLoop(modelAndAction);
    }

    private void completeCurrentFlow() {
        LOG.debug("Completing flow: " + flowStack.peek().getFlowTemplate().getFlowName());
        flowStack.peek().complete();
        flowStack.pop();
    }
}
