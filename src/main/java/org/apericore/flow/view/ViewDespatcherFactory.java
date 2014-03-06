package org.apericore.flow.view;

/**
 * Created by stephenh on 05/03/2014.
 */
public class ViewDespatcherFactory {

    private static ViewDespatcher viewDespatcher = new StandardInOutViewDespatcher();
    private ViewDespatcherFactory() {

    }

    public static ViewDespatcher getViewerDespatcher() {
        return viewDespatcher;
    }
}
