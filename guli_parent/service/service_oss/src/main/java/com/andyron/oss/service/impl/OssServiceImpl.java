package com.andyron.oss.service.impl;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.model.PutObjectRequest;
import com.andyron.oss.service.OssService;
import com.andyron.oss.utils.ConstantPropertiesUtils;
import org.joda.time.DateTime;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

@Service
public class OssServiceImpl implements OssService {
    @Override
    public String uploadFileAvatar(MultipartFile file) {

        String endpoint = ConstantPropertiesUtils.END_POINT;
        String accessKeyId = ConstantPropertiesUtils.KEY_ID;
        String accessKeySecret = ConstantPropertiesUtils.KEY_SECRET;
        String bucketName = ConstantPropertiesUtils.BUCKET_NAME;

        try {
            // 创建OSSClient实例。
            OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
            // 获取上传文件输入流
            InputStream inputStream = file.getInputStream();
            // 获取文件名称
            String filename = file.getOriginalFilename();

            // 在文件名称里添加随机唯一值，让文件不要被覆盖
            String uuid = UUID.randomUUID().toString().replaceAll("-", "");
            filename = uuid + filename;

            // 把文件按日期进行分类 2021/11/11/xxxx01.jpg
            String datePath = new DateTime().toString("yyyy/MM/dd");
            filename = datePath + "/" + filename;

            ossClient.putObject(bucketName, filename, inputStream);
            ossClient.shutdown();

            // 把上传之后的文件路径返回，需要手动拼接
            String url = "https://" + bucketName + "." + endpoint + "/" + filename;
            return url;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

    }
}
