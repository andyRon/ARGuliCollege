package com.andyron.vod.controller;

import com.andyron.common.utils.R;
import com.andyron.vod.service.VodService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Api(tags = "视频点播")
@RestController
@RequestMapping("/eduvod/video")
@CrossOrigin
public class VodController {
    @Autowired
    private VodService vodService;

    @ApiOperation(value = "上传视频到阿里云")
    @PostMapping("uploadAlyiVideo")
    public R uploadVideoByAliyun(MultipartFile file) {
        String videoId = vodService.uploadAlyiVideo(file);
        return R.ok().data("videoId", videoId);
    }


}
