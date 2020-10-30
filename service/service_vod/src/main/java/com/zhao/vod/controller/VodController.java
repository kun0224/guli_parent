package com.zhao.vod.controller;

import com.aliyun.vod.upload.impl.UploadVideoImpl;
import com.aliyun.vod.upload.req.UploadStreamRequest;
import com.aliyun.vod.upload.resp.UploadStreamResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.vod.model.v20170321.DeleteVideoRequest;
import com.aliyuncs.vod.model.v20170321.DeleteVideoResponse;
import com.aliyuncs.vod.model.v20170321.GetVideoPlayAuthRequest;
import com.aliyuncs.vod.model.v20170321.GetVideoPlayAuthResponse;
import com.zhao.baseservice.exception.GuliException;
import com.zhao.commonutils.R;
import com.zhao.vod.utils.AliyunVodSDKUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;

@Api(description = "视频管理")
@RestController
@RequestMapping("/eduvod/video")
//@CrossOrigin
public class VodController {


    //账号AK信息请填写(必选)
    private static final String accessKeyId = "LTAI4GK4H3GUqVEQyFHq9ULZ";
    //账号AK信息请填写(必选)
    private static final String accessKeySecret = "iXYjrFKZ09INEalumyHLrM0Rd7FSF9";


    @ApiOperation(value = "视频上传")
    @PostMapping("uploadVideo")
    public R uploadVideo(MultipartFile file) {
        try {
            //视频标题
            String filename = file.getOriginalFilename();
            String title = filename.substring(0, filename.lastIndexOf("."));

            InputStream inputStream = file.getInputStream();

            //UploadVideoRequest request = new UploadVideoRequest(accessKeyId, accessKeySecret, title, filename);
            UploadStreamRequest request = new UploadStreamRequest(accessKeyId, accessKeySecret, title, filename, inputStream);
            /* 可指定分片上传时每个分片的大小，默认为1M字节 */
            UploadVideoImpl uploader = new UploadVideoImpl();
            UploadStreamResponse response = uploader.uploadStream(request);

            String videoId = response.getVideoId();
            if (!response.isSuccess()) {
                String errorMessage = "阿里云上传错误：" + "code：" + response.getCode() + ", message：" + response.getMessage();

                //判断videoId是否为空
                if (StringUtils.isEmpty(videoId)) {
                    throw new GuliException(20001, errorMessage);
                }
            }
            return R.ok().data("videoId", videoId);

        } catch (IOException e) {
            e.printStackTrace();
            throw new GuliException(20001, "视频上传失败");
        }
    }


    @ApiOperation(value = "视频删除")
    @DeleteMapping("deleteVideoById/{videoId}")
    public R deleteVideoById(@PathVariable String videoId) {
        try {
            //1.创建初始化对象
            DefaultAcsClient client = AliyunVodSDKUtils.initVodClient(accessKeyId, accessKeySecret);

            //2.创建请求对象
            DeleteVideoRequest request = new DeleteVideoRequest();

            //3.向请求对象设置参数
            request.setVideoIds(videoId);
            //4.调用初始化对象方法发送请求
            client.getAcsResponse(request);
            return R.ok();
        } catch (ClientException e) {
            e.printStackTrace();
            throw new GuliException(20001, "视频删除失败");
        }

    }

    @ApiOperation(value = "根据视频id获取视频播放凭证")
    @GetMapping("getPlayAuth/{vid}")
    public R getPlayAuth(@PathVariable String vid) {
        try {
            //（1）创建初始化对象
            DefaultAcsClient client =
                    AliyunVodSDKUtils.initVodClient("LTAI4GK4H3GUqVEQyFHq9ULZ", "iXYjrFKZ09INEalumyHLrM0Rd7FSF9");
//            //2创建请求、响应对象（不同操作、类不一样）
            //（2）创建request、response对象
            GetVideoPlayAuthRequest request = new GetVideoPlayAuthRequest();
            GetVideoPlayAuthResponse response = new GetVideoPlayAuthResponse();
            //（3）向request设置视频id
            request.setVideoId(vid);
            //播放凭证有过期时间，默认值：100秒 。取值范围：100~3000。
            //request.setAuthInfoTimeout(200L);
            //（4）调用初始化方法实现功能
            response = client.getAcsResponse(request);
            //（5）调用方法返回response对象，获取内容
            String playAuth = response.getPlayAuth();
            return R.ok().data("playAuth", playAuth);
        } catch (ClientException e) {
            throw new GuliException(20001, "获取视频凭证失败");
        }
    }

}
