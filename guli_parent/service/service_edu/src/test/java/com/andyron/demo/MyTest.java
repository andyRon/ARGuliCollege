package com.andyron.demo;

import com.andyron.eduservice.entity.EduCourse;
import com.andyron.eduservice.mapper.EduCourseMapper;
import com.andyron.eduservice.service.EduCourseService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import java.util.*;

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

    public List<Integer> findDisappearedNumbers(int[] nums) {
        HashSet<Integer> set = new HashSet<>();
        for (int num : nums) {
            set.add(num);
        }
        List<Integer> res = new ArrayList();
        for (int i = 1; i <= nums.length ; i++) {
            if (!set.contains(i)) {
                res.add(i);
            }
        }
        return res;

    }


}
