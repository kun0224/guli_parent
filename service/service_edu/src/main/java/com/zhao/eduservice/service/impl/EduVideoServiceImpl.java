package com.zhao.eduservice.service.impl;

import com.zhao.eduservice.client.VodClient;
import com.zhao.eduservice.entity.EduVideo;
import com.zhao.eduservice.mapper.EduVideoMapper;
import com.zhao.eduservice.service.EduVideoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 * <p>
 * 课程视频 服务实现类
 * </p>
 *
 * @author zhao
 * @since 2020-09-28
 */
@Service
public class EduVideoServiceImpl extends ServiceImpl<EduVideoMapper, EduVideo> implements EduVideoService {

    @Autowired
    private VodClient vodClient;

    @Override
    public void deleteVideo(String id) {
        EduVideo eduVideo = baseMapper.selectById(id);
        String videoSourceId = eduVideo.getVideoSourceId();
        if (!StringUtils.isEmpty(videoSourceId)){
            vodClient.deleteVideoById(videoSourceId);
        }
        baseMapper.deleteById(id);
    }
}
