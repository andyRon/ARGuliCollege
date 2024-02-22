package com.andyron.mpdemo;

import com.andyron.mpdemo.entity.User;
import com.andyron.mpdemo.mapper.UserMapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.text.SimpleDateFormat;
import java.util.*;

@SpringBootTest
class MpdemoApplicationTests {

    @Autowired
    UserMapper userMapper;

    @Test
    void findAll() {
        List<User> users = userMapper.selectList(null);
        users.forEach(System.out::println);
    }

    @Test
    void addUser() {
        User user = new User();
        user.setAge(61);
        user.setName("laozhang");
        user.setEmail("laozhangs@gmail.com");
        System.out.println(userMapper.insert(user));
    }

    @Test
    void updateUser() {
        User user = new User();
        user.setId(1538332646477991938l);
        user.setAge(121);
        System.out.println(userMapper.updateById(user));
    }

    /**
     * 测试乐观锁
     */
    @Test
    void optimisticLockerTest() {
        // 先查询
        User user = userMapper.selectById(1539063991684653057L);
        // 再改
        user.setAge(100);
        userMapper.updateById(user);
    }

    // 多个id批量查询
    @Test
    void  select1Test() {
        System.out.println(userMapper.selectBatchIds(Arrays.asList(1l, 2l, 5l)));
    }

    // 简单的条件查询
    @Test
    void select2Test() {
        HashMap<String, Object> map = new HashMap<>();
        map.put("name", "AndyRon");
        map.put("age", 3);
        System.out.println(userMapper.selectByMap(map));
    }

    // 分页查询
    @Test
    void pageTest() {
        // 创建分页对象，参数：当前页，每页记录数
        Page<User> page = new Page<>(1, 3);
        // 查询结果所有数据都会被封装到page对象里
        userMapper.selectPage(page, null);

        // 通过page对象获取分页数据
        System.out.println(page.getCurrent());
        System.out.println(page.getRecords());  // 没页数据list集合
        System.out.println(page.getSize());
        System.out.println(page.getTotal());
        System.out.println(page.getPages());  // 总页数
        System.out.println(page.hasNext());  // 是否有下页
        System.out.println(page.hasPrevious());
    }

    @Test
    void deleteTest() {
        System.out.println(userMapper.deleteById(1539063991684653057l));
    }

    // 复杂查询
    @Test
    void selectQueryWrapperTest() {
        QueryWrapper<User> wrapper = new QueryWrapper<>();

        // ge gt le lt
        // 查询age>=30
//        wrapper.ge("age", 30);

        // eq ne
//        wrapper.eq("name", "Andy");
//        wrapper.ne("name", "Andy");

        // between
//        wrapper.between("age", 20, 30);

        // like
//        wrapper.like("name", "J");

        // 排序
//        wrapper.orderByDesc("id");

        // last 在最后拼接一段SQL语句
        wrapper.last("limit 1");

        // 指定查询的列
        wrapper.select("id", "name");

        System.out.println(userMapper.selectList(wrapper));
    }


}
