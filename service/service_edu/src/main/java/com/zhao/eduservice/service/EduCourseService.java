package com.zhao.eduservice.service;

import com.zhao.eduservice.entity.EduCourse;
import com.baomidou.mybatisplus.extension.service.IService;
import com.zhao.eduservice.entity.Vo.CourseInfoForm;

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
}
