package org.burgeon.sbd.infra.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.*;

import java.io.IOException;
import java.util.Map;

/**
 * <p>json工具类<p/>
 * <p><p/>
 *
 * @author Sam Lu
 * @date 2021/6/3
 */
public class JsonUtils {

    private static final ObjectMapper MAPPER = new ObjectMapper();
    private static final ObjectMapper MAPPER_WITH_SNAKE_CASE = new ObjectMapper();
    private static final ObjectMapper MAPPER_WITH_UPPER_CAMEL_CASE = new ObjectMapper();
    private static final ObjectMapper MAPPER_WITH_LOWER_CAMEL_CASE = new ObjectMapper();
    private static final ObjectMapper MAPPER_WITH_LOWER_CASE = new ObjectMapper();
    private static final ObjectMapper MAPPER_WITH_KEBAB_CASE = new ObjectMapper();

    static {
        MAPPER_WITH_SNAKE_CASE.setPropertyNamingStrategy(PropertyNamingStrategies.SNAKE_CASE);
        MAPPER_WITH_UPPER_CAMEL_CASE.setPropertyNamingStrategy(PropertyNamingStrategies.UPPER_CAMEL_CASE);
        MAPPER_WITH_LOWER_CAMEL_CASE.setPropertyNamingStrategy(PropertyNamingStrategies.LOWER_CAMEL_CASE);
        MAPPER_WITH_LOWER_CASE.setPropertyNamingStrategy(PropertyNamingStrategies.LOWER_CASE);
        MAPPER_WITH_KEBAB_CASE.setPropertyNamingStrategy(PropertyNamingStrategies.KEBAB_CASE);

        init(MAPPER);
        init(MAPPER_WITH_SNAKE_CASE);
        init(MAPPER_WITH_UPPER_CAMEL_CASE);
        init(MAPPER_WITH_LOWER_CAMEL_CASE);
        init(MAPPER_WITH_LOWER_CASE);
        init(MAPPER_WITH_KEBAB_CASE);
    }

    /**
     * init
     *
     * @param mapper
     */
    private static void init(ObjectMapper mapper) {
        // Ignore transaction field
        mapper.configure(MapperFeature.PROPAGATE_TRANSIENT_MARKER, true);

        // Don't throw an exception when json has extra fields you are
        // not serializing on. This is useful when you want to use a pojo
        // for deserialization and only care about a portion of the json
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        // Don't throw an exception when json property is empty
        mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);

        // custom date format
        mapper.setDateFormat(new CustomDateFormat(mapper.getDateFormat()));

        // write date as timestamp, must set after date format set
        mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, true);
    }

    /**
     * object to json
     *
     * @param src
     * @return
     */
    public static String toJson(Object src) {
        try {
            return MAPPER.writeValueAsString(src);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e.getLocalizedMessage());
        }
    }

    /**
     * object to json
     *
     * @param src
     * @param strategy
     * @return
     */
    public static String toJson(Object src, PropertyNamingStrategy strategy) {
        try {
            ObjectMapper objectMapper = getMapper(strategy);
            return objectMapper.writeValueAsString(src);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e.getLocalizedMessage());
        }
    }

    /**
     * json to object
     *
     * @param json
     * @param clazz
     * @param <T>
     * @return
     */
    public static <T> T fromJson(String json, Class<T> clazz) {
        try {
            return MAPPER.readValue(json, clazz);
        } catch (IOException e) {
            throw new RuntimeException(e.getLocalizedMessage());
        }
    }

    /**
     * json to object
     *
     * @param json
     * @param clazz
     * @param strategy
     * @param <T>
     * @return
     */
    public static <T> T fromJson(String json, Class<T> clazz, PropertyNamingStrategy strategy) {
        try {
            ObjectMapper objectMapper = getMapper(strategy);
            return objectMapper.readValue(json, clazz);
        } catch (IOException e) {
            throw new RuntimeException(e.getLocalizedMessage());
        }
    }

    /**
     * object to map
     *
     * @param src
     * @return
     */
    public static Map<String, Object> toMap(Object src) {
        String json = toJson(src);
        Map<String, Object> map;
        try {
            map = MAPPER.readValue(json, new TypeReference<Map<String, Object>>() {});
        } catch (IOException e) {
            throw new RuntimeException(e.getLocalizedMessage());
        }
        return map;
    }

    /**
     * object to map
     *
     * @param src
     * @param strategy
     * @return
     */
    public static Map<String, Object> toMap(Object src, PropertyNamingStrategy strategy) {
        String json = toJson(src, strategy);
        Map<String, Object> map;
        try {
            map = MAPPER.readValue(json, new TypeReference<Map<String, Object>>() {});
        } catch (IOException e) {
            throw new RuntimeException(e.getLocalizedMessage());
        }
        return map;
    }

    /**
     * map to object
     *
     * @param map
     * @param clazz
     * @param <T>
     * @return
     */
    public static <T> T fromMap(Map<String, Object> map, Class<T> clazz) {
        String json = toJson(map);
        return fromJson(json, clazz);
    }

    /**
     * map to object
     *
     * @param map
     * @param clazz
     * @param strategy
     * @param <T>
     * @return
     */
    public static <T> T fromMap(Map<String, Object> map, Class<T> clazz, PropertyNamingStrategy strategy) {
        String json = toJson(map, strategy);
        return fromJson(json, clazz, strategy);
    }

    /**
     * 获取对应策略的ObjectMapper
     *
     * @param strategy
     * @return
     */
    private static ObjectMapper getMapper(PropertyNamingStrategy strategy) {
        if (strategy == PropertyNamingStrategies.SNAKE_CASE) {
            return MAPPER_WITH_SNAKE_CASE;
        } else if (strategy == PropertyNamingStrategies.UPPER_CAMEL_CASE) {
            return MAPPER_WITH_UPPER_CAMEL_CASE;
        } else if (strategy == PropertyNamingStrategies.LOWER_CAMEL_CASE) {
            return MAPPER_WITH_LOWER_CAMEL_CASE;
        } else if (strategy == PropertyNamingStrategies.LOWER_CASE) {
            return MAPPER_WITH_LOWER_CASE;
        } else if (strategy == PropertyNamingStrategies.KEBAB_CASE) {
            return MAPPER_WITH_KEBAB_CASE;
        }
        throw new IllegalArgumentException("Not supports strategy!");
    }

}
