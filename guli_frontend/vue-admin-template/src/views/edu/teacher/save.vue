<template>
    <div class="app-container">
      讲师添加
      <el-form label-width="120px">
        <el-form-item label="讲师名称">
            <el-input v-model="teacher.name" placeholder=""></el-input>
        </el-form-item>
        <el-form-item label="讲师排序">
            <el-input-number v-model="teacher.sort" controls-position="right" :min="0" ></el-input-number>
        </el-form-item>
        <el-form-item label="讲师头衔">
            <el-select v-model="teacher.level" placeholder="请选择" clearable >
              <!-- 数据类型一定要和取出的json中的一致，否则没法回填。因此，这里value使用动态绑定的值，保证器数据类型是number -->
              <el-option label="高级讲师" :value="1"></el-option>
              <el-option label="首席讲师" :value="2"></el-option>
            </el-select>
        </el-form-item>
        <el-form-item label="讲师资历">
            <el-input v-model="teacher.career" ></el-input>
        </el-form-item>
        <el-form-item label="讲师简介">
            <el-input v-model="teacher.intro" placeholder="" :rows="10" type="textarea"></el-input>
        </el-form-item>

        <!-- 讲师头像-->
        <el-form-item label="讲师头像">
            <!-- 头像缩略图 -->
            <pan-thumb :image="String(teacher.avatar)"/>
            <!-- 文件上传按钮 -->
            <el-button type="primary" icon="el-icon-upload" @click="imagecropperShow=true">更换头像</el-button>
            
            <!-- v-show： 是否显示上传组件
                :key 类似于id，如果一个页面多个图片上传控件，可以做区分
                :url  后台上传的url地址
                @close 关闭上传组件
                @crop-upload-success 上传成功后的回调
                field类似input标签的file属性
            -->
            <image-cropper
                v-show="imagecropperShow"
                :width="300"
                :height="300"
                :key="imagecropperKey"
                :url="BASE_API"
                field="file"
                @close="close"
                @crop-upload-success="cropSuccess" />
        
        </el-form-item>


        <el-form-item label="">
            <el-button type="primary" :disabled="saveBtnDisabled" @click="saveOrUpdate">保存</el-button>
        </el-form-item>
      </el-form>
    </div>
</template>

<script>
import  teacherApi  from "@/api/edu/teacher"
import ImageCropper from "@/components/ImageCropper"
import PanThumb from "@/components/PanThumb"

export default {
    components: {ImageCropper, PanThumb},
    data() {
        return {
            teacher: {  // 字段不加也可以
                name: '',
                sort: 0,
                level: 1,
                career: '',
                intro: '',
                avatar: ''
            },
            imagecropperShow: false,
            imagecropperKey: 0, // 上传组件key值
            BASE_API: process.env.VUE_APP_BASE_API + '/eduoss/fileoss', // 

            saveBtnDisabled: false  // 保存按钮是否禁用
        }
    },
    created() { // 页面渲染之前执行
        this.init()
    },
    watch: { // 监听
        $route(to, from) { // 路由发生变化，此方法就会执行
            this.init()
        }
    },
    methods: {
        init() {
            // 判断路径是否有id值
            if (this.$route.params && this.$route.params.id) {
                this.getInfo(this.$route.params.id)
            } else {
                // 清空表单
                this.teacher = {}
            }
        },

        saveOrUpdate() {
            // 根据teacher是否有id判断是修改还是添加
            if (!this.teacher.id) {
                this.saveTeacher()
            } else {
                this.updateTeacher()
            }
        },
        saveTeacher() {
            teacherApi.addTeacher(this.teacher)
                .then(response => {
                    // 提示信息
                    this.$message({
                        type: 'success',
                        message: '添加成功!'
                    })
                    // 回到列表页面，路由跳转（重定向）
                    this.$router.push({path: '/teacher/table'})
                })
        },
        // 根据讲师id查询
        getInfo(id) {
            teacherApi.getTeacherInfo(id)
                .then(response => {
                    this.teacher = response.data.teacher
                })
        },
        // 修改讲师
        updateTeacher() {
            teacherApi.updateTeacherInfo(this.teacher)
                .then(response => {
                    // 提示信息
                    this.$message({
                        type: 'success',
                        message: '修改成功!'
                    })
                    // 回到列表页面，路由挑战（重定向）
                    this.$router.push({path: '/teacher/table'})
                })
        },
        
        close() { // 关闭上传弹框
            this.imagecropperShow=false
            // 上传组件初始化，就上传成功后，想要更换图片时，图片组件初始化
            this.imagecropperKey = this.imagecropperKey + 1
        },
        cropSuccess(data) { // 上传成功
            this.imagecropperShow=false
            this.teacher.avatar=data.url
            this.imagecropperKey = this.imagecropperKey + 1
        }
        
    }
}

</script>