<template>
  <div>
    <el-card>
      <el-button type="primary" style="margin-bottom: 10px" @click="openResourceAdd">新增</el-button>
      <div style="margin-bottom: 10px" @change="getData">
        <el-radio-group v-model="menuFlag">
          <el-radio-button label="N">菜单资源</el-radio-button>
          <el-radio-button label="Y">页面资源</el-radio-button>
        </el-radio-group>
      </div>
      <el-table border :data="dataList" v-loading="tableLoading">
        <el-table-column type="index" label="序号" align="center" width="50"></el-table-column>
        <el-table-column label="应用标识" prop="appCode" align="center" show-overflow-tooltip></el-table-column>
        <el-table-column label="所属模块" prop="modularName" align="center" show-overflow-tooltip></el-table-column>
        <el-table-column label="资源名称" prop="name" align="center" show-overflow-tooltip></el-table-column>
        <el-table-column label="资源地址" prop="url" align="center" show-overflow-tooltip></el-table-column>
        <el-table-column label="是否需要登录" align="center" show-overflow-tooltip>
          <template slot-scope="scope">
            <span v-if="scope.row.requiredLogin">是</span>
            <span v-if="!scope.row.requiredLogin">否</span>
          </template>
        </el-table-column>
        <el-table-column label="是否需要权限验证" prop="requiredPermissionFlag" align="center" show-overflow-tooltip>
          <template slot-scope="scope">
            <span v-if="scope.row.requiredPermission">是</span>
            <span v-if="!scope.row.requiredPermission">否</span>
          </template>
        </el-table-column>
        <el-table-column label="操作" align="center" width="272">
          <template slot-scope="scope">
            <el-button type="text" @click="openResourceDetail(scope.row.url)">详情</el-button>
            <el-button type="text" @click="openResourceEdit(scope.row.url)">编辑</el-button>
            <el-button type="text" style="color: red" @click="deleteResource(scope.row.url)">删除</el-button>
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
    <resource-addEdit v-if="isShowResourceAddEdit"
                      :url="url"
                      @close="closeResourceAddEdit"></resource-addEdit>
    <!--详情-->
    <resource-detail v-if="isShowResourceDetail"
                     :url="url"
                     @close="closeResourceDetail"></resource-detail>
  </div>
</template>

<script>
  import ResourceMaintainIndexApi from '@/api/auth/resourceMaintain/ResourceMaintainIndexApi'
  export default {
    name: "resource_maintain_index",
    components:{
      ResourceAddEdit: resolve => require(['./components/resourceAddEdit'], resolve),
      ResourceDetail: resolve => require(['./components/resourceDetail'], resolve),
    },
    data(){
      return{
        appName:'', //搜索的应用名称
        menuFlag:'N',
        tableLoading:false,
        dataList:[],
        pageNo: 1, //当前页面
        pageSize: 10, // 当前页数
        total: 0, // 总条数
        isShowResourceAddEdit:false,
        url:'',
        isShowResourceDetail:false,
      }
    },
    created(){
      this.getData()
    },
    methods: {
      //获取表格数据源
      getData() {
        this.tableLoading = true
        let data = {
          menuFlag: this.menuFlag,
          pageNo: this.pageNo,
          pageSize: this.pageSize,
        }
        this.getTableListData(new ResourceMaintainIndexApi(), data, 'getResCacheList').then(res => {
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
      //打开新增弹窗
      openResourceAdd(){
        this.isShowResourceAddEdit = true
      },
      //打开编辑弹窗
      openResourceEdit(url){
        this.url = url
        this.isShowResourceAddEdit = true
      },
      //关闭新增编辑弹窗
      closeResourceAddEdit(){
        this.url = ''
        this.isShowResourceAddEdit = false
        this.getData()
      },
      //打开详情弹窗
      openResourceDetail(url){
        this.url = url
        this.isShowResourceDetail = true
      },
      //关闭详情弹窗
      closeResourceDetail(){
        this.url = ''
        this.isShowResourceDetail = false
      },
      //删除
      deleteResource(cacheKey){
        this.$confirm('此操作将永久删除该资源, 是否继续?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          const loading = this.$loading({
            lock: true,
            text: '删除中，请耐心等待.....',
            spinner: 'el-icon-loading',
            background: 'rgba(0, 0, 0, 0.7)'
          })
          let http = new ResourceMaintainIndexApi()
          http.removeResCache({cacheKey}).then(res => {
            loading.close()
            this.$message.success('删除成功!')
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
