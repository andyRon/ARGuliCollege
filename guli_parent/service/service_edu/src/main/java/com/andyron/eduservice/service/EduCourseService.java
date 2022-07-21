package com.andyron.eduservice.service;

import com.andyron.eduservice.entity.EduCourse;
import com.andyron.eduservice.entity.vo.CourseInfoVo;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 课程 服务类
 * </p>
 *
 * @author andyron
 * @since 2022-07-21
 */
public interface EduCourseService extends IService<EduCourse> {

    String saveCourseInfo(CourseInfoVo courseInfoVo);
}
