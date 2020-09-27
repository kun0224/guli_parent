package com.zhao.oss.controller;

import com.zhao.commonutils.R;
import com.zhao.oss.service.OssService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@Api(description="上传文件管理")
@RequestMapping("/ossservice/ossfile")
@RestController
@CrossOrigin
public class Osscontroller {

    @Autowired
    private OssService ossService;

    @ApiOperation(value = "文件上传")
    @PostMapping("uploadFile")
    public R uploadFile(MultipartFile file){
        //1.获取文件
        //2.调接口上传文件
        String url = ossService.uploadFileOss(file);
        //3.返回文件url
        return R.ok().data("url", url);
    }

}
