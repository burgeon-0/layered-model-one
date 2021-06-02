package org.burgeon.sbd.core.base;

import org.burgeon.sbd.core.Copyable;
import org.burgeon.sbd.core.SpringBeanFactory;

import java.io.Serializable;

/**
 * @author Sam Lu
 * @date 2021/5/30
 */
public class BaseModel implements Serializable {

    public <T> T to(Class<T> tClass) {
        T target = null;
        try {
            target = tClass.newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        Copyable copyable = SpringBeanFactory.getBean(Copyable.class);
        copyable.copy(this, target);
        return target;
    }

}
