package com.andyron.msmservice.controller;

import com.andyron.common.utils.R;
import com.andyron.msmservice.service.MsmService;
import com.andyron.msmservice.utils.RandomUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @author andyron
 **/
@Api(tags = "短信服务")
@RestController
@RequestMapping("/edumsm/msm")
@CrossOrigin
@Slf4j
public class MsmController {
    @Autowired
    private MsmService msmService;
    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    // TODO test
    @ApiOperation("想手机发送验证码短信")
    @GetMapping("/send/{phone}")
    public R sendMsm(@PathVariable String phone) {
        // 1 从redis获取验证码，如果有，就直接返回
        String code = redisTemplate.opsForValue().get(phone);
        if (!StringUtils.isEmpty(code)) {
            return R.ok();
        }

        code = RandomUtil.getFourBitRandom();
        Map<String, Object> param = new HashMap<>();
        param.put("code", code);
//        boolean isSend = msmService.send(param, phone);
        log.info(code);
        boolean isSend = true;
        if (isSend) {
            // 2 发送成功后，把验证码放到redis
            redisTemplate.opsForValue().set(phone, code, 5, TimeUnit.MINUTES);
            return R.ok();
        } else {
            return R.error().message("短信发送失败");
        }
    }
}
