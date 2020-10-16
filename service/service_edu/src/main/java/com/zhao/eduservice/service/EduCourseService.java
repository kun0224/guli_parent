package com.zhao.eduservice.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zhao.eduservice.entity.EduCourse;
import com.baomidou.mybatisplus.extension.service.IService;
import com.zhao.eduservice.entity.Vo.CourseInfoForm;
import com.zhao.eduservice.entity.Vo.CoursePublishVo;
import com.zhao.eduservice.entity.Vo.CourseQueryVo;
import com.zhao.eduservice.entity.Vo.CourseWebVo;

import java.util.Map;

/**
 * <p>
 * 课程 服务类
 * </p>
 *
 * @author zhao
 * @since 2020-09-27
 */
public interface EduCourseService extends IService<EduCourse> {

    String addCourseInfo(CourseInfoForm courseInfoForm);

    CourseInfoForm getCourseInfoById(String id);

    void updateCourseInfo(CourseInfoForm courseInfoForm);

    CoursePublishVo getCoursePublishById(String id);

    void deleteCourseInfo(String courseId);

    Map<String, Object> getCoursePageVo(Page<EduCourse> pageParam, CourseQueryVo courseQueryVo);

    CourseWebVo getCourseWebVo(String id);
}
