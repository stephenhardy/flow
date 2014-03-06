package org.apericore.flow.controller.annotations;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Created by stephenh on 02/03/2014.
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface Transition {

    public final static String EVENT_ALL = "*";

    String event() default EVENT_ALL;
    String targetState();
}
