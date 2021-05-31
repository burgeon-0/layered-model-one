package org.burgeon.sbd.domain;

import java.io.Serializable;

/**
 * @author Sam Lu
 * @date 2021/5/30
 */
public class MagicObject implements Serializable {

    public <T> T to(Class<T> tClass) {
        T target = null;
        try {
            target = tClass.newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        Copyable copyable = ApplicationContextHolder.getBean(Copyable.class);
        copyable.copy(this, target);
        return target;
    }

}
