package org.yamikaze.compare;

import java.util.ArrayList;
import java.util.List;

import static org.yamikaze.compare.CompareUtils.appendNewLine;
import static org.yamikaze.compare.CompareUtils.appendTab;

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

    private String successMsg;

    private List<String> skipFields = new ArrayList<>(16);

    /**
     * The compare fail items.
     */
    private List<CompareFailItem> failItems = new ArrayList<>(16);

    public List<CompareFailItem> getFailItems() {
        return failItems;
    }

    public void addFailItem(CompareFailItem failItem) {
        failItems.add(failItem);
    }

    public String getSuccessMsg() {
        return successMsg;
    }

    public void setSuccessMsg(String successMsg) {
        this.successMsg = successMsg;
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

        if (successMsg != null) {
            sb.append("successMsg is ").append(successMsg);
        }

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
            for (CompareFailItem skipField : failItems) {
                appendNewLine(sb);
                appendTab(sb);
                sb.append(++index).append("、").append(skipField);
            }
        }


        return sb.toString();
    }
}
