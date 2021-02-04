package org.yamikaze.compare;

import org.yamikaze.compare.diff.DifferenceError;
import org.yamikaze.compare.diff.NotEqualsDifference;
import org.yamikaze.compare.diff.NullOfOneObject;
import org.yamikaze.compare.diff.TypeCompareDifference;
import org.yamikaze.compare.utils.InternalClassUtils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Objects;

/**
 * @author qinluo
 * @version 1.0.0
 * @since 2019-05-27 17:48
 * cs:off
 */
public class ObjectCompare extends AbstractCompare<Object> {

    @Override
    @SuppressWarnings({"rawtypes", "unchecked"})
    public void compareObj(Object expect, Object actual, CompareContext<Object> context) {
        Object nonnull = selectNonnull(expect, actual);
        Class<?> nonnullType = nonnull.getClass();
        boolean isBoolean = InternalClassUtils.isBoolean(nonnullType);

        if (InternalClassUtils.isSimple(nonnullType) && !isBoolean) {
            boolean result = Objects.equals(expect, actual);
            if (!result) {
                context.addDiff(new NotEqualsDifference(context.getPath(), expect, actual));
            }
            return;
        }

        boolean hasOneNull = (expect == null || actual == null);
        if (!hasOneNull) {
            boolean expectIsColl = InternalClassUtils.isCollection(expect.getClass());
            boolean actualIsColl = InternalClassUtils.isCollection(actual.getClass());
            //有一个是集合，但是另外一个不是的情况
            if (expectIsColl != actualIsColl) {
                context.addDiff(new TypeCompareDifference(context.getPath(), expect, actual));
                return;
            }

            //2个都不是集合的情况
            if (!expectIsColl) {
                if (expect.getClass() != actual.getClass()) {
                    context.addDiff(new TypeCompareDifference(context.getPath(), expect, actual));
                    return;
                }
            } else if (!InternalClassUtils.isSameCollection(expect.getClass(), actual.getClass())){
                //不属于同一种集合的情况
                context.addDiff(new TypeCompareDifference(context.getPath(), expect, actual));
                return;
            }
        }

        CompareContext newContext = context.clone(expect, actual);
        // decr depth.
        newContext.decr();

        if (isBoolean) {
            newContext.setType(Boolean.class);
            CompareFactory.getCompare(Boolean.class).compareObj(newContext);
            return;
        }

        if (nonnullType == String.class) {
            newContext.setType(String.class);
            CompareFactory.getCompare(String.class).compareObj(newContext);
            return;
        }

        if (!InternalClassUtils.isCollection(nonnullType)) {
            try {
                //如果相应对象自己定义了equals方法，则使用equals方法进行比较
                Method em = nonnullType.getMethod("equals", Object.class);
                if (em.getDeclaringClass() == nonnullType) {
                    boolean result = Objects.equals(expect, actual);
                    if (!result) {
                        context.addDiff(new NotEqualsDifference(context.getPath(), expect, actual));
                    }
                    return;
                }
            } catch (Exception e) {
                //
                context.addDiff(new DifferenceError(e));
                return;
            } catch (StackOverflowError error) {
                // avoid stack overflow in equals method.
                context.addDiff(new DifferenceError(new RuntimeException("can't use compare in equals method.")));
                return;
            }

            if (hasOneNull) {
                context.addDiff(new NullOfOneObject(context.getPath(), expect, actual));
                return;
            }

            List<Field> fields = InternalClassUtils.getAllFields(nonnullType);
            if (fields.size() == 0) {
                return;
            }

            if (checkRecycled(context, expect, actual)) {
                context.getResult().addCmpMsg("detect recycle reference in path " + context.getPath());
                return;
            }

            for (Field field : fields) {
                NamedType namedType = new NamedType(field.getName(), field.getType());
                if (ignored(context, namedType)) {
                    context.getResult().addSkipField(context.getNPath(field.getName()));
                    continue;
                }

                boolean isAccessible = field.isAccessible();
                if (!isAccessible) {
                    field.setAccessible(true);
                }

                Object ev = null;
                Object av = null;

                Class fieldType = field.getType();
                try {
                    ev = field.get(expect);
                    av = field.get(actual);
                } catch (Exception e) {
                    //do nothing
                } finally {
                    field.setAccessible(isAccessible);
                }
                // check equals in references.
                if (ev == av) {
                    continue;
                }

                if (checkRecycled(context, ev, av)) {
                    context.getResult().addCmpMsg("detect recycle reference in path " + context.getNPath(field.getName()));
                    continue;
                }

                CompareContext<Object> nc = context.clone(ev, av);
                nc.setPath(context.getNPath(field.getName()));

                AbstractNamedTypeCompare compare = CompareFactory.getNamedTypeCompare(namedType);
                if (compare != null) {
                    compare.compareObj(nc);
                    continue;
                }

                CompareFactory.getCompare(fieldType).compareObj(nc);
            }
            return;
        }

        //数组先暂时跳过比较
        if (nonnullType.isArray()) {
            Class<?> componentType = getPureType(nonnullType);
            int dimension = getDimension(nonnullType);

            //根据数组维度和数组基本类型找到对比类
            return;
        }

        if (checkRecycled(context, expect, actual)) {
            context.getResult().addCmpMsg("detect recycle reference in path " + context.getPath());
            return;
        }

        Class<?> highestType = InternalClassUtils.lookupHighestType(nonnullType);
        newContext.setType(highestType);
        CompareFactory.getCompare(highestType).compareObj(newContext);

    }

    private Object selectNonnull(Object expect, Object actual) {
        return expect == null ? actual : expect;
    }

    private int getDimension(Class<?> clz) {
        int index = 1;
        while (clz.isArray()) {
            clz = clz.getComponentType();
            index++;
        }

        return index;
    }

    private Class<?> getPureType(Class<?> clz) {
        while (clz.isArray()) {
            clz = clz.getComponentType();
        }

        return clz;
    }
}
