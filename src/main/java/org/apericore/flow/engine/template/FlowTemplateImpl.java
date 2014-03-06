package org.apericore.flow.engine.template;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by stephenh on 02/03/2014.
 */
public class FlowTemplateImpl implements FlowTemplate {

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
}
