package org.burgeon.sbd.infra.repository;

import org.burgeon.sbd.domain.DomainRepository;
import org.burgeon.sbd.domain.user.UserAggregate;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author Sam Lu
 * @date 2021/5/31
 */
@Component
public class UserRepositoryImpl implements DomainRepository<UserAggregate, String> {

    @Override
    public UserAggregate load(String orderNo) {
        return null;
    }

    @Override
    public void save(UserAggregate order) {

    }

    @Override
    public void save(List<UserAggregate> productAggregates) {

    }

}
