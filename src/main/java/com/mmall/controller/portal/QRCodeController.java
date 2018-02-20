package com.mmall.controller.portal;

import com.mmall.common.ServerResponse;
import com.mmall.pojo.QrCode;
import com.mmall.service.IQRCodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.UUID;

/**
 * Created by zhengquan on 2017/6/6.
 */

@Controller
@RequestMapping("/qrcode/")
public class QRCodeController {

    @Autowired
    private IQRCodeService iqrCodeService;

    @RequestMapping(value = "get_qrcode.do")
    @ResponseBody
    public ServerResponse<QrCode> getQRCodeImage(HttpServletRequest request) {
        String path = request.getSession().getServletContext().getRealPath("QRCode");
        return iqrCodeService.getQRCodeImage(path);
    }


    @RequestMapping(value = "check_qrcode_login.do")
    @ResponseBody
    public String checkQRCodeLogin(String uuid, RedirectAttributes redirectAttributes) {
        QrCode qrCode = iqrCodeService.checkQRCodeLogin(uuid);
        redirectAttributes.addAttribute("username", qrCode.getUsername());
        redirectAttributes.addAttribute("password", qrCode.getPassword());
        return "redirect:/user/login.do";
    }

    @RequestMapping(value = "phone_login.do", method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse<String> phoneLogin(String uuid, String username, String password) {
        return iqrCodeService.phoneLogin(uuid, username, password);
    }


}
