package com.mmall.service;

import org.springframework.web.multipart.MultipartFile;

/**
 * Created by zhengquan on 2017/6/3.
 */
public interface IFileService {

    String upload(MultipartFile file, String path);
}
