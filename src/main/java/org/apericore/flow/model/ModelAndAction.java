package org.apericore.flow.model;

/**
 * Created by stephenh on 01/03/2014.
 */
public class ModelAndAction {

    public final static String ACTION_CONTINUE = "action_continue";
    public final static String ACTION_CANCEL = "action_cancel";
    public final static String ACTION_FLOW_ENTRY = "action_flow_entry";

    private String action = null;
    private Model model = null;

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public void addData(String key, Object value){

    }

    public Object getData(String key){
        return null;
    }

    public static class Builder {
        private String action = ACTION_CONTINUE;
        private Model model = new Model();

        public Builder(String action){
            this.action = action;
        }

        public Builder addModelData(Model model){
            this.model.putAll(model);
            return this;
        }

        public Builder addModelData(FlowContext flowContext){
            this.model.putAll(flowContext);
            return this;
        }

        public Builder addModelData(String key, Object value){
            this.model.put(key, value);
            return this;
        }

        public ModelAndAction build() {
            return new ModelAndAction(this);
        }
    }

    private ModelAndAction(Builder builder) {
        this.action = builder.action;

    }
}
