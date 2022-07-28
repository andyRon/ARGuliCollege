import request from '@/utils/request'

export default {
    addVideo(video) {
        return request({
            url: '/eduservice/video/add/',
            method: 'post',
            data: video
        })
    },
    getVideoById(videoId) {
        return request({
            url: '/eduservice/video/getVideoById/' + videoId,
            method: 'get'
        })
    },
    updateVideo(video) {
        return request({
            url: '/eduservice/video/update/',
            method: 'post',
            data: video
        })
    },
    deleteVideo(videoId) {
        return request({
            url: '/eduservice/video/' + videoId,
            method: 'delete'
        })
    },
    
}