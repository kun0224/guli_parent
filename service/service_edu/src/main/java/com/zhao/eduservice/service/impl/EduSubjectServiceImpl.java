package com.zhao.eduservice.service.impl;

import com.alibaba.excel.EasyExcel;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zhao.baseservice.exception.GuliException;
import com.zhao.eduservice.entity.EduSubject;
import com.zhao.eduservice.entity.Vo.OneSubjectVo;
import com.zhao.eduservice.entity.Vo.TwoSubjectVo;
import com.zhao.eduservice.entity.excel.ExcelSubjectData;
import com.zhao.eduservice.listener.SubjectExcelListener;
import com.zhao.eduservice.mapper.EduSubjectMapper;
import com.zhao.eduservice.service.EduSubjectService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.ibatis.annotations.One;
import org.springframework.beans.BeanUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程科目 服务实现类
 * </p>
 *
 * @author zhao
 * @since 2020-09-24
 */
@Service
public class EduSubjectServiceImpl extends ServiceImpl<EduSubjectMapper, EduSubject> implements EduSubjectService {

    /**
     * excel表信息上传
     *
     * @param file
     * @param eduSubjectService
     */
    @Override
    public void addSubjectInfo(MultipartFile file, EduSubjectService eduSubjectService) {

        try {
            InputStream inputStream = file.getInputStream();
            EasyExcel.read(inputStream, ExcelSubjectData.class, new SubjectExcelListener(eduSubjectService)).sheet().doRead();
        } catch (IOException e) {
            e.printStackTrace();
            throw new GuliException(20001, "导入数据失败");
        }
    }

    /**
     * 查询所有课程分类信息
     *
     * @return
     */
    @Override
    public List<OneSubjectVo> getAllSubject() {

        //1.查询一级信息
        QueryWrapper<EduSubject> oneQueryWrapper = new QueryWrapper<>();
        oneQueryWrapper.eq("parent_id", "0");
        List<EduSubject> oneSubjectList = baseMapper.selectList(oneQueryWrapper);

        //2.查询二级信息
        QueryWrapper<EduSubject> twoQueryWrapper = new QueryWrapper<>();
        twoQueryWrapper.ne("parent_id", "0");
        List<EduSubject> twoSubjectList = baseMapper.selectList(twoQueryWrapper);

        //3.封装一级
        List<OneSubjectVo> oneSubjectVoList = new ArrayList<>();
        for (int i = 0; i < oneSubjectList.size(); i++) {
            //3.1.取出每一条一级
            EduSubject oneSubject = oneSubjectList.get(i);
            //3.2.把oneSubject转化为oneSubjectVo
            OneSubjectVo oneSubjectVo = new OneSubjectVo();
            BeanUtils.copyProperties(oneSubject, oneSubjectVo);
            oneSubjectVoList.add(oneSubjectVo);
            //4.封装二级
            List<TwoSubjectVo> twoSubjectVoList = new ArrayList<>();
            for (int m = 0; m < twoSubjectList.size(); m++) {
                //4.1.取出每一条一级
                EduSubject twoSubject = twoSubjectList.get(m);
                if (twoSubject.getParentId().equals(oneSubject.getId())){
                    TwoSubjectVo twoSubjectVo = new TwoSubjectVo();
                    BeanUtils.copyProperties(twoSubject, twoSubjectVo);
                    twoSubjectVoList.add(twoSubjectVo);
                }
            }
            oneSubjectVo.setChildren(twoSubjectVoList);
        }


        return oneSubjectVoList;
    }
}
