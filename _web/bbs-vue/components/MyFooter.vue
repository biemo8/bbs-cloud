<template>
  <footer class="footer">
    <div class="container content">
      <div>
        <nuxt-link to="/about">关于</nuxt-link>
        <nuxt-link to="/tags">标签</nuxt-link>
        <nuxt-link to="/links">友链</nuxt-link>
      </div>
      <div>
        © 2021 Powered by
        <a href="https://cloud.biemo.com" target="_blank" class="light">biemo-cloud</a> <br>
        <p>发表言论时，遵守当地法律法规。本站部分图片、文章来源于网络，版权归原作者所有，如有侵权，请与我联系删除。</p>
        <a target="_blank" href="http://www.miitbeian.gov.cn">湘ICP备14016160号-1</a>
      </div>
    </div>
  </footer>
</template>

<script>
export default {}
</script>

<style lang="scss" scoped>
.footer {
  font-size: 14px;
  color: #666;
  background: none;
  text-align: left;
  margin: 0 10px;
  a {
    color: #666;
    text-decoration: none;
  }

  .light {
    color: #eb5424;
    font-weight: bold;
  }
}
</style>
