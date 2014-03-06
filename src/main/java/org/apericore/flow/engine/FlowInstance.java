package org.apericore.flow.engine;

import org.apericore.flow.controller.annotations.StateType;
import org.apericore.flow.engine.repository.FlowTemplateRepository;
import org.apericore.flow.engine.template.FlowTemplate;
import org.apericore.flow.engine.template.StateMethod;
import org.apericore.flow.exception.FlowDefinitionException;
import org.apericore.flow.model.FlowContext;
import org.apericore.flow.model.ModelAndAction;
import org.apericore.flow.model.annotations.ContextParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;
import java.util.List;

/**
 * Created by stephenh on 02/03/2014.
 */
public class FlowInstance {
    private final static Logger LOG = LoggerFactory.getLogger(FlowInstance.class);

    private Object flow = null;
    private FlowTemplate flowTemplate = null;
    private FlowContext flowContext = null;
    private StateMethod currentState = null;
    private FlowTemplateRepository templateRepos = FlowTemplateRepository.getInstance();
    private FlowInstanceState flowState = FlowInstanceState.INIT;

    public FlowInstance(Object flow){
        super();
        init(flow);
    }

    private void init(Object flow) {
        this.flow = flow;
        this.flowTemplate = templateRepos.getFlowTemplateFor(flow.getClass());
        this.flowContext = new FlowContext();
    }

    protected ModelAndAction start(FlowContext initialData){
        LOG.debug("Start() called.");
        flowState = FlowInstanceState.RUNNING;
        ModelAndAction modelAndAction = null;
        if (null != currentState) {
            LOG.error("You cannot start() an already started flow.");
            throw new FlowDefinitionException();
        }
        populateContextInputs(initialData);
        enterState(getFlowTemplate().getInitMethod());
        modelAndAction = new ModelAndAction
                            .Builder(ModelAndAction.ACTION_FLOW_ENTRY)
                            .addModelData(initialData)
                            .build();
        return runState(currentState, modelAndAction);
    }

    public ModelAndAction onEvent(ModelAndAction modelAndAction) {
        LOG.debug("onEvent() : " + modelAndAction.getAction());
        StateMethod nextState = nextState = currentState.transistion(modelAndAction.getAction());
        return runState(nextState, modelAndAction);
    }

    private ModelAndAction runState(StateMethod state, ModelAndAction modelAndAction) {
        if (null != state) {
            enterState(state);
            if (StateType.VIEW.equals(state.getStateType())) {
                flowState = FlowInstanceState.PAUSED_VIEW;
                return runStateMethod(modelAndAction);
            } else if (null != state.getSubflowName()) {
                flowState = FlowInstanceState.PAUSED_SUBFLOW;
                return runStateMethod(modelAndAction);
            }
            modelAndAction = runStateMethod(modelAndAction);
            onEvent(modelAndAction);
        } else {
            flowState = FlowInstanceState.COMPLETE;
            currentState = null;
        }
        return modelAndAction;
    }

    private void enterState(StateMethod nextState){
        LOG.debug("enterState() : " + nextState.getState());
        currentState = nextState;
    }

    private ModelAndAction runStateMethod(ModelAndAction modelAndAction) {
        LOG.debug("runStateMethod() : " + currentState.getState());
        try {
            Method m = currentState.getStateMethod();
            Class[] definedParams = m.getParameterTypes();
            Object[] inputParams = new Object[definedParams.length];
            int i=0;
            for (Class c : definedParams) {
                ContextParam contextParam = (ContextParam) c.getAnnotation(ContextParam.class);
                if (null != contextParam) {
                    inputParams[i] = getFlowContext().get(contextParam.value());
                }
                i++;
            }
            if (0 == inputParams.length) {
                inputParams = null;
            }
            modelAndAction = (ModelAndAction) currentState.getStateMethod().invoke(getFlow(), inputParams);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return modelAndAction;
    }

    private void populateContextInputs(FlowContext parentContext) {
        List<String> inputKeys = flowTemplate.getFlowInputList();
        for (String key : inputKeys) {
            LOG.debug("Adding the following to the context: " + key);
            flowContext.put(key, parentContext.get(key));
        }
    }

    protected FlowContext getFlowContext() {
        return flowContext;
    }

    protected void setFlowContext(FlowContext flowContext) {
        this.flowContext = flowContext;
    }

    protected Object getFlow() {
        return flow;
    }

    protected void setFlow(Object flow) {
        this.flow = flow;
    }

    protected FlowTemplate getFlowTemplate() {
        return flowTemplate;
    }

    protected void setFlowTemplate(FlowTemplate flowTemplate) {
        this.flowTemplate = flowTemplate;
    }

    protected StateMethod getCurrentState() {
        return currentState;
    }

    public FlowInstanceState getFlowState() {
        return flowState;
    }

}
