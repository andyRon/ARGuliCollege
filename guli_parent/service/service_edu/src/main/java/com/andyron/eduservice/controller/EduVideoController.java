package com.andyron.eduservice.controller;


import com.andyron.common.utils.R;
import com.andyron.eduservice.entity.EduVideo;
import com.andyron.eduservice.service.EduVideoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 课程视频 前端控制器
 * </p>
 *
 * @author andyron
 * @since 2022-07-21
 */
@Api(tags = "小节管理")
@RestController
@RequestMapping("/eduservice/video")
@CrossOrigin
public class EduVideoController {

    @Autowired
    EduVideoService videoService;

    @ApiOperation(value = "添加小节")
    @PostMapping("add")
    public R add(@RequestBody EduVideo video) {
        videoService.save(video);
        return R.ok();
    }

    @ApiOperation(value = "修改小节")
    @PostMapping("update")
    public R update(@RequestBody EduVideo video) {
        videoService.updateById(video);
        return R.ok();
    }

    @ApiOperation(value = "根据id获取小节")
    @GetMapping("getVideoById/{id}")
    public R getVideoById(@PathVariable String id) {
        EduVideo video = videoService.getById(id);
        return R.ok().data("video", video);
    }

    // todo 删除视频
    @ApiOperation(value = "删除小节")
    @DeleteMapping("{id}")
    public R delete(@PathVariable String id) {
        videoService.removeById(id);
        return R.ok();
    }

}

