<template>
  <div>
    <el-card>
      <el-form inline>
        <el-form-item label="账号:" class="mgb-0">
          <el-input v-model="account"></el-input>
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
        <el-table-column label="名称" prop="name" align="center" show-overflow-tooltip></el-table-column>
        <el-table-column label="客户端名称" prop="clientName" align="center" show-overflow-tooltip></el-table-column>
        <el-table-column label="操作内容" prop="operation" align="center" show-overflow-tooltip></el-table-column>
        <el-table-column label="ip地址" prop="ipAddress" align="center" show-overflow-tooltip></el-table-column>
        <el-table-column label="地点" prop="localAddress" align="center" show-overflow-tooltip></el-table-column>
        <el-table-column label="时间" prop="loginTime" align="center" show-overflow-tooltip></el-table-column>
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
  import LoginLogApi from '@/api/auth/securityAudit/LoginLogApi'
  export default {
    name: "login_log",
    data(){
      return{
        account:'', //账号
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
          account: this.account,
          pageNo: this.pageNo,
          pageSize: this.pageSize,
        }
        this.getTableListData(new LoginLogApi(), data, 'pageList').then(res => {
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
        this.account = ''
      },
    }
  }
</script>

<style scoped>

</style>
