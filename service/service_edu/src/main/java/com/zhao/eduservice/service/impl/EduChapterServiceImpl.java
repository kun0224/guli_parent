package com.zhao.eduservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zhao.eduservice.entity.EduChapter;
import com.zhao.eduservice.entity.EduVideo;
import com.zhao.eduservice.entity.Vo.ChapterVo;
import com.zhao.eduservice.entity.Vo.VideoVo;
import com.zhao.eduservice.mapper.EduChapterMapper;
import com.zhao.eduservice.service.EduChapterService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zhao.eduservice.service.EduVideoService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程 服务实现类
 * </p>
 *
 * @author zhao
 * @since 2020-09-28
 */
@Service
public class EduChapterServiceImpl extends ServiceImpl<EduChapterMapper, EduChapter> implements EduChapterService {

    @Autowired
    EduVideoService eduVideoService;

    @Override
    public List<ChapterVo> getChapterVideoById(String courseId) {

        //1.根据课程id查询章节信息
        QueryWrapper<EduChapter> chapterWrapper = new QueryWrapper<>();
        chapterWrapper.eq("course_id", courseId);
        List<EduChapter> eduChapterList = baseMapper.selectList(chapterWrapper);

        //2.根据课程id查询小节信息
        QueryWrapper<EduVideo> VideoWrapper = new QueryWrapper<>();
        VideoWrapper.eq("course_id", courseId);
        List<EduVideo> eduVideoList = eduVideoService.list(VideoWrapper);

        //3.封装章节信息
        List<ChapterVo> chapterVideoList = new ArrayList<>();
        for (int i = 0; i < eduChapterList.size(); i++) {
            //3.1 取出每一个章节
            EduChapter eduChapter = eduChapterList.get(i);
            //3.2 eduChapter 转化为 ChapterVo
            ChapterVo chapterVo = new ChapterVo();
            BeanUtils.copyProperties(eduChapter, chapterVo);
            //4.封装小节信息
            //4.1 创建小节结点
            List<VideoVo> videoVoList = new ArrayList<>();
            //4.2 遍历小节集合
            for (int m = 0; m < eduVideoList.size(); m++) {
                EduVideo eduVideo = eduVideoList.get(m);
                //4.3 判断小节和章节的关系
                if (eduVideo.getChapterId().equals(eduChapter.getId())){
                    //将eduVideo转化为VideoVo
                    VideoVo videoVo = new VideoVo();
                    BeanUtils.copyProperties(eduVideo, videoVo);
                    //将videoVo存入集合中
                    videoVoList.add(videoVo);
                }
            }
            //将小节集合存入到对应的章节对象中
            chapterVo.setChildren(videoVoList);
            //3.3 chapterVo存入集合中
            chapterVideoList.add(chapterVo);
        }
        return chapterVideoList;
    }
}
