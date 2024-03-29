<template>
    <div class="app-container">
        <h2 style="text-align: center;">发布新课程</h2>
        <el-steps :active="2" style="margin-bottom: 40px;" align-center process-status="wait">
            <el-step title="填写课程基本信息"></el-step>
            <el-step title="创建课程大纲"></el-step>
            <el-step title="最终发布"></el-step>
        </el-steps>

        <el-button type="text" @click="openDialog">添加章节</el-button>

        <ul class="chapterList">
            <li v-for="chapter in chapterVideoList" :key="chapter.id">
                <p>{{chapter.title}}
                    <span class="acts">
                        <el-button type="text" @click="openVideo(chapter.id)">添加小节</el-button>
                        <el-button type="text" @click="openEditChapter(chapter.id)">编辑</el-button>
                        <el-button type="text" @click="deleteChapter(chapter.id)">删除</el-button>
                    </span>
                </p>

                <ul class="chapterList videoList">
                    <li v-for="video in chapter.children" :key="video.id">
                        <p>{{video.title}}
                            <span class="acts">
                                <el-button type="text" @click="openEditVideo(video.id)">编辑</el-button>
                                <el-button type="text" @click="deleteVideo(video.id)">删除</el-button>
                            </span>
                        </p>
                    </li>
                </ul>
            </li>
        </ul>

        
        <div>
            <el-button @click="previous">上一步</el-button>    
            <el-button :disabled="saveBtnDisabled" type="primary" @click="next">保存并下一步</el-button>    
        </div>

        <!-- 保存和修改章节弹框 -->
        <el-dialog title="添加章节" :visible.sync="dialogChapterFormVisible">
            <el-form :model="chapter" label-width="120px">
                <el-form-item label="章节标题" >
                    <el-input v-model="chapter.title" ></el-input>
                </el-form-item>
                <el-form-item label="章节排序">
                    <el-input-number v-model="chapter.sort" :min="0" controls-position="right"></el-input-number>
                </el-form-item>
            </el-form>
            <div slot="footer" class="dialog-footer">
                <el-button @click="dialogChapterFormVisible = false">取 消</el-button>
                <el-button type="primary" @click="saveOrUpdate">确 定</el-button>
            </div>
        </el-dialog>

        <!-- 保存和修改小节弹框 -->
        <el-dialog title="添加小节" :visible.sync="dialogVideoFormVisible">
            <el-form :model="video" label-width="120px">
                <el-form-item label="小节标题" >
                    <el-input v-model="video.title" ></el-input>
                </el-form-item>
                <el-form-item label="小节排序">
                    <el-input-number v-model="video.sort" :min="0" controls-position="right"></el-input-number>
                </el-form-item>
                <el-form-item label="是否免费">
                    <el-radio-group v-model="video.isFree">
                        <el-radio :label="1" >免费</el-radio>
                        <el-radio :label="0" >默认</el-radio>
                    </el-radio-group>
                </el-form-item>
                <el-form-item label="上传视频">
                    <el-upload
                        :on-success="handleVodUploadSuccess"
                        :on-remove="handleVodRemove"
                        :before-remove="beforeVodRemove"
                        :on-exceed="handleUploadExceed"
                        :file-list="fileList"
                        :action="BASE_API+'/eduvod/video/uploadAlyiVideo'"
                        :limit="1"
                        class="upload-demo">
                        <el-button size="small" type="primary">上传视频</el-button>
                        <el-tooltip placement="right-end">
                            <div slot="content">最大支持1G，<br>
                                支持3GP、ASF、AVI、DAT、DV、FLV、F4V、<br>
                                GIF、M2T、M4V、MJ2、MJPEG、MKV、MOV、MP4、<br>
                                MPE、MPG、MPEG、MTS、OGG、QT、RM、RMVB、<br>
                                SWF、TS、VOB、WMV、WEBM 等视频格式上传</div>
                            <i class="el-icon-question"/>
                        </el-tooltip>
                    </el-upload>
                </el-form-item>
                
            </el-form>
            <div slot="footer" class="dialog-footer">
                <el-button @click="dialogVideoFormVisible = false">取 消</el-button>
                <el-button type="primary" @click="saveOrUpdateVideo">确 定</el-button>
            </div>
        </el-dialog>
    </div>
</template>

<script>
import chapter from '@/api/edu/chapter'
import video from '@/api/edu/video'

