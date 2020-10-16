package com.zhao.eduservice.api;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zhao.commonutils.R;
import com.zhao.eduservice.entity.EduCourse;
import com.zhao.eduservice.entity.EduTeacher;
import com.zhao.eduservice.service.EduCourseService;
import com.zhao.eduservice.service.EduTeacherService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Api(description = "前台讲师展现")
@RestController
@RequestMapping("/eduservice/teacherapi")
//@CrossOrigin
public class TeacherApiController {

    @Autowired
    private EduTeacherService teacherService;

    @Autowired
    private EduCourseService courseService;

    /**
     * 分页查询所有讲师
     *
     * @param page
     * @param limit
     * @return
     */
    @ApiOperation(value = "分页查询所有讲师")
    @GetMapping("getFroutTeacherPage/{page}/{limit}")
    public R getFroutTeacherPage(@PathVariable Long page, @PathVariable Long limit) {

        Page<EduTeacher> pageParam = new Page<>(page, limit);

        teacherService.page(pageParam, null);
        //获取查询结果，总共有多少行
        List<EduTeacher> records = pageParam.getRecords();
        //获取当前页
        long current = pageParam.getCurrent();
        //总共分了多少页
        long pages = pageParam.getPages();
        //每页多少条数据
        long size = pageParam.getSize();
        //获取总记录数
        long total = pageParam.getTotal();
        //判断是否有下一页
        boolean hasNext = pageParam.hasNext();
        System.out.println("-----hasNaext=="+hasNext);
        //判断是否有上一页
        boolean hasPrevious = pageParam.hasPrevious();

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("items", records);
        map.put("current", current);
        map.put("pages", pages);
        map.put("size", size);
        map.put("total", total);
        map.put("hasNext", hasNext);
        map.put("hasPrevious", hasPrevious);
        return R.ok().data(map);
    }

    /**
     * 根据id查询讲师详情
     *
     * @param id
     * @return
     */
    @ApiOperation(value = "根据id查询讲师详情")
    @GetMapping("getTeacherCourseById/{id}")
    public R getTeacherCourseById(@PathVariable String id) {

        //1.根据主键查询讲师信息
        EduTeacher eduTeacher = teacherService.getById(id);

        //2.根据讲师id查询课程信息
        QueryWrapper<EduCourse> wrapper = new QueryWrapper<>();
        wrapper.eq("teacher_id", id);
        List<EduCourse> courseList = courseService.list(wrapper);

        return R.ok().data("eduTeacher", eduTeacher).data("courseList", courseList);
    }
}
