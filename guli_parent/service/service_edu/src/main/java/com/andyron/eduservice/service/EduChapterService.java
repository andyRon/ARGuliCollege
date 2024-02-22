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
 */
public interface EduChapterService extends IService<EduChapter> {

    List<ChapterVo> getChapterVideoByCourseId(String courseId);

    /**
     * 删除章节
     * @param chapterId
     * @return
     */
    boolean deleteChapter(String chapterId);

    void removeChapterByCourseId(String courseId);
}
