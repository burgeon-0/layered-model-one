package org.burgeon.sbd.core;

import java.util.List;

/**
 * @author Sam Lu
 * @date 2021/5/31
 */
public interface DomainRepository<Entity, Key> {

    /**
     * 加载实体
     *
     * @param key
     * @return
     */
    Entity load(Key key);

    /**
     * 保存实体
     *
     * @param entity
     */
    void save(Entity entity);

    /**
     * 批量保存实体
     *
     * @param entities
     */
    void save(List<Entity> entities);

}
