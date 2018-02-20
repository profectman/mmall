package com.mmall.common;

import com.google.common.collect.Sets;

import java.util.Set;

/**
 * Created by zhengquan on 2017/5/28.
 */
public class Const {

    public static final String CURRENT_USER = "currentUser";

    public static final String EMAIL = "email";

    public static final String USERNAME = "username";


    public interface ProductListOrderBy {
        Set<String> PRICE_ASC_DESC = Sets.newHashSet("price_asc", "price_desc");
    }

    public interface Cart {
        int CHECKED = 1;
        int UN_CHECKED = 0;

        String LIMIT_NUM_SUCCESS = "limit_num_success";
        String LIMIT_NUM_FAIL = "limit_num_success";
    }

    //tips:利用接口对常量进行分类
    public interface Role {
        int ROLE_CUSTOMER = 0; //普通用户
        int ROLE_ADMIN = 1;  //管理员
    }

    public enum ProductStatusEnum {
        ON_SALE("在线", 1);
        private String value;
        private int code;

        ProductStatusEnum(String value, int code) {
            this.value = value;
            this.code = code;
        }

        public String getValue() {
            return value;
        }

        public int getCode() {
            return code;
        }
    }

    public enum OrderStatusEnum {
        CANCELED("已取消", 0),
        NO_PAY("未支付", 10),
        PAYED("已支付", 20),
        SHIPPED("已发货", 40),
        ORDER_SUCCESS("订单完成", 50),
        ORDER_CLOSE("订单关闭", 60);

        private String value;
        private int code;

        OrderStatusEnum(String value, int code) {
            this.value = value;
            this.code = code;
        }


        public int getCode() {
            return code;
        }

        public void setCode(int code) {
            this.code = code;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }
        public static  OrderStatusEnum codeOf(int code){
            for (OrderStatusEnum orderStatusEnum:values()){
                if(orderStatusEnum.getCode() ==code){
                    return orderStatusEnum;
                }
            }
            throw  new RuntimeException("没找到对应的枚举");
        }

    }

    public interface AlipayCallback {
        String TRADE_STATUS_WAIT_BUYER_PAY = "WAIT_BUYER_PAY";
        String TRADE_STATUS_TRADE_SUCCESS = "TRADE_SUCCESS";
        String RESPONSE_FAILED = "failed";
        String RESPONSE_SUCCESS = "success";
    }

    public enum PayPlatformEnum {
        ALIPAY("支付宝", 1);
        private String value;
        private int code;

        PayPlatformEnum(String value, int code) {
            this.value = value;
            this.code = code;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }

        public int getCode() {
            return code;
        }

        public void setCode(int code) {
            this.code = code;
        }
    }

    public enum PaymentTypeEnum{
        ON_ONLINE_PAY("在线支付",1);
        private String value;
        private int code;

        PaymentTypeEnum(String value, int code) {
            this.value = value;
            this.code = code;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }

        public int getCode() {
            return code;
        }

        public void setCode(int code) {
            this.code = code;
        }

        public static  PaymentTypeEnum codeOf(int code){
            for (PaymentTypeEnum paymentTypeEnum:values()){
                if(paymentTypeEnum.getCode() ==code){
                    return paymentTypeEnum;
                }
            }
            throw  new RuntimeException("没找到对应的枚举");
        }
    }

}
