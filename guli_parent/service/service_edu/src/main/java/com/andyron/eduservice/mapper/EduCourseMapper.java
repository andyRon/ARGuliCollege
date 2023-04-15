package com.andyron.eduservice.mapper;

import com.andyron.eduservice.entity.EduCourse;
import com.andyron.eduservice.entity.frontvo.CourseWebVo;
import com.andyron.eduservice.entity.vo.CoursePublishVo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * <p>
 * 课程 Mapper 接口
 * </p>
 *
 * @author andyron
 * @since 2022-07-21
 */
public interface EduCourseMapper extends BaseMapper<EduCourse> {

    public CoursePublishVo getPublishCourseInfo(String courseId);

    CourseWebVo getBaseCourseInfo(String courseId);
}
