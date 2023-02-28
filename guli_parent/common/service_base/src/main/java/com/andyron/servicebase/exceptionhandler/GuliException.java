package com.andyron.servicebase.exceptionhandler;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 异常类
 *
 * @author andyron
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class GuliException extends RuntimeException {

    /**
     * 异常状态码
     */
    private Integer code;
    /**
     * 异常信息
     */
    private String msg;
}
