package org.burgeon.sbd.infra.common;

import org.burgeon.sbd.core.Copyable;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

/**
 * @author Sam Lu
 * @date 2021/5/31
 */
@Component
public class PropertyManager implements Copyable {

    @Override
    public void copy(Object source, Object target) {
        BeanUtils.copyProperties(source, target);
    }

}
