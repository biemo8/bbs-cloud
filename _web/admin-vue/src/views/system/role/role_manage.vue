<template>
  <div>
    <el-card>
      <el-form inline>
        <el-form-item label="应用名称:" class="mgb-0">
          <el-select v-model="searchData.appId" filterable placeholder="请选择">
            <el-option
              v-for="item in appList"
              :key="item.appId"
              :label="item.appName"
              :value="item.appId">
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="角色名称:" class="mgb-0">
          <el-input v-model="searchData.roleName"></el-input>
        </el-form-item>
        <el-form-item class="mgb-0">
          <el-button type="primary" icon="el-icon-search" @click="searchList"></el-button>
          <el-button type="success" icon="el-icon-refresh" @click="clearList"></el-button>
        </el-form-item>
      </el-form>
    </el-card>
    <el-card style="margin-top: 10px">
      <div style="margin-bottom: 10px">
        <el-button type="primary" @click="openRoleAddEdit">新增</el-button>
      </div>
      <el-table border :data="dataList" v-loading="tableLoading" :show-overflow-tooltip="true">
        <el-table-column label="序号" align="center" type="index" width="50"></el-table-column>
        <el-table-column label="所属应用" prop="appName" align="center" show-overflow-tooltip></el-table-column>
        <el-table-column label="角色名称" prop="roleName" align="center" show-overflow-tooltip></el-table-column>
        <el-table-column label="角色描述" prop="roleDesc" align="center" show-overflow-tooltip></el-table-column>
        <el-table-column label="状态" align="center" show-overflow-tooltip>
          <template slot-scope="scope">
            <el-tag v-if="scope.row.status === 1">启用</el-tag>
            <el-tag v-if="scope.row.status === 2" type="danger">禁用</el-tag>
          </template>
        </el-table-column>
        <el-table-column label="操作" align="center" width="257">
          <template slot-scope="scope">
            <el-button type="text" @click="openRoleDetail(scope.row.roleId)">详情</el-button>
            <el-button type="text" @click="editRole(scope.row.roleId)">修改</el-button>
            <el-button type="text" style="color: red" v-if="scope.row.status === 1" @click="disEnableMenu(scope.row.roleId, '禁用')">禁用</el-button>
            <el-button type="text" v-if="scope.row.status === 2" @click="disEnableMenu(scope.row.roleId, '启用')">启用</el-button>
            <el-button type="text" style="color: red" @click="deleteRole(scope.row.roleId)">删除</el-button>
            <el-button type="text" @click="openDistributPermission(scope.row)">分配权限</el-button>
          </template>
        </el-table-column>
      </el-table>
      <el-pagination
        style="float:right; margin: 10px 0;"
        @size-change="handleSizeChange"
        @current-change="handleCurrentChange"
        :current-page="pageNo"
        :page-sizes="[10, 15, 20, 50]"
        :page-size="pageSize"
        layout="total, sizes, prev, pager, next, jumper"
        prev-text="上一页"
        next-text="下一页"
        :total="total">
      </el-pagination>
    </el-card>
    <!--新增编辑弹窗-->
    <role-manage-addEdit v-if="isShowRoleAddEdit"
                         :roleId="roleId"
                         @close="closeRoleAddEdit"></role-manage-addEdit>
    <!--详情弹窗-->
    <role-manage-detail v-if="isShowRoleDetail"
                        :roleId="roleId"
                        @close="closeRoleDetail"></role-manage-detail>
    <!--分配权限弹窗-->
    <distribut-permission v-if="isShowDistributPermission"
                          :appId="appId"
                          :roleId="roleId"
                          @close="closeDistributPermission"></distribut-permission>
  </div>
</template>

