package org.apericore.flow.controller.annotations;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Created by stephenh on 01/03/2014.
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface State {
    StateType stateType();
    String state();
}
