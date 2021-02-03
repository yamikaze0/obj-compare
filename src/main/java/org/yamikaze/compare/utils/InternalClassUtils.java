package org.yamikaze.compare.utils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author qinluo
 * @version 1.0.0
 * @date 2021/2/4 1:43 上午
 */
public final class InternalClassUtils {

    private static final List<Class<?>> SIMPLE_CLASS = new ArrayList<>(8);

    static {
        SIMPLE_CLASS.add(int.class);
        SIMPLE_CLASS.add(double.class);
        SIMPLE_CLASS.add(float.class);
        SIMPLE_CLASS.add(byte.class);
        SIMPLE_CLASS.add(char.class);
        SIMPLE_CLASS.add(long.class);
        SIMPLE_CLASS.add(short.class);
        SIMPLE_CLASS.add(boolean.class);
        SIMPLE_CLASS.add(Integer.class);
        SIMPLE_CLASS.add(Byte.class);
        SIMPLE_CLASS.add(Short.class);
        SIMPLE_CLASS.add(Long.class);
        SIMPLE_CLASS.add(Float.class);
        SIMPLE_CLASS.add(Double.class);
        SIMPLE_CLASS.add(Character.class);
        SIMPLE_CLASS.add(Boolean.class);
        SIMPLE_CLASS.add(Object.class);
    }

    public static boolean isSimple(Class<?> clz) {
        return SIMPLE_CLASS.contains(clz);
    }
}
