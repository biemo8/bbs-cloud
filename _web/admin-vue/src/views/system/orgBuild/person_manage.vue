<template>
    <div>
      <el-card>
        <el-form inline>
          <el-form-item label="人员名称:" class="mgb-0">
            <el-input v-model="name"></el-input>
          </el-form-item>
          <el-form-item class="mgb-0">
            <el-button type="primary" icon="el-icon-search" @click="searchData"></el-button>
            <el-button type="success" icon="el-icon-refresh" @click="clearData"></el-button>
          </el-form-item>
        </el-form>
      </el-card>
      <el-card style="margin-top: 10px">
        <div style="margin-bottom: 10px">
          <el-button type="primary" @click="openPersonAdd">新增</el-button>
        </div>
        <el-table border :data="dataList" v-loading="tableLoading">
          <el-table-column label="序号" align="center" type="index" width="50"></el-table-column>
          <el-table-column label="人员名称" prop="userName" align="center" show-overflow-tooltip></el-table-column>
          <el-table-column label="性别" prop="sex" align="center" show-overflow-tooltip></el-table-column>
          <el-table-column label="公司名称" prop="companyName" align="center" show-overflow-tooltip></el-table-column>
          <el-table-column label="部门名称" prop="deptName" align="center" show-overflow-tooltip></el-table-column>
          <el-table-column label="职务名称" prop="dutyName" align="center" show-overflow-tooltip></el-table-column>
          <el-table-column label="状态" align="center" show-overflow-tooltip>
            <template slot-scope="scope">
              <el-tag v-if="scope.row.status === 1" type="success">启用</el-tag>
              <el-tag v-if="scope.row.status === 2" type="danger">禁用</el-tag>
            </template>
          </el-table-column>
          <el-table-column label="操作" align="center" width="340">
            <template slot-scope="scope">
              <el-button type="text" @click="openPersonDetail(scope.row.userId)">详情</el-button>
              <el-button type="text" v-if="scope.row.status === 1" @click="openPersonEdit(scope.row.userId)">编辑</el-button>
              <el-button type="text" style="color: red" v-if="scope.row.status === 1" @click="disEnable(scope.row.userId, '禁用')">禁用</el-button>
              <el-button type="text" v-if="scope.row.status === 2" @click="disEnable(scope.row.userId, '启用')">启用</el-button>
              <el-button type="text" style="color: red" @click="deletePerson(scope.row.userId)">删除</el-button>
              <el-button type="text" @click="configRole(scope.row.userId)">配置角色</el-button>
              <el-button type="text" style="color: red" @click="resetPassword(scope.row.userId)">重置密码</el-button>
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
<!--      新增编辑弹窗-->
      <person-addEdit v-if="isShowPersonAddEdit"
                      :userId="userId"
                      @close="closePersonAddEdit"></person-addEdit>
<!--      详情弹窗-->
      <person-detail v-if="isShowPersonDetail"
                     :userId="userId"
                     @close="closePersonDetail"></person-detail>
<!--      配置角色-->
      <config-role v-if="isShowConfigRole"
                   :userId="userId"
                   @close="closeConfigRole"></config-role>
    </div>
</template>

<script>
  import PersonManageApi from '@/api/system/orgBuild/PersonManageApi'
  import CompanyManageApi from '@/api/system/orgBuild/CompanyManageApi'
  import {flatTurnTree} from '@/utils/DataTypeConvert'
  export default {
    name: 'person_manage',
    components:{
      PersonAddEdit: resolve => require(['./components/person_addEdit'], resolve),
      PersonDetail: resolve => require(['./components/person_detail'], resolve),
      ConfigRole: resolve => require(['./components/config_role'], resolve),
    },
    data(){
      return{
        name:'',
        tableLoading:false,
        dataList:[],
        page: 1, //当前页面
        pageSize: 10, // 当前页数
        totalRows: 0, // 总条数
        userId:'',
        isShowPersonAddEdit:false, //控制新增编辑弹窗是否显示
        isShowPersonDetail:false, //控制详情弹窗是否显示
        isShowConfigRole:false, //控制配置角色弹窗是否显示
      }
    },
    created(){
      this.getData()
    },
    methods:{
      //获取表格数据源
      getData(){
        let http = new PersonManageApi()
        this.tableLoading = true
        let data = {
          name:this.name,
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
      },
      //打开人员新增弹窗
      openPersonAdd(){
        this.isShowPersonAddEdit = true
      },
      //打开人员编辑弹窗
      openPersonEdit(id){
        this.userId = id
        this.isShowPersonAddEdit = true
      },
      //关闭人员新增弹窗
      closePersonAddEdit(){
        this.userId = ''
        this.isShowPersonAddEdit = false
        this.getData()
      },
      //打开详情弹窗
      openPersonDetail(id){
        this.userId = id
        this.isShowPersonDetail = true
      },
      //关闭详情弹窗
      closePersonDetail(){
        this.userId = ''
        this.isShowPersonDetail = false
      },
      //禁用或启用
      disEnable(userId, text){
        const loading = this.$loading({
          lock: true,
          text: `${text}中，请耐心等待.....`,
          spinner: 'el-icon-loading',
          background: 'rgba(0, 0, 0, 0.7)'
        })
        let http = new PersonManageApi()
        let status = text === '禁用' ? 2 : 1
        http.changeStatus({userId, status}).then(res => {
          this.$message.success(`${text}成功!`)
          loading.close()
          this.getData()
        }).catch(e => {
          loading.close()
        })
      },
      //删除人员
      deletePerson(userId){
        this.$confirm('此操作将永久删除该人员, 是否继续?', '提示', {
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
          let http = new PersonManageApi()
          http.delete({userId}).then(res => {
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
      //配置角色
      configRole(id){
        this.userId = id
        this.isShowConfigRole = true
      },
      //关闭配置角色弹窗
      closeConfigRole(){
        this.userId = ''
        this.isShowConfigRole = false
      },
      //重置密码
      resetPassword(uerId){
        this.$confirm('确定要重置密码吗?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          const loading = this.$loading({
            lock: true,
            text: `重置中，请耐心等待.....`,
            spinner: 'el-icon-loading',
            background: 'rgba(0, 0, 0, 0.7)'
          })
          let http = new PersonManageApi()
          http.resetPassword({uerId}).then(res => {
            loading.close()
            this.$alert(`这是您的密码:${res.data}`, '重置成功!', {
              confirmButtonText: '确定',
              callback: action => {}
            })
          }).catch(e => {
            loading.close()
          })
        }).catch(() => {
          this.$message({
            type: 'info',
            message: '已取消重置'
          })
        })
      },
    },
  }
</script>

<style scoped>

</style>
