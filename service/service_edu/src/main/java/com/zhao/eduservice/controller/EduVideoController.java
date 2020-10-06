package com.zhao.eduservice.controller;


import com.zhao.commonutils.R;
import com.zhao.eduservice.entity.EduVideo;
import com.zhao.eduservice.service.EduVideoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 课程视频 前端控制器
 * </p>
 *
 * @author zhao
 * @since 2020-09-28
 */
@Api(description = "课程小节管理")
@RestController
@RequestMapping("/eduservice/edu-video")
@CrossOrigin
public class EduVideoController {

    @Autowired
    EduVideoService eduVideoService;

    /**
     * 添加小节信息
     *
     * @param eduVideo
     * @return
     */
    @ApiOperation(value = "添加小节信息")
    @PostMapping("addVideo")
    public R addVideo(@RequestBody EduVideo eduVideo) {
        eduVideoService.save(eduVideo);
        return R.ok();
    }

    /**
     * 根据id删除相应的小节信息
     *
     * @param id
     * @return
     */
    @ApiOperation(value = "根据id删除相应的小节信息")
    @DeleteMapping("deleteVideo/{id}")
    //TODO 删除小节的同时删除云端存储视频
    public R deleteVideo(@PathVariable String id) {
        eduVideoService.removeById(id);
        return R.ok();
    }

    /**
     * 根据id查询小节信息
     * @param id
     * @return
     */
    @ApiOperation(value = "根据id查询小节信息")
    @GetMapping("getVideoById/{id}")
    public R getVideoById(@PathVariable String id) {
        EduVideo eduVideo = eduVideoService.getById(id);
        return R.ok().data("eduVideo", eduVideo);
    }


    /**
     * 修改小节信息
     * @param eduVideo
     * @return
     */
    @ApiOperation(value = "修改小节信息")
    @PostMapping("updateVideo")
    public R updateVideo(@RequestBody EduVideo eduVideo){
        eduVideoService.updateById(eduVideo);
        return R.ok();
    }

}

