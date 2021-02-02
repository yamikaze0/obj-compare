package org.yamikaze.compare;

import java.util.Collection;
import java.util.Map;

/**
 * @author qinluo
 * @version 1.0.0
 * @since 2019-05-27 23:00
 */
class CompareUtils {

    static String nullString(boolean isNull) {
        return isNull ? "null" : "not null";
    }

    static boolean isBlank(String params) {
        return params == null || params.trim().length() == 0;
    }

    static boolean isEmpty(Collection list) {
        return list == null || list.isEmpty();
    }

    static boolean isEmpty(Map map) {
        return map == null || map.isEmpty();
    }

    static void appendNewLine(StringBuilder sb) {
        sb.append("\n");
    }

    static void appendTab(StringBuilder sb) {
        sb.append("\t");
    }
}
