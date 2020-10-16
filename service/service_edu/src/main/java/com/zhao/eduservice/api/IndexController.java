package com.zhao.eduservice.api;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zhao.commonutils.R;
import com.zhao.eduservice.entity.EduCourse;
import com.zhao.eduservice.entity.EduTeacher;
import com.zhao.eduservice.service.EduCourseService;
import com.zhao.eduservice.service.EduTeacherService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Api(description = "首页展示")
@RestController
@RequestMapping("/eduservice/index")
//@CrossOrigin
public class IndexController {

    @Autowired
    EduCourseService courseService;

    @Autowired
    EduTeacherService teacherService;

    @ApiOperation(value = "获取8门课程信息，4门讲师信息")
    @GetMapping("getCourseTeacher")
    public R getCourseTeacher(){
        //1.查询8门课程信息
        QueryWrapper<EduCourse> courseWrapper = new QueryWrapper<>();
        courseWrapper.orderByDesc("id");
        courseWrapper.last("LIMIT 8");
        List<EduCourse> courseList = courseService.list(courseWrapper);

        //2.查询4位讲师信息
        QueryWrapper<EduTeacher> teacherWrapper = new QueryWrapper<>();
        teacherWrapper.orderByDesc("id");
        teacherWrapper.last("LIMIT 4");
        List<EduTeacher> teacherList = teacherService.list(teacherWrapper);

        return R.ok().data("courseList",courseList).data("teacherList",teacherList);
    }

}
