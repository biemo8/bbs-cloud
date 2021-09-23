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
        <el-form-item label="权限名称:" class="mgb-0">
          <el-input v-model="searchData.permissionName"></el-input>
        </el-form-item>
        <el-form-item class="mgb-0">
          <el-button type="primary" icon="el-icon-search" @click="searchList"></el-button>
          <el-button type="success" icon="el-icon-refresh" @click="clearList"></el-button>
        </el-form-item>
      </el-form>
    </el-card>
    <el-card style="margin-top: 10px">
      <div style="margin-bottom: 10px">
        <el-button type="primary" @click="openPermissionAdd">新增权限</el-button>
        <el-button type="success" @click="batchDisEnabled('启用')">批量启用</el-button>
        <el-button type="danger" @click="batchDisEnabled('禁用')">批量禁用</el-button>
      </div>
      <el-table border :data="dataList" v-loading="tableLoading" :show-overflow-tooltip="true" @selection-change="handleSelectionChange">
        <el-table-column align="center" type="selection" width="55"></el-table-column>
        <el-table-column label="应用名称" prop="appName" align="center" show-overflow-tooltip></el-table-column>
        <el-table-column label="权限名称" prop="permissionName" align="center" show-overflow-tooltip></el-table-column>
        <el-table-column label="权限描述" prop="permissionDesc" align="center" show-overflow-tooltip></el-table-column>
        <el-table-column label="上级权限" prop="parentId" align="center" show-overflow-tooltip></el-table-column>
        <el-table-column label="状态" align="center" show-overflow-tooltip>
          <template slot-scope="scope">
            <el-tag v-if="scope.row.status === 1">启用</el-tag>
            <el-tag v-if="scope.row.status === 2" type="danger">禁用</el-tag>
          </template>
        </el-table-column>
        <el-table-column label="操作" align="center">
          <template slot-scope="scope">
            <el-button type="text" @click="detailPermission(scope.row)">详情</el-button>
            <el-button type="text" style="color: red" v-if="scope.row.status === 1" @click="disEnableMenu(scope.row.permissionId, '禁用')">禁用</el-button>
            <el-button type="text" v-if="scope.row.status === 2" @click="disEnableMenu(scope.row.permissionId, '启用')">启用</el-button>
            <el-button type="text" style="color: red" @click="deletePermission(scope.row.permissionId)">删除</el-button>
            <el-button type="text" @click="openBindResource(scope.row.permissionId, scope.row.appId)">绑定资源</el-button>
          </template>
        </el-table-column>
      </el-table>
      <el-pagination
        style="float:right; margin: 10px 0;"
        @size-change="handleSizeChange"
        @current-change="handleCurrentChange"
        :current-page="current"
        :page-sizes="[10, 15, 20, 50]"
        :page-size="size"
        layout="total, sizes, prev, pager, next, jumper"
        prev-text="上一页"
        next-text="下一页"
        :total="total">
      </el-pagination>
    </el-card>
    <!--新增弹窗-->
    <permission-manage-add v-if="isShowPermissionAdd" @close="closePermissionAdd"></permission-manage-add>
    <!--绑定资源-->
    <permission-bind-resource v-if="isShowBindResource"
                              :permissionId="permissionId"
                              :appId="appId"
                              @close="closeBindResource"></permission-bind-resource>
    <!--详情弹窗-->
    <permission-manage-detail v-if="isShowPermissionDetail"
                              :permissionData="permissionData"
                              @close="closePermissionDetail"></permission-manage-detail>
  </div>
</template>

