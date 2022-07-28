package com.andyron.eduservice.entity.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class CourseQuery {

    @ApiModelProperty("课程名称")
    private String title;

    @ApiModelProperty("课程发布状态")
    private String status;
}
