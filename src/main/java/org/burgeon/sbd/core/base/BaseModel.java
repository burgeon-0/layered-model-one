package org.burgeon.sbd.core.base;

import lombok.extern.slf4j.Slf4j;
import org.burgeon.sbd.core.PropertyManager;
import org.burgeon.sbd.core.SpringBeanFactory;

import java.io.Serializable;

/**
 * @author Sam Lu
 * @date 2021/5/30
 */
@Slf4j
public class BaseModel implements Serializable {

    public <T> T to(Class<T> tClass) {
        T target = null;
        try {
            target = tClass.newInstance();
        } catch (InstantiationException e) {
            log.error("Instantiation Exception", e);
        } catch (IllegalAccessException e) {
            log.error("Illegal Access Exception", e);
        }
        PropertyManager propertyManager = SpringBeanFactory.getBean(PropertyManager.class);
        propertyManager.copy(this, target);
        return target;
    }

}
