<template>
  <div>
    <el-dialog :title="title" :width="width"
               :visible.sync="isShowVisible"
               center :close-on-click-modal="false"
               :before-close="() => $emit('close')">
      <el-card>
        <el-form label-width="105px">
          <el-row>
            <el-col :span="12">
              <el-form-item label="应用名称:">
                <span>{{appData.appName}}</span>
              </el-form-item>
              <el-form-item label="应用编码:">
                <span>{{appData.appCode}}</span>
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item label="应用图标地址:">
                <span>{{appData.appIcon}}</span>
              </el-form-item>
            </el-col>
          </el-row>
          <el-form-item label="应用地址:">
            <span>{{appData.appJumpAddress}}</span>
          </el-form-item>
          <el-row>
            <el-col :span="12">
              <el-form-item label="排序:">
                <span>{{appData.orderNo}}</span>
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item label="应用类型:">
                <span>{{appData.appTypeName}}</span>
              </el-form-item>
            </el-col>
          </el-row>
          <el-form-item label="应用描述:">
            <span>{{appData.description}}</span>
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
  import AppRegisterApi from '@/api/system/app/AppRegisterApi'
  export default {
    name: "app_register_detail",
    props:{
      width:{
        type:String,
        default:'60vw'
      },
      appId:{
        type:String,
      },
    },
    data(){
      return{
        title:'应用详情',
        isShowVisible:true,
        appData:{},
      }
    },
    created(){
      this.isLoading = true
      let http = new AppRegisterApi()
      http.detail({appId:this.appId}).then(res => {
        this.appData = res.data
        this.isLoading = false
      })
    },
  }
</script>

<style scoped>

</style>
