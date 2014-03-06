package org.apericore.flow.engine.template;

import java.util.List;

/**
 * Created by stephenh on 02/03/2014.
 */
public interface FlowTemplate {
    public StateMethod getInitMethod();
    public StateMethod getMethodForState(String state);
    public List<String> getFlowInputList();
    public String getFlowName();
}
