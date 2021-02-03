package org.yamikaze.compare;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author qinluo
 * @version 1.0.0
 * @since 2019-05-27 17:52
 */
public class CompareFactory {

    private static final Map<Class, Compare> COMPARE_MAP = new HashMap<>(32);

    private static final Map<NamedType, AbstractNamedTypeCompare> NAMED_TYPE_COMPARE = new HashMap<>(32);

    static {
        COMPARE_MAP.put(String.class, new StringCompare());
        COMPARE_MAP.put(Boolean.class, new BooleanCompare());
        COMPARE_MAP.put(List.class, new ListCompare());
        COMPARE_MAP.put(Object.class, new ObjectCompare());
        COMPARE_MAP.put(Map.class, new MapCompare());
        COMPARE_MAP.put(null, new NoOpCompare());
        COMPARE_MAP.put(Set.class, new SetCompare());
        COMPARE_MAP.put(Date.class, new DateCompare());

        NAMED_TYPE_COMPARE.put(new NamedType("attribute", String.class),
                new AttributesNamedTypeCompare("attribute", String.class));


        NAMED_TYPE_COMPARE.put(new NamedType("attributes", String.class),
                new AttributesNamedTypeCompare("attributes", String.class));


        NAMED_TYPE_COMPARE.put(new NamedType("tag", String.class),
                new TagNamedTypeCompare("tag", String.class));


        NAMED_TYPE_COMPARE.put(new NamedType("tags", String.class),
                new TagNamedTypeCompare("tags", String.class));
    }

    public static void addCompare(NamedType namedType, AbstractNamedTypeCompare compare) {
        if (namedType == null) {
            throw new IllegalArgumentException("register namedType must not be null!");
        }

        if (compare == null) {
            throw new IllegalArgumentException("register compare must not be null!");
        }

        if (!compare.isMatched(namedType.getName(), namedType.getType())) {
            throw new IllegalArgumentException("register namedType must match compare!");
        }

        NAMED_TYPE_COMPARE.put(namedType, compare);
    }

    public static AbstractNamedTypeCompare getNamedTypeCompare(NamedType namedType) {
        if (namedType == null) {
            throw new IllegalArgumentException("register namedType must not be null!");
        }

        return NAMED_TYPE_COMPARE.get(namedType);
    }


    public static void addCompare(Class<?> clz, Compare compare) {
        if (clz == null) {
            throw new IllegalArgumentException("register class must not be null!");
        }

        if (compare instanceof AbstractCompare) {
            AbstractCompare abstractCompare = (AbstractCompare) compare;
            if (!abstractCompare.getType().getTypeName().equals(clz.getName())) {
                throw new IllegalArgumentException("register class must be same type with compare#getType!");
            }
        }

        COMPARE_MAP.put(clz, compare);
    }

    @SuppressWarnings("unchecked")
    public static <T> Compare<T> getCompare(Class<T> clz) {
        Compare<T> compare = COMPARE_MAP.get(clz);
        if (compare != null) {
            return compare;
        }

        Compare<T> bestMatch = COMPARE_MAP.get(Object.class);

        //兼容集合类比较，统一以集合类的父类进行比较，但是要排除Object
        for (Map.Entry<Class, Compare> entry : COMPARE_MAP.entrySet()) {
            if (entry.getKey() != null && entry.getKey().isAssignableFrom(clz) && entry.getKey() != Object.class) {
                bestMatch = entry.getValue();
            }

            //bestMatch 不必再找其他的Compare了
            if (clz == entry.getKey()) {
                break;
            }
        }

        return bestMatch;
    }


}
