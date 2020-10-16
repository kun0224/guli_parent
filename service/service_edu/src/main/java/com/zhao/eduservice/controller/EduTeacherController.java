package com.zhao.eduservice.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zhao.commonutils.R;
import com.zhao.eduservice.entity.EduTeacher;
import com.zhao.eduservice.entity.Vo.TeacherQuery;
import com.zhao.eduservice.service.EduTeacherService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 讲师 前端控制器
 * </p>
 *
 * @author zhao
 * @since 2020-09-19
 */
@Api(description = "讲师管理")
@RestController
@RequestMapping("/eduservice/edu-teacher")
//@CrossOrigin
public class EduTeacherController {

    @Autowired
    private EduTeacherService eduTeacherService;

    /**
     * 查询所有讲师信息
     *
     * @return
     */
    @GetMapping
    @ApiOperation(value = "查询所有讲师信息")
    public R list() {
        return R.ok().data("items", eduTeacherService.list(null));
    }

    /**
     * 逻辑删除操作
     *
     * @param id
     * @return
     */
    @ApiOperation(value = "逻辑删除操作")
    @DeleteMapping("{id}")
    public R deleteTeacher(@PathVariable String id) {
        boolean b = eduTeacherService.removeById(id);
        return R.ok();
    }

    /**
     * 分页查询所有1
     * page :当前页码
     * limit :每页记录数
     *
     * @return
     */
    @ApiOperation(value = "分页查询所有")
    @GetMapping("getPageTeacher/{page}/{limit}")
    public R getPageTeacher(@PathVariable("page") Long page, @PathVariable("limit") Long limit) {
        Page<EduTeacher> teacherPage = new Page<>(page, limit);
        eduTeacherService.page(teacherPage, null);
        List<EduTeacher> records = teacherPage.getRecords();
        long total = teacherPage.getTotal();
        return R.ok().data("records", records).data("todal", total);
    }

    /**
     * 分页查询所有2
     *
     * @param page
     * @param limit
     * @return
     */
    @ApiOperation(value = "分页查询所有(方式2)")
    @GetMapping("getPageTeacher2/{page}/{limit}")
    public R getPageToHow(@PathVariable("page") Long page, @PathVariable("limit") Long limit) {
        Page<EduTeacher> teacherPage = new Page<>(page, limit);
        eduTeacherService.page(teacherPage, null);
        List<EduTeacher> records = teacherPage.getRecords();
        long total = teacherPage.getTotal();

        Map<String, Object> map = new HashMap<>();
        map.put("records", records);
        map.put("total", total);
        return R.ok().data(map);
    }

    /**
     * 带条件的分页查询
     *
     * @param page
     * @param limit
     * @param teacherQuery
     * @return
     */
    @ApiOperation(value = "带条件的分页查询")
    @PostMapping("getPageTeacherVo/{page}/{limit}")
    public R getPageTeacherVo(@PathVariable Long page, @PathVariable Long limit, @RequestBody TeacherQuery teacherQuery) {

        //创建配置对象
        Page<EduTeacher> teacherPage = new Page<>(page, limit);

        //创建条件查询构造器
        QueryWrapper<EduTeacher> wrapper = new QueryWrapper<>();

        //取出查询条件
        String name = teacherQuery.getName();
        Integer level = teacherQuery.getLevel();
        String begin = teacherQuery.getBegin();
        String end = teacherQuery.getEnd();

//        判断条件

        if (!StringUtils.isEmpty(level)) {
            wrapper.eq("level", level);
        }
        if (!StringUtils.isEmpty(begin)) {
            wrapper.ge("gmt_create", begin);
        }
        if (!StringUtils.isEmpty(end)) {
            wrapper.le("gmt_create", end);
        }
        if (!StringUtils.isEmpty(name)) {
            //wrapper.like("name", name);
            wrapper.like("name", name);
        }

        eduTeacherService.page(teacherPage, wrapper);
        List<EduTeacher> records = teacherPage.getRecords();
        long total = teacherPage.getTotal();
        return R.ok().data("records", records).data("total", total);

    }


    /**
     * 添加功能
     * @param teacher
     * @return
     */
    @ApiOperation(value = "新增讲师")
    @PostMapping("addTeacher")
    public R addTeacher(
            @ApiParam(name = "teacher", value = "讲师对象", required = true)
            @RequestBody EduTeacher teacher) {

        boolean save = eduTeacherService.save(teacher);
        if (save){
            return R.ok();
        }else {
            return R.error();
        }

    }


}

