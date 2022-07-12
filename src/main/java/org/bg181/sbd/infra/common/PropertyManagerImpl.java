package org.bg181.sbd.infra.common;

import org.bg181.sbd.core.PropertyManager;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

/**
 * @author Sam Lu
 * @date 2021/5/31
 */
@Component
public class PropertyManagerImpl implements PropertyManager {

    /**
     * TODO copy child object
     *
     * @param source
     * @param target
     */
    @Override
    public void copy(Object source, Object target) {
        BeanUtils.copyProperties(source, target);
    }

}
