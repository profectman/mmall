package com.mmall.service;

import com.mmall.common.ServerResponse;
import com.mmall.pojo.User;

/**
 * Created by zhengquan on 2017/5/26.
 */
public interface IUserService {

    ServerResponse<User> login(String username, String password);

    ServerResponse<String> register(User user);

    ServerResponse<String> checkValid(String str,String type);

    ServerResponse<String> selectQuestion(String username);

    ServerResponse<String> checkAnswer(String username,String quesion,String answer);

    ServerResponse<String> forgetResetPassword(String username,String newPassword,String forgetToken);

    ServerResponse<String> resetPassword(String oldPassword,String newPassword,User user);

    ServerResponse<User> updateInformation(User user);

    ServerResponse<User> getInformation(Integer id);
}