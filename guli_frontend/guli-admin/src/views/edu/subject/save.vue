<template>
    <div class="app-container">
        <el-form label-width="120px">
            <el-form-item label="信息描述">
                <el-tag type="info">excel模板说明</el-tag>
                <el-tag>
                    <i class="el-icon-download"/>
                    <a href="https://edu-ar.oss-cn-shanghai.aliyuncs.com/01.xlsx">点击下载模板</a>
                </el-tag>
            </el-form-item>
            <el-form-item label="选择Excel">
                <!-- ref 类似唯一标识符 -->
                <el-upload
                    ref="upload"
                    :action="BASE_API + '/eduservice/subject/addSubject'"
                    :auto-upload="false"
                    :on-success="fileUploadSuccess"
                    :on-error="fileUploadError"
                    :disabled="importBtnDisabled"
                    :limit="1"
                    name="file"
                    >
                    <el-button slot="trigger" size="small" type="primary">选取文件</el-button>
                    <el-button 
                        style="margin-left: 10px;" size="small" type="success"
                        :loading="loading" 
                        @click="submitUpload">上传到服务器</el-button>
                </el-upload>
            </el-form-item>
        </el-form>    
        
        
    </div>
</template>


<script>
  export default {
    data() {
      return {
        BASE_API: process.env.VUE_APP_BASE_API,
        OSS_PATH: process.env.OSS_PATH,
        importBtnDisabled: false,       // 按钮是否禁用
        loading: false
      };
    },
    created() {

    },
    methods: {
      // 上传  
      submitUpload() {
        this.importBtnDisabled = true
        this.loading = true 
        // js的写法： document.getElementById("upload").submit()
        this.$refs.upload.submit();
      },
      fileUploadSuccess(response) {
        // 提示信息
        this.loading = false
        this.$message({
            type: 'success',
            message: '添加课程分类成功'
        })
        // 跳转到课程分类列表
        this.$router.push({path: '/subject/list'})
      },
      fileUploadError() {
        this.loading = false
        this.$message({
            type: 'error',
            message: '添加课程分类失败'
        })
      }

    }
  }
</script>