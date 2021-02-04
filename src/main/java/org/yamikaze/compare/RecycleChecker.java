package org.yamikaze.compare;

import java.util.ArrayList;
import java.util.List;

/**
 * Object recycle checker.
 *
 * @author qinluo
 * @version 1.0.0
 * @date 2021/2/4 12:11:52
 */
public class RecycleChecker {

    private final List<RecycleCache> caches = new ArrayList<>(36);


    /**
     * Add recycle cache for recycle check.
     *
     * @param expect expect
     * @param actual actual
     */
    public void addRecycle(Object expect, Object actual) {
        caches.add(new RecycleCache(expect, actual));
    }

    /**
     * Check expect and actual object is recycle.
     *
     * @param expect expect
     * @param actual actual
     * @return       recycle check result;
     */
    public boolean isRecycle(Object expect, Object actual) {
        for (RecycleCache cache : caches) {
            if (cache.isMatch(expect, actual)) {
                return true;
            }
        }

        return false;
    }


    private static class RecycleCache {

        private final Object expect;

        private final Object actual;

        public RecycleCache(Object expect, Object actual) {
            this.expect = expect;
            this.actual = actual;
        }

        public boolean isMatch(Object expect, Object actual) {
            return (this.expect == expect && this.actual == actual)
                    || (this.expect == actual && this.actual == expect);
        }
    }

}
