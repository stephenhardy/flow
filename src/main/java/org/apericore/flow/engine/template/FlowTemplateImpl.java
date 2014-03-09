package org.apericore.flow.engine.template;

import org.apericore.flow.controller.annotations.Transitions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

/**
 * Created by stephenh on 02/03/2014.
 */
public class FlowTemplateImpl implements FlowTemplate {
    private final static Logger LOG = LoggerFactory.getLogger(FlowTemplateImpl.class);
    private StateMethod initMethod = null;
    private Map<String, StateMethod> stateMethods = new HashMap<String, StateMethod>();
    private List<String> flowInputList = null;
    private String flowName = null;

    public StateMethod getInitMethod() {
        return initMethod;
    }

    protected void setInitMethod(StateMethod initMethod) {
        this.initMethod = initMethod;
    }

    public StateMethod getMethodForState(String state) {
        return stateMethods.get(state);
    }

    public void addMethodForState(String state, StateMethod stateMethod) {
        stateMethods.put(state, stateMethod);
    }

    public Collection<StateMethod> getStateMethods() {
        return stateMethods.values();
    }

    public List<String> getFlowInputList() {
        return flowInputList;
    }

    protected void setFlowInputList(List<String> flowInputList) {
        this.flowInputList = flowInputList;
    }

    public String getFlowName() {
        return flowName;
    }

    protected void setFlowName(String flowName) {
        this.flowName = flowName;
    }

    protected String stateTable() {
        StringBuilder sb = new StringBuilder("State Table for ");
        sb.append(flowName).append(":").append("\n");

        Set<String> stateKeys = stateMethods.keySet();
        StateMethod sm = null;
        Map<String, StateMethod> transitions = null;
        Set<String> transKeys = null;
        for (String stateKey: stateKeys) {
            sb.append(stateKey).append(" (");
            sm = stateMethods.get(stateKey);
            transitions = sm.getStateTransistions();
            transKeys = transitions.keySet();
            for(String transKey: transKeys) {
                sb.append(transKey).append(" -> ");
                StateMethod tsm = transitions.get(transKey);
                if (null != tsm) {
                    sb.append(tsm.getState()).append(", ");
                }
            }
            sb.append(")");
            sb.append("\n");
        }
        return sb.toString();
    }
}
