package com.andyron.eduservice.service.impl;

import com.andyron.eduservice.entity.EduVideo;
import com.andyron.eduservice.mapper.EduVideoMapper;
import com.andyron.eduservice.service.EduVideoService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 课程视频 服务实现类
 * </p>
 *
 * @author andyron
 * @since 2022-07-21
 */
@Service
public class EduVideoServiceImpl extends ServiceImpl<EduVideoMapper, EduVideo> implements EduVideoService {

    /**
     * 根据课程id删除小节
     * TODO 删除视频
     * @param courseId
     */
    @Override
    public void removeVideoByCourseId(String courseId) {
        QueryWrapper wrapper = new QueryWrapper<>();
        wrapper.eq("course_id", courseId);
        baseMapper.delete(wrapper);
    }
}
