package com.zhao.eduservice.service;

import com.zhao.eduservice.entity.EduVideo;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 课程视频 服务类
 * </p>
 *
 * @author zhao
 * @since 2020-09-28
 */
public interface EduVideoService extends IService<EduVideo> {

    void deleteVideo(String id);
}
