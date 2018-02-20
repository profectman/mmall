package com.mmall.controller.backend;

import com.github.pagehelper.PageInfo;
import com.mmall.common.Const;
import com.mmall.common.ResponseCode;
import com.mmall.common.ServerResponse;
import com.mmall.pojo.User;
import com.mmall.service.IOrderService;
import com.mmall.vo.OrderVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

/**
 * Created by zhengquan on 2017/6/11.
 */
@Controller
@RequestMapping("/manage/order")
public class OrderManageController {

    @Autowired
    private IOrderService iOrderService;

    @RequestMapping("list.do")
    @ResponseBody
    public ServerResponse<PageInfo> list(HttpSession session, @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
                                         @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize) {
        User currentUser = (User) session.getAttribute(Const.CURRENT_USER);
        if (currentUser == null) {
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(), ResponseCode.NEED_LOGIN.getDesc());
        }
        if (currentUser.getRole() == Const.Role.ROLE_ADMIN) {
            //获取后台订单列表的分页信息
            return iOrderService.manageList(pageNum, pageSize);
        }
        return ServerResponse.createByErrorMessage("用户无权限");
    }

    @RequestMapping("detail.do")
    @ResponseBody
    public ServerResponse<OrderVo> detail(HttpSession session, Long orderNo){
        User currentUser = (User) session.getAttribute(Const.CURRENT_USER);
        if (currentUser == null) {
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(), ResponseCode.NEED_LOGIN.getDesc());
        }
        if (currentUser.getRole() == Const.Role.ROLE_ADMIN) {
            //获取后台订单明细的分页信息
            return iOrderService.manageDetail(orderNo);
        }
        return ServerResponse.createByErrorMessage("用户无权限");
    }

    @RequestMapping("search.do")
    @ResponseBody
    public ServerResponse<PageInfo> search(HttpSession session, Long orderNo,@RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
                                          @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize){
        User currentUser = (User) session.getAttribute(Const.CURRENT_USER);
        if (currentUser == null) {
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(), ResponseCode.NEED_LOGIN.getDesc());
        }
        if (currentUser.getRole() == Const.Role.ROLE_ADMIN) {
            //获取后台搜索订单
            return iOrderService.manageSearch(orderNo,pageNum,pageSize);
        }
        return ServerResponse.createByErrorMessage("用户无权限");
    }

    @RequestMapping("send_goods.do")
    @ResponseBody
    public ServerResponse<String> sendGoods(HttpSession session, Long orderNo){
        User currentUser = (User) session.getAttribute(Const.CURRENT_USER);
        if (currentUser == null) {
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(), ResponseCode.NEED_LOGIN.getDesc());
        }
        if (currentUser.getRole() == Const.Role.ROLE_ADMIN) {
            //发货
            return iOrderService.manageSendGoods(orderNo);
        }
        return ServerResponse.createByErrorMessage("用户无权限");
    }
}
