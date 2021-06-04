package org.burgeon.sbd.infra.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.*;

import java.io.IOException;
import java.text.*;
import java.util.Date;
import java.util.Locale;
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

    /**
     * 自定义时间格式解析器
     *
     * @author Sam Lu
     * @date 2021/6/3
     */
    private static class CustomDateFormat extends DateFormat {

        private String pattern1 = "yyyy-M-d";
        private String pattern2 = "yyyy-MM-dd";
        private String pattern3 = "yyyy-M-d H:m:s";
        private String pattern4 = "yyyy-MM-dd HH:mm:ss";
        private String pattern5 = "MMM d, yyyy h:m:s aa";
        private String pattern6 = "EEE MMM d HH:mm:ss 'CST' yyyy";

        private SimpleDateFormat format1 = new SimpleDateFormat(pattern1);
        private SimpleDateFormat format2 = new SimpleDateFormat(pattern2);
        private SimpleDateFormat format3 = new SimpleDateFormat(pattern3);
        private SimpleDateFormat format4 = new SimpleDateFormat(pattern4);
        private SimpleDateFormat format5 = new SimpleDateFormat(pattern5, Locale.ENGLISH);
        private SimpleDateFormat format6 = new SimpleDateFormat(pattern6, Locale.ENGLISH);

        private DateFormat dateFormat;

        public CustomDateFormat(DateFormat dateFormat) {
            this.dateFormat = dateFormat;
        }

        @Override
        public StringBuffer format(Date date, StringBuffer toAppendTo, FieldPosition fieldPosition) {
            return dateFormat.format(date, toAppendTo, fieldPosition);
        }

        /**
         * 解析时间
         *
         * @param source
         * @param pos
         * @return
         */
        @Override
        public Date parse(String source, ParsePosition pos) {

            Date date;
            try {
                date = getSimpleDateFormat(source).parse(source, pos);
            } catch (Exception e) {
                date = dateFormat.parse(source, pos);
            }

            return date;
        }

        /**
         * 解析时间
         *
         * @param source
         * @return
         * @throws ParseException
         */
        @Override
        public Date parse(String source) throws ParseException {

            Date date;
            try {
                date = getSimpleDateFormat(source).parse(source);
            } catch (Exception e) {
                date = dateFormat.parse(source);
            }

            return date;
        }

        /**
         * clone
         *
         * @return
         */
        @Override
        public Object clone() {
            Object format = dateFormat.clone();
            return new CustomDateFormat((DateFormat) format);
        }

        private SimpleDateFormat getSimpleDateFormat(String str) {
            if (str == null) {
                return null;
            }

            if (str.length() >= pattern1.length() && str.length() < pattern2.length()) {
                return format1;
            } else if (str.length() == pattern2.length()) {
                return format2;
            } else if (str.length() >= pattern3.length() && str.length() < pattern4.length()) {
                return format3;
            } else if (str.length() == pattern4.length()) {
                return format4;
            } else if (str.length() >= pattern5.length() && str.length() < pattern6.length()) {
                return format5;
            } else if (str.length() >= pattern6.length()) {
                return format6;
            }

            return null;
        }

    }

}
