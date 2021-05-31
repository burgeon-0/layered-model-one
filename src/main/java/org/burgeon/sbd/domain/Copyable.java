package org.burgeon.sbd.domain;

/**
 * @author Sam Lu
 * @date 2021/5/31
 */
public interface Copyable {

    /**
     * 复制对象
     *
     * @param source
     * @param target
     */
    void copy(Object source, Object target);

}
