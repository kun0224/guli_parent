package com.zhao.eduservice.service;

import com.zhao.eduservice.entity.EduChapter;
import com.baomidou.mybatisplus.extension.service.IService;
import com.zhao.eduservice.entity.Vo.ChapterVo;

import java.util.List;

/**
 * <p>
 * 课程 服务类
 * </p>
 *
 * @author zhao
 * @since 2020-09-28
 */
public interface EduChapterService extends IService<EduChapter> {

    List<ChapterVo> getChapterVideoById(String courseId);
}
