package com.andyron.demo.excel;


import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

@Data
public class DemoData {
    /**
     * 设计Excel表头，列对应的属性
     * index 表示属性对应的Excel中的列
     */
    @ExcelProperty(value = "学生编号", index = 0)
    private Integer sno;
    @ExcelProperty(value = "学生姓名", index = 1)
    private String sname;
}
