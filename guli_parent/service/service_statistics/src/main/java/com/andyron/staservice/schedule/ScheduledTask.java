package com.andyron.staservice.schedule;

import com.andyron.staservice.service.StatisticsDailyService;
import com.andyron.staservice.utils.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @author andyron
 **/
@Component
public class ScheduledTask {

    @Autowired
    private StatisticsDailyService staService;


    /**
     * 在每天凌晨1点，把前一天数据进行查询添加统计
     */
    @Scheduled(cron = "0 0 1 * * ?")
    public void task() {
        staService.registerCount(DateUtil.formatDate(DateUtil.addDays(new Date(), -1)));
    }
}
