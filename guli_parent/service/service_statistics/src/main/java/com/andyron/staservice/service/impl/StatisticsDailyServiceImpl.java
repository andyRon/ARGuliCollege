package com.andyron.staservice.service.impl;

import com.andyron.common.utils.R;
import com.andyron.staservice.client.UcenterClient;
import com.andyron.staservice.entity.StatisticsDaily;
import com.andyron.staservice.mapper.StatisticsDailyMapper;
import com.andyron.staservice.service.StatisticsDailyService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.swagger.models.auth.In;
import org.apache.commons.lang3.RandomUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 网站统计日数据 服务实现类
 * </p>
 *
 * @author andyron
 * @since 2023-04-13
 */
@Service
public class StatisticsDailyServiceImpl extends ServiceImpl<StatisticsDailyMapper, StatisticsDaily> implements StatisticsDailyService {

    @Autowired
    private UcenterClient ucenterClient;

    @Override
    public void registerCount(String day) {
        // 先删除相同日期的数据
        LambdaQueryWrapper<StatisticsDaily> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(StatisticsDaily::getDateCalculated, day);
        baseMapper.delete(wrapper);

        // 远程调用得到某一天注册人数
        R registerR = ucenterClient.countRegister(day);
        Integer countRegister = (Integer) registerR.getData().get("countRegister");

        // 添加数据到统计分析表里面
        StatisticsDaily sta = new StatisticsDaily();
        sta.setRegisterNum(countRegister);
        sta.setDateCalculated(day);

        // TODO
        sta.setVideoViewNum(RandomUtils.nextInt(100, 200));
        sta.setLoginNum(RandomUtils.nextInt(100, 200));
        sta.setCourseNum(RandomUtils.nextInt(100, 200));

        baseMapper.insert(sta);
    }

    @Override
    public Map<String, Object> getShowData(String type, String begin, String end) {
        QueryWrapper<StatisticsDaily> wrapper = new QueryWrapper<>();
        wrapper.between("date_calculated", begin, end)
                .select("date_calculated", type);
        List<StatisticsDaily> staList = baseMapper.selectList(wrapper);

        // 格式化数据
        List<String> date_calculatedList = new ArrayList<>();
        List<Integer> numDataList = new ArrayList<>();
        for (StatisticsDaily sta : staList) {
            date_calculatedList.add(sta.getDateCalculated());
            switch (type) {
                case "login_num":
                    numDataList.add(sta.getLoginNum());
                    break;
                case "register_num":
                    numDataList.add(sta.getRegisterNum());
                    break;
                case "video_view_num":
                    numDataList.add(sta.getVideoViewNum());
                    break;
                case "course_num":
                    numDataList.add(sta.getCourseNum());
                    break;
                default:
                    break;
            }
        }
        Map<String, Object> map = new HashMap<>();
        map.put("date_calculatedList", date_calculatedList);
        map.put("numDataList", numDataList);

        return map;
    }
}
