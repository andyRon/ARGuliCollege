package com.andyron.vodtest;

import com.aliyun.vod.upload.impl.UploadVideoImpl;
import com.aliyun.vod.upload.req.UploadVideoRequest;
import com.aliyun.vod.upload.resp.UploadVideoResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.vod.model.v20170321.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;

import java.util.List;

@Configuration
public class TestVod {


    public static void main(String[] args) throws ClientException {


//        deleteByid("16cfb3f3cca146a0a7c0567a1d503e99");

//        testUploadVideo("video_test", "/Users/andyron/Downloads/video_test.mp4");

        getPlayURLByid("922a13f0b63071edbffb6732b68f0102");
        getPlayAuthByid("922a13f0b63071edbffb6732b68f0102");


    }


    /**
     * 根据视频id获取视频播放地址
     */
    public static void getPlayURLByid(String id) throws ClientException {
        // 创建初始化对象
        DefaultAcsClient client = InitObject.initVodClient(InitObject.ACCESSKEYID, InitObject.ACCESSKEYSECRET);

        // 创建获取视频地址request和response
        GetPlayInfoRequest request = new GetPlayInfoRequest();
        GetPlayInfoResponse response = new GetPlayInfoResponse();

        // 设置id
        request.setVideoId(id);

        // 获取response
        response = client.getAcsResponse(request);

        List<GetPlayInfoResponse.PlayInfo> playInfoList = response.getPlayInfoList();
        //播放地址
        for (GetPlayInfoResponse.PlayInfo playInfo : playInfoList) {
            System.out.print("PlayInfo.PlayURL = " + playInfo.getPlayURL() + "\n");
        }
        //Base信息
        System.out.print("VideoBase.Title = " + response.getVideoBase().getTitle() + "\n");
    }

    /**
     * 根据视频id获取视频播放凭证
     */
    public static void getPlayAuthByid(String id) throws ClientException {
        DefaultAcsClient client = InitObject.initVodClient(InitObject.ACCESSKEYID, InitObject.ACCESSKEYSECRET);
        GetVideoPlayAuthRequest request = new GetVideoPlayAuthRequest();
        GetVideoPlayAuthResponse response = new GetVideoPlayAuthResponse();
        request.setVideoId(id);
        response = client.getAcsResponse(request);
        System.out.print("PlayAuth = " + response.getPlayAuth() + "\n");
    }

    /**
     * 根据视频id删除视频
     * @param ids 支持传入多个视频ID，多个用逗号分隔
     */
    public static void deleteByid(String ids) throws ClientException {
        DefaultAcsClient client = InitObject.initVodClient(InitObject.ACCESSKEYID, InitObject.ACCESSKEYSECRET);
        DeleteVideoRequest request = new DeleteVideoRequest();
        DeleteVideoResponse response = new DeleteVideoResponse();
        request.setVideoIds(ids);
        response = client.getAcsResponse(request);
        System.out.print("RequestId = " + response.getRequestId() + "\n");

    }

    /**
     * 本地文件上传接口
     * @param title 上传之后的文件名称
     * @param fileName 要上传的本地文件路径和名称
     */
    public static void testUploadVideo(String title, String fileName) {
        UploadVideoRequest request = new UploadVideoRequest(InitObject.ACCESSKEYID, InitObject.ACCESSKEYSECRET, title, fileName);
        // 设置各种配置
        /* 可指定分片上传时每个分片的大小，默认为2M字节 */
        request.setPartSize(2 * 1024 * 1024L);
        /* 可指定分片上传时的并发线程数，默认为1，（注：该配置会占用服务器CPU资源，需根据服务器情况指定）*/
        request.setTaskNum(1);

        UploadVideoImpl uploader = new UploadVideoImpl();
        UploadVideoResponse response = uploader.uploadVideo(request);
//        System.out.print("RequestId=" + response.getRequestId() + "\n");  //请求视频点播服务的请求ID
        if (response.isSuccess()) {
            System.out.print("VideoId=" + response.getVideoId() + "\n");
        } else {
            /* 如果设置回调URL无效，不影响视频上传，可以返回VideoId同时会返回错误码。其他情况上传失败时，VideoId为空，此时需要根据返回错误码分析具体错误原因 */
            System.out.print("VideoId=" + response.getVideoId() + "\n");
            System.out.print("ErrorCode=" + response.getCode() + "\n");
            System.out.print("ErrorMessage=" + response.getMessage() + "\n");
        }

    }
}
