package org.burgeon.sbd.core;

import java.util.List;

/**
 * @author Sam Lu
 * @date 2021/5/31
 */
public abstract class AbstractDomainRepository<Entity, Key> implements DomainRepository<Entity, Key> {

    @Override
    public Entity load(Key key) {
        return null;
    }

    @Override
    public void save(Entity entity) {

    }

    @Override
    public void save(List<Entity> entities) {

    }

}
