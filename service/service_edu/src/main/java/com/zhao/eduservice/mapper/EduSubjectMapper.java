package com.zhao.eduservice.mapper;

import com.zhao.eduservice.entity.EduSubject;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zhao.eduservice.entity.excel.ExcelSubjectData;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * <p>
 * 课程科目 Mapper 接口
 * </p>
 *
 * @author zhao
 * @since 2020-09-24
 */
@Component
public interface EduSubjectMapper extends BaseMapper<EduSubject> {
    @Select("select `id` `oneSubjectName`,`title` `twoSubjectName` from edu_subject")
    List<ExcelSubjectData> getEduSubjectList();
}
