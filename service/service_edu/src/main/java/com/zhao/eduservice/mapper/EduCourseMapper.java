package com.zhao.eduservice.mapper;

import com.zhao.eduservice.entity.EduCourse;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zhao.eduservice.entity.Vo.CoursePublishVo;
import com.zhao.eduservice.entity.Vo.CourseWebVo;

/**
 * <p>
 * 课程 Mapper 接口
 * </p>
 *
 * @author zhao
 * @since 2020-09-27
 */
public interface EduCourseMapper extends BaseMapper<EduCourse> {

    CoursePublishVo getCoursePublishById(String id);

    CourseWebVo getCourseWeVo(String id);
}
