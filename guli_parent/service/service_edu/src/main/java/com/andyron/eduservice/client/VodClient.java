package com.andyron.eduservice.client;

import com.andyron.common.utils.R;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 *
 * VodClient这个接口没有具体的实现类；VodClient注入到Controller里，可以把它的实现类理解为根据调用服务名称远程调用service-vod的方法
 * @Author: andyron
 **/
@FeignClient(name = "service-vod", fallback = VodFileDegradeFeignClient.class) // 指定调用的服务名称
@Component
public interface VodClient {

    /**
     * 方法路径要完整路径
     * `@PathVariable`注解一定要指定参数名称，否则出错
     */
    @DeleteMapping("/eduvod/video/deleteAliVideo/{id}")
    public R deleteAliVideo(@PathVariable("id") String id);

    /**
     *
     */
    @DeleteMapping("/eduvod/video/delete-batch")
    public R deleteBatch(@RequestParam("videoIdList") List<String> videoIdList);
}
