package org.bg181.sbd.domain.user;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Sam Lu
 * @date 2021/6/5
 */
public class TokenFactory {

    private static final Map<String, String> TOKEN_MAP = new HashMap<>();

    public static void put(String token, String username) {
        TOKEN_MAP.put(token, username);
    }

    public static String get(String token) {
        return TOKEN_MAP.get(token);
    }

}
