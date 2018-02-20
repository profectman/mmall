package com.mmall.controller.backend;

import com.google.common.collect.Maps;
import com.mmall.common.Const;
import com.mmall.common.ResponseCode;
import com.mmall.common.ServerResponse;
import com.mmall.pojo.Product;
import com.mmall.pojo.User;
import com.mmall.service.IFileService;
import com.mmall.service.IProductService;
import com.mmall.util.PropertiesUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Map;

/**
 * Created by zhengquan on 2017/5/30.
 */
@Controller
@RequestMapping("/manage/product")
public class ProductManageController {

    @Autowired
    private IProductService iProductService;

    @Autowired
    private IFileService iFileService;

    @RequestMapping("save.do")
    @ResponseBody
    public ServerResponse productSave(HttpSession session, Product product) {
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if (user == null) {
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(), "用户未登陆，请登陆管理员");
        }

        if (user.getRole() == Const.Role.ROLE_ADMIN) {
            //增加产品的业务
            return iProductService.saveOrUpdateProduct(product);
        }
        return ServerResponse.createByErrorMessage("用户无权限");
    }


    @RequestMapping("set_sale_status.do")
    @ResponseBody
    public ServerResponse setSaleStatus(HttpSession session, Integer productId, Integer status) {
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if (user == null) {
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(), "用户未登陆，请登陆管理员");
        }

        if (user.getRole() == Const.Role.ROLE_ADMIN) {
            //产品上下架状态更变的业务
            return iProductService.setSaleStatus(productId, status);
        }
        return ServerResponse.createByErrorMessage("用户无权限");
    }

    @RequestMapping("detail.do")
    @ResponseBody
    public ServerResponse getDetail(HttpSession session, Integer productId) {
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if (user == null) {
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(), "用户未登陆，请登陆管理员");
        }

        if (user.getRole() == Const.Role.ROLE_ADMIN) {
            //获取产品详情信息
            return iProductService.manageProductDateil(productId);
        }
        return ServerResponse.createByErrorMessage("用户无权限");
    }

    @RequestMapping("list.do")
    @ResponseBody
    public ServerResponse getList(HttpSession session, @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum, @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize) {
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if (user == null) {
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(), "用户未登陆，请登陆管理员");
        }

        if (user.getRole() == Const.Role.ROLE_ADMIN) {
            //获取产品列表的分页信息
            return iProductService.getProductList(pageNum, pageSize);
        }
        return ServerResponse.createByErrorMessage("用户无权限");
    }

    @RequestMapping("search.do")
    @ResponseBody
    public ServerResponse productSearch(HttpSession session, String productName, Integer productId, @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum, @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize) {
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if (user == null) {
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(), "用户未登陆，请登陆管理员");
        }

        if (user.getRole() == Const.Role.ROLE_ADMIN) {
            //获取产品搜索的分页信息
            return iProductService.productSearch(productName, productId, pageNum, pageSize);
        }
        return ServerResponse.createByErrorMessage("用户无权限");
    }

    @RequestMapping("upload.do")
    @ResponseBody
    public ServerResponse upload(HttpSession session, @RequestParam(value = "upload_file", required = false) MultipartFile file, HttpServletRequest request) {
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if (user == null) {
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(), "用户未登陆，请登陆管理员");
        }
        if (user.getRole() == Const.Role.ROLE_ADMIN) {
            //上传文件
            String path = request.getSession().getServletContext().getRealPath("upload");
            String targetFileName = iFileService.upload(file, path);
            String url = new StringBuilder().append(PropertiesUtil.getProperty("ftp.server.http.prefix")).append(targetFileName).toString();

            Map fileMap = Maps.newHashMap();
            fileMap.put("uri", targetFileName);
            fileMap.put("url", url);
            return ServerResponse.createBySuccess(fileMap);
        }
        return ServerResponse.createByErrorMessage("用户无权限");
    }


    @RequestMapping(value = "richtext_img_upload.do", method = RequestMethod.POST)
    @ResponseBody
    public Map richTextImgUpload(HttpSession session, @RequestParam(value = "upload_file", required = false) MultipartFile file, HttpServletRequest request, HttpServletResponse response) {
        Map resultMap = Maps.newHashMap();
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if (user == null) {
            resultMap.put("success", false);
            resultMap.put("msg", "请登陆管理员");
            return resultMap;
        }
//      富文本中对于返回值有自己的要求，我们使用搞事simditor所以按照要求进行返回
//        {
//            "success": true/false,
//                "msg": "error message", # optional
//            "file_path": "[real file path]"
//        }
        if (user.getRole() == Const.Role.ROLE_ADMIN) {
            //上传文件
            String path = request.getSession().getServletContext().getRealPath("upload");
            String targetFileName = iFileService.upload(file, path);
            if (StringUtils.isBlank(targetFileName)) {
                resultMap.put("success", false);
                resultMap.put("msg", "上传失败");
                return resultMap;
            }
            String url = new StringBuilder().append(PropertiesUtil.getProperty("ftp.server.http.prefix")).append(targetFileName).toString();
            resultMap.put("success", true);
            resultMap.put("msg", "上传成功");
            resultMap.put("url", url);
            System.out.println("real file path:"+url);
            response.addHeader("Access-Control-Allow-Headers", "X-File-Name");
            return resultMap;
        }

        resultMap.put("success", false);
        resultMap.put("msg", "用户无权限");
        return resultMap;
    }

}
