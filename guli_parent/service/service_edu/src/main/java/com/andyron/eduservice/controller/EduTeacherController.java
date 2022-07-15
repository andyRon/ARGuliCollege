package com.andyron.eduservice.controller;


import com.andyron.common.utils.R;
import com.andyron.eduservice.entity.EduTeacher;
import com.andyron.eduservice.entity.vo.TeacherQuery;
import com.andyron.eduservice.service.EduTeacherService;
import com.andyron.servicebase.exceptionhandler.GuliException;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 讲师 前端控制器
 * </p>
 *
 * @author andyron
 * @since 2021-11-07
 */
@Api(tags = "讲师管理")
@RestController
@RequestMapping("/eduservice/teacher")
@CrossOrigin
public class EduTeacherController {

    @Autowired
    private EduTeacherService teacherService;

    /**
     * 1 查询讲师表所有数据
     */
    @ApiOperation(value = "所有讲师列表")
    @GetMapping("findAll")
    public R findAllTeacher() {
        try {
            int i = 1/0;
        } catch(Exception e) {
            throw new GuliException(20001, "执行自定义异常处理...");
        }
        List<EduTeacher> list = teacherService.list(null);
        return R.ok().data("items", list);

    }

    /**
     * 2 逻辑删除讲师
     */
    @ApiOperation(value = "逻辑删除讲师")
    @DeleteMapping("{id}")
    public R removeTeacher(@ApiParam(name = "id", value = "讲师ID", readOnly = true) @PathVariable String id) {
        Boolean flag = teacherService.removeById(id);
        if (flag) {
            return R.ok();
        } else {
            return R.error();
        }
    }

    /**
     * 3 分页查询讲师
     */
    @ApiOperation(value = "讲师分页查询")
    @GetMapping("/pageTeacher/{current}/{size}")
    public R pageTeacher(@PathVariable long current, @PathVariable long size) {
        Page<EduTeacher> pageTeacher = new Page<>(current, size);
        // 分页数据都会被封装到pageTeacher
        teacherService.page(pageTeacher, null);
        // 总记录数
        long total = pageTeacher.getTotal();
        // 数据list集合
        List<EduTeacher> records = pageTeacher.getRecords();
        return R.ok().data("total", total).data("rows", records);
    }

    /**
     * 4 条件查询讲师
     */
    @ApiOperation(value = "讲师条件查询")
    @PostMapping("/pageTeacherCondition/{current}/{size}")
    public R pageTeacherCondition(@PathVariable long current,
                                  @PathVariable long size,
                                  @RequestBody(required = false) TeacherQuery teacherQuery) { // false表示这个参数可以为空
        Page<EduTeacher> pageTeacher = new Page<>(current, size);
        // 构建条件
        QueryWrapper wrapper = new QueryWrapper();
        // 多条件组合查询
        String name = teacherQuery.getName();
        Integer level = teacherQuery.getLevel();
        String begin = teacherQuery.getBegin();
        String end = teacherQuery.getEnd();
        if (!StringUtils.isEmpty(name)) {
            wrapper.like("name", name);
        }
        if (!StringUtils.isEmpty(level)) {
            wrapper.eq("level", level);
        }
        if (!StringUtils.isEmpty(begin)) {
            // 数据字段名称
            wrapper.ge("gmt_create", begin);
        }
        if (!StringUtils.isEmpty(end)) {
            wrapper.le("gmt_create", end);
        }

        // 排序
        wrapper.orderByDesc("gmt_create");

        teacherService.page(pageTeacher, wrapper);
        long total = pageTeacher.getTotal();
        List<EduTeacher> records = pageTeacher.getRecords();
        return R.ok().data("total", total).data("rows", records);

    }

    @ApiOperation("讲师添加")
    @PostMapping("addTeacher")
    public R addTeacher(@RequestBody EduTeacher eduTeacher) {
        boolean save = teacherService.save(eduTeacher);
        if (save) {
            return R.ok();
        } else {
            return R.error();
        }
    }

    @ApiOperation("根据id查询讲师")
    @GetMapping("getTeacher/{id}")
    public R getTeacher(@PathVariable String id) {
        EduTeacher teacher = teacherService.getById(id);
        return R.ok().data("teacher", teacher);
    }

    @ApiOperation("根据id修改讲师")
    @PostMapping("updateTeacher")
    public R updateTeacher(@ApiParam(name = "teacher", value = "讲师对象", required = true)
                               @RequestBody EduTeacher eduTeacher) {
        boolean flag = teacherService.updateById(eduTeacher);
        if (flag) {
            return R.ok();
        } else {
            return R.error();
        }
    }

}

