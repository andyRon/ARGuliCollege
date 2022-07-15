package com.andyron.servicebase.exceptionhandler;

import com.andyron.common.utils.ExceptionUtil;
import com.andyron.common.utils.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    // 指定出现扫描异常执行这个方法
    @ExceptionHandler(Exception.class)
    @ResponseBody // 为了返回数据，在controller有@RestController表示有返回值，其实@RestController也被@ResponseBody注解了
    public R error(Exception e) {
        e.printStackTrace();
        return R.error().message("执行了全局异常处理");
    }

    // 特定异常处理
    @ExceptionHandler(ArithmeticException.class)
    @ResponseBody
    public R error(ArithmeticException e) {
        e.printStackTrace();
        return R.error().message("执行了ArithmeticException异常处理");
    }

    // 自定义异常处理
    @ExceptionHandler(GuliException.class)
    @ResponseBody
    public R error(GuliException e) {
        // 把异常信息输出到日志文件中，可以自定更多信息（栈信息等）输出到日志文件中
        log.error(e.getMessage());
        // 上面的方式只会只有一样信息输出到日志文件中，要把堆栈信息也输出到日志文件中
//        log.error(ExceptionUtil.getMessage(e));

        e.printStackTrace();
        return R.error().code(e.getCode()).message(e.getMsg());
    }
}
