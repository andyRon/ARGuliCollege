package com.andyron.demo;

import com.andyron.eduservice.entity.EduCourse;
import com.andyron.eduservice.mapper.EduCourseMapper;
import com.andyron.eduservice.service.EduCourseService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Scanner;
public class MyTest {
    @Autowired
    EduCourseMapper eduCourseMapper;

    @Autowired
    EduCourseService eduCourseService;

    public static void main(String[] args) {

    }

    @Test
    public void test1() {
        Page<EduCourse> page = new Page<>(1, 3);
        eduCourseMapper.selectPage(page, null);


        System.out.println(page.getTotal());

    }


}
