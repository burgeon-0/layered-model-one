package org.burgeon.sbd.infra.utils;

/**
 * @author Sam Lu
 * @date 2021/6/2
 */
public class StringUtils {

    /**
     * Snake Case To Camel Case
     *
     * @param source
     * @return
     */
    public static String snakeCaseToCamelCase(String source) {
        StringBuilder result = new StringBuilder();
        String[] arr = source.toLowerCase().split("_");
        for (String str : arr) {
            if (result.length() == 0) {
                result.append(str.toLowerCase());
            } else {
                result.append(str.substring(0, 1).toUpperCase());
                result.append(str.substring(1).toLowerCase());
            }
        }
        return result.toString();
    }

    /**
     * Camel Case To Snake Case
     *
     * @param source
     * @return
     */
    public static String camelCaseToSnakeCase(String source) {
        StringBuilder result = new StringBuilder();
        char c = source.charAt(0);
        result.append(Character.toLowerCase(c));
        for (int i = 1; i < source.length(); i++) {
            char ch = source.charAt(i);
            if (Character.isUpperCase(ch)) {
                result.append('_');
                result.append(Character.toLowerCase(ch));
            } else {
                result.append(ch);
            }
        }
        return result.toString();
    }

}
