<template>
  <div id="aCoursesList" class="bg-fa of">
    <!-- /课程列表 开始 -->
    <section class="container">
      <header class="comm-title">
        <h2 class="fl tac">
          <span class="c-333">全部课程</span>
        </h2>
      </header>
      <section class="c-sort-box">
        <section class="c-s-dl">
          <dl>
            <dt>
              <span class="c-999 fsize14">课程类别</span>
            </dt>
            <dd class="c-s-dl-li">
              <ul class="clearfix">
                <li>
                  <a title="全部" href="#">全部</a>
                </li>
                <li v-for="(item,index) in subjectNestedList" :key="index" :class="{active : oneIndex == index}">
                  <a :title="item.title" href="#" @click="searchOne(item.id, index)">{{ item.title }}</a>
                </li>            
              </ul>
            </dd>
          </dl>
          <dl>
            <dt>
              <span class="c-999 fsize14"></span>
            </dt>
            <dd class="c-s-dl-li">
              <ul class="clearfix">
                <li v-for="(item,index) in subSubjectList" :key="index" :class="{active : twoIndex == index}">
                  <a :title="item.title" href="#" @click="searchTwo(item.id, index)">{{ item.title }}</a>
                </li>
              </ul>
            </dd>
          </dl>
          <div class="clear"></div>
        </section>
        <div class="js-wrap">
          <section class="fr">
            <span class="c-ccc">
              <i class="c-master f-fM">1</i>/
              <i class="c-666 f-fM">1</i>
            </span>
          </section>
          <section class="fl">
            <ol class="js-tap clearfix">
              <li :class="{'current bg-orange' : buyCountSort != ''}">
                <a title="销量" href="javascript:void(0);" @click="searchBuyCount()">销量</a>
                <span :class="{hide:buyCountSort==''}">↓</span>
              </li>
              <li :class="{'current bg-orange' : gmtCreateSort != ''}">
                <a title="最新" href="javascript:void(0);" @click="searchGmtCreate()">最新</a>
                <span :class="{hide:gmtCreateSort==''}">↓</span>
              </li>
              <li :class="{'current bg-orange' : priceSort != ''}">
                <a title="价格" href="javascript:void(0);" @click="searchPrice()">价格</a>
                <span :class="{hide:priceSort==''}">↓</span>
              </li>
            </ol>
          </section>
        </div>
        <div class="mt40">
          <!-- /无数据提示 开始-->
          <section class="no-data-wrap" v-if="data.total == 0">
            <em class="icon30 no-data-ico">&nbsp;</em>
            <span class="c-666 fsize14 ml10 vam">没有相关数据，小编正在努力整理中...</span>
          </section>
          <!-- /无数据提示 结束-->
          <article class="comm-course-list" v-if="data.total > 0">
            <ul class="of" id="bna">
              <li v-for="item in data.items" :key="item.id">
                <div class="cc-l-wrap">
                  <section class="course-img">
                    <img :src="item.cover" class="img-responsive" :alt="item.title">
                    <div class="cc-mask">
                      <a :href="'/course/' + item.id" :title="item.title" class="comm-btn c-btn-1">开始学习</a>
                    </div>
                  </section>
                  <h3 class="hLh30 txtOf mt10">
                    <a :href="'/course/' + item.id" :title="item.title" class="course-title fsize18 c-333">{{ item.title }}</a>
                  </h3>
                  <section class="mt10 hLh20 of">
                    <span class="fr jgTag bg-green" v-if="Number(item.price)">
                      <i class="c-fff fsize12 f-fA">免费</i>
                    </span>
                    <span class="fl jgAttr c-ccc f-fA">
                      <i class="c-999 f-fA">9634人学习</i>
                      |
                      <i class="c-999 f-fA">9634评论</i>
                    </span>
                  </section>
                </div>
              </li>
              
            </ul>
            <div class="clear"></div>
          </article>
        </div>
        <!-- 公共分页 开始 -->
        <div>
          <div class="paging">
            <a :class="{undisable: !data.hasPrevious}" href="#" title="首页" @click.prevent="gotoPage(1)">首</a>
            <a :class="{undisable: !data.hasPrevious}" href="#" title="前一页" @click.prevent="gotoPage(data.current - 1)">前一页</a>
            <a v-for="page in data.pages"
            :key="page"
            :class="{current: data.current == page, undisable: data.current == page}"
            :title="'第' + page + '页'"
            href="#"
            @click.prevent="gotoPage(page)"
            >{{ page }}</a>
            <a :class="{undisable: !data.hasNext}" href="#" title="后一页" @click.prevent="gotoPage(data.current + 1)">后一页</a>
            <a :class="{undisable: !data.hasNext}" href="#" title="末页" @click.prevent="gotoPage(1)">末</a>
            <div class="clear"></div>
          </div>
        </div>
        <!-- 公共分页 结束 -->
      </section>
    </section>
    <!-- /课程列表 结束 -->
  </div>
