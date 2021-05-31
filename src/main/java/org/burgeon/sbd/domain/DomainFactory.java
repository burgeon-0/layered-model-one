package org.burgeon.sbd.domain;

import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author Sam Lu
 * @date 2021/5/31
 */
public class DomainFactory<Entity, Key> {

    @Autowired
    private DomainRepository<Entity, Key> domainRepository;

    public Entity load(Key key) {
        return domainRepository.load(key);
    }

}
