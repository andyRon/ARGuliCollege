package com.andyron.eduservice.service;

import com.andyron.eduservice.entity.EduCourse;
import com.andyron.eduservice.entity.EduTeacher;
import com.andyron.eduservice.entity.frontvo.CourseFrontVo;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

/**
 * <p>
 * 讲师 服务类
 * </p>
 *
 * @author andyron
 * @since 2021-11-07
 */
public interface EduTeacherService extends IService<EduTeacher> {

    Map<String, Object> getTeacherFrontList(Page<EduTeacher> pageParam);

}
