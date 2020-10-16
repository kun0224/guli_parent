package com.zhao.eduservice.controller;


import com.zhao.commonutils.R;
import com.zhao.eduservice.entity.EduChapter;
import com.zhao.eduservice.entity.Vo.ChapterVo;
import com.zhao.eduservice.service.EduChapterService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 课程 前端控制器
 * </p>
 *
 * @author zhao
 * @since 2020-09-28
 */
@Api(description = "课程章节管理")
@RestController
@RequestMapping("/eduservice/edu-chapter")
//@CrossOrigin
public class EduChapterController {

    @Autowired
    EduChapterService eduChapterService;

    /**
     * 根据课程id查询章节小节信息
     * @param courseId
     * @return
     */
    @ApiOperation(value = "根据课程id查询章节小节信息")
    @GetMapping("getChapterVideoById/{courseId}")
    public R getChapterVideoById(@PathVariable String courseId){

        List<ChapterVo> chapterVideoList = eduChapterService.getChapterVideoById(courseId);
        return R.ok().data("chapterVideoList", chapterVideoList);

    }

    /**
     * 添加章节信息
     * @param eduChapter
     * @return
     */
    @ApiOperation(value = "添加章节信息")
    @PostMapping("addChapter")
    public R addChapter(@RequestBody EduChapter eduChapter){
        eduChapterService.save(eduChapter);
        return R.ok();
    }

    /**
     * 删除章节信息
     * @param id
     * @return
     */
    @ApiOperation(value = "删除章节信息")
    @DeleteMapping("deleteChapter/{id}")
    public R deleteChapter(@PathVariable String id){
        eduChapterService.removeById(id);
        return R.ok();
    }

    /**
     * 修改章节信息
     * @param eduChapter
     * @return
     */
    @ApiOperation(value = "修改章节信息")
    @PostMapping("updateChapter")
    public R updateChapter(@RequestBody EduChapter eduChapter){
        eduChapterService.updateById(eduChapter);
        return R.ok();
    }

    /**
     * 根据id查询章节信息
     * @return
     */
    @ApiOperation(value = "根据id查询章节信息")
    @GetMapping("getChapterById/{id}")
    public R getChapterById(@PathVariable String id){
        EduChapter eduChapter = eduChapterService.getById(id);
        return R.ok().data("eduChapter", eduChapter);
    }

}

