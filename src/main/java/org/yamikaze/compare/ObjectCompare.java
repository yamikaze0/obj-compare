package org.yamikaze.compare;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author qinluo
 * @version 1.0.0
 * @since 2019-05-27 17:48
 * cs:off
 */
public class ObjectCompare extends AbstractCompare<Object>{

    private static List<Class> classes = new ArrayList<>(8);

    static {
        classes.add(int.class);
        classes.add(double.class);
        classes.add(float.class);
        classes.add(byte.class);
        classes.add(char.class);
        classes.add(long.class);
        classes.add(short.class);
        classes.add(boolean.class);
        classes.add(Integer.class);
        classes.add(Byte.class);
        classes.add(Short.class);
        classes.add(Long.class);
        classes.add(Float.class);
        classes.add(Double.class);
        classes.add(Character.class);
        classes.add(Boolean.class);
        classes.add(Object.class);
    }

    @Override
    public void compareObj(Object expectObject, Object compareObject, CompareContext<Object> context) {
        Object noNullObject = orElse(expectObject, compareObject);
        Class<?> noNullObjectClass = noNullObject.getClass();

        if (classes.contains(noNullObjectClass) && !isBoolean(noNullObjectClass)) {
            boolean result = Objects.equals(expectObject, compareObject);
            if (!result) {
                context.addFailItem(new NotEqualsFailItem(context.generatePrefix(), expectObject, compareObject));
            }
            return;
        }

        boolean hasOneNull = expectObject == null || compareObject == null;
        if (!hasOneNull) {
            boolean objIsColl = isCollection(expectObject.getClass());
            boolean compareIsColl = isCollection(compareObject.getClass());
            //有一个是集合，但是另外一个不是的情况
            if (objIsColl != compareIsColl) {
                context.addFailItem(new TypeCompareFailItem(context.generatePrefix(), expectObject, compareObject));
                return;
            }

            //2个都不是集合的情况
            if (!objIsColl) {
                if (expectObject.getClass() != compareObject.getClass()) {
                    context.addFailItem(new TypeCompareFailItem(context.generatePrefix(), expectObject, compareObject));
                    return;
                }
            } else if (!noNullObjectClass.isArray() && !isSameCollection(expectObject.getClass(), compareObject.getClass())){
                //不属于同一种集合的情况
                context.addFailItem(new TypeCompareFailItem(context.generatePrefix(), expectObject, compareObject));
                return;
            }
        }

        CompareContext newContext = new CompareContext();
        newContext.setExpectObject(expectObject);
        newContext.setCompareObject(compareObject);
        newContext.setStrictMode(context.isStrictMode());
        newContext.setComparePath(context.getComparePath());
        newContext.setResult(context.getResult());
        newContext.setIgnoreFields(context.getIgnoreFields());

        if (isBoolean(noNullObjectClass)) {
            newContext.setType(Boolean.class);
            CompareFactory.getCompare(Boolean.class).compareObj(newContext);
            return;
        }

        if (noNullObjectClass == String.class) {
            newContext.setType(String.class);
            CompareFactory.getCompare(String.class).compareObj(newContext);
            return;
        }

        if (!isCollection(noNullObjectClass)) {
            try {
                //如果相应对象自己定义了equals方法，则使用equals方法进行比较
                Method equalsMethod = noNullObjectClass.getMethod("equals", Object.class);
                if (equalsMethod.getDeclaringClass() != Object.class) {
                    boolean result = Objects.equals(expectObject, compareObject);
                    if (!result) {
                        context.addFailItem(new NotEqualsFailItem(context.generatePrefix(), objValue(expectObject), objValue(compareObject)));
                    }
                    return;
                }
            } catch (Exception e) {
                //
                context.addFailItem(new CompareErrorItem(e));
                return;
            }

            if (hasOneNull) {
                context.addFailItem(new HasNullFailItem(context.generatePrefix(), expectObject, compareObject));
                return;
            }

            //可以考虑加入缓存
            List<Field> fields = getAllDeclaredFields(noNullObjectClass);
            if (fields.size() == 0) {
                return;
            }

            for (Field field : fields) {
                boolean isAccessible = field.isAccessible();
                if (!isAccessible) {
                    field.setAccessible(true);
                }

                if (isIgnoreFields(context, field)) {
                    context.getResult().addSkipField(prefix(context.getComparePath()) + field.getName());
                    continue;
                }

                Object filedValue = null;
                Object compareValue = null;

                Class fieldType = field.getType();

                try {
                    filedValue = field.get(expectObject);
                    compareValue = field.get(compareObject);
                } catch (Exception e) {
                    //do nothing
                }

                CompareContext<Object> context1 = context.clone(filedValue, compareValue);
                context1.setComparePath(prefix(context.getComparePath()) + field.getName());

                NamedType namedType = new NamedType(field.getName(), field.getType());
                AbstractNamedTypeCompare compare = CompareFactory.getNamedTypeCompare(namedType);
                if (compare != null) {
                    compare.compareObj(context1);
                    continue;
                }

                CompareFactory.getCompare(fieldType).compareObj(context1);
                field.setAccessible(isAccessible);
            }

            return;
        }

        //数组先暂时跳过比较
        if (noNullObjectClass.isArray()) {
            Class<?> componentType = getArrayType(noNullObjectClass);
            int dimension = getArrayDimension(noNullObjectClass, 0);

            //根据数组维度和数组基本类型找到对比类
            return;
        }

        newContext.setType(getCollectionTopClz(noNullObjectClass));
        CompareFactory.getCompare(getCollectionTopClz(noNullObjectClass)).compareObj(newContext);

    }

