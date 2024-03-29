import request from '@/utils/request'

export default {
    // 1 课程章节小节
    getChapterVideo(courseId) {
        return request({
            url: '/eduservice/chapter/getChapterVideo/' + courseId,
            method: 'get'
        })
    },
    addChapter(chapter) {
        return request({
            url: '/eduservice/chapter/addChapter/',
            method: 'post',
            data: chapter
        })
    },
    getChapterById(chapterId) {
        return request({
            url: '/eduservice/chapter/getChapterById/' + chapterId,
            method: 'get'
        })
    },
    updateChapter(chapter) {
        return request({
            url: '/eduservice/chapter/updateChapter/',
            method: 'post',
            data: chapter
        })
    },
    deleteChapter(chapterId) {
        return request({
            url: '/eduservice/chapter/' + chapterId,
            method: 'delete'
        })
    },
    
}