package com.andyron.vodtest;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.profile.DefaultProfile;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;


public class InitObject {

    public  static String ACCESSKEYID = "";
    public final static String ACCESSKEYSECRET = "";



    //填入AccessKey信息
    public static DefaultAcsClient initVodClient(String accessKeyId, String accessKeySecret) throws ClientException {
        String regionId = "cn-shanghai";  // 点播服务接入地域
        DefaultProfile profile = DefaultProfile.getProfile(regionId, accessKeyId, accessKeySecret);
        DefaultAcsClient client = new DefaultAcsClient(profile);
        return client;
    }

    public static DefaultAcsClient getClient() throws ClientException {
        return initVodClient(ACCESSKEYID, ACCESSKEYSECRET);
    }
}
