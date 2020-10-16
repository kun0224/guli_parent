package com.zhao.eduservice.client;

import com.zhao.commonutils.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient("service-vod")
@Component
public interface VodClient {

    //视频删除
    //1、访问url必须写全
    //2、参数注解不能省略参数名
    @DeleteMapping("/eduvod/video/deleteVideoById/{videoId}")
    public R deleteVideoById(@PathVariable("videoId") String videoId);
}
