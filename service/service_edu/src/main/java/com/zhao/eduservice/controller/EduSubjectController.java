package com.zhao.eduservice.controller;


import com.alibaba.excel.EasyExcel;
import com.zhao.commonutils.R;
import com.zhao.eduservice.entity.EduSubject;
import com.zhao.eduservice.entity.Vo.OneSubjectVo;
import com.zhao.eduservice.entity.excel.ExcelSubjectData;
import com.zhao.eduservice.mapper.EduSubjectMapper;
import com.zhao.eduservice.service.EduSubjectService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程科目 前端控制器
 * </p>
 *
 * @author zhao
 * @since 2020-09-24
 */
@Api(description = "课程科目")
@RestController
@RequestMapping("/eduservice/edu-subject")
@CrossOrigin
public class EduSubjectController {

    @Autowired
    EduSubjectMapper eduSubjectMapper;

    @Autowired
    EduSubjectService eduSubjectService;

    @PostMapping("test")
    public List<ExcelSubjectData> test(){
        List<ExcelSubjectData> eduSubjectList = eduSubjectMapper.getEduSubjectList();
        String filename = "E:\\22.xlsx";
        EasyExcel.write(filename, ExcelSubjectData.class).sheet("写入").doWrite(eduSubjectList);
        return eduSubjectList;
    }

    @ApiOperation(value = "导入课程分类信息")
    @PostMapping("addSubject")
    public R addSubject(MultipartFile file){
        eduSubjectService.addSubjectInfo(file, eduSubjectService);
        return R.ok();
    }

    @ApiOperation(value = "查询所有课程分类信息")
    @GetMapping("getAllSubject")
    public R getAllSubject(){
        List<OneSubjectVo> oneSubjectVoList = eduSubjectService.getAllSubject();
        return R.ok().data("allsubject", oneSubjectVoList);
    }

}

