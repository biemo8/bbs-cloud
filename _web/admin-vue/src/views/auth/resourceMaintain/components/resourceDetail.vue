<template>
  <div>
    <el-dialog :title="title" :width="width"
               :visible.sync="isShowVisible"
               center :close-on-click-modal="false"
               :before-close="() => $emit('close')">
      <el-card>
        <el-form label-width="152px" v-loading="formLoading">
          <div class="text_box">
            <span>基本信息</span>
          </div>
          <div class="text_box_line"></div>
          <el-row>
            <el-col :span="12">
              <el-form-item label="应用标识:">
                <span>{{resourceData.appCode}}</span>
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item label="控制器类名:">
                <span>{{resourceData.className}}</span>
              </el-form-item>
            </el-col>
          </el-row>
          <el-row>
            <el-col :span="12">
              <el-form-item label="控制器中的方法名称:">
                <span>{{resourceData.methodName}}</span>
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item label="资源所属模块:">
                <span>{{resourceData.modularName}}</span>
              </el-form-item>
            </el-col>
          </el-row>
          <el-row>
            <el-col :span="12">
              <el-form-item label="资源标识:">
                <span>{{resourceData.code}}</span>
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item label="资源名称:">
                <span>{{resourceData.name}}</span>
              </el-form-item>
            </el-col>
          </el-row>
          <el-form-item label="资源的请求路径:">
            <span>{{resourceData.url}}</span>
          </el-form-item>
          <el-row>
            <el-col :span="12">
              <el-form-item label="http请求方法:">
                <span>{{resourceData.httpMethod}}</span>
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item label="是否是菜单:">
                <span v-if="resourceData.menuFlag">是</span>
                <span v-if="!resourceData.menuFlag">否</span>
              </el-form-item>
            </el-col>
          </el-row>
          <el-row>
            <el-col :span="12">
              <el-form-item label="是否需要登录:">
                <span v-if="resourceData.requiredLogin">是</span>
                <span v-if="!resourceData.requiredLogin">否</span>
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item label="是否需要鉴权:">
                <span v-if="resourceData.requiredPermission">是</span>
                <span v-if="!resourceData.requiredPermission">否</span>
              </el-form-item>
            </el-col>
          </el-row>
          <el-form-item label="资源添加日期:">
            <span>{{resourceData.createTime}}</span>
          </el-form-item>
          <el-form-item label="资源初始化服务器的ip地址:" label-width="193px">
            <span>{{resourceData.ipAddress}}</span>
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
  import ResourceMaintainIndexApi from '@/api/auth/resourceMaintain/ResourceMaintainIndexApi'
  export default {
    name: "resourceDetail",
    props:{
      width:{
        type:String,
        default:'60vw'
      },
      url:{
        type:String,
      },
    },
    data(){
      return{
        title:'资源详情',
        isShowVisible:true,
        formLoading:false, //formLoading动画
        resourceData:{},
      }
    },
    async created(){
      this.formLoading = true
      let http = new ResourceMaintainIndexApi()
      let resourceData = await http.getDetail({cacheKey: this.url})
      this.resourceData = resourceData.data
      this.formLoading = false
    },
  }
</script>

<style scoped>

</style>