    private Object orElse(Object obj1, Object obj2) {
        return obj1 != null ? obj1 : obj2;
    }

    private boolean isBoolean(Class clz) {
        return clz == boolean.class || clz == Boolean.class;
    }

    private boolean isCollection(Class clz) {
        return clz.isArray() || List.class.isAssignableFrom(clz) || Map.class.isAssignableFrom(clz)
                || Set.class.isAssignableFrom(clz);
    }

    private boolean isSameCollection(Class objClz, Class compareClz) {
        //不考虑数组的情况
        return (List.class.isAssignableFrom(objClz) && List.class.isAssignableFrom(compareClz))
                    || (Map.class.isAssignableFrom(objClz) && Map.class.isAssignableFrom(compareClz))
                    || (Set.class.isAssignableFrom(objClz) && Set.class.isAssignableFrom(compareClz));
    }

    private Class getCollectionTopClz(Class clz) {
        if (isCollection(clz) && !clz.isArray()) {
            if (List.class.isAssignableFrom(clz)) {
                return List.class;
            }

            if (Map.class.isAssignableFrom(clz)) {
                return Map.class;
            }

            return Set.class;
        }

        return clz;
    }

    private String objValue(Object value) {
        if (value == null) {
            return null;
        }

        if (classes.contains(value.getClass())) {
            return String.valueOf(value);
        }

        return value.toString();

        //return value.getClass().getName() + "@" + value.hashCode();
    }

    private int getArrayDimension(Class clz, int index) {
        if (clz.isArray()) {
            return getArrayDimension(clz.getComponentType(), index + 1);
        }

        return index;
    }

    private Class getArrayType(Class clz) {
        if (clz.isArray()) {
            return getArrayType(clz.getComponentType());
        }

        return clz;
    }

    private List<Field> getAllDeclaredFields(Class<?> clz) {
        List<Field> allFields = new ArrayList<>(128);
        Class<?> waitProcessClz = clz;
        while (waitProcessClz != Object.class) {
            addElements(allFields, waitProcessClz.getDeclaredFields());
            waitProcessClz = waitProcessClz.getSuperclass();
        }

        // filter static fields
        return allFields.stream().filter(p -> !Modifier.isStatic(p.getModifiers())).collect(Collectors.toList());
    }

    private <T> void addElements(List<T> list, T[] fields) {
        if (list == null || fields == null) {
            return;
        }

        Collections.addAll(list, fields);
    }

    private boolean isIgnoreFields(CompareContext<Object> context, Field field) {
        List<IgnoreField> ignoreFields = context.getAllIgnoreFields();
        if (ignoreFields == null || ignoreFields.isEmpty()) {
            return false;
        }

        NamedType namedType = new NamedType(field.getName(), field.getType());
        for (IgnoreField ignoreField : ignoreFields) {
            if (ignoreField.ignored(context, namedType)) {
                return true;
            }
        }

        return false;
    }

}