<script>
  import RoleManageApi from "@/api/system/role/RoleManageApi"
  import MenuManagementApi from "@/api/system/role/MenuManagementApi"
  export default {
    name: "role_manage",
    components:{
      RoleManageAddEdit: resolve => require(['./components/role_manage_addEdit'], resolve),
      RoleManageDetail: resolve => require(['./components/role_manage_detail'], resolve),
      DistributPermission: resolve => require(['./components/distribut_permission'], resolve),
    },
    data(){
      return{
        searchData:{
          appId:'', //应用id
          roleName:'', //角色名称
        },
        appList:[], //应用名称的数据源
        tableLoading:false, //表格loading动画
        dataList:[],
        pageNo: 1, //当前页面
        pageSize: 10, // 当前页数
        total: 0, // 总条数
        isShowRoleAddEdit:false, //控制新增编辑弹窗是否显示
        roleId:'', //角色Id
        appId:'', //应用id
        isShowRoleDetail:false, //控制详情弹窗是否显示
        isShowDistributPermission:false, //控制分配权限弹窗是否显示
      }
    },
    created(){
      this.initData()
    },
    methods: {
      //基础数据渲染
      async initData() {
        this.tableLoading = true
        let https = new MenuManagementApi()
        //获取应用名称下拉框数据源
        let list = await https.getAppSelect({})
        this.appList = list.data
        this.getData()
      },
      //获取表格数据源
      getData() {
        this.tableLoading = true
        this.searchData.pageNo = this.pageNo
        this.searchData.pageSize = this.pageSize
        this.getTableListData(new RoleManageApi(), this.searchData, 'page').then(res => {
          this.dataList = res.dataList
          this.pageNo = res.pageNo
          this.pageSize = res.pageSize
          this.total = res.total
          this.tableLoading = false
        })
      },
      // 当前页码修改时
      handleCurrentChange(pageNo) {
        this.pageNo = pageNo
        this.getData()
      },
      // 当前页数修改时
      handleSizeChange(pageSize) {
        this.pageSize = pageSize
        this.getData()
      },
      //搜索
      searchList() {
        this.pageNo = 1
        this.getData()
      },
      //清空搜索
      clearList() {
        this.searchData.appId = ''
        this.searchData.roleName = ''
      },
      //打开新增弹窗
      openRoleAddEdit(){
        this.isShowRoleAddEdit = true
      },
      //关闭新增编辑弹窗
      closeRoleAddEdit(){
        this.roleId = ''
        this.isShowRoleAddEdit = false
        this.getData()
      },
      //打开编辑弹窗
      editRole(id){
        this.roleId = id
        this.isShowRoleAddEdit = true
      },
      //删除
      deleteRole(roleId){
        this.$confirm('您确定要删除此项吗?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning',
        }).then(() => {
          const loading = this.$loading({
            lock: true,
            text: '正在删除.....',
            spinner: 'el-icon-loading',
            background: 'rgba(0, 0, 0, 0.7)'
          })
          let http = new RoleManageApi()
          http.delete({roleId}).then(res => {
            if(res.code === 200){
              loading.close()
              this.$message.success('删除成功！')
              this.getData()
            }else{
              this.$message.error('删除失败！')
            }
          }).catch(err => {
            loading.close()
          })
        }).catch(() => {
          this.$message.info('已取消删除')
        })
      },
      //启用或禁用
      disEnableMenu(roleId, text){
        const loading = this.$loading({
          lock: true,
          text: '请耐心等待.....',
          spinner: 'el-icon-loading',
          background: 'rgba(0, 0, 0, 0.7)'
        })
        let http = new RoleManageApi()
        let status = text === '启用' ? 1 : 2
        http.status({roleId, status}).then(res => {
          if(res.code === 200){
            this.$message.success(`${text}成功!`)
            this.getData()
          }else{
            this.$message.error(`${text}失败!`)
          }
          loading.close()
        }).catch(e => {
          loading.close()
        })
      },
      //打开详情弹窗
      openRoleDetail(id){
        this.roleId = id
        this.isShowRoleDetail = true
      },
      //关闭详情弹窗
      closeRoleDetail(){
        this.roleId = ''
        this.isShowRoleDetail = false
      },
      //打开分配权限弹窗
      openDistributPermission(val){
        this.appId = val.appId
        this.roleId = val.roleId
        this.isShowDistributPermission = true
      },
      //关闭分配权限弹窗
      closeDistributPermission(){
        this.appId = ''
        this.roleId = ''
        this.isShowDistributPermission = false
      },
    },
  }
</script>

<style scoped>

</style>

