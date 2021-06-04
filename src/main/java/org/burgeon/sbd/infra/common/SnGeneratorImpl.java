package org.burgeon.sbd.infra.common;

import org.burgeon.sbd.core.SnGenerator;
import org.burgeon.sbd.infra.utils.Snowflake;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * @author Sam Lu
 * @date 2021/6/3
 */
@Component
public class SnGeneratorImpl implements SnGenerator {

    private static final Map<Integer, Snowflake> SNOWFLAKE_MAP = new HashMap<>();

    @Override
    public String generateSn(long nodeId) {
        Snowflake snowflake = SNOWFLAKE_MAP.get(nodeId);
        if (snowflake == null) {
            snowflake = new Snowflake(nodeId);
        }
        return String.valueOf(snowflake.nextId());
    }

}
