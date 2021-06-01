package org.burgeon.sbd.infra.repository;

import org.burgeon.sbd.core.DomainRepository;
import org.burgeon.sbd.domain.user.UserAggregate;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Sam Lu
 * @date 2021/5/31
 */
@Component
public abstract class AbstractUserRepositoryImpl implements DomainRepository<UserAggregate, String> {

    private Map<String, UserAggregate> userMap = new HashMap<>();

    @Override
    public UserAggregate load(String username) {
        return userMap.get(username);
    }

    @Override
    public void save(UserAggregate user) {
        userMap.put(user.getUsername(), user);
    }

}
