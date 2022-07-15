import request from '@/utils/request'
// request.js 封装了axios请求

export default {
    // 1 讲师列表（条件查询分页）
    getTeacherListPage(current, limit, teacherQuery) {
        return request({
            url: `/eduservice/teacher/pageTeacherCondition/${current}/${limit}`,
            method: 'post',
            // teacherQuery条件对象，后端使用RequestBody获取数据，前端就要使用JSON来传递
            // data表示把对象转换json进行传递到后端接口
            data: teacherQuery
        })
    },
    // 2 讲师删除
    deleteTeacherId(id) {
        return request({
            url: `/eduservice/teacher/${id}`,
            method: 'delete'
        })
    },
    // 3 添加讲师
    addTeacher(teacher) {
        return request({
            url: `/eduservice/teacher/addTeacher`,
            method: 'post',
            data: teacher
        })
    },
    // 4 根据id查询讲师
    getTeacherInfo(id) {
        return request({
            url: `/eduservice/teacher/getTeacher/${id}`,
            method: 'get'
        })
    },
    // 5 修改讲师
    updateTeacherInfo(teacher) {
        return request({
            url: `/eduservice/teacher/updateTeacher/`,
            method: 'post',
            data: teacher
        })
    }
}