export default {
    data() {
        return {
            saveBtnDisabled: false,
            courseId: '',
            chapterVideoList: [],
            chapter: {
                id: '',
                title: '',
                sort: 0
            },
            video: {
                id: '',
                title: '',
                sort: 0,
                isFree: 0,
                videoSourceId: '',
                videoOriginalName: ''   // 视频名称
            },
            dialogChapterFormVisible: false,  // 章节弹框是否显示
            dialogVideoFormVisible: false,     // 小结弹框

            fileList: [],//上传文件列表
            BASE_API: process.env.VUE_APP_BASE_API // 接口API地址
        }
    },
    created() {
        // 获取路由的id值
        if (this.$route.params && this.$route.params.id) {
            this.courseId = this.$route.params.id
            this.getChapterVideo()
        }
        
    },
    methods: {
        previous() {
            this.$router.push({path: '/course/info/' + this.courseId})
        },
        next() {
            this.$router.push({path: '/course/publish/' + this.courseId})
        },
        getChapterVideo() {
            chapter.getChapterVideo(this.courseId)
                .then(r => {
                    this.chapterVideoList = r.data.allChapterVideo
                })
        },
        addChapter() {
            // 设置课程id到chapter对象里
            this.chapter.courseId = this.courseId
            chapter.addChapter(this.chapter)
                .then(response => {
                    // 关闭弹框
                    this.dialogChapterFormVisible = false
                    // 提示
                    this.$message({
                        type: 'success',
                        message: '添加章节成功！'
                    })
                    // 刷新页面
                    this.getChapterVideo()
                })
        },
        updateChapter() {
            chapter.updateChapter(this.chapter)
                .then(response => {
                    // 关闭弹框
                    this.dialogChapterFormVisible = false
                    // 提示
                    this.$message({
                        type: 'success',
                        message: '修改章节成功！'
                    })
                    // 刷新页面
                    this.getChapterVideo()
                })
        },
        saveOrUpdate() {
            if (!this.chapter.id) {
                this.addChapter()
            } else {
                this.updateChapter()
            }
        },
        // 展示添加章节弹框
        openDialog() {
            this.dialogChapterFormVisible = true
            this.chapter.id = ''
            this.chapter.title = ''
            this.chapter.sort = 0
        },
        // 展示修改章节弹框，并回显数据
        openEditChapter(chapterId) {
            this.dialogChapterFormVisible = true
            chapter.getChapterById(chapterId)
                .then(response => {
                    this.chapter = response.data.chapter
                })
        },
        deleteChapter(chapterId) {
            this.$confirm('此操作将永久删除章节，是否继续？', '提示', {
                confirmButtonText: '确定',
                cancelButtonText: '取消',
                type: 'warning'
            }).then(() => {
                chapter.deleteChapter(chapterId)
                    .then(response => {
                        this.$message({
                            type: 'success',
                            message: '删除成功！'
                        });
                        this.getChapterVideo()
                    })
            })
        },
        // ===========小节操作============
        // 添加小节弹框
        openVideo(chapterId) {
            this.dialogVideoFormVisible = true
            // 设置章节id
            this.video.chapterId = chapterId
            //
            this.video.id = ''
            this.video.title = ''
            this.video.sort = 0
        },
        saveVideo() {
            this.video.courseId = this.courseId
            video.addVideo(this.video)
                .then(response => {
                    this.dialogVideoFormVisible = false
                    this.$message({
                        type: 'success',
                        message: '添加小节成功！'
                    })
                    this.getChapterVideo()
                })
        },
        updateVideo() {
            video.updateVideo(this.video)
                .then(response => {
                    this.dialogVideoFormVisible = false
                    this.$message({
                        type: 'success',
                        message: '修改小节成功！'
                    })
                    this.getChapterVideo()
                })
        },
        saveOrUpdateVideo() {
            if (!this.video.id) {
                this.saveVideo()
            } else {
                this.updateVideo()
            }
        },
        openEditVideo(id) {
            this.dialogVideoFormVisible = true
            video.getVideoById(id)
                .then(response => {
                    this.video = response.data.video
                })
        },
        deleteVideo(id) {
            this.$confirm('此操作将永久删除小节，是否继续？', '提示', {
                confirmButtonText: '确定',
                cancelButtonText: '取消',
                type: 'warning'
            }).then(() => {
                video.deleteVideo(id)
                    .then(response => {
                        this.$message({
                            type: 'success',
                            message: '删除成功！'
                        });
                        this.getChapterVideo()
                    })
            })
        },

        // ========小节中视频操作======
        // 上传视频成功调用的方法
        handleVodUploadSuccess(response, file, filelist) {
            this.video.videoSourceId = response.data.videoId
            this.video.videoOriginalName = file.name
        },
        handleUploadExceed() {
            this.$message.warning('想要重新上传视频，请先删除已上传的视频')
        },
        // 点击删除x是调用
        beforeVodRemove(file, filelist) {
            return this.$confirm(`确定删除 ${file.name}`)
        },
        // 点击删除弹框确定调用
        handleVodRemove() {
            // 调用接口删除视频
            video.deleteAliyunVideo(this.video.videoSourceId)
                .then(response => {
                    this.$message({
                        type: 'success',
                        message: '删除视频成功'
                    });
                    // 把文件列表清空
                    this.fileList = []
                    // 把视频id和视频名称清空
                    this.video.videoSourceId = ''
                    this.video.videoOriginalName = ''
                })
        }

    }
}
</script>

<style scoped>
.chapterList{
    position: relative;
    list-style: none;
    margin: 0;
    padding: 0;
}
.chapterList li{
  position: relative;
}
.chapterList p{
  float: left;
  font-size: 20px;
  margin: 10px 0;
  padding: 10px;
  height: 70px;
  line-height: 50px;
  width: 100%;
  border: 1px solid #DDD;
}
.chapterList .acts {
    float: right;
    font-size: 14px;
}

.videoList{
  padding-left: 50px;
}
.videoList p{
  float: left;
  font-size: 14px;
  margin: 10px 0;
  padding: 10px;
  height: 50px;
  line-height: 30px;
  width: 100%;
  border: 1px dotted #DDD;
}

</style>