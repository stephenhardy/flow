package org.apericore.flow.controller;

import org.apericore.flow.controller.annotations.StateType;

/**
 * Created by stephenh on 07/03/2014.
 */
public interface State {
    public abstract String getState();
    public abstract StateType getStateType();
}
