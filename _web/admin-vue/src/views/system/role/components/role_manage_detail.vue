<template>
  <div>
    <el-dialog :title="title" :width="width"
               :visible.sync="isShowVisible"
               center :close-on-click-modal="false"
               :before-close="() => $emit('close')">
      <el-card>
        <el-form label-width="115px">
          <el-row>
<!--            <el-col :span="12">-->
<!--              <el-form-item label="所属公司类型:">-->
<!--                <span v-if="roleData.mgrFlag === '1'">管理公司角色</span>-->
<!--                <span v-if="roleData.mgrFlag === '2'">执行公司角色</span>-->
<!--              </el-form-item>-->
<!--            </el-col>-->
            <el-col :span="12">
              <el-form-item label="角色排序码:">
                <span>{{roleData.orderNo}}</span>
              </el-form-item>
            </el-col>
          </el-row>
          <el-form-item label="所属应用:">
            <span>{{roleData.appName}}</span>
          </el-form-item>
          <el-form-item label="角色名称:">
            <span>{{roleData.roleName}}</span>
          </el-form-item>
          <el-form-item label="角色编码:">
            <span>{{roleData.roleCode}}</span>
          </el-form-item>
          <el-form-item label="角色描述:">
            <span>{{roleData.roleDesc}}</span>
          </el-form-item>
          <el-form-item label="状态:">
            <span v-if="roleData.status === 1">启用</span>
            <span v-if="roleData.status === 2">禁用</span>
          </el-form-item>
<!--          <el-form-item label="角色类型:">-->
<!--            <span v-if="roleData.roleType === '1'">平台管理员</span>-->
<!--            <span v-if="roleData.roleType === '2'">企业管理员</span>-->
<!--            <span v-if="roleData.roleType === '3'">企业业务类</span>-->
<!--            <span v-if="roleData.roleType === '4'">项目业务类</span>-->
<!--          </el-form-item>-->
<!--          <el-form-item label="是否是集团角色:">-->
<!--            <span v-if="roleData.blocFlag === '1'">非集团公司</span>-->
<!--            <span v-if="roleData.blocFlag === '2'">集团公司</span>-->
<!--          </el-form-item>-->
<!--          <el-form-item label="角色标记:">-->
<!--            <span v-if="roleData.type === '1'">项目角色</span>-->
<!--            <span v-if="roleData.type === '2'">易招标角色</span>-->
<!--            <span v-if="roleData.type === '3'">既是项目角色又是易招标角色</span>-->
<!--            <span v-if="roleData.type === '0'">其他</span>-->
<!--          </el-form-item>-->
<!--          <el-form-item label="是否隐藏:">-->
<!--            <span v-if="roleData.hideFlag === 'Y'">是</span>-->
<!--            <span v-if="roleData.hideFlag === 'N'">否</span>-->
<!--          </el-form-item>-->
        </el-form>
      </el-card>
      <div slot="footer" class="dialog-footer">
        <el-button @click="$emit('close')">关闭</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
  import RoleManageApi from "@/api/system/role/RoleManageApi";
  export default {
    name: "role_manage_detail",
    props:{
      width:{
        type:String,
        default:'60vw'
      },
      roleId:{
        type:String,
      },
    },
    data(){
      return{
        title:'角色详情',
        isShowVisible:true,
        formLoading:false, //formLoading动画
        roleData:{},
      }
    },
    async created(){
      this.formLoading = true
      let https = new RoleManageApi()
      let roleData = await https.detail({roleId: this.roleId})
      this.roleData = roleData.data
      this.formLoading = false
    },
  }
</script>

<style scoped>

</style>
