package com.andyron.staservice.controller;


import com.andyron.common.utils.R;
import com.andyron.staservice.service.StatisticsDailyService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * <p>
 * 网站统计日数据 前端控制器
 * </p>
 *
 * @author andyron
 * @since 2023-04-13
 */
@Api(tags = "统计分析")
@RestController
@RequestMapping("/staservice/sta")
@CrossOrigin
public class StatisticsDailyController {
    @Autowired
    private StatisticsDailyService staService;

    @ApiOperation("统计某一天注册人数，生成统计数据")
    @PostMapping("registerCount/{day}")
    public R registerCount(@PathVariable String day) {
        staService.registerCount(day);
        return R.ok();
    }

    @ApiOperation("图表显示：日期json数组，数量json数组")
    @GetMapping("showData/{type}/{begin}/{end}")
    public R showData(@PathVariable String type, @PathVariable String begin,@PathVariable String end) {
        Map<String, Object> map = staService.getShowData(type, begin, end);
        return R.ok().data(map);
    }

}

