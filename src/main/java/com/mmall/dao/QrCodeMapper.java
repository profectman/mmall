package com.mmall.dao;

import com.mmall.pojo.QrCode;
import org.apache.ibatis.annotations.Param;

public interface QrCodeMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(QrCode record);

    int insertSelective(QrCode record);

    QrCode selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(QrCode record);

    int updateByPrimaryKey(QrCode record);

    QrCode selectByUUID(String uuid);

    int updateByUUID(@Param("uuid") String uuid, @Param("username") String username, @Param("password") String password);
}