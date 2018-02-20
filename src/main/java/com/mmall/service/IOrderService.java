package com.mmall.service;

import com.github.pagehelper.PageInfo;
import com.mmall.common.ServerResponse;
import com.mmall.vo.OrderVo;

import java.util.Map;

/**
 * Created by zhengquan on 2017/6/9.
 */
public interface IOrderService {

    ServerResponse pay(Integer userId, String path, Long orderNo);

    ServerResponse alipayCallback(Map<String, String> params);

    ServerResponse<Boolean> queryOrderPayStatus(Integer userId, Long orderNo);

    ServerResponse createOrder(Integer userId, Integer shippingId);

    ServerResponse cancel(Integer userId, Long orderNo);

    ServerResponse getOrderCartProduct(Integer userId);

    ServerResponse<OrderVo> getOrderDetail(Integer userId, Long orderNo);

    ServerResponse<PageInfo> getOrderList(Integer userId, Integer pageNum, Integer pageSize);

    ServerResponse<PageInfo> manageList(Integer pageNum, Integer pageSize);

    ServerResponse<OrderVo> manageDetail(Long orderNo);

    ServerResponse<PageInfo> manageSearch(Long orderNo,Integer pageNum,Integer pageSize);

    ServerResponse<String> manageSendGoods(Long orderNo);
}
