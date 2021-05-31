package org.burgeon.sbd.domain;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

/**
 * @author Sam Lu
 * @date 2021/5/31
 */
@Component
public class DomainEventBus {

    @Autowired
    private ApplicationContext applicationContext;

    @Bean
    public DomainEventBus getInstance() {
        return new DomainEventBus();
    }

    public void publishEvent(DomainEvent event) {
        applicationContext.publishEvent(event);
    }

}
