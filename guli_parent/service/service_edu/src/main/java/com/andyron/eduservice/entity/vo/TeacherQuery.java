package com.andyron.eduservice.entity.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class TeacherQuery {

    @ApiModelProperty(value = "教师名称，模糊查询")
    private String name;

    @ApiModelProperty(value = "头衔 1 高级讲师 2 首席讲师")
    private Integer level;

    @ApiModelProperty(value = "查询开始时间", example = "2021/10/8 10:02")
    private String begin;

    @ApiModelProperty(value = "查询结束时间", example = "2021/11/8 10:02")
    private String end;
}
