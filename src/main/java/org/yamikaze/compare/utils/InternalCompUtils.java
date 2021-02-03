package org.yamikaze.compare.utils;

import java.util.Collection;
import java.util.Map;

/**
 * @author qinluo
 * @version 1.0.0
 * @since 2019-05-27 23:00
 */
public class InternalCompUtils {

    public static String nullString(boolean isNull) {
        return isNull ? "null" : "not null";
    }

    public static boolean isBlank(String params) {
        return params == null || params.trim().length() == 0;
    }

    public static boolean isEmpty(Collection list) {
        return list == null || list.isEmpty();
    }

    public static boolean isEmpty(Map map) {
        return map == null || map.isEmpty();
    }

    public static void appendNewLine(StringBuilder sb) {
        sb.append("\n");
    }

    public static void appendTab(StringBuilder sb) {
        sb.append("\t");
    }
}
