package com.demo.tlias.controller;

import com.demo.tlias.pojo.Result;
import com.demo.tlias.utils.AliOSSUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

/**
 * @author wjh
 * @date 2024/3/2 10:33
 */
@Slf4j
@RestController
public class UploadController {
    @Autowired
    AliOSSUtils aliOSSUtils;
    @PostMapping("/upload")
    public Result upload(MultipartFile image) throws IOException {
        log.info("文件上传{}",image.getOriginalFilename());
        String url = aliOSSUtils.upload(image);
        log.info("上传完成url{}",url);
        return Result.success(url);
    }
}
