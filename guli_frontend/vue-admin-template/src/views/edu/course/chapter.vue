<template>
    <div class="app-container">
        <h2 style="text-align: center;">发布新课程</h2>
        <el-steps :active="2" style="margin-bottom: 40px;" align-center process-status="wait">
            <el-step title="填写课程基本信息"></el-step>
            <el-step title="创建课程大纲"></el-step>
            <el-step title="最终发布"></el-step>
        </el-steps>


        <ul class="chapterList">
            <li v-for="chapter in chapterVideoList" :key="chapter.id">
                {{chapter.title}}

                <ul class="chapterList videoList">
                    <li v-for="video in chapter.children" :key="video.id">
                        {{video.title}}
                    </li>
                </ul>
            </li>
        </ul>

        <el-form  label-width="120px">
            <el-form-item>
                <el-button @click="previous">上一步</el-button>    
                <el-button :disabled="saveBtnDisabled" type="primary" @click="next">保存并下一步</el-button>    
            </el-form-item>
        </el-form>
    </div>
</template>

<script>
import chapter from '@/api/edu/chapter'

export default {
    data() {
        return {
            saveBtnDisabled: false,
            courseId: '',
            chapterVideoList: [],
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
            this.$router.push({path: '/course/info/1'})
        },
        next() {
            this.$router.push({path: '/course/publish/1'})
        },
        getChapterVideo() {
            chapter.getChapterVideo(this.courseId)
                .then(r => {
                    this.chapterVideoList = r.data.allChapterVideo
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