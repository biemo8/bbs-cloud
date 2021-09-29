<template>
  <section class="main">
    <div class="container main-container is-white left-main size-320">
      <div class="left-container">
        <load-more
          v-slot="{ results }"
          :init-data="articlesPage"
          url="/api/article/articles"
        >
          <article-list :articles="results" :show-ad="true" />
        </load-more>
      </div>
      <div class="right-container">
        <site-notice />

        <div class="ad">
          <!-- 展示广告 -->
          <!-- cpu feeds block -->
          <div class="cpu-feeds-block" data-site-id="269157108" data-block-id="89275"></div>
          <script>
            (function(){var url="//cpu.baidu.com";var getEntry=function(){var script=document.createElement("script");script.id="cpu-block-entry";script.src=url+"/js/cb_pc.js";var scriptTag=document.getElementsByTagName("script")[0];scriptTag.parentNode.insertBefore(script,scriptTag)};var sentMsg=function(e){var msg={msg:e.toString()||"no err msg",line:e.lineno||-1};var img=document.createElement("img");if(JSON){msg=encodeURIComponent(JSON.stringify(msg))}else{msg="&line="+msg.line+"&msg="+msg.msg}img.src=url+"/msg?m=block&e="+msg};try{window.feedsbycpu=window.feedsbycpu||[];window.feedsbycpu.push({siteId:"269157108",blockId:"89275",display:"auto"});document.getElementById("cpu-block-entry")||getEntry()}catch(e){sentMsg(e)}})();
          </script>
          <!-- cpu feeds block end-->
        </div>

        <div class="ad">
          <!-- 展示广告 -->
          <!-- cpu feeds block -->
          <div class="cpu-feeds-block" data-site-id="269157108" data-block-id="89275"></div>
          <script>
            (function(){var url="//cpu.baidu.com";var getEntry=function(){var script=document.createElement("script");script.id="cpu-block-entry";script.src=url+"/js/cb_pc.js";var scriptTag=document.getElementsByTagName("script")[0];scriptTag.parentNode.insertBefore(script,scriptTag)};var sentMsg=function(e){var msg={msg:e.toString()||"no err msg",line:e.lineno||-1};var img=document.createElement("img");if(JSON){msg=encodeURIComponent(JSON.stringify(msg))}else{msg="&line="+msg.line+"&msg="+msg.msg}img.src=url+"/msg?m=block&e="+msg};try{window.feedsbycpu=window.feedsbycpu||[];window.feedsbycpu.push({siteId:"269157108",blockId:"89275",display:"auto"});document.getElementById("cpu-block-entry")||getEntry()}catch(e){sentMsg(e)}})();
          </script>
          <!-- cpu feeds block end-->
        </div>
      </div>
    </div>
  </section>
</template>

<script>
export default {
  async asyncData({ $axios }) {
    try {
      const [articlesPage] = await Promise.all([
        $axios.get('/api/article/articles'),
      ])
      return {
        articlesPage,
      }
    } catch (e) {
      console.error(e)
    }
  },
  head() {
    return {
      title: this.$siteTitle('文章'),
      meta: [
        {
          hid: 'description',
          name: 'description',
          content: this.$siteDescription(),
        },
        { hid: 'keywords', name: 'keywords', content: this.$siteKeywords() },
      ],
    }
  },
}
</script>

<style lang="scss" scoped></style>
