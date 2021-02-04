package org.yamikaze.compare.utils;

import org.yamikaze.compare.TypeAssigner;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * @author qinluo
 * @version 1.0.0
 * @date 2021/2/4 1:43 上午
 */
public final class InternalClassUtils {

    private static final List<Class<?>> SIMPLE_CLASS = new ArrayList<>(8);

    private static final TypeAssigner MAP_ASSIGNER = new TypeAssigner(true, Map.class);
    private static final TypeAssigner SET_ASSIGNER = new TypeAssigner(true, Set.class);
    private static final TypeAssigner LIST_ASSIGNER = new TypeAssigner(true, List.class);

    /**
     * Field Caches.
     */
    private static final Map<Class<?>, List<Field>> FIELD_CACHE = new ConcurrentHashMap<>(128);

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

    public static boolean isBoolean(Class<?> clz) {
        return clz == boolean.class || clz == Boolean.class;
    }

    public static boolean isCollection(Class<?> clz) {
        return LIST_ASSIGNER.checkCast(clz) || SET_ASSIGNER.checkCast(clz) || MAP_ASSIGNER.checkCast(clz);
    }

    public static boolean isSameCollection(Class<?> source, Class<?> target) {
        return (LIST_ASSIGNER.checkCast(source) && LIST_ASSIGNER.checkCast(target))
                || (SET_ASSIGNER.checkCast(source) && SET_ASSIGNER.checkCast(target))
                || (MAP_ASSIGNER.checkCast(source) && MAP_ASSIGNER.checkCast(target));
    }

    public static Class<?> lookupHighestType(Class<?> clz) {
        if (!isCollection(clz)) {
            return clz;
        }

        if (LIST_ASSIGNER.checkCast(clz)) {
            return List.class;
        }

        if (MAP_ASSIGNER.checkCast(clz)) {
            return Map.class;
        }

        if (SET_ASSIGNER.checkCast(clz)) {
            return Set.class;
        }

        return clz;
    }

    public static List<Field> getAllFields(Class<?> clz) {
        List<Field> fields = FIELD_CACHE.get(clz);
        if (fields != null) {
            return fields;
        }

        List<Field> allFields = new ArrayList<>(128);
        Class<?> waitProcessClz = clz;
        while (waitProcessClz != Object.class) {
            addElements(allFields, waitProcessClz.getDeclaredFields());
            waitProcessClz = waitProcessClz.getSuperclass();
        }

        // filter static fields
        allFields = allFields.stream().filter(p -> !Modifier.isStatic(p.getModifiers()) && !p.isSynthetic())
                .collect(Collectors.toList());
        FIELD_CACHE.put(clz, allFields);

        return allFields;
    }

    private static <T> void addElements(List<T> list, T[] fields) {
        if (list == null || fields == null) {
            return;
        }
        Collections.addAll(list, fields);
    }
}
