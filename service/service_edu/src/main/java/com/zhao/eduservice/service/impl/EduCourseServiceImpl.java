package com.zhao.eduservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zhao.baseservice.exception.GuliException;
import com.zhao.eduservice.entity.EduChapter;
import com.zhao.eduservice.entity.EduCourse;
import com.zhao.eduservice.entity.EduCourseDescription;
import com.zhao.eduservice.entity.EduVideo;
import com.zhao.eduservice.entity.Vo.CourseInfoForm;
import com.zhao.eduservice.entity.Vo.CoursePublishVo;
import com.zhao.eduservice.entity.Vo.CourseQueryVo;
import com.zhao.eduservice.entity.Vo.CourseWebVo;
import com.zhao.eduservice.mapper.EduCourseMapper;
import com.zhao.eduservice.service.EduChapterService;
import com.zhao.eduservice.service.EduCourseDescriptionService;
import com.zhao.eduservice.service.EduCourseService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zhao.eduservice.service.EduVideoService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 课程 服务实现类
 * </p>
 *
 * @author zhao
 * @since 2020-09-27
 */
@Service
public class EduCourseServiceImpl extends ServiceImpl<EduCourseMapper, EduCourse> implements EduCourseService {

    @Autowired
    EduCourseDescriptionService eduCourseDescriptionService;

    @Autowired
    EduVideoService videoService;

    @Autowired
    EduChapterService chapterService;


    /**
     * 添加课程信息
     *
     * @param courseInfoForm
     * @return
     */
    @Override
    public String addCourseInfo(CourseInfoForm courseInfoForm) {
        //1.添加课程信息
        EduCourse eduCourse = new EduCourse();
        BeanUtils.copyProperties(courseInfoForm, eduCourse);
        int insert = baseMapper.insert(eduCourse);
        if (insert == 0) {
            throw new GuliException(20001, "添加课程信息失败");
        }
        //2.添加课程描述信息(向课程描述信息表EduCourseDescription对象添加信息)
        String courseid = eduCourse.getId();
        EduCourseDescription eduCourseDescription = new EduCourseDescription();
        eduCourseDescription.setId(courseid);   //课程信息表id与课程描述信息表id相同，方便后面的查询
        eduCourseDescription.setDescription(courseInfoForm.getDescription());
        eduCourseDescriptionService.save(eduCourseDescription);

        return courseid;
    }

    /**
     * 根据id查询课程信息
     *
     * @param id
     * @return
     */
    @Override
    public CourseInfoForm getCourseInfoById(String id) {
        //1.查询课程基本信息
        EduCourse eduCourse = baseMapper.selectById(id);
        //2.封装课程基本信息
        CourseInfoForm courseInfoForm = new CourseInfoForm();
        BeanUtils.copyProperties(eduCourse, courseInfoForm);
        //3.查询课程描述信息
        EduCourseDescription byId = eduCourseDescriptionService.getById(id);
        //4.封装课程描述信息
        courseInfoForm.setDescription(byId.getDescription());
        return courseInfoForm;
    }

    /**
     * 修改课程信息
     *
     * @param courseInfoForm
     */
    @Override
    public void updateCourseInfo(CourseInfoForm courseInfoForm) {
        //1.修改课程信息
        EduCourse eduCourse = new EduCourse();
        BeanUtils.copyProperties(courseInfoForm, eduCourse);
        int i = baseMapper.updateById(eduCourse);
        //2.判断修改课程信息是否成功
        if (i == 0) {
            throw new GuliException(20001, "修改课程失败");
        }
        //修改课程描述信息
        String id = eduCourse.getId();
        EduCourseDescription eduCourseDescription = new EduCourseDescription();
        eduCourseDescription.setId(id);
        eduCourseDescription.setDescription(courseInfoForm.getDescription());
        eduCourseDescriptionService.updateById(eduCourseDescription);
    }

    /**
     * 根据id查询课程发布信息
     *
     * @param id
     * @return
     */
    @Override
    public CoursePublishVo getCoursePublishById(String id) {
        CoursePublishVo coursePublishVo = baseMapper.getCoursePublishById(id);
        return coursePublishVo;
    }

    /**
     * 删除课程信息
     *
     * @param courseId
     */
    @Override
    public void deleteCourseInfo(String courseId) {
        //1.删除小节
        QueryWrapper<EduVideo> videowrapper = new QueryWrapper<>();
        videowrapper.eq("course_id", courseId);
        videoService.remove(videowrapper);

        //2.删除章节
        QueryWrapper<EduChapter> Chapterwrapper = new QueryWrapper<>();
        Chapterwrapper.eq("course_id", courseId);
        chapterService.remove(Chapterwrapper);

        //3.删除课程描述信息
        eduCourseDescriptionService.removeById(courseId);

        //4.删除课程
        baseMapper.deleteById(courseId);
    }

    /**
     * 带条件的分页查询课程
     *
     * @param pageParam
     * @param courseQueryVo
     * @return
     */
    @Override
    public Map<String, Object> getCoursePageVo(Page<EduCourse> pageParam, CourseQueryVo courseQueryVo) {
        //1.获取查询条件
        String subjectParentId = courseQueryVo.getSubjectParentId();
        String subjectId = courseQueryVo.getSubjectId();
        String buyCountSort = courseQueryVo.getBuyCountSort();
        String gmtCreateSort = courseQueryVo.getGmtCreateSort();
        String priceSort = courseQueryVo.getPriceSort();

        //2.判断拼装查询条件
        QueryWrapper<EduCourse> wrapper = new QueryWrapper<>();
        if (!StringUtils.isEmpty(subjectParentId)) {
            wrapper.eq("subject_parent_id", subjectParentId);
        }
        if (!StringUtils.isEmpty(subjectId)) {
            wrapper.eq("subject_id", subjectId);
        }
        if (!StringUtils.isEmpty(buyCountSort)) {
            wrapper.orderByDesc("buy_count");
        }
        if (!StringUtils.isEmpty(gmtCreateSort)) {
            wrapper.orderByDesc("gmt_create");
        }
        if (!StringUtils.isEmpty(priceSort)) {
            wrapper.orderByDesc("price");
        }

        //3.带条件分页查询
        baseMapper.selectPage(pageParam, wrapper);

        //4.封装查询结果
        List<EduCourse> records = pageParam.getRecords();
        long current = pageParam.getCurrent();
        long pages = pageParam.getPages();
        long size = pageParam.getSize();
        long total = pageParam.getTotal();
        boolean hasNext = pageParam.hasNext();
        boolean hasPrevious = pageParam.hasPrevious();

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("items", records);
        map.put("current", current);
        map.put("pages", pages);
        map.put("size", size);
        map.put("total", total);
        map.put("hasNext", hasNext);
        map.put("hasPrevious", hasPrevious);

        return map;

    }

    @Override
    public CourseWebVo getCourseWebVo(String id) {
        CourseWebVo courseWebVo = baseMapper.getCourseWeVo(id);
        return courseWebVo;
    }
}
