package com.andyron.eduservice.entity.excel;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

@Data
public class SubjectData {
    /**
     * value值要写上，否者可能获取不到数据
     */
    @ExcelProperty(value = "一级分类", index = 0)
    private String oneSubjectName;

    @ExcelProperty(value = "二级分类", index = 1)
    private String twoSubjectName;
}
