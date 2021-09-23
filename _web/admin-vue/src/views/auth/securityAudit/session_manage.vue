<template>
  <div>
    <el-card>
      <el-form inline>
        <el-form-item label="姓名或账号:" class="mgb-0">
          <el-input v-model="userName"></el-input>
        </el-form-item>
        <el-form-item class="mgb-0">
          <el-button type="primary" icon="el-icon-search" @click="searchData"></el-button>
          <el-button type="success" icon="el-icon-refresh" @click="clearSearch"></el-button>
        </el-form-item>
      </el-form>
    </el-card>
    <el-card style="margin-top: 10px">
      <el-table border :data="dataList" v-loading="tableLoading">
        <el-table-column type="index" label="序号" align="center" width="50"></el-table-column>
        <el-table-column label="账号" prop="account" align="center" show-overflow-tooltip></el-table-column>
        <el-table-column label="姓名" prop="userName" align="center" show-overflow-tooltip></el-table-column>
        <el-table-column label="ip" prop="ip" align="center" show-overflow-tooltip></el-table-column>
        <el-table-column label="地点" prop="ipAddress" align="center" show-overflow-tooltip></el-table-column>
        <el-table-column label="时间" prop="loginTime" align="center" show-overflow-tooltip></el-table-column>
        <el-table-column label="操作" align="center">
          <template slot-scope="scope">
            <el-button type="text" style="color: red" @click="offline(scope.row.ticket)">下线</el-button>
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
  </div>
</template>

<script>
  import SessionManage from '@/api/auth/securityAudit/SessionManage'
  export default {
    name: "session_manage",
    data(){
      return{
        userName:'', //姓名或账号
        tableLoading:false,
        appList:[],
        dataList:[],
        pageNo: 1, //当前页面
        pageSize: 10, // 当前页数
        total: 0, // 总条数
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
          userName: this.userName,
          pageNo: this.pageNo,
          pageSize: this.pageSize,
        }
        this.getTableListData(new SessionManage(), data, 'pageList').then(res => {
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
      clearSearch() {
        this.userName = ''
      },
      //下线
      offline(ticket){
        this.$confirm('确定要下线吗?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          const loading = this.$loading({
            lock: true,
            text: '下线中.....',
            spinner: 'el-icon-loading',
            background: 'rgba(0, 0, 0, 0.7)'
          })
          let http = new SessionManage()
          http.forcedOffline({ticket}).then(res => {
            this.$message({
              type: 'success',
              message: '下线成功!'
            })
            loading.close()
            this.getData()
          }).catch(e => {
            loading.close()
          })
        }).catch(() => {
          this.$message({
            type: 'info',
            message: '已取消下线'
          })
        })
      },
    }
  }
</script>

<style scoped>

</style>
