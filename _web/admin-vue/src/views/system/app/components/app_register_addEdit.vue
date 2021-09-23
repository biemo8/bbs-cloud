<template>
  <div>
    <el-dialog :title="title" :width="width"
               :visible.sync="isShowVisible"
               center :close-on-click-modal="false"
               :before-close="() => $emit('close')">
      <el-card v-loading="isLoading">
        <el-form label-width="105px" :model="appData" ref="appData" :rules="rules">
          <el-row>
            <el-col :span="12">
              <el-form-item label="应用名称:" prop="appName" id="appName">
                <el-input v-model="appData.appName"></el-input>
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item label="应用编码:" prop="appCode" id="appCode">
                <el-input v-model="appData.appCode" :disabled="appId !== ''"></el-input>
              </el-form-item>
            </el-col>
          </el-row>
          <el-form-item label="应用地址:">
            <el-input v-model="appData.appJumpAddress"></el-input>
          </el-form-item>
          <el-form-item label="应用图标地址:">
            <el-input v-model="appData.appIcon"></el-input>
          </el-form-item>
          <el-row>
            <el-col :span="12">
              <el-form-item label="排序:" prop="orderNo" id="orderNo">
                <el-input-number v-model.number="appData.orderNo"></el-input-number>
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item label="应用类型:" prop="appType" id="appType">
                <base-select dictTypeCode="APP_TYPE" v-model="appData.appType"></base-select>
              </el-form-item>
            </el-col>
          </el-row>
          <el-form-item label="应用状态:" prop="status" id="status">
            <el-radio-group v-model="appData.status">
              <el-radio :label="1">启用</el-radio>
              <el-radio :label="2">禁用</el-radio>
            </el-radio-group>
          </el-form-item>
          <el-form-item label="应用描述:">
            <el-input v-model="appData.description"></el-input>
          </el-form-item>
        </el-form>
      </el-card>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="saveApp">保存</el-button>
        <el-button @click="$emit('close')">取消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
  import AppRegisterApi from '@/api/system/app/AppRegisterApi'
  export default {
    name: "app_register_addEdit",
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
        title:'新增应用',
        isShowVisible:true,
        isLoading:false,
        rules:{
          appName: [{required: true, message: '请填写应用名称', tirgger: 'blur'}],
          appCode: [{required: true, message: '请填写应用编码', tirgger: 'blur'}],
          orderNo: [{type:'number', required: true, message: '请填写排序', tirgger: 'change'}],
          appType: [{required: true, message: '请选择应用类型', tirgger: 'change'}],
          status: [{type:'number', required: true, message: '请选择应用状态', tirgger: 'change'}],
        },
        appData:{},
      }
    },
    created(){
      if(this.appId){
        this.isLoading = true
        this.title = '编辑应用'
        let http = new AppRegisterApi()
        http.detail({appId:this.appId}).then(res => {
          this.appData = res.data
          this.isLoading = false
        })
      }
    },
    methods:{
      saveApp(){
        this.$refs['appData'].validate((valid, object) => {
          if (valid) {
            const loading = this.$loading({
              lock: true,
              text: '请耐心等待.....',
              spinner: 'el-icon-loading',
              background: 'rgba(0, 0, 0, 0.7)'
            })
            let http = new AppRegisterApi()
            //若有appId证明时编辑没有则时新增
            if(this.appId){
              http.update(this.appData).then(res => {
                loading.close()
                if(res.code === 200){
                  this.$message.success('保存成功!')
                  this.$emit('close')
                }else{
                  this.$message.error('保存失败!')
                }
              }).catch(e => {
                loading.close()
              })
            }else{
              http.add(this.appData).then(res => {
                loading.close()
                if(res.code === 200){
                  this.$message.success('保存成功!')
                  this.$emit('close')
                }else{
                  this.$message.error('保存失败!')
                }
              }).catch(e => {
                loading.close()
              })
            }
          } else {
            let validArr = []
            Object.keys(object).forEach(function (key) {
              validArr.push(key)
            });
            let validVal = "#" + validArr[0];
            document.querySelector(validVal).scrollIntoView(true);
          }
        })
      },
    },
  }
</script>

<style scoped lang="scss">
</style>
