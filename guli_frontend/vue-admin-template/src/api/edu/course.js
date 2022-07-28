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
    },
    // 根据课程id查询课程基本信息
    getCourseInfoById(courseId) {
        return request({
            url: `/eduservice/course/getCourseInfoById/${courseId}`,
            method: 'get'
        })
    },
    // 修改课程信息
    updateCourseInfo(courseInfo) {
        return request({
            url: `/eduservice/course/updateCourseInfo`,
            method: 'post',
            data: courseInfo
        })
    },
    getPublishCourseInfo(courseId) {
        return request({
            url: `/eduservice/course/getPublishCourseInfo/${courseId}`,
            method: 'get'
        })
    },
    publishCourse(courseId) {
        return request({
            url: `/eduservice/course/publishCourse/${courseId}`,
            method: 'post'
        })
    },
    // 课程条件分页查询
    getCourseCondition(current, size, courseQuery) {
        return request({
            url: `/eduservice/course/getCourseCondition/${current}/${size}`,
            method: 'post',
            data: courseQuery
        })
    },
    deleteCourse(courseId) {
        return request({
            url: `/eduservice/course/deleteCourse/${courseId}`,
            method: 'delete'
        })
    }
}