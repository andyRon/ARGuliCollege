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
    // 删除小节
    deleteVideo(videoId) {
        return request({
            url: '/eduservice/video/' + videoId,
            method: 'delete'
        })
    },
    // 删除视频
    deleteAliyunVideo(id) {
        return request({
            url: '/eduvod/video/deleteAliVideo/' + id,
            method: 'delete'
        })
    },
    
}