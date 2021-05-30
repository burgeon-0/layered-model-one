package org.burgeon.sbd.domain;

import org.springframework.beans.BeanUtils;

import java.io.Serializable;

/**
 * @author Sam Lu
 * @date 2021/5/30
 */
public class BaseObject implements Serializable {

    public <T> T to(Class<T> tClass) {
        T target = null;
        try {
            target = tClass.newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        BeanUtils.copyProperties(this, target);
        return target;
    }

}
