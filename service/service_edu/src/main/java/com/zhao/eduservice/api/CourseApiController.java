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

    @Autowired
    private EduCourseService courseService;

    @Autowired
    private EduChapterService chapterService;

    @ApiOperation(value = "带条件的分页查询课程")
    @PostMapping("getCoursePageVo/{page}/{limit}")
    public R getCoursePageVo(@PathVariable Long page, @PathVariable Long limit, @RequestBody CourseQueryVo courseQueryVo){
        Page<EduCourse> pageParam = new Page<>(page, limit);
        Map<String, Object> map = courseService.getCoursePageVo(pageParam,courseQueryVo);
        return R.ok().data(map);
    }

    @ApiOperation(value = "根据课程id查询课程详情")
    @GetMapping("getCourseInById/{id}")
    public R getCourseInById(@PathVariable String id){
        //1.课程相关信息查询
        CourseWebVo courseWebVo = courseService.getCourseWebVo(id);

        //2.课程大纲信息查询
        List<ChapterVo> chapterVideoList = chapterService.getChapterVideoById(id);

        return R.ok().data("courseWebVo",courseWebVo).data("chapterVideoList", chapterVideoList);
    }

}
