package org.apericore.flow.event;

import org.apericore.flow.controller.State;

/**
 * Created by stephenh on 07/03/2014.
 */
public class StateChangedEvent {
    private State oldState = null;
    private State newState = null;

    public StateChangedEvent(State oldState, State newState) {
        super();
        this.oldState = oldState;
        this.newState = newState;
    }

    public State getNewState() {
        return newState;
    }

    public void setNewState(State newState) {
        this.newState = newState;
    }

    public State getOldState() {
        return oldState;
    }

    public void setOldState(State oldState) {
        this.oldState = oldState;
    }

}
