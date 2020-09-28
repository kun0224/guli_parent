package com.zhao.eduservice.controller;


import com.zhao.commonutils.R;
import com.zhao.eduservice.entity.Vo.CourseInfoForm;
import com.zhao.eduservice.service.EduCourseService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 课程 前端控制器
 * </p>
 *
 * @author zhao
 * @since 2020-09-27
 */
@Api(description = "课程管理")
@RestController
@RequestMapping("/eduservice/edu-course")
@CrossOrigin    //跨域
public class EduCourseController {

    @Autowired
    EduCourseService eduCourseService;

    @ApiOperation(value = "添加课程信息")
    @PostMapping("addCourseInfo")
    public R addCourseInfo(@RequestBody CourseInfoForm courseInfoForm) {

        String courseId = eduCourseService.addCourseInfo(courseInfoForm);
        return R.ok().data("courseId", courseId);

    }

    @ApiOperation(value = "根据id查询课程信息")
    @GetMapping("getCourseInfoById/{id}")
    public R getCourseInfoById(@PathVariable String id){
        CourseInfoForm courseInfoForm = eduCourseService.getCourseInfoById(id);
        return R.ok().data("courseInfoForm", courseInfoForm);
    }

    @ApiOperation(value = "修改课程信息")
    @PostMapping("updateCourseById")
    public R updateCourseById(@RequestBody CourseInfoForm courseInfoForm){
        eduCourseService.updateCourseInfo(courseInfoForm);
        return R.ok();
    }
}

