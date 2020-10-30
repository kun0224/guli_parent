package com.zhao.eduservice.api;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zhao.commonutils.R;
import com.zhao.eduservice.entity.EduCourse;
import com.zhao.eduservice.entity.Vo.ChapterVo;
import com.zhao.eduservice.entity.Vo.CourseQueryVo;
import com.zhao.eduservice.entity.Vo.CourseWebVo;
import com.zhao.eduservice.service.EduChapterService;
import com.zhao.eduservice.service.EduCourseService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Api(description = "前台课程展示")
@RestController
@RequestMapping("/eduservice/courseapi")
//@CrossOrigin
public class CourseApiController {

    //账号AK信息请填写(必选)
    private static final String accessKeyId = "LTAI4GK4H3GUqVEQyFHq9ULZ";
    //账号AK信息请填写(必选)
    private static final String accessKeySecret = "iXYjrFKZ09INEalumyHLrM0Rd7FSF9";

    @Autowired
    private EduCourseService courseService;

    @Autowired
    private EduChapterService chapterService;

//    @Autowired
//    EduVideoService videoService;


    @ApiOperation(value = "带条件的分页查询课程")
    @PostMapping("getCoursePageVo/{page}/{limit}")
    public R getCoursePageVo(@PathVariable Long page, @PathVariable Long limit, @RequestBody CourseQueryVo courseQueryVo) {
        Page<EduCourse> pageParam = new Page<>(page, limit);
        Map<String, Object> map = courseService.getCoursePageVo(pageParam, courseQueryVo);
        return R.ok().data(map);
    }

    @ApiOperation(value = "根据课程id查询课程详情")
    @GetMapping("getCourseInById/{id}")
    public R getCourseInById(@PathVariable String id) {
        //1.课程相关信息查询
        CourseWebVo courseWebVo = courseService.getCourseWebVo(id);

        //2.课程大纲信息查询
        List<ChapterVo> chapterVideoList = chapterService.getChapterVideoById(id);

        return R.ok().data("courseWebVo", courseWebVo).data("chapterVideoList", chapterVideoList);
    }


//    @ApiOperation(value = "根据视频id获取播放凭证")
//    @GetMapping("getvideoPById/{id}")
//    public R getvideoPByTId(@PathVariable String id) {
//        try {
//            //1.创建初始化对象
//            DefaultAcsClient client =
//                    AliyunVodSDKUtils.initVodClient("LTAI4GK4H3GUqVEQyFHq9ULZ", "iXYjrFKZ09INEalumyHLrM0Rd7FSF9");
//            //2创建请求、响应对象（不同操作、类不一样）
//            GetVideoPlayAuthRequest request = new GetVideoPlayAuthRequest();
//            GetVideoPlayAuthResponse response = new GetVideoPlayAuthResponse();
//            //3向请求对象设置参数（视频id）
//            EduVideo byId = videoService.getById(id);
//            System.out.println(byId.getVideoSourceId());
//            request.setVideoId(byId.getVideoSourceId());
//            //4使用初始化对象方法发送请求，拿到响应
//            response = client.getAcsResponse(request);
//            //播放凭证
//            System.out.print("PlayAuth = " + response.getPlayAuth() + "\n");
//            //VideoMeta信息
//            System.out.print("VideoMeta.Title = " + response.getVideoMeta().getTitle() + "\n");
//            return R.ok();
//        } catch (Exception e) {
//            e.printStackTrace();
//            throw new GuliException(20001, "错误");
//        }
//
//    }

}


