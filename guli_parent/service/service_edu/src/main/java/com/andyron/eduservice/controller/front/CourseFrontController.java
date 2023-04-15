package com.andyron.eduservice.controller.front;

import com.andyron.common.utils.R;
import com.andyron.eduservice.entity.EduCourse;
import com.andyron.eduservice.entity.EduTeacher;
import com.andyron.eduservice.entity.chapter.ChapterVo;
import com.andyron.eduservice.entity.frontvo.CourseFrontVo;
import com.andyron.eduservice.entity.frontvo.CourseWebVo;
import com.andyron.eduservice.service.EduChapterService;
import com.andyron.eduservice.service.EduCourseService;
import com.andyron.eduservice.service.EduTeacherService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * @author andyron
 **/
@Api(tags = "前台课程管理")
@RestController
@RequestMapping("/eduservice/coursefront")
@CrossOrigin
public class CourseFrontController {
    @Autowired
    private EduTeacherService teacherService;
    @Autowired
    private EduCourseService courseService;
    @Autowired
    private EduChapterService chapterService;

    @ApiOperation("课程列表")
    @PostMapping("/getCourseFrontList/{page}/{limit}")
    public R getCourseFrontList(@PathVariable long page, @PathVariable long limit,
                                @RequestBody(required = false) CourseFrontVo courseFrontVo) {
        Page<EduCourse> pageCourse = new Page<>(page, limit);
        Map<String, Object> map = courseService.getCourseFrontList(pageCourse, courseFrontVo);

        return R.ok().data(map);
    }

    @ApiOperation("课程详情")
    @GetMapping("/getCourseFrontInfo/{courseId}")
    public R getCourseFrontInfo(@PathVariable String courseId) {
        // 课程信息
        CourseWebVo courseWebVo = courseService.getBaseCourseInfo(courseId);
        // 章节和小结
        List<ChapterVo> chapterVideoList = chapterService.getChapterVideoByCourseId(courseId);
        return R.ok().data("courseWebVo", courseWebVo).data("chapterVideoList", chapterVideoList);
    }
}
