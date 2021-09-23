<template>
  <div>
    <el-card>
      <el-tabs v-model="activeName" @tab-click="handleClick">
        <el-tab-pane label="api资源" name="N"></el-tab-pane>
        <el-tab-pane label="页面资源" name="Y"></el-tab-pane>
      </el-tabs>
      <el-row :gutter="10">
        <el-col>
          <el-form inline>
            <el-form-item label="应用名称:">
              <el-select v-model="searchData.appId" clearable filterable placeholder="请选择">
                <el-option
                  v-for="item in appList"
                  :key="item.appId"
                  :label="item.appName"
                  :value="item.appId">
                </el-option>
              </el-select>
            </el-form-item>
            <el-form-item label="资源名称:">
              <el-input v-model="searchData.resourceName"></el-input>
            </el-form-item>
            <el-form-item>
              <el-button type="primary" @click="searchList">搜索</el-button>
              <el-button type="success" @click="clearList">清空搜索</el-button>
            </el-form-item>
          </el-form>
        </el-col>
      </el-row>
      <el-table border :data="dataList" v-loading="tableLoading" class="resource-table">
        <el-table-column type="index" label="序号" align="center" width="50"></el-table-column>
        <el-table-column label="应用类型" prop="appName" align="center" show-overflow-tooltip></el-table-column>
        <el-table-column label="所属模块" prop="modularName" align="center" show-overflow-tooltip></el-table-column>
        <el-table-column label="资源名称" prop="name" align="center" show-overflow-tooltip></el-table-column>
        <el-table-column label="资源id" prop="resourceId" align="center" show-overflow-tooltip></el-table-column>
        <el-table-column label="资源地址" prop="url" align="center" show-overflow-tooltip></el-table-column>
        <el-table-column label="是否需要登录" align="center" show-overflow-tooltip>
          <template slot-scope="scope">
            <span v-if="scope.row.requiredLoginFlag === 'Y'">是</span>
            <span v-if="scope.row.requiredLoginFlag === 'N'">否</span>
          </template>
        </el-table-column>
        <el-table-column label="是否需要权限验证" prop="requiredPermissionFlag" align="center" show-overflow-tooltip>
          <template slot-scope="scope">
            <span v-if="scope.row.requiredPermissionFlag === 'Y'">是</span>
            <span v-if="scope.row.requiredPermissionFlag === 'N'">否</span>
          </template>
        </el-table-column>
      </el-table>
      <el-pagination
        style="text-align: right; margin-top: 10px"
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
  </div>
</template>

<script>
  import ResourceManageApi from "@/api/system/role/ResourceManageApi";
  import MenuManagementApi from "@/api/system/role/MenuManagementApi";
  export default {
    name: "resource_manage",
    data(){
      return{
        activeName:'N',//页面资源 -- N是api资源
        searchData:{
          appId:'', //应用id
          resourceName:'', //资源名称
        },
        appList:[], //应用名称的数据源
        tableLoading:false, //表格loading动画
        dataList:[],
        current: 1, //当前页面
        size: 10, // 当前页数
        total: 0, // 总条数
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
        let http = new ResourceManageApi()
        this.searchData.menuFlag = this.activeName
        this.searchData.current = this.current
        this.searchData.size = this.size
        http.list(this.searchData).then(res => {
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
        this.searchData.resourceName = ''
      },
      //切换tab标签
      handleClick(val){
        this.clearList()
        this.current = 1
        this.getData()
      },
    }
  }
</script>

<style scoped lang="scss">
  .resource-table{
     .cell{
      line-height: 2;
    }
  }
</style>

