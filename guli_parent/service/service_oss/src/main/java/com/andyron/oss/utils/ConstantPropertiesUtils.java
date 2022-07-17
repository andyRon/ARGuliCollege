package com.andyron.oss.utils;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 @Component 表示把类交给spring管理
 @Value 会把值赋值给对应属性，再通过SpEL表达式直接读取配置文件中参数；这样就保证spring启动时，读取配置文件中对应参数，并复制给对应属性。
 （@Value不能直接用于静态）
 继承Spring接口InitializingBean会在上面操作加载完后，执行afterPropertiesSet方法；这样就可以定义公开的静态常量
 */
@Component
public class ConstantPropertiesUtils implements InitializingBean {

    @Value("${aliyun.oss.file.endpoint}")
    private String endpoint;
    @Value("${aliyun.oss.file.keyid}")
    private String keyid;
    @Value("${aliyun.oss.file.keysecret}")
    private String keysecret;
    @Value("${aliyun.oss.file.bucketname}")
    private String bucketname;

    /**
     * 定义公开静态
     */
    public static String END_POINT;
    public static String KEY_ID;
    public static String KEY_SECRET;
    public static String BUCKET_NAME;

    @Override
    public void afterPropertiesSet() throws Exception {
        END_POINT = endpoint;
        KEY_ID = keyid;
        KEY_SECRET = keysecret;
        BUCKET_NAME = bucketname;
    }
}
