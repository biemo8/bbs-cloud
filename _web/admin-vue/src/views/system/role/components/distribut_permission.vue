<template>
  <div>
    <el-dialog :title="title" :width="width"
               :visible.sync="isShowVisible"
               center :close-on-click-modal="false"
               :before-close="() => $emit('close')">
      <el-card v-loading="formLoading">
        <el-form label-width="100px">
          <el-form-item label="应用名称:">
            <el-select v-model="selectAppId" @change="appIdChange" filterable placeholder="请选择">
              <el-option
                v-for="item in appList"
                :key="item.appId"
                :label="item.appName"
                :value="item.appId">
              </el-option>
            </el-select>
          </el-form-item>
        </el-form>
        <el-tree
          :props="defaultProps"
          :data="dataTree"
          default-expand-all
          node-key="permissionId"
          :default-checked-keys="defaultCheckedList"
          show-checkbox
          @check-change="handleCheckChange">
        </el-tree>
      </el-card>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="saveDistributPermission">保存</el-button>
        <el-button @click="$emit('close')">关闭</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
  import MenuManagementApi from "@/api/system/role/MenuManagementApi";
  import RoleManageApi from "@/api/system/role/RoleManageApi";
  export default {
    name: "distribut_permission",
    props:{
      width:{
        type:String,
        default:'60vw'
      },
      appId:{
        type:String,
      },
      roleId:{
        type:String,
      },
    },
    data(){
      return{
        title:'分配权限',
        isShowVisible:true,
        selectAppId:'',
        appList:[],
        dataTree:[],
        defaultProps: {
          label: 'permissionName'
        },
        defaultCheckedList:[], //已经分配的权限
        cacheCurrentCheckedPermission:[], //缓存当前应用已经分配的权限，在保存时作过滤用
      }
    },
    async created(){
      this.formLoading = true
      let http = new MenuManagementApi()
      let https = new RoleManageApi()
      //获取应用名称下拉框数据源
      let appList = await http.getAppSelect({})
      this.appList = appList.data
      this.selectAppId = this.appId
      //获取已经分配的权限
      let permissionTreeData = await https.findRoleAllPermissions({roleId:this.roleId})
      this.defaultCheckedList = permissionTreeData.data
      this.initData()
    },
    methods:{
      //初始数据操作
      async initData(){
        this.formLoading = true
        let https = new RoleManageApi()
        //获取权限树数据
        let rolePermissionData = await https.permissions({roleId:this.roleId, appId:this.selectAppId})
        this.dataTree = rolePermissionData.data
        this.formLoading = false
      },
      //应用名称变化时
      appIdChange(val){
        this.initData()
      },
      //选择节点时
      handleCheckChange(data, checked) {
        if(checked){
          //若是选择则往已经选择的权限里加一条
          this.defaultCheckedList.push(data.permissionId)
        }else{
          //若是取消选择则在已经选择的权限里删除这一条
          this.defaultCheckedList.splice(this.defaultCheckedList.indexOf(data.permissionId), 1)
        }
      },
      //保存
      saveDistributPermission(){
        const loading = this.$loading({
          lock: true,
          text: '请耐心等待.....',
          spinner: 'el-icon-loading',
          background: 'rgba(0, 0, 0, 0.7)'
        })
        let http = new RoleManageApi()
        http.permission({roleId:this.roleId, appId:this.selectAppId, permissionIds:this.defaultCheckedList}).then(res => {
          loading.close()
          if(res.code === 200){
            this.$message.success('权限分配成功!')
            this.$emit('close')
          }else{
            this.$message.error('权限分配失败!')
          }
        }).catch(e => {
          loading.close()
        })
      },
    },
  }
</script>

<style scoped>

</style>
