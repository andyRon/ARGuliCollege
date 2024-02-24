package com.andyron.msmservice.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.aliyuncs.CommonRequest;
import com.aliyuncs.CommonResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.http.ProtocolType;
import com.aliyuncs.profile.DefaultProfile;
import com.andyron.msmservice.service.MsmService;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Map;

/**
 * @author andyron
 **/
@Service
public class MsmServiceImpl implements MsmService {
    @Override
    public boolean send(Map<String, Object> param, String phone) {
        if (StringUtils.isEmpty(phone)) {
            return false;
        }
        // TODO 改进 统一存储敏感信息
        DefaultProfile profile = DefaultProfile.getProfile("default", "", "");
        IAcsClient client = new DefaultAcsClient(profile);

        // 设置相关固定的参数
        CommonRequest request = new CommonRequest();
//        request.setSysProtocol(ProtocolType.HTTPS);
        request.setSysMethod(MethodType.POST);
        request.setSysDomain("dysmsapi.aliyuncs.com");
        request.setSysVersion("2017-05-25");
        request.setSysAction("SendSms");

        // 设置发送相关的参数
        request.putQueryParameter("PhoneNumbers", phone);
        request.putQueryParameter("SignName", "我的谷粒学院在线教育网站");  // 签名名称
        request.putQueryParameter("TemplateCode", "SMS_465410152");     // 模版code
        request.putQueryParameter("TemplateParam", JSONObject.toJSONString(param));  // 验证码数据

        try {
            // 最终发送
            CommonResponse response = client.getCommonResponse(request);
            boolean success = response.getHttpResponse().isSuccess();
            return success;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
