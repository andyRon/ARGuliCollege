package com.andyron.eduservice.controller;

import com.andyron.common.utils.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

/**
 * 模拟登录
 */
@Api(tags = "模拟登录")
@RestController
@RequestMapping("/eduservice/user")
//@CrossOrigin  // 解决跨域
public class EduLoginController {

    /**
     * login
     */
    @ApiOperation(value = "登录")
    @PostMapping("login")
    public R login() {
        return R.ok().data("token", "admin");
    }

    /**
     * info
     */
    @GetMapping("info")
    public R info() {
        return R.ok().data("name", "admin")
                .data("avatar", "https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif");
    }
}
