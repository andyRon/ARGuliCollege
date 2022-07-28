package com.andyron.eduservice.controller;


import com.andyron.common.utils.R;
import com.andyron.eduservice.entity.EduCourse;
import com.andyron.eduservice.entity.vo.CourseInfoVo;
import com.andyron.eduservice.entity.vo.CoursePublishVo;
import com.andyron.eduservice.entity.vo.CourseQuery;
import com.andyron.eduservice.service.EduCourseService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 课程 前端控制器
 * </p>
 *
 * @author andyron
 * @since 2022-07-21
 */
@Api(tags = "课程管理")
@RestController
@RequestMapping("/eduservice/course")
@CrossOrigin
public class EduCourseController {

    @Autowired
    private EduCourseService courseService;

    @ApiOperation(value = "课程条件分页查询")
    @PostMapping("getCourseCondition/{current}/{size}")
    public R getCourseCondition(@PathVariable long current,
                                @PathVariable long size,
                                @RequestBody(required = false) CourseQuery courseQuery) {
        Page<EduCourse> pageCourse = new Page<>(current, size);
        QueryWrapper wrapper = new QueryWrapper<>();
        String title = courseQuery.getTitle();
        String status = courseQuery.getStatus();
        if (!StringUtils.isEmpty(title)) {
            wrapper.like("title", title);
        }
        if (!StringUtils.isEmpty(status)) {
            wrapper.eq("status", status);
        }
        wrapper.orderByDesc("gmt_create");
        courseService.page(pageCourse, wrapper);

        long total = pageCourse.getTotal();
        List<EduCourse> records = pageCourse.getRecords();
        return R.ok().data("total", total).data("rows", records);
    }

    @ApiOperation(value = "添加课程信息")
    @PostMapping("addCourseInfo")
    public R addCourseInfo(@RequestBody CourseInfoVo courseInfoVo) {
        String id = courseService.saveCourseInfo(courseInfoVo);
        return R.ok().data("courseId", id);
    }

    @ApiOperation(value = "根据课程id查询课程基本信息")
    @GetMapping("getCourseInfoById/{courseId}")
    public R getCourseInfoById(@PathVariable String courseId) {
        CourseInfoVo courseInfoVo = courseService.getCourseInfoById(courseId);
        return R.ok().data("courseInfoVo", courseInfoVo);
    }

    @ApiOperation(value = "修改课程信息接口")
    @PostMapping("updateCourseInfo")
    public R updateCourseInfo(@RequestBody CourseInfoVo courseInfoVo) {
        courseService.updateCourseInfo(courseInfoVo);
        return R.ok();
    }

    @ApiOperation(value = "根据课程id获取课程发布信息")
    @GetMapping("getPublishCourseInfo/{courseId}")
    public R getPublishCourseInfo(@PathVariable String courseId) {
        CoursePublishVo coursePublish = courseService.getPublishCourseInfo(courseId);
        return R.ok().data("publishCourse", coursePublish);
    }

    @ApiOperation(value = "课程最终发布")
    @PostMapping("publishCourse/{courseId}")
    public R publishCourse(@PathVariable String courseId) {
        EduCourse course = new EduCourse();
        course.setId(courseId);
        course.setStatus(EduCourse.STATUS_NORMAL);
        courseService.updateById(course);
        return R.ok();
    }

    /**
     *
     */
    @ApiOperation(value = "删除课程")
    @DeleteMapping("deleteCourse/{courseId}")
    public R deleteCourse(@PathVariable String courseId) {
        courseService.removeCourse(courseId);
        return R.ok();
    }
}

