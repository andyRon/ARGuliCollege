package com.andyron.eduservice.service;

import com.andyron.eduservice.entity.EduCourse;
import com.andyron.eduservice.entity.vo.CourseInfoVo;
import com.andyron.eduservice.entity.vo.CoursePublishVo;
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
}
