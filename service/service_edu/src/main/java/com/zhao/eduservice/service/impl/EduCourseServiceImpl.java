package com.zhao.eduservice.service.impl;

import com.zhao.baseservice.exception.GuliException;
import com.zhao.eduservice.entity.EduCourse;
import com.zhao.eduservice.entity.EduCourseDescription;
import com.zhao.eduservice.entity.Vo.CourseInfoForm;
import com.zhao.eduservice.entity.Vo.CoursePublishVo;
import com.zhao.eduservice.mapper.EduCourseMapper;
import com.zhao.eduservice.service.EduCourseDescriptionService;
import com.zhao.eduservice.service.EduCourseService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
     * @param id
     * @return
     */
    @Override
    public CoursePublishVo getCoursePublishById(String id) {
        CoursePublishVo coursePublishVo = baseMapper.getCoursePublishById(id);
        return coursePublishVo;
    }
}
