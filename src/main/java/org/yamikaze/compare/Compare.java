package org.yamikaze.compare;

/**
 * @author qinluo
 * @version 1.0.0
 * @since 2019-05-27 16:48
 */
public interface Compare<T> {

    /**
     * 比较2个对象是否相等
     * @param context 比较上下文，承载比较结果
     */
    void compareObj(CompareContext<T> context);
}
