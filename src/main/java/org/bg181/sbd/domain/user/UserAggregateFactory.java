package org.bg181.sbd.domain.user;

import org.bg181.sbd.core.DomainFactory;
import org.bg181.sbd.core.SpringBeanFactory;
import org.springframework.stereotype.Component;

/**
 * @author Sam Lu
 * @date 2021/5/31
 */
@Component
public class UserAggregateFactory extends DomainFactory<UserAggregate, String> {

    @Override
    public UserAggregate load(String username) {
        domainRepository = SpringBeanFactory.getDomainRepository(UserAggregate.class, String.class);
        return super.load(username);
    }

    public UserAggregate loadByToken(String token) {
        String username = TokenFactory.get(token);
        if (username == null) {
            return null;
        }
        return load(username);
    }

}
