package com.mmall.test;

import org.junit.Test;

import java.math.BigDecimal;

/**
 * Created by zhengquan on 2017/6/4.
 */
public class BigDecimalTest {


    //浮点价格丢失精度的解决
    @Test
    public void test1() {
        System.out.println(0.05 + 0.01);
        System.out.println(1.0 - 0.42);
        System.out.println(4.015 * 100);
        System.out.println(123.3 / 100);
    }

    @Test
    public void test2() {
        BigDecimal b1 = new BigDecimal(0.05);
        BigDecimal b2 = new BigDecimal(0.01);
        System.out.println(b1.add(b2));
    }

    //为了不丢失精度一定要用参数是String的BigDecimal构造器
    @Test
    public void test3() {
        BigDecimal b1 = new BigDecimal("0.05");
        BigDecimal b2 = new BigDecimal("0.01");
        System.out.println(b1.add(b2));
    }


}
