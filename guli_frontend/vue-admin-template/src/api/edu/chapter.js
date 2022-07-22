import request from '@/utils/request'

export default {
    // 1 课程章节小节
    getChapterVideo(courseId) {
        return request({
            url: '/eduservice/chapter/getChapterVideo/' + courseId,
            method: 'get'
        })
    }
    
}