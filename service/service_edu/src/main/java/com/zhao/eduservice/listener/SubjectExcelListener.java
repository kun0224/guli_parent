package com.zhao.eduservice.listener;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zhao.baseservice.exception.GuliException;
import com.zhao.eduservice.entity.EduSubject;
import com.zhao.eduservice.entity.excel.ExcelSubjectData;
import com.zhao.eduservice.service.EduSubjectService;


public class SubjectExcelListener extends AnalysisEventListener<ExcelSubjectData> {

    private EduSubjectService eduSubjectService;

    public SubjectExcelListener() {

    }

    public SubjectExcelListener(EduSubjectService subjectService){
        this.eduSubjectService = subjectService;
    }

    @Override
    public void invoke(ExcelSubjectData excelSubjectData, AnalysisContext analysisContext) {
        //1.获取一行数据，判断此行数据是否为空
        if(excelSubjectData == null){
            throw new GuliException(20001, "导入数据失败");
        }
        //2.判断一级是否存在
        EduSubject eduSubject = this.existOneSubjiect(eduSubjectService, excelSubjectData.getOneSubjectName());
        //2.1.一级不存在则添加一级
        if (eduSubject == null) {
            eduSubject = new EduSubject();
            eduSubject.setParentId("0");
            eduSubject.setTitle(excelSubjectData.getOneSubjectName());
            eduSubjectService.save(eduSubject);
        }

        //3.判断二级是否存在
        String pid = eduSubject.getId();
        EduSubject twoSubjiect = this.existTwoSubjiect(eduSubjectService, excelSubjectData.getTwoSubjectName(), pid);
        //3.1.二级不存在则添加二级
        if (twoSubjiect == null){
            twoSubjiect = new EduSubject();
            twoSubjiect.setParentId(pid);
            twoSubjiect.setTitle(excelSubjectData.getTwoSubjectName());
            eduSubjectService.save(twoSubjiect);
        }
    }

    //判断一级分类是否存在
    private EduSubject existOneSubjiect(EduSubjectService eduSubjectService, String name){
        QueryWrapper<EduSubject> wrapper = new QueryWrapper<>();
        wrapper.eq("parent_id", "0");
        wrapper.eq("title", name);
        EduSubject subject = eduSubjectService.getOne(wrapper);
        return subject;
    }

    private EduSubject existTwoSubjiect(EduSubjectService eduSubjectService, String name, String pid){
        QueryWrapper<EduSubject> wrapper = new QueryWrapper<>();
        wrapper.eq("parent_id", pid);
        wrapper.eq("title", name);
        EduSubject subject = eduSubjectService.getOne(wrapper);
        return subject;
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {

    }
}
