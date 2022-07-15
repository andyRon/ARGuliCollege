package com.andyron.eduservice.service.impl;

import com.alibaba.excel.EasyExcel;
import com.andyron.eduservice.entity.EduSubject;
import com.andyron.eduservice.entity.excel.SubjectData;
import com.andyron.eduservice.listener.SubjectExcelListenser;
import com.andyron.eduservice.mapper.EduSubjectMapper;
import com.andyron.eduservice.service.EduSubjectService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;

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
}
