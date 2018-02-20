package com.mmall.controller.backend;

import com.mmall.common.Const;
import com.mmall.common.ServerResponse;
import com.mmall.pojo.User;
import com.mmall.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

/**
 * Created by zhengquan on 2017/5/28.
 */
@Controller
@RequestMapping("/manage/user")
public class UserManagerController {

    @Autowired
    private IUserService iUserService;


    /**
     * 后台管理员登陆
     * @param username
     * @param password
     * @param session
     * @return
     */
    @RequestMapping(value = "login.do",method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse<User> login(String username, String password, HttpSession session){
           ServerResponse<User> serverResponse = iUserService.login(username,password);
           if(serverResponse.isSuccess()){
               User user = serverResponse.getData();
               session.setAttribute(Const.CURRENT_USER,user);
               if(user.getRole() != Const.Role.ROLE_ADMIN){
                   return ServerResponse.createByErrorMessage("不是管理员无法登陆");
               }
           }

           return serverResponse;

    }
}
