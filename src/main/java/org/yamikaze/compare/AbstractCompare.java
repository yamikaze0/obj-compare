package org.yamikaze.compare;

import org.yamikaze.compare.utils.InternalCompUtils;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Collection;
import java.util.Map;

/**
 * @author qinluo
 * @version 1.0.0
 * @since 2019-05-27 17:06
 */
public abstract class AbstractCompare<T> implements Compare<T> {

    /**
     * Actually Generic Type.
     */
    private final Type type;

    public AbstractCompare() {
        Type genericSuperclass = this.getClass().getGenericSuperclass();
        if (genericSuperclass instanceof ParameterizedType) {
            ParameterizedType parameterizedType = (ParameterizedType) genericSuperclass;
            type = parameterizedType.getActualTypeArguments()[0];
        } else {
            type = Object.class;
        }
    }

    public Type getType() {
        return type;
    }

    @Override
    public void compareObj(CompareContext<T> context) {
        T expectObject = context.getType().cast(context.getExpectObject());
        T compareObject = context.getType().cast(context.getCompareObject());

        //2个对象都为空的时候
        if (expectObject == null && compareObject == null) {
            return;
        }

        compareObj(expectObject, compareObject, context);
    }

    /**
     * 比较2个对象是否相等
     * @param expectObject    比较基准对象1
     * @param compareObject   待比较对象2
     * @param context         比较上下文，承载比较结果
     */
    public abstract void compareObj(T expectObject, T compareObject, CompareContext<T> context);

    String nullString(boolean isNull) {
        return InternalCompUtils.nullString(isNull);
    }

    boolean isEmpty(Map map) {
        return InternalCompUtils.isEmpty(map);
    }

    boolean isEmpty(Collection map) {
        return InternalCompUtils.isEmpty(map);
    }

    String prefix(String str) {
        if (str == null || str.trim().length() == 0) {
            return "";
        }

        return str + ".";
    }

    boolean isBlank(String params) {
        return InternalCompUtils.isBlank(params);
    }
}
