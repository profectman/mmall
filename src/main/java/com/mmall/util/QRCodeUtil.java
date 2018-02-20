package com.mmall.util;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Hashtable;
import java.util.UUID;

/**
 * Created by zhengquan on 2017/6/6.
 */
public class QRCodeUtil {

    private static Logger logger = LoggerFactory.getLogger(QRCodeUtil.class);

    public static String createQRCode(String content, String path) {
        //设置二维码纠错级别MAP
        Hashtable<EncodeHintType, ErrorCorrectionLevel> hintMap = new Hashtable<EncodeHintType, ErrorCorrectionLevel>();
        hintMap.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.L);
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        BitMatrix bitMatrix = null;
        //创建比特矩阵(位矩阵)的QR码编码的字符串
        try {
            bitMatrix = qrCodeWriter.encode(content, BarcodeFormat.QR_CODE, 900, 900, hintMap);
        } catch (WriterException e) {
            logger.error("信息创建二维码异常...", e);
        }
        //使用BufferedImage勾画QRCode(是行二维码的像素点)
        int matrixWidth = bitMatrix.getWidth();
        BufferedImage bufferedImage = new BufferedImage(matrixWidth - 200, matrixWidth - 200, BufferedImage.TYPE_INT_RGB);
        bufferedImage.createGraphics();
        Graphics2D graphics = (Graphics2D) bufferedImage.getGraphics();
        graphics.setColor(Color.WHITE);
        graphics.fillRect(0, 0, matrixWidth, matrixWidth);
        // 使用比特矩阵画并保存图像
        graphics.setColor(Color.BLACK);
        for (int i = 0; i < matrixWidth; i++) {
            for (int j = 0; j < matrixWidth; j++) {
                if (bitMatrix.get(i, j)) {
                    graphics.fillRect(i - 100, j - 100, 1, 1);
                }
            }
        }

        File file = new File(path);
        if (!file.exists()) {
            file.setWritable(true);
            file.mkdirs();
        }
        String FileName = new StringBuilder().append(UUID.randomUUID().toString()).append(".jpg").toString();
        File targetFile = new File(path + "/" + FileName);
        try {
            ImageIO.write(bufferedImage, "jpg", targetFile);
        } catch (IOException e) {
            logger.error("保存二维码图片异常...", e);
        }
        return targetFile.getPath();
    }

    public static void main(String[] args) {
        QRCodeUtil.createQRCode("www.baidu.com","/Users/zhengquan/IdeaProjects/mmall/QRCode");
    }
}
