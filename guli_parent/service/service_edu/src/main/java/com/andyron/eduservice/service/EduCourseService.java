package com.andyron.eduservice.service;

import com.andyron.eduservice.entity.EduCourse;
import com.andyron.eduservice.entity.frontvo.CourseFrontVo;
import com.andyron.eduservice.entity.frontvo.CourseWebVo;
import com.andyron.eduservice.entity.vo.CourseInfoVo;
import com.andyron.eduservice.entity.vo.CoursePublishVo;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

/**
 * <p>
 * 课程 服务类
 * </p>
 *
 * @author andyron
 */
public interface EduCourseService extends IService<EduCourse> {
    /**
     * 添加课程信息
     * @param courseInfoVo
     * @return
     */
    String saveCourseInfo(CourseInfoVo courseInfoVo);

    /**
     * 根据课程id查询课程基本信息
     * @param courseId
     * @return
     */
    CourseInfoVo getCourseInfoById(String courseId);

    /**
     * 修改课程信息接口
     * @param courseInfoVo
     */
    void updateCourseInfo(CourseInfoVo courseInfoVo);

    CoursePublishVo getPublishCourseInfo(String courseId);

    void removeCourse(String courseId);

    /**
     * 条件查询带分页查询课程前台
     */
    Map<String, Object> getCourseFrontList(Page<EduCourse> pageParam, CourseFrontVo courseFrontVo);

    /**
     * 根据课程id，编写sql语句查询课程信息
     */
    CourseWebVo getBaseCourseInfo(String courseId);
}
