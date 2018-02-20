package com.mmall.controller.backend;

import com.mmall.common.Const;
import com.mmall.common.ResponseCode;
import com.mmall.common.ServerResponse;
import com.mmall.pojo.Category;
import com.mmall.pojo.User;
import com.mmall.service.ICategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * Created by zhengquan on 2017/5/29.
 */
@Controller
@RequestMapping("/manage/category")
public class CategoryManagerController {

    @Autowired
    private ICategoryService iCategoryService;

    /**
     * 新增品类
     * @param session
     * @param categoryName
     * @param parentId
     * @return
     */
    @RequestMapping(value = "add_category.do")
    @ResponseBody
    public ServerResponse addCategory(HttpSession session, String categoryName, @RequestParam(value = "parentId", defaultValue = "0")Integer parentId) {

        User user = (User) session.getAttribute(Const.CURRENT_USER);

        if (user == null) {
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(), "用户未登陆");
        }

        if (user.getRole() == Const.Role.ROLE_ADMIN) {
            //是管理员，插入品类信息
            return iCategoryService.addCategory(categoryName, parentId);
        }

        return ServerResponse.createByErrorMessage("用户无权限");
    }

    /**
     * 修改品类名称
     * @param session
     * @param categoryNewName
     * @param categoryId
     * @return
     */
    @RequestMapping(value = "set_category_name.do")
    @ResponseBody
    public ServerResponse setCategoryName(HttpSession session, String categoryNewName, Integer categoryId) {

        User user = (User) session.getAttribute(Const.CURRENT_USER);

        if (user == null) {
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(), "用户未登陆");
        }

        if (user.getRole() == Const.Role.ROLE_ADMIN) {
            //是管理员，修改品类的名称
            return iCategoryService.updateCategoryName(categoryNewName, categoryId);
        }

        return ServerResponse.createByErrorMessage("用户无权限");

    }

    /**
     * 通过id获取品类信息
     * @param session
     * @param categoryId
     * @return
     */
    @RequestMapping(value = "get_category.do")
    @ResponseBody
    public ServerResponse<List<Category>> getCategoryChildInfoByCategoryId(HttpSession session, @RequestParam(value = "categoryId", defaultValue = "0") Integer categoryId) {
        User user = (User) session.getAttribute(Const.CURRENT_USER);

        if (user == null) {
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(), "用户未登陆");
        }

        if (user.getRole() == Const.Role.ROLE_ADMIN) {
            //是管理员，获取子节点
            return iCategoryService.getCategoryChildInfoByCategoryId(categoryId);
        }

        return ServerResponse.createByErrorMessage("用户无权限");


    }

    @RequestMapping(value = "get_deep_category.do")
    @ResponseBody
    public ServerResponse getCateforyAndDescendantIdByCategoryId(HttpSession session,Integer categoryId){
        User user = (User) session.getAttribute(Const.CURRENT_USER);

        if (user == null) {
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(), "用户未登陆");
        }

        if (user.getRole() == Const.Role.ROLE_ADMIN) {
            //是管理员，获取当前节点id及递归子节点的categoryId
            return iCategoryService.getCateforyAndDescendantIdByCategoryId(categoryId);

        }

        return ServerResponse.createByErrorMessage("用户无权限");


    }




}