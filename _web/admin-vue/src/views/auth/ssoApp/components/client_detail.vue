<template>
  <div>
    <el-dialog :title="title" :width="width"
               :visible.sync="isShowVisible"
               center :close-on-click-modal="false"
               :before-close="() => $emit('close')">
      <el-card v-loading="isLoading">
        <div class="text_box">
          <span>基本信息</span>
        </div>
        <div class="text_box_line"></div>
        <el-form label-width="150px">
          <el-form-item label="所属应用:">
            <span>{{clientData.appName}}</span>
          </el-form-item>
          <el-form-item label="设备类型:">
            <span v-if="clientData.clientType === 1">浏览器端</span>
            <span v-if="clientData.clientType === 2">手机端</span>
          </el-form-item>
          <el-form-item label="客户端名称:">
            <span>{{clientData.name}}</span>
          </el-form-item>
          <el-form-item label="token接收地址:">
            <span>{{clientData.ssoUrl}}</span>
          </el-form-item>
          <el-form-item label="登录类型:">
            <span v-if="clientData.loginType === 1">应用自定义页面</span>
            <span v-if="clientData.loginType === 2">统一登录页面</span>
          </el-form-item>
          <el-form-item label="私钥:">
            <span>{{clientData.privateKey}}</span>
          </el-form-item>
          <el-form-item label="签名私钥:">
            <span>{{clientData.signPrivateKey}}</span>
          </el-form-item>
          <el-form-item label="数据私钥:">
            <span>{{clientData.dataPrivateKey}}</span>
          </el-form-item>
          <el-form-item label="token过期时间:">
            <span>{{clientData.tokenExp}}s</span>
          </el-form-item>
          <el-form-item label="刷新token过期时间:">
            <span>{{clientData.refreshTokenExp}}s</span>
          </el-form-item>
          <el-form-item label="退出登录的url:">
            <span>{{clientData.loginOutUrl}}</span>
          </el-form-item>
        </el-form>
      </el-card>
      <div slot="footer" class="dialog-footer">
        <el-button @click="$emit('close')">关闭</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
  import ClientManageApi from '@/api/auth/ssoApp/ClientManageApi'
  export default {
    name: "client_detail",
    props:{
      width:{
        type:String,
        default:'60vw'
      },
      clientId:{
        type:String,
      },
    },
    data(){
      return{
        title:'详情',
        isShowVisible:true,
        isLoading:false,
        clientData:{},
      }
    },
    async created(){
      this.isLoading = true
      let http = new ClientManageApi()
      let clientData = await http.detail({clientId:this.clientId})
      this.clientData = clientData.data
      this.isLoading = false
    },
  }
</script>

<style scoped>

</style>
