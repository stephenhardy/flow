package org.apericore.flow.engine.repository;

import org.apericore.flow.controller.annotations.Flow;
import org.apericore.flow.engine.template.FlowTemplate;
import org.apericore.flow.engine.template.FlowTemplateFactory;
import org.apericore.flow.examples.futurebank.controller.CapturePersonalDetailsController;
import org.apericore.flow.examples.futurebank.controller.SavingsAccountApplicationController;
import org.reflections.Reflections;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Created by stephenh on 02/03/2014.
 */
public class FlowTemplateRepository {
    private final static Logger LOG = LoggerFactory.getLogger(FlowTemplateRepository.class);

    private static FlowTemplateRepository ourInstance = null;
    private static Map<Class, FlowTemplate> templates = null;
    private static Map<String, Class> namesToClasses = null;
    private static Set<Class<?>> flows = null;

    /*
     *  Private constructor to ensure no rouge instantiations
     */
    private FlowTemplateRepository() {
    }

    /**
     * Provides a handle to the singleton <code>FlowTemplateRepository</code>.
     * @return FlowTemplateRepository The flow template repository
     */
    public static FlowTemplateRepository getInstance() {
        if (null == ourInstance) {
            init();
        }
        return ourInstance;
    }

    private static void init() {
        ourInstance = new FlowTemplateRepository();
        templates = new HashMap<Class, FlowTemplate>();
        namesToClasses = new HashMap<String, Class>();
        // use scannotations to load all the classes that are annotated with @Flow
        Reflections reflections = new Reflections("org.apericore");
        flows = reflections.getTypesAnnotatedWith(Flow.class);
        if (null != flows && 0 < flows.size()) {
            for(Class c : flows) {
                templates.put(c, getFlowTemplate(c));
                Flow flow = (Flow) c.getAnnotation(Flow.class);
                namesToClasses.put(flow.value(), c);
                if (LOG.isDebugEnabled()) {
                    StringBuffer sb = new StringBuffer("Added @Flow: ");
                    sb.append(flow.value());
                    LOG.debug(sb.toString());
                }
            }
        }

        /*
        Class savingsFlowClz = SavingsAccountApplicationController.class;
        templates.put(savingsFlowClz, getFlowTemplate(savingsFlowClz));
        Flow flow = (Flow) savingsFlowClz.getAnnotation(Flow.class);
        namesToClasses.put(flow.value(), SavingsAccountApplicationController.class);
        Class capturePersonalDetailsFlowClz = CapturePersonalDetailsController.class;
        templates.put(capturePersonalDetailsFlowClz, getFlowTemplate(capturePersonalDetailsFlowClz));
        flow = (Flow) capturePersonalDetailsFlowClz.getAnnotation(Flow.class);
        namesToClasses.put(flow.value(), CapturePersonalDetailsController.class);
        */
    }

    private static FlowTemplate getFlowTemplate(Class flow) {
        return FlowTemplateFactory.getFlowTemplate(flow);
    }

    public FlowTemplate getFlowTemplateFor(Class clz) {
        return templates.get(clz);
    }

    public Object getFlowForName(String flowName) {
        Class clz = namesToClasses.get(flowName);
        Object flowObject = null;
        try {
            flowObject = clz.newInstance();
        } catch (Exception e) {
            LOG.error(e.getMessage());
            throw new RuntimeException();
        }
        return flowObject;
    }
}
