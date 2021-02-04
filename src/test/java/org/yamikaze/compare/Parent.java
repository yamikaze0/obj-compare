package org.yamikaze.compare;

import java.util.List;

/**
 * @author qinluo
 * @version 1.0.0
 * @date 2021/2/3 11:47:34
 */
public class Parent {

    private String type;

    private String name;

    private List<Integer> features;

    public List<Integer> getFeatures() {
        return features;
    }

    public void setFeatures(List<Integer> features) {
        this.features = features;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
