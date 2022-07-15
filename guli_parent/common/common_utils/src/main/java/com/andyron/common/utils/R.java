package com.andyron.common.utils;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.HashMap;
import java.util.Map;

/**
 * 统一返回结果类
 */
@Data
public class R {
    @ApiModelProperty(value = "是否成功")
    public Boolean success;

    @ApiModelProperty(value = "返回码")
    public Integer code;

    @ApiModelProperty(value = "返回信息")
    public String message;

    @ApiModelProperty(value = "返回数据")
    public Map<String, Object> data = new HashMap<String, Object>();

    /**
     * 把构造方法私有
     */
    private R() {}

    public static R ok() {
        R r = new R();
        r.setSuccess(true);
        r.setCode(ResultCode.SUCCESS);
        r.setMessage("成功");
        return r;
    }

    public static R error() {
        R r = new R();
        r.setSuccess(false);
        r.setCode(ResultCode.ERROR);
        r.setMessage("失败");
        return r;
    }

    /**
     * return this; 是为了实现链式写法
     */
    public R success(Boolean success) {
        this.setSuccess(success);
        return this;
    }

    public R code(Integer code) {
        this.setCode(code);
        return this;
    }

    public R message(String message) {
        this.setMessage(message);
        return this;
    }

    public R data(Map<String, Object> data) {
        this.setData(data);
        return this;
    }

    public R data(String key, Object value) {
        this.data.put(key, value);
        return this;
    }
}
