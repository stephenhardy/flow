package org.apericore.flow.engine.template;

import org.apericore.flow.controller.annotations.StateType;
import org.apericore.flow.controller.annotations.Transition;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by stephenh on 02/03/2014.
 */
public class StateMethod {

    private String state = null;
    private Method stateMethod = null;
    private StateType stateType = null;
    private Map<String, StateMethod> stateTransistions = null;
    private String subflowName = null;

    public Map<String, StateMethod> getStateTransistions() {
        if (null == stateTransistions) {
            stateTransistions = new HashMap<String, StateMethod>();
        }
        return stateTransistions;
    }

    public void setStateTransistions(Map<String, StateMethod> stateTransistions) {
        this.stateTransistions = stateTransistions;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public Method getStateMethod() {
        return stateMethod;
    }

    public void setStateMethod(Method stateMethod) {
        this.stateMethod = stateMethod;
    }

    public StateType getStateType() {
        return stateType;
    }

    public void setStateType(StateType stateType) {
        this.stateType = stateType;
    }

    public String getSubflowName() {
        return subflowName;
    }

    public void setSubflowName(String subflowName) {
        this.subflowName = subflowName;
    }

    public StateMethod transistion(String action){
        StateMethod target = null;
        if (null != stateTransistions) {
            target = stateTransistions.get(action);
            if (null == target) {
                target = stateTransistions.get(Transition.EVENT_ALL);
            }
        }
        return target;
    }

}
