<template>
  <section class="main">
    <div class="container">
      <div class="widget">
        <div class="widget-header">关于</div>
        <div class="widget-content content">
          <h2>简介</h2>
          <p>
            开发者社区 www.makesoft.cn，一个交流分享互助平台，帮助大家打造个人品牌，积累资源，知识分享变现，助力职业发展。 由 Springboot+mybatis-plus 微服务框架，希望大家喜欢。开源地址https://gitee.com/biemo/bbs-cloud
          </p>

          <p>
            为方便大家学习和交流我们搭建了&nbsp;<a
              href="https://makesoft.cn"
              target="_blank"
              >开发者社区</a
            >，欢迎大家前往交流。
          </p>

          <h2>与我联系</h2>
          <p>
            如有任何想法、意见、问题可前往&nbsp;<a
              href="https://www.makesoft.cn"
              target="_blank"
              >开发者社区</a
            >&nbsp;交流。
          </p>
          <!-- 展示广告 -->
        </div>
      </div>
    </div>
  </section>
</template>

<script>
export default {
  head() {
    return {
      title: this.$siteTitle('关于'),
    }
  },
}
</script>

<style lang="scss" scoped></style>
