package com.andyron.eduservice.service;

import com.andyron.eduservice.entity.EduSubject;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.web.multipart.MultipartFile;

/**
 * <p>
 * 课程科目 服务类
 * </p>
 *
 * @author andyron
 * @since 2021-11-19
 */
public interface EduSubjectService extends IService<EduSubject> {

    /**
     * 添加课程分类
     */
    void saveSubject(MultipartFile file, EduSubjectService eduSubjectService);
}
