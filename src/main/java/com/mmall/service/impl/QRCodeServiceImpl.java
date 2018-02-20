package com.mmall.service.impl;

import com.mmall.common.ServerResponse;
import com.mmall.dao.QrCodeMapper;
import com.mmall.pojo.QrCode;
import com.mmall.service.IQRCodeService;
import com.mmall.util.QRCodeUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.Date;
import java.util.UUID;

/**
 * Created by zhengquan on 2017/6/6.
 */
@Service("iQRCodeService")
public class QRCodeServiceImpl implements IQRCodeService {


    private Logger logger = LoggerFactory.getLogger(QRCodeServiceImpl.class);
    @Autowired
    private QrCodeMapper qrCodeMapper;

    public ServerResponse<QrCode> getQRCodeImage(String path) {
        String uuid = UUID.randomUUID().toString();
        String QRCodeImage = QRCodeUtil.createQRCode(uuid, path);

        QrCode qrCode = new QrCode();
        qrCode.setQrcodeImage(QRCodeImage);
        qrCode.setUuid(uuid);

        int rowCount = qrCodeMapper.insert(qrCode);
        if (rowCount == 0) {
            return ServerResponse.createByErrorMessage("二维码生成失败");
        }
        return ServerResponse.createBySuccess(qrCode);
    }

    public QrCode checkQRCodeLogin(String uuid) {
        boolean bool = true;
        QrCode qrCode = null;
        long inTime = new Date().getTime();
        while (bool){
            logger.info("....");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            qrCode = qrCodeMapper.selectByUUID(uuid);
            if(qrCode.getUsername()!= null && qrCode.getPassword()!=null){
                bool = false;
                qrCodeMapper.deleteByPrimaryKey(qrCode.getId());
                File file = new File(qrCode.getQrcodeImage());
                file.delete();
            }else if(new Date().getTime()- inTime > 10000){
                bool = false;
            }
        }
        return qrCode;
    }

    public ServerResponse<String> phoneLogin(String uuid, String username, String password){
        if(StringUtils.isBlank(uuid)||StringUtils.isBlank(username) ||StringUtils.isBlank(password)){
            return ServerResponse.createByErrorMessage("参数错误");
        }
        int updateCount = qrCodeMapper.updateByUUID(uuid,username,password);
        if(updateCount == 0){
            return ServerResponse.createByErrorMessage("未登陆成功,请重试");
        }
        return ServerResponse.createBySuccess();
    }
}
