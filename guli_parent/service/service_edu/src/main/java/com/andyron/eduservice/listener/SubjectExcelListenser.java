package com.andyron.eduservice.listener;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.andyron.eduservice.entity.EduSubject;
import com.andyron.eduservice.entity.excel.SubjectData;
import com.andyron.eduservice.service.EduSubjectService;
import com.andyron.servicebase.exceptionhandler.GuliException;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import java.util.Map;

public class SubjectExcelListenser extends AnalysisEventListener<SubjectData> {
    /*
        因为SubjectExcelListenser不能交给spring管理，需要自己new，不能通过@Autowire注入其它对象；
        数据库操作对象，通过有参构造注入
     */
    public EduSubjectService eduSubjectService;
    public SubjectExcelListenser() {
    }
    public SubjectExcelListenser(EduSubjectService eduSubjectService) {
        this.eduSubjectService = eduSubjectService;
    }

    @Override
    public void invoke(SubjectData subjectData, AnalysisContext analysisContext) {
        if (subjectData == null) {
            throw new GuliException(20001, "文件数据为空");
        }

        // 一行一行读取，每次读取两个值，第一个值是一级分类，第二个值是二级分类
        EduSubject existOneSubject = existOneSubject(eduSubjectService, subjectData.getOneSubjectName());
        // 没有一级分类，就添加
        if (existOneSubject == null) {
            existOneSubject = new EduSubject();
            existOneSubject.setTitle(subjectData.getOneSubjectName());
            existOneSubject.setParentId("0");
            eduSubjectService.save(existOneSubject);
        }

        String pid = existOneSubject.getId();
        EduSubject existTwoSubject = existTwoSubject(eduSubjectService, subjectData.getTwoSubjectName(), pid);
        if (existTwoSubject == null) {
            existTwoSubject = new EduSubject();
            existTwoSubject.setParentId(pid);
            existTwoSubject.setTitle(subjectData.getTwoSubjectName());
            eduSubjectService.save(existTwoSubject);
        }

    }

    /**
     * 判断一级分类不能重复添加
     */
    private EduSubject existOneSubject(EduSubjectService eduSubjectService, String name) {
        QueryWrapper<EduSubject> wrapper = new QueryWrapper<>();
        wrapper.eq("title", name);
        wrapper.eq("parent_id", "0");
        return eduSubjectService.getOne(wrapper);
    }

    /**
     * 判断一级分类不能重复添加
     */
    private EduSubject existTwoSubject(EduSubjectService eduSubjectService, String name, String pid) {
        QueryWrapper<EduSubject> wrapper = new QueryWrapper<>();
        wrapper.eq("title", name);
        wrapper.eq("parent_id", pid);
        return eduSubjectService.getOne(wrapper);
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {
    }
}
