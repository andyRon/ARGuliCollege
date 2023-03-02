package com.andyron.eduservice.client;

import com.andyron.common.utils.R;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 出错之后执行
 * @Author: andyron
 **/
@Component
public class VodFileDegradeFeignClient implements VodClient {
    @Override
    public R deleteAliVideo(String id) {
        return R.error().message("删除视频出错了");
    }

    @Override
    public R deleteBatch(List<String> videoIdList) {
        return R.error().message("删除多个视频出错了");
    }
}
