package org.yamikaze.compare;

import java.util.List;

/**
 * @author qinluo
 * @version 1.0.0
 * @date 2020-05-15 17:57
 */
public class UserV2 {


    private UserV2 parentUser;

    private String username;

    private String password;

    private List<Integer> values;

    private String attribute;

    private String tags;

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public void setAttribute(String attribute) {
        this.attribute = attribute;
    }

    public List<Integer> getValues() {
        return values;
    }

    public void setValues(List<Integer> values) {
        this.values = values;
    }

    public UserV2 getParentUser() {
        return parentUser;
    }

    public void setParentUser(UserV2 parentUser) {
        this.parentUser = parentUser;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
