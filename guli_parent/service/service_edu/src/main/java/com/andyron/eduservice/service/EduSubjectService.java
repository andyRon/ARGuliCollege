package com.andyron.eduservice.service;

import com.andyron.eduservice.entity.EduSubject;
import com.andyron.eduservice.entity.subject.OneSubject;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * <p>
 * 课程科目 服务类
 * </p>
 *
 * @author andyron
 */
public interface EduSubjectService extends IService<EduSubject> {

    /**
     * 添加课程分类
     */
    void saveSubject(MultipartFile file, EduSubjectService eduSubjectService);

    List<OneSubject> getAllSubject();
}
