<template>
    <div>
      <el-card>
        <el-form inline>
          <el-form-item label="公司名称:" class="mgb-0">
            <el-input v-model="name"></el-input>
          </el-form-item>
<!--          <el-form-item label="公司编码:" class="mgb-0">-->
<!--            <el-input v-model="code"></el-input>-->
<!--          </el-form-item>-->
          <el-form-item class="mgb-0">
            <el-button type="primary" icon="el-icon-search" @click="searchData"></el-button>
            <el-button type="success" icon="el-icon-refresh" @click="clearData"></el-button>
          </el-form-item>
        </el-form>
      </el-card>
      <el-card style="margin-top: 10px">
        <div style="margin-bottom: 10px">
          <el-button type="primary" @click="openCompanyAdd">新增</el-button>
        </div>
        <el-table border :data="dataList" v-loading="tableLoading">
          <el-table-column label="序号" align="center" type="index" width="50"></el-table-column>
          <el-table-column label="公司名称" prop="name" align="center" show-overflow-tooltip></el-table-column>
          <el-table-column label="公司简称" prop="shortName" align="center" show-overflow-tooltip></el-table-column>
          <el-table-column label="公司编码" prop="cpCode" align="center" show-overflow-tooltip></el-table-column>
          <el-table-column label="状态" align="center" show-overflow-tooltip>
            <template slot-scope="scope">
              <el-tag v-if="scope.row.status === 1" type="success">启用</el-tag>
              <el-tag v-if="scope.row.status === 2" type="danger">禁用</el-tag>
            </template>
          </el-table-column>
          <el-table-column label="操作" align="center">
            <template slot-scope="scope">
              <el-button type="text" @click="openCompanyDetail(scope.row.companyId)">详情</el-button>
              <el-button type="text" @click="openCompanyEdit(scope.row.companyId)">编辑</el-button>
              <el-button type="text" style="color: red" v-if="scope.row.status === 1" @click="disEnable(scope.row.companyId, '禁用')">禁用</el-button>
              <el-button type="text" v-if="scope.row.status === 2" @click="disEnable(scope.row.companyId, '启用')">启用</el-button>
              <el-button type="text" style="color: red" @click="deleteCompany(scope.row.companyId)">删除</el-button>
              <el-button type="text" @click="configApp(scope.row.companyId)">配置应用</el-button>
            </template>
          </el-table-column>
        </el-table>
        <el-pagination
          style="float:right; margin: 10px 0;"
          @size-change="handleSizeChange"
          @current-change="handleCurrentChange"
          :current-page="page"
          :page-sizes="[10, 15, 20, 50]"
          :page-size="pageSize"
          layout="total, sizes, prev, pager, next, jumper"
          prev-text="上一页"
          next-text="下一页"
          :total="totalRows">
        </el-pagination>
      </el-card>
      <!--新增-->
      <company-addEdit v-if="isShowCompanyAddEdit"
                       :companyId="companyId"
                       @close="closeCompanyAddEdit"></company-addEdit>
      <!--详情-->
      <company-detail v-if="isShowCompanyDetail"
                      :companyId="companyId"
                      @close="closeCompanyDetail"></company-detail>
<!--      应用配置-->
      <config-app v-if="isShowConfigApp"
                  :companyId="companyId"
                  @close="closeConfigApp"></config-app>
    </div>
</template>

<script>
  import CompanyManageApi from '@/api/system/orgBuild/CompanyManageApi'
  export default {
    name: 'company_manage',
    components:{
      CompanyAddEdit: resolve => require(['./components/company_addEdit'], resolve),
      CompanyDetail: resolve => require(['./components/company_detail'], resolve),
      ConfigApp: resolve => require(['./components/config_app'], resolve),
    },
    data(){
      return{
        name:'',
        code:'',
        tableLoading:false,
        dataList:[],
        page: 1, //当前页面
        pageSize: 10, // 当前页数
        totalRows: 0, // 总条数
        companyId:'',
        isShowCompanyAddEdit:false,
        isShowCompanyDetail:false,
        isShowConfigApp:false,
      }
    },
    created(){
      this.getData()
    },
    methods:{
      //获取表格数据源
      getData(){
        let http = new CompanyManageApi()
        this.tableLoading = true
        let data = {
          name:this.name,
          code:this.code,
          pageSize:this.pageSize,
          page:this.page,
        }
        http.queryListPage(data).then(res => {
          this.dataList = res.data.rows
          this.page = res.data.page
          this.pageSize = res.data.pageSize
          this.totalRows = res.data.totalRows
          this.tableLoading = false
        })
      },
      // 当前页码修改时
      handleCurrentChange(page){
        this.page = page
        this.getData()
      },
      // 当前页数修改时
      handleSizeChange(pageSize){
        this.pageSize = pageSize
        this.getData()
      },
      searchData(){
        this.page = 1
        this.getData()
      },
      clearData(){
        this.name = ''
        this.code = ''
      },
      //打开新增弹窗
      openCompanyAdd(){
        this.isShowCompanyAddEdit = true
      },
      //打开编辑弹窗
      openCompanyEdit(id){
        this.companyId = id
        this.isShowCompanyAddEdit = true
      },
      //关闭新增编辑弹窗
      closeCompanyAddEdit(){
        this.isShowCompanyAddEdit = false
        this.companyId = ''
        this.getData()
      },
      //打开详情弹窗
      openCompanyDetail(id){
        this.companyId = id
        this.isShowCompanyDetail = true
      },
      //关闭详情弹窗
      closeCompanyDetail(){
        this.companyId = ''
        this.isShowCompanyDetail = false
      },
      //禁用或启用
      disEnable(companyId, text){
        const loading = this.$loading({
          lock: true,
          text: `${text}中，请耐心等待.....`,
          spinner: 'el-icon-loading',
          background: 'rgba(0, 0, 0, 0.7)'
        })
        let http = new CompanyManageApi()
        let status = text === '禁用' ? 2 : 1
        http.changeStatus({companyId, status}).then(res => {
          this.$message.success(`${text}成功!`)
          loading.close()
          this.getData()
        }).catch(e => {
          loading.close()
        })
      },
      //删除
      deleteCompany(companyId){
        this.$confirm('此操作将永久删除该公司, 是否继续?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          const loading = this.$loading({
            lock: true,
            text: `删除中，请耐心等待.....`,
            spinner: 'el-icon-loading',
            background: 'rgba(0, 0, 0, 0.7)'
          })
          let http = new CompanyManageApi()
          http.delete({companyId}).then(res => {
            this.$message({ type: 'success', message: '删除成功!' })
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
      //配置应用
      configApp(id){
        this.companyId = id
        this.isShowConfigApp = true
      },
      //配置应用弹窗关闭
      closeConfigApp(){
        this.companyId = ''
        this.isShowConfigApp = false
      },
    },
  }
</script>

<style scoped>

</style>
