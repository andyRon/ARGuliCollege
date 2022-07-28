package com.andyron.eduservice.service.impl;

import com.andyron.eduservice.entity.EduCourse;
import com.andyron.eduservice.entity.EduCourseDescription;
import com.andyron.eduservice.entity.vo.CourseInfoVo;
import com.andyron.eduservice.entity.vo.CoursePublishVo;
import com.andyron.eduservice.mapper.EduCourseMapper;
import com.andyron.eduservice.service.EduChapterService;
import com.andyron.eduservice.service.EduCourseDescriptionService;
import com.andyron.eduservice.service.EduCourseService;
import com.andyron.eduservice.service.EduVideoService;
import com.andyron.servicebase.exceptionhandler.GuliException;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 课程 服务实现类
 * </p>
 *
 * @author andyron
 * @since 2022-07-21
 */
@Service
public class EduCourseServiceImpl extends ServiceImpl<EduCourseMapper, EduCourse> implements EduCourseService {

    @Autowired
    private EduCourseDescriptionService courseDescriptionService;

    @Autowired
    private EduChapterService chapterService;

    @Autowired
    private EduVideoService videoService;

    @Override
    public String saveCourseInfo(CourseInfoVo courseInfoVo) {
        // 1 向课程表中添加课程基本信息
        EduCourse course = new EduCourse();
        BeanUtils.copyProperties(courseInfoVo, course);
        if (baseMapper.insert(course) == 0) {
            throw new GuliException(20001, "添加课程信息失败");
        }
        // 2 向课程简介表中添加简介
        EduCourseDescription courseDescription = new EduCourseDescription();
        // 一对一关系，id要相同
        String cid = course.getId();
        courseDescription.setId(cid);
        courseDescription.setDescription(courseInfoVo.getDescription());
        courseDescriptionService.save(courseDescription);

        return cid;
    }

    @Override
    public CourseInfoVo getCourseInfoById(String courseId) {
        EduCourse course = baseMapper.selectById(courseId);
        CourseInfoVo courseInfoVo = new CourseInfoVo();
        BeanUtils.copyProperties(course, courseInfoVo);

        EduCourseDescription courseDescription = courseDescriptionService.getById(courseId);
        courseInfoVo.setDescription(courseDescription.getDescription());

        return courseInfoVo;
    }

    @Override
    public void updateCourseInfo(CourseInfoVo courseInfoVo) {
        EduCourse course = new EduCourse();
        BeanUtils.copyProperties(courseInfoVo, course);
        int update = baseMapper.updateById(course);
        if (update == 0) {
            throw new GuliException(20001, "修改课程信息失败");
        }

        EduCourseDescription description = new EduCourseDescription();
        description.setId(course.getId());
        description.setDescription(courseInfoVo.getDescription());
        courseDescriptionService.updateById(description);
    }

    @Override
    public CoursePublishVo getPublishCourseInfo(String courseId) {
        return baseMapper.getPublishCourseInfo(courseId);
    }

    /**
     *
     */
    @Override
    public void removeCourse(String courseId) {
        // 1 删除小节
        videoService.removeVideoByCourseId(courseId);
        // 2 删除章节
        chapterService.removeChapterByCourseId(courseId);
        // 3 删除课程描述
        courseDescriptionService.removeById(courseId);
        // 4 删除课程
        int i = baseMapper.deleteById(courseId);
        if (i == 0) {
            throw new GuliException(20001, "删除失败");
        }
    }
}
