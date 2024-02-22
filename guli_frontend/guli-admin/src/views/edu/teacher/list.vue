<template>
    <div class="app-container">
        讲师列表

        <!-- 查询表单 
            :inline表示单行显示
        -->
        <el-form :inline="true" class="demo-form-inline">
          <el-form-item label="">
              <el-input v-model="teacherQuery.name" placeholder="讲师名"></el-input>
          </el-form-item>
          <el-form-item label="">
              <el-select v-model="teacherQuery.level" placeholder="讲师头衔" clearable >
                <el-option label="高级讲师" :value="1"></el-option>
                <el-option label="首席讲师" :value="2"></el-option>
              </el-select>
          </el-form-item>

          <el-form-item label="添加时间">
              <el-date-picker v-model="teacherQuery.begin" 
                placeholder="选择开始时间"
                type="datetime" 
                value-format="yyyy-MM-dd HH:mm:ss"
                default-time="00:00:00"></el-date-picker>
          </el-form-item>

          <el-form-item label="">
              <el-date-picker v-model="teacherQuery.end" 
                placeholder="选择截止时间"
                type="datetime"
                value-format="yyyy-MM-dd HH:mm:ss"
                default-time="00:00:00"></el-date-picker>
          </el-form-item>

          <el-button type="primary" icon="el-icon-search" @click="getList()">查询</el-button>
          <el-button type="default" @click="resetData()">清空</el-button>

        </el-form>

        <!-- 
            list是数组对象，这里框架做了封装，不需要v-for 
        -->
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

          <el-table-column prop="name" label="名称" min-width="80" />

          <el-table-column label="头衔" min-width="80">
              <template slot-scope="scope">  <!-- 整个表格的数据叫做scope，scope.row表示或的某行的数据 -->
                  {{scope.row.level === 1 ? '高级讲师' : '首席讲师'}}
              </template>
          </el-table-column>

          <el-table-column prop="intro" label="资历" min-width="80" />
          <el-table-column prop="gmtCreate" label="添加时间" min-width="160" />
          <el-table-column prop="sort" label="排序" min-width="60" />

          <el-table-column label="操作" min-width="200" align="center">
              <template slot-scope="scope">
                  <router-link :to="'/teacher/edit/' + scope.row.id">
                    <el-button type="primary" size="mini" icon="el-icon-edit">修改</el-button>
                  </router-link>
                  <el-button type="danger" size="mini" icon="el-icon-delete" @click="removeDataById(scope.row.id)">删除</el-button>
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
// 引入调用teacher.js文件
import teacher from "@/api/edu/teacher";

/*
export default 表示被别人调用
原始使用Vue，需要new Vue({})
现在不需要这样写，在main.js中已经封装好了
*/
export default {
    // 写核心代码位置

    // 两种写法形式都可以
    // data: {

    // },
    data() {  // 定义当页面中使用的变量和初始值
        return {
            list: null, // 查询之后接口返回集合
            page: 1, // 当前页
            limit: 10, // 每页记录数
            total: 0,
            teacherQuery: {} // 条件封装对象，js中可以不预先定义属性

        }
    }, 
    created() { // 页面渲染之前执行，一般调用methods定义的方法
        this.getList()
    },
    methods: { // 创建具体的方法，调用teacher.js定义的方法
        // 讲师列表的方法
        // 可以把这边的getList方法名写成请求的teacher.getTeacherListPage相同，这边为了开发调式清晰故意写成不同
        getList(page = 1) {   
            this.page = page   // 分页
            teacher.getTeacherListPage(this.page, this.limit, this.teacherQuery)
                .then(response => {  // 请求成功
                    // response是接口返回的数据
                    // console.log(response);
                    this.list = response.data.rows
                    this.total = response.data.total 
                })
            
        },
        resetData() {
            // 表单输入项数据清空，（因为是双向绑定）
            this.teacherQuery = {}
            // 查询所有讲师数据
            this.getList()
        },
        removeDataById(id) {
            this.$confirm('此操作将永久删除讲师记录，是否继续？', '提示', {
                confirmButtonText: '确定',
                cancelButtonText: '取消',
                type: 'warning'
            }).then(() => { // 点击确定，执行then方法
                teacher.deleteTeacherId(id)
                .then(response => { // 删除成功
                    // 提示信息
                    this.$message({
                        type: 'success',
                        message: '删除成功！'
                    });
                    // 回到列表页面
                    this.getList()
                })
            })     
                
        }
    }
}

</script>

