// module.exports = {
export default {
  
  // Global page headers: https://go.nuxtjs.dev/config-head
  head: {
    title: '谷粒学院 -Java视频|HTML5视频|前端视频|Python视频1大数据视频-自学皇1万+月薪的IT在线视频课程，谷粉力挺，老学员为你推荐。',
    htmlAttrs: {
      lang: 'en'
    },
    meta: [
      { charset: 'utf-8' },
      { name: 'viewport', content: 'width=device-width, initial-scale=1' },
      {hid: 'keywords', name: 'keywords', content: '谷粒学院，IT在线视频教程，了ava视频，HTMLS视频，前端视频,Python视频，大数据视频上'},
      { hid: 'description', name: 'description', content: '谷粒学院是国内领先的工T在线视频学习平台、职业教育平台。截止目前，谷粒学院线上、线下学习人次数以万计！会同上百个知名开发团队联合制定的Java、HTMLS前端、大数据、Python等视频课程，被广大学习者及工T工程师誉为：业界最适合自学、代码量最大、素例最多、实战性最强、技术最前沿的IT系列视频课程！' },
      { name: 'format-detection', content: 'telephone=no' }
    ],
    link: [
      { rel: 'icon', type: 'image/x-icon', href: '/favicon.ico' }
    ]
  },

  // Global CSS: https://go.nuxtjs.dev/config-css
  css: [
    'swiper/dist/css/swiper.css'
  ],

  // Plugins to run before rendering page: https://go.nuxtjs.dev/config-plugins
  plugins: [
    { src: '~/plugins/nuxt-swiper-plugin.js', ssr: false }
  ],

  // Auto import components: https://go.nuxtjs.dev/config-components
  components: true,

  // Modules for dev and build (recommended): https://go.nuxtjs.dev/config-modules
  buildModules: [
  ],

  // Modules: https://go.nuxtjs.dev/config-modules
  modules: [
  ],

  // Build Configuration: https://go.nuxtjs.dev/config-build
  build: {
  }
}
