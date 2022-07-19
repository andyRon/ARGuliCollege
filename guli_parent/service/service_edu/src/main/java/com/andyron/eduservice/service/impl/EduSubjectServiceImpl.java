package com.andyron.eduservice.service.impl;

import com.alibaba.excel.EasyExcel;
import com.andyron.eduservice.entity.EduSubject;
import com.andyron.eduservice.entity.excel.SubjectData;
import com.andyron.eduservice.entity.subject.OneSubject;
import com.andyron.eduservice.entity.subject.TwoSubject;
import com.andyron.eduservice.listener.SubjectExcelListenser;
import com.andyron.eduservice.mapper.EduSubjectMapper;
import com.andyron.eduservice.service.EduSubjectService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程科目 服务实现类
 * </p>
 *
 * @author andyron
 * @since 2021-11-19
 */
@Service
public class EduSubjectServiceImpl extends ServiceImpl<EduSubjectMapper, EduSubject> implements EduSubjectService {

    @Override
    public void saveSubject(MultipartFile file, EduSubjectService eduSubjectService) {
        try {
            // 文件输入流
            InputStream in = file.getInputStream();
            EasyExcel.read(in, SubjectData.class, new SubjectExcelListenser(eduSubjectService)).sheet().doRead();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 树形数据形式返回
     */
    @Override
    public List<OneSubject> getAllSubject() {
        // 1 查询所有一级分类
        QueryWrapper<EduSubject> oneWrapper = new QueryWrapper<>();
        oneWrapper.eq("parent_id", "0");
        List<EduSubject> oneSubjects = baseMapper.selectList(oneWrapper);

        // 2 查询所有二级分类
        QueryWrapper<EduSubject> twoWrapper = new QueryWrapper<>();
        twoWrapper.ne("parent_id", "0");
        List<EduSubject> twoSubjects = baseMapper.selectList(twoWrapper);

        // 存储最终封装数据
        List<OneSubject> finalSubjects = new ArrayList<>();
        // 3 封装一级分类
        for (EduSubject subject : oneSubjects) {
            OneSubject oneSubject = new OneSubject();
            // 使用spring中的工具类，把一个对象的属性复制到另一个对象（目标对象有的属性）
            BeanUtils.copyProperties(subject, oneSubject);

            // 4 封装二级分类
            for (EduSubject tSubject : twoSubjects) {
                if (subject.getId().equals(tSubject.getParentId())) {
                    TwoSubject twoSubject = new TwoSubject();
                    BeanUtils.copyProperties(tSubject, twoSubject);
                    oneSubject.getChildren().add(twoSubject);
                }
            }
            finalSubjects.add(oneSubject);
        }

        return finalSubjects;
    }
}