<script>
  import PermissionManageApi from "@/api/system/role/PermissionManageApi";
  import MenuManagementApi from "@/api/system/role/MenuManagementApi";
  export default {
    name: "permission_manage",
    components:{
      PermissionManageAdd: resolve => require(['./components/permission_manage_add'], resolve),
      PermissionManageDetail: resolve => require(['./components/permission_manage_detail'], resolve),
      PermissionBindResource: resolve => require(['./components/permission_bind_resource'], resolve),
    },
    data(){
      return{
        searchData:{
          appId:'', //应用id
          permissionName:'', //权限名称
        },
        appList:[], //应用名称的数据源
        tableLoading:false, //表格loading动画
        dataList:[],
        current: 1, //当前页面
        size: 20, // 当前页数
        total: 0, // 总条数
        isShowPermissionAdd:false, //控制新增弹窗是否显示
        isShowBindResource:false, //控制绑定资源弹窗是否显示
        permissionId:'', //权限id
        appId:'', //应用id
        selectPermissionIds:[], //需要改变状态的数组集合
        isShowPermissionDetail:false, //控制详情弹窗是否显示
        permissionData:{}, //传给详情弹窗的对象
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
        let http = new PermissionManageApi()
        this.searchData.current = this.current
        this.searchData.size = this.size
        http.getPermissionPageList(this.searchData).then(res => {
          this.dataList = res.data.records
          this.current = res.data.current
          this.size = res.data.size
          this.total = res.data.total
          this.tableLoading = false
        })
      },
      // 当前页码修改时
      handleCurrentChange(pageNo) {
        this.current = pageNo
        this.getData()
      },
      // 当前页数修改时
      handleSizeChange(pageSize) {
        this.size = pageSize
        this.getData()
      },
      //搜索
      searchList() {
        this.current = 1
        this.getData()
      },
      //清空搜索
      clearList() {
        this.searchData.appId = ''
        this.searchData.permissionName = ''
      },
      //选择的需要删除的菜单
      handleSelectionChange(val){
        if(val.length === 0){
          this.selectPermissionIds = []
        }else{
          this.selectPermissionIds = val
        }
      },
      //禁用或启用
      disEnableMenu(permissionId, text){
        const loading = this.$loading({
          lock: true,
          text: '请耐心等待.....',
          spinner: 'el-icon-loading',
          background: 'rgba(0, 0, 0, 0.7)'
        })
        let http = new PermissionManageApi()
        let status = text === '启用' ? 1 : 2
        http.setPermissionStatus({permissionIds:[permissionId], status}).then(res => {
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
      //批量启用或禁用
      batchDisEnabled(text){
        if(this.selectPermissionIds.length === 0){
          this.$message.warning(`请先选择需要${text}的权限!`)
        }else{
          const loading = this.$loading({
            lock: true,
            text: '请耐心等待.....',
            spinner: 'el-icon-loading',
            background: 'rgba(0, 0, 0, 0.7)'
          })
          let http = new PermissionManageApi()
          let status = text === '启用' ? 1 : 2
          let permissionIds = this.selectPermissionIds.map(item => item.permissionId)
          http.setPermissionStatus({permissionIds, status}).then(res => {
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
        }
      },
      //打开新增弹窗
      openPermissionAdd(){
        this.isShowPermissionAdd = true
      },
      //关闭新增弹窗
      closePermissionAdd(){
        this.isShowPermissionAdd = false
        this.getData()
      },
      //打开绑定资源弹窗
      openBindResource(permissionId, appId){
        this.permissionId = permissionId
        this.appId = appId
        this.isShowBindResource = true
      },
      //关闭绑定资源弹窗
      closeBindResource(){
        this.isShowBindResource = false
      },
      //详情
      detailPermission(val){
        this.permissionData = val
        this.isShowPermissionDetail = true
      },
      //关闭详情弹窗
      closePermissionDetail(){
        this.isShowPermissionDetail = false
      },
      //删除权限
      deletePermission(permissionId){
        this.$confirm('此操作将永久删除该文件, 是否继续?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          const loading = this.$loading({
            lock: true,
            text: '请耐心等待.....',
            spinner: 'el-icon-loading',
            background: 'rgba(0, 0, 0, 0.7)'
          })
          let http = new PermissionManageApi()
          http.removePermission({permissionId}).then(res => {
            this.$message.success('删除成功!')
            loading.close()
            this.getData()
          }).catch(e => {
            loading.close()
          })
        }).catch(() => {
          this.$message({
            type: 'info',
            message: '已取消删除'
          })
        })
      },
    }
  }
</script>

<style scoped>

</style>

