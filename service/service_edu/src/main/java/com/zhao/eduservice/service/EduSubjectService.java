package com.zhao.eduservice.service;

import com.zhao.eduservice.entity.EduSubject;
import com.baomidou.mybatisplus.extension.service.IService;
import com.zhao.eduservice.entity.Vo.OneSubjectVo;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * <p>
 * 课程科目 服务类
 * </p>
 *
 * @author zhao
 * @since 2020-09-24
 */
public interface EduSubjectService extends IService<EduSubject> {


    void addSubjectInfo(MultipartFile file, EduSubjectService eduSubjectService);

    List<OneSubjectVo> getAllSubject();

}
