package org.yamikaze.compare;

import org.yamikaze.compare.diff.Difference;

import java.util.ArrayList;
import java.util.List;

import static org.yamikaze.compare.utils.InternalCompUtils.appendNewLine;
import static org.yamikaze.compare.utils.InternalCompUtils.appendTab;

/**
 * @author qinluo
 * @version 1.0.0
 * @date 2020-08-18 14:57
 */
public class CompareResult {

    /**
     * Compare result.
     */
    private boolean success = false;

    private final List<String> skipFields = new ArrayList<>(16);

    /**
     * The compare fail items.
     */
    private final List<Difference> failItems = new ArrayList<>(16);

    public List<Difference> getFailItems() {
        return failItems;
    }

    public void addFailItem(Difference failItem) {
        failItems.add(failItem);
    }

    public boolean isSuccess() {
        return success || failItems.isEmpty();
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public List<String> getSkipFields() {
        return skipFields;
    }

    public void addSkipField(String field) {
        if (skipFields.contains(field)) {
            return;
        }

        skipFields.add(field);
    }

    @Override
    public String toString() {
        success = failItems.isEmpty();
        StringBuilder sb = new StringBuilder(512);
        sb.append("compare result is ").append(success);
        appendNewLine(sb);

        if (skipFields.size() > 0) {
            sb.append("skipFields are ");
            int index = 0;
            for (String skipField : skipFields) {
                appendNewLine(sb);
                appendTab(sb);
                sb.append(++index).append(" : ").append(skipField);
            }
            appendNewLine(sb);

        }

        if (failItems.size() > 0) {
            appendNewLine(sb);
            sb.append("compare errors are ");
            int index = 0;
            for (Difference skipField : failItems) {
                appendNewLine(sb);
                appendTab(sb);
                sb.append(++index).append("、").append(skipField);
            }
        }


        return sb.toString();
    }
}
