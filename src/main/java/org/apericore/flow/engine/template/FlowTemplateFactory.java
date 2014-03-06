package org.apericore.flow.engine.template;

import org.apericore.flow.controller.annotations.*;
import org.apericore.flow.exception.FlowDefinitionException;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by stephenh on 03/03/2014.
 */
public class FlowTemplateFactory {

    public static final FlowTemplate getFlowTemplate(Class flow) {
        FlowTemplateImpl template = new FlowTemplateImpl();
        template.setFlowName(getFlowName(flow));
        buildInputList(flow, template);
        buildStateMethods(flow, template);
        buildInitMethod(flow, template);
        return template;
    }

    private static void buildInputList(Class flow, FlowTemplateImpl template) {
        List<String> inputs = new ArrayList<String>();
        FlowInputs flowInputs = (FlowInputs) flow.getAnnotation(FlowInputs.class);
        if (null != flowInputs) {
            String[] inputsArray = flowInputs.value();
            if (null != inputsArray) {
                for (String s : inputsArray) {
                    inputs.add(s);
                }
            }
        }
        template.setFlowInputList(inputs);
    }
    private static void buildInitMethod(Class flow, FlowTemplateImpl template) {
        Method initMethod = getInitMethod(flow);
        if (null == initMethod) {
            StringBuilder sb = new StringBuilder("No init method has been defined!");
            sb.append("\n");
            sb.append("Class: ");
            sb.append(flow.getClass().getSimpleName());
            throw new FlowDefinitionException();
        }
        State initState = getStateAnnotation(initMethod);
        StateMethod initStateMethod = buildStateMethod(initState, initMethod);

    }

    private static void buildStateMethods(Class flow, FlowTemplateImpl template){
        for(Method m : flow.getMethods()) {
            State state = null;
            StateMethod stateMethod = null;
            if (m.isAnnotationPresent(State.class)){
                state = m.getAnnotation(State.class);
                stateMethod = buildStateMethod(state, m);
                template.addMethodForState(state.state(), stateMethod);
                if (m.isAnnotationPresent(Init.class)) {
                    template.setInitMethod(stateMethod);
                }
            }
        }
        StateMethod targetState = null;
        for(StateMethod stateMethod : template.getStateMethods()) {
            Transition transition = stateMethod.getStateMethod().getAnnotation(Transition.class);
            if (null != transition) {
                targetState = template.getMethodForState((transition.targetState()));
                if (null != transition.event()) {
                    stateMethod.getStateTransistions().put(transition.event(), targetState);
                } else {
                    stateMethod.getStateTransistions().put(Transition.EVENT_ALL, targetState);
                }
            } else {
                Transitions transitions = stateMethod.getStateMethod().getAnnotation(Transitions.class);
                if (null != transitions) {
                    Transition[] transitionsArray = transitions.value();
                    for (Transition t : transitionsArray) {
                        targetState = template.getMethodForState((t.targetState()));
                        if (null != t.event()) {
                            stateMethod.getStateTransistions().put(t.event(), targetState);
                        } else {
                            stateMethod.getStateTransistions().put(Transition.EVENT_ALL, targetState);
                        }
                    }
                }
            }
        }
    }

    private static StateMethod buildStateMethod(State state, Method method) {
        StateMethod stateMethod = new StateMethod();
        stateMethod.setState(state.state());
        stateMethod.setStateMethod(method);
        stateMethod.setStateType(state.stateType());
        Flow flow = method.getAnnotation(Flow.class);
        if (null != flow) {
            stateMethod.setSubflowName(flow.value());
        }
        return stateMethod;
    }

    private static State getStateAnnotation(Method m){
        return m.getAnnotation(State.class);
    }

    private static Method getInitMethod(Class clz){
        Method init = null;
        for(Method m : clz.getMethods()) {
            if (m.isAnnotationPresent(Init.class)){
                init = m;
                break;
            }
        }
        return init;
    }

    private static String getFlowName(Class clz) {
        Flow flow = (Flow) clz.getAnnotation(Flow.class);
        String name = flow.value();
        if (null == name) {
            name = clz.getSimpleName();
        }
        return name;
    }

}
