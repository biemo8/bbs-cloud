<template>
  <div>
    <el-dialog :title="title" :width="width"
               :visible.sync="isShowVisible"
               center :close-on-click-modal="false"
               :before-close="() => $emit('close')">
      <el-card>
        <el-form label-width="100px">
          <el-form-item label="应用名称:">
            <span>{{permissionData.appName}}</span>
          </el-form-item>
          <el-form-item label="权限名称:">
            <span>{{permissionData.permissionName}}</span>
          </el-form-item>
          <el-form-item label="权限描述:">
            <span>{{permissionData.permissionDesc}}</span>
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
  import PermissionManageApi from "@/api/system/role/PermissionManageApi";
  export default {
    name: "permission_manage_detail",
    props:{
      width:{
        type:String,
        default:'60vw'
      },
      permissionData:{
        type:Object,
      },
    },
    data(){
      return{
        title:'权限详情',
        isShowVisible:true,
      }
    },
  }
</script>

<style scoped>

</style>
