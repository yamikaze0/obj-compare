package org.yamikaze.compare;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

/**
 * @author qinluo
 * @version 1.0.0
 * @since 2019-05-27 17:06
 */
public abstract class AbstractCompare<T> implements Compare<T> {

    /**
     * Actually Generic Type.
     */
    protected final Type type;

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
        // avoid stack overflow.
        if (context.getDepth() > CompareConfig.MAX_COMP_DEPTH) {
            return;
        }

        T expect = context.getType().cast(context.getExpect());
        T actual = context.getType().cast(context.getActual());

        //2个对象都为空的时候, 即使对象实际类型不一致 也认为相等
        if (expect == null && actual == null) {
            return;
        }

        compareObj(expect, actual, context);
    }

    /**
     * 比较2个对象是否相等
     * @param expect    比较基准对象1
     * @param actual    待比较对象2
     * @param context   比较上下文，承载比较结果
     */
    public abstract void compareObj(T expect, T actual, CompareContext<T> context);

    protected boolean ignored(CompareContext<?> context, NamedType type) {
        List<IgnoreField> ignoreFields = context.getAllIgnoreFields();
        if (ignoreFields == null || ignoreFields.isEmpty()) {
            return false;
        }

        for (IgnoreField ignoreField : ignoreFields) {
            if (ignoreField.ignored(context, type)) {
                return true;
            }
        }

        return false;
    }

    /**
     * Check current compare is recycled.
     *
     * @param context compare context.
     * @param expect  expect.
     * @param actual  actual
     * @return        is recycled.
     */
    protected boolean checkRecycled(CompareContext<?> context, Object expect, Object actual) {
        if (context.getRecycleChecker().isRecycle(expect, actual)) {
            return true;
        }

        context.getRecycleChecker().addRecycle(expect, actual);
        return false;
    }
}
