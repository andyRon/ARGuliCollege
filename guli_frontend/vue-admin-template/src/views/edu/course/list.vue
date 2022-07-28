<template>
    <div class="app-container">
        课程列表
        <el-form :inline="true" class="demo-form-inline">
          <el-form-item label="">
              <el-input v-model="courseQuery.title" placeholder="课程名称"></el-input>
          </el-form-item>
          <el-form-item label="">
              <el-select v-model="courseQuery.status" placeholder="发布状态" clearable >
                <el-option label="未发布" :value="'Draft'"></el-option>
                <el-option label="已发布" :value="'Normal'"></el-option>
              </el-select>
          </el-form-item>

          <el-button type="primary" icon="el-icon-search" @click="getList()">查询</el-button>
          <el-button type="default" @click="resetData()">清空</el-button>

        </el-form>

        <el-table 
        :data="list"
        border 
        fit
        hightlight-current-row
        >
          <el-table-column 
          label="序号"
          min-width="70"
          align="center">
          <template slot-scope="scope">
              {{(page - 1) * limit + scope.$index + 1}}
          </template>
          </el-table-column>

          <el-table-column prop="title" label="名称" min-width="80" />

          <el-table-column label="发布状态" min-width="80">
              <template slot-scope="scope">  
                  {{scope.row.status === 'Normal' ? '已发布' : '未发布'}}
              </template>
          </el-table-column>

          <el-table-column prop="lessonNum" label="课程数" min-width="40" />
          <el-table-column prop="gmtCreate" label="添加时间" min-width="80" />
          <el-table-column prop="viewCount" label="浏览数" min-width="60" />
          <el-table-column prop="buyCount" label="销售数量" min-width="60" />

          <el-table-column label="操作" min-width="200" align="center">
              <template slot-scope="scope">
                  <router-link :to="'/course/info/' + scope.row.id">
                    <el-button type="primary" size="mini" icon="el-icon-edit">编辑课程基本信息</el-button>
                  </router-link>
                  <router-link :to="'/course/chapter/' + scope.row.id">
                    <el-button type="primary" size="mini" icon="el-icon-edit">编辑课程大纲信息</el-button>
                  </router-link>
                  <el-button type="danger" size="mini" icon="el-icon-delete" @click="removeCourseById(scope.row.id)">删除课程</el-button>
              </template>
          </el-table-column>

        </el-table>

        <!-- 分页 -->
        <el-pagination 
            :total="total" 
            :current-page="page" 
            :page-size="limit" 
            layout="total, prev, pager, next, jumper"
            style="padding: 30px 0; text-align: center;"
            @current-change="getList" 
        />

    </div>
</template>

<script>
import course from "@/api/edu/course";

export default {
    data() {  // 定义当页面中使用的变量和初始值
        return {
            list: null, // 查询之后接口返回集合
            page: 1, // 当前页
            limit: 10, // 每页记录数
            total: 0,
            courseQuery: {} 

        }
    }, 
    created() { 
        this.getList()
    },
    methods: { 
        
        getList(page = 1) {   
            this.page = page  
            course.getCourseCondition(this.page, this.limit, this.courseQuery)
                .then(response => {  
                    this.list = response.data.rows
                    this.total = response.data.total 
                })
            
        },
        resetData() {
            // 表单输入项数据清空，（因为是双向绑定）
            this.courseQuery = {}
            // 查询所有讲师数据
            this.getList()
        },
        removeCourseById(courseId) {
            this.$confirm('此操作将永久删除课程机器相关章节、小节、描述、视频，是否继续？', '提示', {
                confirmButtonText: '确定',
                cancelButtonText: '取消',
                type: 'warning'
            }).then(() => { 
                course.deleteCourse(courseId)
                .then(response => { 
                    this.$message({
                        type: 'success',
                        message: '删除成功！'
                    });
                    this.getList()
                })
            })  
        }
    }
}

</script>

