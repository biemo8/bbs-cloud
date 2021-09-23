<template>
  <div>
    <el-dialog :title="title" :width="width"
               :visible.sync="isShowVisible"
               center :close-on-click-modal="false"
               :before-close="() => $emit('close')">
      <el-card>
        <el-form label-width="100px" :model="permissionData" ref="permissionData" :rules="rules" v-loading="formLoading">
          <el-form-item label="应用名称:" prop="appId" id="appId">
            <el-select v-model="permissionData.appId" filterable placeholder="请选择">
              <el-option
                v-for="item in appList"
                :key="item.appId"
                :label="item.appName"
                :value="item.appId">
              </el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="权限名称:" prop="permissionName" id="permissionName">
            <el-input v-model="permissionData.permissionName"></el-input>
          </el-form-item>
          <el-form-item label="权限描述:">
            <el-input v-model="permissionData.permissionDesc"></el-input>
          </el-form-item>
        </el-form>
      </el-card>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="savePermission">保存</el-button>
        <el-button @click="$emit('close')">取消</el-button>
      </div>
    </el-dialog>
    <!--绑定资源-->
    <permission-bind-resource v-if="isShowBindResource"
                              :permissionId="permissionId"
                              :appId="appId"
                              @close="closeBindResource"></permission-bind-resource>
  </div>
</template>

<script>
  import PermissionManageApi from "@/api/system/role/PermissionManageApi";
  import MenuManagementApi from "@/api/system/role/MenuManagementApi";
  export default {
    name: "permission_manage_add",
    props:{
      width:{
        type:String,
        default:'60vw'
      },
    },
    components:{
      PermissionBindResource: resolve => require(['./permission_bind_resource'], resolve),
    },
    data(){
      var checkAppId = (rule, value, callback) => {
        if (value === '') {
          return callback(new Error('请选择应用名称'))
        }else{
          callback()
        }
      };
      return{
        title:'新增权限',
        isShowVisible:true,
        formLoading:false, //formLoading动画
        permissionData:{
          appId:'',
        },
        rules:{
          appId: [{required: true, validator: checkAppId, tirgger: 'change'}],
          permissionName: [{required: true, message: '请填写权限名称', tirgger: 'blur'}],
        },
        appList:[],
        isShowBindResource:false, //控制绑定资源弹窗是否显示
        permissionId:'', //权限id
        appId:'', //应用id
      }
    },
    async created(){
      this.formLoading = true
      let http = new MenuManagementApi()
      //获取应用名称下拉框数据源
      let appList = await http.getAppSelect({})
      this.appList = appList.data
      this.formLoading = false
    },
    methods:{
      //保存
      savePermission(){
        this.$refs['permissionData'].validate((valid, object) => {
          if (valid) {
            const loading = this.$loading({
              lock: true,
              text: '请耐心等待.....',
              spinner: 'el-icon-loading',
              background: 'rgba(0, 0, 0, 0.7)'
            })
            let http = new PermissionManageApi()
            http.add(this.permissionData).then(res => {
              loading.close()
              if(res.code === 200){
                this.$message.success('保存成功!')
                //打开绑定资源弹窗去绑定资源
                this.permissionId = res.data
                this.appId = this.permissionData.appId
                this.isShowBindResource = true
              }else{
                this.$message.error('保存失败!')
              }
            }).catch(e => {
              loading.close()
            })
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
      //关闭资源弹窗
      closeBindResource(){
        this.$emit('close')
        this.isShowBindResource = false
      },
    },
  }
</script>

<style scoped>

</style>
