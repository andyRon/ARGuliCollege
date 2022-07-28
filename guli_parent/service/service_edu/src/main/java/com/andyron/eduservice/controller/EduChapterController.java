package com.andyron.eduservice.controller;


import com.andyron.common.utils.R;
import com.andyron.eduservice.entity.EduChapter;
import com.andyron.eduservice.entity.chapter.ChapterVo;
import com.andyron.eduservice.service.EduChapterService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
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
@Api(tags = "课程章节和小节")
@RestController
@RequestMapping("/eduservice/chapter")
@CrossOrigin
public class EduChapterController {

    @Autowired
    private EduChapterService chapterService;

    @ApiOperation(value = "获取课程的章节和小节")
    @GetMapping("getChapterVideo/{courseId}")
    public R getChapterVideo(@PathVariable String courseId) {
        List<ChapterVo> list = chapterService.getChapterVideoByCourseId(courseId);
        return R.ok().data("allChapterVideo", list);
    }

    @ApiOperation(value = "添加章节")
    @PostMapping("addChapter")
    public R addChapter(@RequestBody EduChapter chapter) {
        chapterService.save(chapter);
        return R.ok();
    }

    @ApiOperation(value = "根据Id获取章节")
    @GetMapping("getChapterById/{chapterId}")
    public R getChapterById(@PathVariable String chapterId) {
        EduChapter chapter = chapterService.getById(chapterId);
        return R.ok().data("chapter", chapter);
    }

    @ApiOperation(value = "修改章节")
    @PostMapping("updateChapter")
    public R updateChapter(@RequestBody EduChapter chapter) {
        chapterService.updateById(chapter);
        return R.ok();
    }

    @ApiOperation(value = "删除章节")
    @DeleteMapping("{chapterId}")
    public R deleteChapter(@PathVariable String chapterId) {
        boolean res = chapterService.deleteChapter(chapterId);
        if (res) {
            return R.ok();
        } else {
            return R.error();
        }

    }
}

