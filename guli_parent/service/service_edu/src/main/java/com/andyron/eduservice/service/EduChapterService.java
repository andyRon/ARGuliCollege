package com.andyron.eduservice.service;

import com.andyron.eduservice.entity.EduChapter;
import com.andyron.eduservice.entity.chapter.ChapterVo;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 课程 服务类
 * </p>
 *
 * @author andyron
 * @since 2022-07-21
 */
public interface EduChapterService extends IService<EduChapter> {

    List<ChapterVo> getChapterVideoByCourseId(String courseId);
}
