package org.yamikaze.compare;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * @author qinluo
 * @version 1.0.0
 * @date 2021/2/3 11:43:28
 */
public class CompareTest {

    @Test
    public void testCompareObj() {
        User expect = new User();
        expect.setId("1");
        expect.setPassword("111212");
        expect.setUsername("278821");
        expect.setSex("male");

        Parent parent = new Parent();
        parent.setType("mother");
        parent.setName("tom");
        List<Integer> f1 = new ArrayList<>();
        f1.add(1);
        f1.add(2);
        f1.add(3);
        parent.setFeatures(f1);

        expect.setParent(parent);

        //////////////////////////////////////////////

        User target = new User();
        target.setId("2");
        target.setPassword("1112112");
        target.setUsername("278821");
        target.setSex("male");

        parent = new Parent();
        parent.setType("father");
        parent.setName("tom");
        List<Integer> f2 = new ArrayList<>();
        f2.add(1);
        f2.add(3);
        f2.add(5);
        parent.setFeatures(f2);


        target.setParent(parent);

        CompareResult result = ObjectEqualsUtils.compare(expect, target);
        System.out.println(result);
    }

    @Test
    public void testRecycle() {
        RecycleUser user = new RecycleUser();
        RecycleUser _user = new RecycleUser();

        user.setName("qinluo");
        user.setPassword("haolo2");
        user.setUser(_user);


        _user.setName("qinluo");
        _user.setPassword("haolo1");
        _user.setUser(user);

        CompareResult result = ObjectEqualsUtils.compare(user, _user);
        System.out.println(result);
    }

    @Test
    public void testCast() {
        Boolean a = null;

        Integer b = Integer.class.cast(a);

    }

    @Test
    public void testRecycle02() {
        List<Object> expect = new ArrayList<>();
        List<Object> actual = new ArrayList<>();

        expect.add(1);
        expect.add(3);
        expect.add(actual);

        actual.add(1);
        actual.add(2);
        actual.add(expect);


        CompareResult result = ObjectEqualsUtils.compare(expect, actual);
        System.out.println(result);
    }

}
