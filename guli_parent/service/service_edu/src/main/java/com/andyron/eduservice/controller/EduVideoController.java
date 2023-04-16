package com.andyron.eduservice.controller;


import com.andyron.common.utils.R;
import com.andyron.eduservice.client.VodClient;
import com.andyron.eduservice.entity.EduVideo;
import com.andyron.eduservice.service.EduVideoService;
import com.andyron.servicebase.exceptionhandler.GuliException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
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
//@CrossOrigin
public class EduVideoController {

    @Autowired
    private EduVideoService videoService;

    @Autowired
    private VodClient vodClient;

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

    @ApiOperation(value = "删除小节、删除对应阿里云视频")
    @DeleteMapping("{id}")
    public R delete(@PathVariable String id) {
        // 根据小节id获得视频id
        String videoSourceId = videoService.getById(id).getVideoSourceId();
        if (!StringUtils.isEmpty(videoSourceId)) {
            // 根据视频id，远程调用实现删除视频
            vodClient.deleteAliVideo(videoSourceId);
         }
        videoService.removeById(id);
        return R.ok();
    }

}

