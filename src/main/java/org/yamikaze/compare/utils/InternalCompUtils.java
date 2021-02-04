package org.yamikaze.compare.utils;

import java.util.Collection;
import java.util.Map;

/**
 * @author qinluo
 * @version 1.0.0
 * @since 2019-05-27 23:00
 */
public class InternalCompUtils {

    public static boolean isBlank(String... params) {
        if (params == null || params.length == 0) {
            return true;
        }

        for (String value : params) {
            if (value != null && value.trim().length() != 0) {
                return false;
            }
        }

        return true;
    }

    public static boolean isEmpty(Collection<?> list) {
        return list == null || list.isEmpty();
    }

    public static boolean isEmpty(Map<?, ?> map) {
        return map == null || map.isEmpty();
    }

    public static boolean isFalse(Boolean ...values) {
        if (values == null || values.length == 0) {
            return true;
        }

        for (Boolean value : values) {
            if (value != null && value) {
                return false;
            }
        }

        return true;
    }

    public static void appendNewLine(StringBuilder sb) {
        sb.append("\n");
    }

    public static void appendTab(StringBuilder sb) {
        sb.append("\t");
    }
}
