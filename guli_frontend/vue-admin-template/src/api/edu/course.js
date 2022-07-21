import request from '@/utils/request'

export default {
    // 1 课程分类列表
    addCourseInfo(courseInfo) {
        return request({
            url: `/eduservice/course/addCourseInfo`,
            method: 'post',
            data: courseInfo
        })
    },
    // 2 查询所有讲师
    getListTeacher() {
        return request({
            url: `/eduservice/teacher/findAll`,
            method: 'get'
        })
    }
    
}