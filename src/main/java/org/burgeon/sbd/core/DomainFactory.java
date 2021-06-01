package org.burgeon.sbd.core;

/**
 * @author Sam Lu
 * @date 2021/5/31
 */
public class DomainFactory<Entity, Key> {

    protected DomainRepository<Entity, Key> domainRepository;

    public Entity load(Key key) {
        return domainRepository.load(key);
    }

}
