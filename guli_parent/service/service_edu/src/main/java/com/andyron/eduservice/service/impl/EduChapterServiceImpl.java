package com.andyron.eduservice.service.impl;

import com.andyron.eduservice.entity.EduChapter;
import com.andyron.eduservice.entity.EduCourse;
import com.andyron.eduservice.entity.EduVideo;
import com.andyron.eduservice.entity.chapter.ChapterVo;
import com.andyron.eduservice.entity.chapter.VideoVo;
import com.andyron.eduservice.mapper.EduChapterMapper;
import com.andyron.eduservice.service.EduChapterService;
import com.andyron.eduservice.service.EduCourseService;
import com.andyron.eduservice.service.EduVideoService;
import com.andyron.servicebase.exceptionhandler.GuliException;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * <p>
 * 课程 服务实现类
 * </p>
 *
 * @author andyron
 */
@Service
public class EduChapterServiceImpl extends ServiceImpl<EduChapterMapper, EduChapter> implements EduChapterService {

    @Autowired
    EduVideoService videoService;

    @Override
    public List<ChapterVo> getChapterVideoByCourseId(String courseId) {
        // 1 根据课程id查询课程中所有章节
        QueryWrapper<EduChapter> chapterQueryWrapper = new QueryWrapper<>();
        chapterQueryWrapper.eq("course_id", courseId);
        List<EduChapter> chapterList = baseMapper.selectList(chapterQueryWrapper);

        // 2 根据课程id查询课程中所有小节
        QueryWrapper<EduVideo> videoQueryWrapper = new QueryWrapper<>();
        videoQueryWrapper.eq("course_id", courseId);
        List<EduVideo> videoList = videoService.list(videoQueryWrapper);

        // 3 遍历章节list集合进行封装
        List<ChapterVo> chapterVoList = new ArrayList<>();
        for (EduChapter eduChapter : chapterList) {
            ChapterVo chapterVo = new ChapterVo();
            BeanUtils.copyProperties(eduChapter, chapterVo);

            // 4 遍历小节list集合进行封装
            List<VideoVo> videoVoList = new ArrayList<>();
            for (EduVideo eduVideo : videoList) {
                if (eduChapter.getId().equals(eduVideo.getChapterId())) {
                    VideoVo videoVo = new VideoVo();
                    BeanUtils.copyProperties(eduVideo, videoVo);
                    videoVoList.add(videoVo);
                }
            }
            chapterVo.setChildren(videoVoList);
            chapterVoList.add(chapterVo);
        }

        return chapterVoList;
    }

    /**
     * 🔖 比较getChapterVideoByCourseId2和getChapterVideoByCourseId执行效率差距有多大？
     */
//    @Override
    public List<ChapterVo> getChapterVideoByCourseId2(String courseId) {
        QueryWrapper<EduChapter> chapterQueryWrapper = new QueryWrapper<>();
        chapterQueryWrapper.eq("course_id", courseId);
        List<EduChapter> eduChapters = baseMapper.selectList(chapterQueryWrapper);

        List<ChapterVo> chapterVoList = new ArrayList<>();
        QueryWrapper<EduVideo> videoQueryWrapper = new QueryWrapper<>();
        videoQueryWrapper.eq("course_id", courseId);
        for (EduChapter eduChapter : eduChapters) {
            ChapterVo chapterVo = new ChapterVo();
            BeanUtils.copyProperties(eduChapter, chapterVo);

            videoQueryWrapper.eq("chapter_id", eduChapter.getId());
            List<EduVideo> videoList = videoService.list(videoQueryWrapper);
            List<VideoVo> videoVoList = new ArrayList<>();
            for (EduVideo eduVideo : videoList) {
                VideoVo videoVo = new VideoVo();
                BeanUtils.copyProperties(eduVideo, videoVo);
                videoVoList.add(videoVo);
            }
            chapterVo.setChildren(videoVoList);
            chapterVoList.add(chapterVo);
        }

        return chapterVoList;
    }

    @Override
    public boolean deleteChapter(String chapterId) {
        QueryWrapper wrapper = new QueryWrapper<>();
        wrapper.eq("chapter_id", chapterId);
        if (videoService.count(wrapper) > 0) {
            throw new GuliException(20001, "章节不能删除");
        } else {
            return baseMapper.deleteById(chapterId) > 0;
        }
    }

    /**
     * 根据课程id删除章节
     * @param courseId
     */
    @Override
    public void removeChapterByCourseId(String courseId) {
        QueryWrapper wrapper = new QueryWrapper<>();
        wrapper.eq("course_id", courseId);
        baseMapper.delete(wrapper);
    }
}
