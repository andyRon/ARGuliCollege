# ARGuliCollege



[详细开发笔记](./GuliCollege.md)



> 目录
>
> guli_parent/		后端Java项目
>
> guli_frontend/guli-admin 	后台管理系统前端
>
> guli_frontend/guli-front     前台用户系统前端
>
> guli_frontend/*			  前端练习	

http://localhost:9528/  http://localhost:9001  `http://localhost:800*`

http://localhost:3000/



```
guli_parent： 在线教学根目录（父工程），管理四个子模块：
	canal-client： canal数据库表同步模块（统计同步数据）				【10000】
	common： 公共模块父节点
    common-util：工具类模块，所有模块都可以依赖于它；统一返回数据格式
    service-base: service服务的base包，包含service服务的公共配置类，所有service模块依赖于它；swagger；统一异常处理
    spring-security：认证与授权模块，需要认证授权的service服务依赖于它
  infrastructure：基础服务模块父节点
    api-gateway： api网关服务			【8222】
  service: api接口服务父节点  
  	service-acl： 用户权限管理api接口服务（用户管理、角色管理和权限管理等）【8009】
    service-cms： cms api接口服务  （前台首页banner部分）	【8004】
    service-edu：教学相关api接口服务（前台、后台都会用到）		【8001】
    service-msm：短信api接口服务			【8005】
    service-order：订单相关api接口服务
    service-oss：阿里云oss（对象存储） api接口服务  				【8002】
    service-statistics：统计报表api接口服务			【8008】
    service-ucenter：会员api接口服务						【8006】
    service-vod： 视频点播api接口服务						【8003】
```

service-base 依赖 common-util，

service 依赖 service-base



### 待改进

-  升级到vue3