</template>
<script>
import courseApi from '@/api/course'

export default {
  data() {
    return {
      page: 1,
      data:{},  //课程列表
      subjectNestedList: [], // 一级分类列表
      subSubjectList: [], // 二级分类列表

      searchObj: {}, // 查询表单对象

      oneIndex:-1,
      twoIndex:-1,
      buyCountSort:"",
      gmtCreateSort:"",
      priceSort:""
    }
  },
  created() {
    this.initCourseFirst()
    this.initSubject()
  },
  methods: {
    // 查询第一页数据
    initCourseFirst() {
      courseApi.getCourseList(1, 8, this.searchObj).then(response => {
        console.log("sfsf" + response.data.data)
        this.data = response.data.data
      })
    },
    // 查询所有一级分类
    initSubject() {
      courseApi.getAllSubject().then(response => {
        this.subjectNestedList = response.data.data.list
      })
    },
    // 分页切换
    gotoPage(page) {
      courseApi.getCourseList(page, 8, this.searchObj).then(response => {
        this.data = response.data.data
      })
    },
    // 根据一级分类查询对应二级分类
    searchOne(subjectParentId, index) {
      this.oneIndex = index;

      this.twoIndex = -1
      this.searchObj.subjectId = ""
      this.subSubjectList = []

      //
      this.searchObj.subjectParentId = subjectParentId
      //
      this.gotoPage(1)

      //
      for (let i = 0; i < this.subjectNestedList.length; i++) {
        var oneSubject = this.subjectNestedList[i]
        if (subjectParentId == oneSubject.id) {
          this.subSubjectList = oneSubject.children
        }
      }
    },
    // 点击二级分类实现查询
    searchTwo(subjectId, index) {
      this.twoIndex = index
      this.searchOb.subjectId = subjectId
      this.gotoPage(1)
    },
    // 根据销量排序
    searchBuyCount() {
      //设置对应变量值，为了样式生效
      this.buyCountSort = "1"
      this.gmtCreateSort = ""
      this.priceSort = ""

      //把值赋值到searchObj
      this.searchObj.buyCountSort = this.buyCountSort
      this.searchObj.gmtCreateSort = this.gmtCreateSort;
      this.searchObj.priceSort = this.priceSort;

      //调用方法查询
      this.gotoPage(1)
    },
    // 最新排序
    searchGmtCreate() {
      //设置对应变量值，为了样式生效
      this.buyCountSort = ""
      this.gmtCreateSort = "1"
      this.priceSort = ""

      //把值赋值到searchObj
      this.searchObj.buyCountSort = this.buyCountSort
      this.searchObj.gmtCreateSort = this.gmtCreateSort;
      this.searchObj.priceSort = this.priceSort;

      //调用方法查询
      this.gotoPage(1)
    },
    // 价格排序
    searchPrice() {
      //设置对应变量值，为了样式生效
      this.buyCountSort = ""
      this.gmtCreateSort = ""
      this.priceSort = "1"

      //把值赋值到searchObj
      this.searchObj.buyCountSort = this.buyCountSort
      this.searchObj.gmtCreateSort = this.gmtCreateSort;
      this.searchObj.priceSort = this.priceSort;

      //调用方法查询
      this.gotoPage(1)
    }
  }
};
</script>
<style scoped>
  .active {
    background: #bdbdbd;
  }
  .hide {
    display: none;
  }
  .show {
    display: block;
  }
</style>