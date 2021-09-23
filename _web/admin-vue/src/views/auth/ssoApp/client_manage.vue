<template>
  <div>
    <el-card>
      <el-form inline>
        <el-form-item label="应用列表:" class="mgb-0">
          <el-select v-model="appId" filterable placeholder="请选择">
            <el-option
              v-for="item in appList"
              :key="item.appId"
              :label="item.appName"
              :value="item.appId">
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="名称:" class="mgb-0">
          <el-input v-model="name"></el-input>
        </el-form-item>
        <el-form-item class="mgb-0">
          <el-button type="primary" icon="el-icon-search" @click="searchData"></el-button>
          <el-button type="success" icon="el-icon-refresh" @click="clearSearch"></el-button>
        </el-form-item>
      </el-form>
    </el-card>
    <el-card style="margin-top: 10px">
      <div style="margin-bottom: 10px">
        <el-button type="primary" @click="openClientAdd">新增</el-button>
      </div>
      <el-table border :data="dataList" v-loading="tableLoading">
        <el-table-column type="index" label="序号" align="center" width="50"></el-table-column>
        <el-table-column label="客户端Id" prop="clientId" align="center" show-overflow-tooltip></el-table-column>
        <el-table-column label="名称" prop="name" align="center" show-overflow-tooltip></el-table-column>
        <el-table-column label="应用名称" prop="appName" align="center" show-overflow-tooltip></el-table-column>
        <el-table-column label="设备类型" prop="clientTypeName" align="center" show-overflow-tooltip></el-table-column>
        <el-table-column label="token接收地址" prop="ssoUrl" align="center" show-overflow-tooltip></el-table-column>
        <el-table-column label="登录类型" prop="loginTypeName" align="center" show-overflow-tooltip></el-table-column>
        <el-table-column label="操作" align="center">
          <template slot-scope="scope">
            <el-button type="text" @click="detailClient(scope.row.clientId)">详情</el-button>
            <el-button type="text" @click="edit(scope.row.clientId)">编辑</el-button>
            <el-button type="text" style="color: red" @click="deleteClient(scope.row.clientId)">删除</el-button>
          </template>
        </el-table-column>
      </el-table>
      <el-pagination
        class="sj-pagination"
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
    <!--新增编辑-->
    <client-addEdit v-if="isShowClientAddEdit"
                    :clientId="clientId"
                    @close="closeClientAddEdit"></client-addEdit>
    <!--详情-->
    <client-detail v-if="isShowClientDetail"
                   :clientId="clientId"
                   @close="closeClientDetail"></client-detail>
  </div>
</template>

<script>
  import ClientManageApi from '@/api/auth/ssoApp/ClientManageApi'
  export default {
    name: "client_manage",
    components:{
      ClientAddEdit: resolve => require(['./components/client_addEdit'], resolve),
      ClientDetail: resolve => require(['./components/client_detail'], resolve),
    },
    data(){
      return{
        appId:'', //搜索的应用id
        name:'', //搜索的应用名称
        tableLoading:false,
        appList:[],
        dataList:[],
        pageNo: 1, //当前页面
        pageSize: 10, // 当前页数
        total: 0, // 总条数
        isShowClientAddEdit:false,
        clientId:'',
        isShowClientDetail:false,
      }
    },
    created(){
      this.tableLoading = true
      let http = new ClientManageApi()
      http.getAppSelectList({}).then(res => {
        this.appList = res.data
        this.getData()
      })
    },
    methods: {
      //获取表格数据源
      getData() {
        this.tableLoading = true
        let data = {
          appId: this.appId,
          name: this.name,
          pageNo: this.pageNo,
          pageSize: this.pageSize,
        }
        this.getTableListData(new ClientManageApi(), data, 'pageList').then(res => {
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
      searchData() {
        this.pageNo = 1
        this.getData()
      },
      //清空搜索
      clearSearch(){
        this.appId = ''
        this.name = ''
      },
      //打开新增弹窗
      openClientAdd(){
        this.isShowClientAddEdit = true
      },
      //编辑
      edit(clientId){
        this.clientId = clientId
        this.isShowClientAddEdit = true
      },
      //关闭新增编辑弹窗
      closeClientAddEdit(){
        this.clientId = ''
        this.isShowClientAddEdit = false
        this.getData()
      },
      //删除
      deleteClient(clientId){
        this.$confirm('确定要删除吗?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          const loading = this.$loading({
            lock: true,
            text: '删除中.....',
            spinner: 'el-icon-loading',
            background: 'rgba(0, 0, 0, 0.7)'
          })
          let http = new ClientManageApi()
          http.delete({clientId}).then(res => {
            this.$message({
              type: 'success',
              message: '删除成功!'
            })
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
      //详情
      detailClient(id){
        this.clientId = id
        this.isShowClientDetail = true
      },
      //关闭详情弹窗
      closeClientDetail(){
        this.clientId = ''
        this.isShowClientDetail = false
      },
    }
  }
</script>

<style scoped>

</style>
