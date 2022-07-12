package org.bg181.sbd.core;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

/**
 * @author Sam Lu
 * @date 2021/5/31
 */
@Component
public class DomainEventBus {

    @Autowired
    private ApplicationContext applicationContext;

    public void publishEvent(DomainEvent event) {
        applicationContext.publishEvent(event);
    }

}
