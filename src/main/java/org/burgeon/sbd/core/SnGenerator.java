package org.burgeon.sbd.core;

/**
 * @author Sam Lu
 * @date 2021/6/3
 */
public interface SnGenerator {

    /**
     * 生成序列号
     *
     * @param nodeId
     * @return
     */
    String generateSn(long nodeId);

}
