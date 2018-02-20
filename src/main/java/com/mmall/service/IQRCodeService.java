package com.mmall.service;

import com.mmall.common.ServerResponse;
import com.mmall.pojo.QrCode;

/**
 * Created by zhengquan on 2017/6/6.
 */

public interface IQRCodeService {

    ServerResponse<QrCode> getQRCodeImage(String path);

    QrCode checkQRCodeLogin(String uuid);

    ServerResponse<String> phoneLogin(String uuid, String username, String password);

}
