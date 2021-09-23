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
        <el-form-item label="父菜单名称:" class="mgb-0">
          <el-input v-model="searchData.pname"></el-input>
        </el-form-item>
        <el-form-item label="菜单名称:" class="mgb-0">
          <el-input v-model="searchData.menuName"></el-input>
        </el-form-item>
        <el-form-item class="mgb-0">
          <el-button type="primary" icon="el-icon-search" @click="searchList"></el-button>
          <el-button type="success" icon="el-icon-refresh" @click="clearList"></el-button>
        </el-form-item>
      </el-form>
    </el-card>
    <el-card style="margin-top: 10px">
      <div style="margin-bottom: 10px">
        <el-button type="primary" @click="openMenuAdd">新增</el-button>
        <el-button type="danger" @click="deleteMenu">删除</el-button>
      </div>
      <el-table border :data="dataList" v-loading="tableLoading"
                :show-overflow-tooltip="true" @selection-change="handleSelectionChange">
        <el-table-column align="center" type="selection" width="55"></el-table-column>
        <el-table-column label="菜单名称" prop="name" align="center" show-overflow-tooltip></el-table-column>
        <el-table-column label="菜单编码" prop="code" align="center" show-overflow-tooltip></el-table-column>
        <el-table-column label="父菜单" prop="parentName" align="center" show-overflow-tooltip></el-table-column>
        <el-table-column label="图标" prop="icon" align="center" show-overflow-tooltip></el-table-column>
        <el-table-column label="应用名称" prop="appName" align="center" show-overflow-tooltip></el-table-column>
        <el-table-column label="资源" prop="resName" align="center" show-overflow-tooltip></el-table-column>
        <el-table-column label="资源code" prop="resCode" align="center" show-overflow-tooltip></el-table-column>
        <el-table-column label="状态" align="center" show-overflow-tooltip>
          <template slot-scope="scope">
            <el-tag v-if="scope.row.status === 1" type="success">启用</el-tag>
            <el-tag v-if="scope.row.status === 2" type="danger">禁用</el-tag>
          </template>
        </el-table-column>
        <el-table-column label="排序" prop="orderNo" align="center" show-overflow-tooltip></el-table-column>
        <el-table-column label="操作" align="center" width="174">
          <template slot-scope="scope">
            <el-button type="text" @click="editMenu(scope.row.menuId)">编辑</el-button>
            <el-button type="text" style="color: red" v-if="scope.row.status === 1" @click="disEnableMenu(scope.row.menuId, '禁用')">禁用</el-button>
            <el-button type="text" v-if="scope.row.status === 2" @click="disEnableMenu(scope.row.menuId, '启用')">启用</el-button>
            <el-button type="text" @click="buttonMaintain(scope.row)">按钮维护</el-button>
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
    <menu-management-addEdit v-if="isShowMenuAddEdit"
                             :menuId="menuId"
                             @close="closeMenuAddEdit"></menu-management-addEdit>
    <!--按钮维护弹窗-->
    <button-maintain v-if="isShowButtonMaintain"
                     :menuData="menuData"
                     @close="closeButtonMaintain"></button-maintain>
  </div>
</template>

<script>
  import MenuManagementApi from "@/api/system/role/MenuManagementApi";
  export default {
    name: "menu_manage",
    components:{
      MenuManagementAddEdit: resolve => require(['./components/menu_management_addEdit'], resolve),
      ButtonMaintain: resolve => require(['./components/button_maintain'], resolve),
    },
    data(){
      return{
        searchData:{
          appId:'', //应用id
          menuName:'', //菜单名称
          pname:'', //父菜单名称
        },
        appList:[], //应用名称的数据源
        tableLoading:false, //表格loading动画
        dataList:[],
        pageNo: 1, //当前页面
        pageSize: 10, // 当前页数
        total: 0, // 总条数
        deleteMenuIds:[], //选择的删除的菜单id集合
        isShowMenuAddEdit:false, //控制新增编辑弹窗是否显示
        menuId:'', //传给新增编辑弹窗的id
        isShowButtonMaintain:false, //控制按钮维护弹窗是否显示
        menuData:{}, //传给按钮维护弹窗的menuData
      }
    },
    created(){
      this.initData()
    },
    methods: {
      //基础数据渲染
      async initData(){
        this.tableLoading = true
        let http = new MenuManagementApi()
        //获取应用名称下拉框数据源
        let list = await http.getAppSelect({})
        this.appList = list.data
        this.getData()
      },
      //获取表格数据源
      getData() {
        this.tableLoading = true
        this.searchData.pageNo = this.pageNo
        this.searchData.pageSize = this.pageSize
        this.getTableListData(new MenuManagementApi(), this.searchData, 'list').then(res => {
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
      clearList(){
        this.searchData = {
          appId:'', //应用id
          menuName:'', //菜单名称
          pname:'', //父菜单名称
        }
      },
      //选择的需要删除的菜单
      handleSelectionChange(val){
        if(val.length === 0){
          this.deleteMenuIds = []
        }else{
          this.deleteMenuIds = val.map(item => String(item.menuId))
        }
      },
      //打开新增弹窗
      openMenuAdd(){
        this.isShowMenuAddEdit = true
      },
      //关闭新增编辑弹窗
      closeMenuAddEdit(){
        this.menuId = ''
        this.isShowMenuAddEdit = false
        this.getData()
      },
      //编辑
      editMenu(id){
        this.menuId = id
        this.isShowMenuAddEdit = true
      },
      //禁用或启用
      disEnableMenu(menuId, text){
        const loading = this.$loading({
          lock: true,
          text: '请耐心等待.....',
          spinner: 'el-icon-loading',
          background: 'rgba(0, 0, 0, 0.7)'
        })
        let http = new MenuManagementApi()
        let status = text === '启用' ? 1 : 2
        http.changeStatus({menuId, status}).then(res => {
          if(res.code === 200){
            this.$message.success(`${text}成功!`)
            this.getData()
          }else{
            this.$message.success(`${text}失败!`)
          }
          loading.close()
        }).catch(e => {
          loading.close()
        })
      },
      //删除
      deleteMenu(){
        if(this.deleteMenuIds.length === 0){
          this.$message.warning('请先选择需要删除的菜单!')
        }else{
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
            let http = new MenuManagementApi()
            http.delete({menuIds:this.deleteMenuIds}).then(res => {
              if(res.code === 200){
                loading.close()
                this.$message.success('删除成功！')
                this.deleteMenuIds = []
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
        }
      },
      //按钮维护
      buttonMaintain(data){
        this.menuData = data
        this.isShowButtonMaintain = true
      },
      //关闭按钮维护弹窗
      closeButtonMaintain(){
        this.menuData = {}
        this.isShowButtonMaintain = false
      },
    },
  }
</script>

<style scoped>

</style>

