package org.burgeon.sbd.core;

import java.util.HashMap;
import java.util.Map;

/**
 * TODO 使用雪花算法进行改造
 *
 * @author Sam Lu
 * @date 2021/5/30
 */
public class SnKeeper {

    private static final Map<String, Integer> SN_MAP = new HashMap<>();

    public synchronized static String get(String key) {
        Integer sn = SN_MAP.get(key);
        if (sn == null) {
            sn = 1;
        } else {
            sn++;
        }
        SN_MAP.put(key, sn);
        return String.format("%06d", sn);
    }

}
