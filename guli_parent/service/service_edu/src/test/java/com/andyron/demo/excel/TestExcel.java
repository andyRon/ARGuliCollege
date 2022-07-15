package com.andyron.demo.excel;

import com.alibaba.excel.EasyExcel;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class TestExcel {

    @Test
    public void writeTest() {
        String fileName = "/Users/andyron/tmp/write.xlsx";

        EasyExcel.write(fileName, DemoData.class).sheet("学生列表").doWrite(getData());
    }

    private static List<DemoData> getData() {
        List<DemoData> list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            DemoData data = new DemoData();
            data.setSno(i);
            data.setSname("andy"+i);
            list.add(data);
        }
        return list;
    }

    @Test
    public void readTest() {
        String fileName = "/Users/andyron/tmp/write.xlsx";
        EasyExcel.read(fileName, DemoData.class, new ExcelListenser()).sheet().doRead();
    }
}
