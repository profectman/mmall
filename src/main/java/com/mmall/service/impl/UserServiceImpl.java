package com.mmall.service.impl;

import com.mmall.common.Const;
import com.mmall.common.ServerResponse;
import com.mmall.common.TokenCache;
import com.mmall.dao.UserMapper;
import com.mmall.pojo.User;
import com.mmall.service.IUserService;
import com.mmall.util.MD5Util;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

/**
 * Created by zhengquan on 2017/5/26.
 */
@Service("iUserService")
public class UserServiceImpl implements IUserService {

    @Autowired
    private UserMapper userMapper;

    public ServerResponse<User> login(String username, String password) {
        int resultCount = userMapper.checkUserName(username);
        if (resultCount == 0){
            return ServerResponse.createByErrorMessage("用户名不存在");
        }

        String md5Password = MD5Util.MD5EncodeUtf8(password);

        User user = userMapper.selectLogin(username,md5Password);
        if(user == null){
            return ServerResponse.createByErrorMessage("密码错误");
        }
        user.setPassword(StringUtils.EMPTY);

        return ServerResponse.createBySuccess("登录成功",user);
    }

    public ServerResponse<String> register(User user){
        ServerResponse<String>vaildResponse = this.checkValid(user.getUsername(),Const.USERNAME);
        if(!vaildResponse.isSuccess()){
            return vaildResponse;
        }
        vaildResponse = this.checkValid(user.getEmail(),Const.EMAIL);
        if (!vaildResponse.isSuccess()){
            return vaildResponse;
        }

        user.setRole(Const.Role.ROLE_CUSTOMER);

        //md5加密
        user.setPassword(MD5Util.MD5EncodeUtf8(user.getPassword()));

        int resultCount = userMapper.insert(user);
        if (resultCount == 0){
            return ServerResponse.createByErrorMessage("注册失败");
        }
        return ServerResponse.createBySuccessMessage("注册成功");

    }

    public ServerResponse<String> checkValid(String str,String type){
        if(StringUtils.isNotBlank(type)){
            //开始参数校验
            if (Const.USERNAME.equals(type)){
                int resultCount = userMapper.checkUserName(str);
                if (resultCount > 0){
                    return ServerResponse.createByErrorMessage("用户名已存在");
                }
            }else if (Const.EMAIL.equals(type)){
                int resultCount = userMapper.checkEmail(str);
                if (resultCount > 0){
                    return ServerResponse.createByErrorMessage("email已存在");
                }
            }else{
                return ServerResponse.createByErrorMessage("参数错误");
            }
        }else{
            return ServerResponse.createByErrorMessage("参数错误");
        }
        return ServerResponse.createBySuccessMessage("校验成功");

    }

    public ServerResponse<String> selectQuestion(String username){

        ServerResponse vaildResponse = this.checkValid(username,Const.USERNAME);
        if(vaildResponse.isSuccess()){
            return ServerResponse.createByErrorMessage("用户不存在");
        }

        String question = userMapper.selectQuestion(username);
        if (StringUtils.isNotBlank(question)){
            return ServerResponse.createBySuccess(question);
        }

        return ServerResponse.createByErrorMessage("找回密码的问题为空");

    }

    public ServerResponse<String> checkAnswer(String username,String quesion,String answer){

        int resultCount = userMapper.checkAnswer(username, quesion, answer);
        if(resultCount > 0){
            String forgetToken = UUID.randomUUID().toString();
            TokenCache.setKey(TokenCache.TOKEN_PREFIX+username,forgetToken);
            return ServerResponse.createBySuccess(forgetToken);
        }
        return ServerResponse.createByErrorMessage("问题的答案错误");
    }

    public ServerResponse<String> forgetResetPassword(String username,String newPassword,String forgetToken){
        if (StringUtils.isBlank(forgetToken)){
            return ServerResponse.createByErrorMessage("参数错误，Token需要传递");
        }
        ServerResponse<String>vaildResponse = this.checkValid(username,Const.USERNAME);
        if(vaildResponse.isSuccess()){
            return vaildResponse;
        }

        String token = TokenCache.getKey(TokenCache.TOKEN_PREFIX+username);

        if (StringUtils.isBlank(token)){
            return ServerResponse.createByErrorMessage("Token无效或者过期");
        }

        if (StringUtils.equals(token,forgetToken)){
            String md5Password = MD5Util.MD5EncodeUtf8(newPassword);
            int rowCount = userMapper.updatePasswordByUserName(username,md5Password);

            if (rowCount > 0){
                return ServerResponse.createBySuccessMessage("修改密码成功");
            }
        }else{
            return ServerResponse.createByErrorMessage("Token错误，请重新获取重置密码的Token");
        }

        return ServerResponse.createByErrorMessage("修改密码失败");
    }

    public ServerResponse<String> resetPassword(String oldPassword,String newPassword,User user){
        //防止横向越权，要校验一下这个用户的旧密码。
        int resultCount = userMapper.checkPassword(user.getId(),MD5Util.MD5EncodeUtf8(oldPassword));
        if (resultCount == 0){
            return ServerResponse.createByErrorMessage("旧密码错误");
        }
        user.setPassword(MD5Util.MD5EncodeUtf8(newPassword));
        int updateCount = userMapper.updateByPrimaryKeySelective(user);
        if(updateCount > 0){
            return ServerResponse.createBySuccessMessage("密码更新成功");
        }
        return ServerResponse.createByErrorMessage("密码更新失败");
    }


    public ServerResponse<User> updateInformation(User user){
        //username不能更新
        //email要进行校验，校验新的email是不是已经存在，存在则不能更新为当前用户的email
        int resultCount = userMapper.checkEmailByUserId(user.getEmail(),user.getId());
        if (resultCount > 0){
            return ServerResponse.createByErrorMessage("Email已被占用");
        }

        User updateUser = new User();
        updateUser.setId(user.getId());
        updateUser.setUsername(user.getUsername());
        updateUser.setEmail(user.getEmail());
        updateUser.setPhone(user.getPhone());
        updateUser.setQuestion(user.getQuestion());
        updateUser.setAnswer(user.getAnswer());

        int updateCount = userMapper.updateByPrimaryKeySelective(updateUser);
        if (updateCount > 0){
            return ServerResponse.createBySuccess("更新个人信息成功",updateUser);
        }

        return ServerResponse.createByErrorMessage("更新个人信息失败");
    }


    public ServerResponse<User> getInformation(Integer id){
        User user = userMapper.selectByPrimaryKey(id);
        if(user == null){
            return ServerResponse.createByErrorMessage("找不到当前的用户信息");
        }
        user.setPassword(StringUtils.EMPTY);
        return ServerResponse.createBySuccess(user);
    }

}
