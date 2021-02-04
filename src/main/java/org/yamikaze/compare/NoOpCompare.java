package org.yamikaze.compare;

/**
 * @author qinluo
 * @version 1.0.0
 * @since 2019-05-27 23:15
 */
public class NoOpCompare implements Compare<Object> {

    @Override
    public void compareObj(CompareContext<Object> context) {
        //do nothing
    }
}
