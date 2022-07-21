<template>
    <div class="app-container">
        <h2 style="text-align: center;">发布新课程</h2>
        <el-steps :active="1"  style="margin-bottom: 40px;" align-center process-status="wait">
            <el-step title="填写课程基本信息"></el-step>
            <el-step title="创建课程大纲"></el-step>
            <el-step title="最终发布"></el-step>
        </el-steps>

        <el-form  label-width="120px">
            
            <el-form-item label="课程标题">
                <el-input v-model="courseInfo.title" placeholder="示例：机器学习项目课。（专业名称注意大小写）"></el-input>
            </el-form-item>

            <!-- 课程分类 -->
            <el-form-item label="课程分类">
                <el-select v-model="courseInfo.subjectParentId" placeholder="一级分类" @change="subjectOneChange">
                    <el-option 
                        v-for="subject in subjectOneList"
                        :key="subject.id"
                        :label="subject.title"
                        :value="subject.id"
                    />
                </el-select>

                <el-select v-model="courseInfo.subjectId" placeholder="二级分类" >
                    <el-option 
                        v-for="subject in subjectTwoList"
                        :key="subject.id"
                        :label="subject.title"
                        :value="subject.id"
                    />
                </el-select>
            </el-form-item>

            <!-- 课程讲师 -->
            <el-form-item label="课程讲师">
                <el-select v-model="courseInfo.teacherId" placeholder="请选择">
                    <el-option 
                        v-for="teacher in teacherList"
                        :key="teacher.id"
                        :label="teacher.name"
                        :value="teacher.id"
                    />
                </el-select>
            </el-form-item>

            <el-form-item label="总课程时">
                <el-input-number v-model="courseInfo.lessonNum" :min="0" controls-position="right" placeholder="请填写课程的总课时数"></el-input-number>
            </el-form-item>

            <el-form-item label="课程简介">
                <el-input v-model="courseInfo.description" placeholder=""></el-input>
            </el-form-item>

            <!-- 课程封面 -->
            <el-form-item label="课程封面">
                <el-upload
                    class="avatar-uploader"
                    :show-file-list="false"
                    :action="BASE_API"
                    :on-success="handleAvatarSuccess"
                    :before-upload="beforeAvatarUpload"
                    >
                    <img :src="courseInfo.cover" />
                </el-upload>
            </el-form-item>

            <el-form-item label="课程价格">
                <el-input-number v-model="courseInfo.price" :min="0" controls-position="right" placeholder="请填写课程价格"></el-input-number> 元
            </el-form-item>

            <el-form-item>
                <el-button :disabled="saveBtnDisabled" type="primary" @click="saveOrUpdate">保存并下一步</el-button>    
            </el-form-item>
        </el-form>
        
    </div>
</template>

<script>
import course from '@/api/edu/course'
import subject from '@/api/edu/subject'

export default {
    data() {
        return {
            saveBtnDisabled: false,
            courseInfo: {
                title: '',
                subjectId: '',
                subjectParentId: '',
                teacherId: '',
                lessonNum: 0,
                description: '',
                cover: '/static/avatar.jpg',
                price: 0
            },
            teacherList: [],
            subjectOneList: [],
            subjectTwoList: [],
            BASE_API: process.env.VUE_APP_BASE_API + '/eduoss/fileoss',

        }
    },
    created() {
        // 初始化所有讲师
        this.getListTeacher()
        this.getSubjectOneList() 
    },
    methods: {
        saveOrUpdate() {
            course.addCourseInfo(this.courseInfo)
                .then(response => {
                    this.$message({
                        type: 'success',
                        message: '添加课程信息成功！'
                    })
                    // 跳转到第二步
                    this.$router.push({path: '/course/chapter/' + response.data.courseId})
                })

            
        },
        // 查询所有讲师
        getListTeacher() {
            course.getListTeacher()
                .then(response => {
                    this.teacherList = response.data.items
                })
        },
        // 查询所有一级分类
        getSubjectOneList() {
            subject.getSubjectList()
                .then(response => {
                    this.subjectOneList = response.data.list
                })
        },
        // 选择一级分类，显示对应二级分类
        subjectOneChange(value) { // value就是一级分类id，vue默认会把下拉列表中选择项值传过来，不要再定义了
            for(var i=0; i < this.subjectOneList.length; i++) {
                if (this.subjectOneList[i].id == value) {
                    this.subjectTwoList = this.subjectOneList[i].children
                    // 清空二级分类id
                    this.courseInfo.subjectId = ''
                }
            }
        },
        // 上传成功后调用
        handleAvatarSuccess(res, file) {
            this.courseInfo.cover = res.data.url

        },
        // 上传之前调用，一般用来限制图片的类型、大小等
        beforeAvatarUpload(file) {
            const isJPG = file.type === 'image/jpeg'
            const isLt2M = file.size / 1024 / 1024 < 2
            if (!isJPG) {
                this.$message.error('上传头像图片只能是JPG格式')
            } 
            if (!isLt2M) {
                this.$message.error('上传头像图片大小不能超过2M')
            }
            return isJPG && isLt2M
        }
    }
}
</script>