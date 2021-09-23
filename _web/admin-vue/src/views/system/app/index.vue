<template>
  <div>
    <el-card>
      <el-form inline>
        <el-form-item label="应用名称:" class="mgb-0">
          <el-input v-model="appName"></el-input>
        </el-form-item>
        <el-form-item class="mgb-0">
          <el-button type="primary" icon="el-icon-search" @click="searchData"></el-button>
          <el-button type="success" icon="el-icon-refresh" @click="() => appName = ''"></el-button>
        </el-form-item>
      </el-form>
    </el-card>
    <el-card style="margin-top: 10px">
      <el-button type="primary" style="margin-bottom: 10px" @click="openAppAdd">新增</el-button>
      <el-table border :data="dataList" v-loading="tableLoading" :show-overflow-tooltip="true">
        <el-table-column type="index" label="序号" align="center" width="50"></el-table-column>
        <el-table-column label="应用名称" prop="appName" align="center" show-overflow-tooltip></el-table-column>
        <el-table-column label="应用id" prop="appId" align="center" show-overflow-tooltip></el-table-column>
        <el-table-column label="应用编码" prop="appCode" align="center" show-overflow-tooltip></el-table-column>
        <el-table-column label="应用状态" align="center" show-overflow-tooltip>
          <template slot-scope="scope">
            <el-tag v-if="scope.row.status === 1" type="success">启用</el-tag>
            <el-tag v-if="scope.row.status === 2" type="danger">禁用</el-tag>
          </template>
        </el-table-column>
        <el-table-column label="操作" align="center" width="272">
          <template slot-scope="scope">
            <el-button type="text" @click="detailApp(scope.row.appId)">详情</el-button>
            <el-button type="text" @click="editApp(scope.row.appId)">编辑</el-button>
            <el-button type="text" style="color: red" v-if="scope.row.status === 1" @click="disEnableApp(scope.row.appId, '禁用')">禁用</el-button>
            <el-button type="text" style="color: red" @click="deleteApp(scope.row.appId)">删除</el-button>
            <el-button type="text" v-if="scope.row.status === 2" @click="disEnableApp(scope.row.appId, '启用')">启用</el-button>
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
    <!--新增弹窗-->
    <app-register-addEdit v-if="isShowAppAddEdit"
                          :appId="appId"
                          @close="closeAppAddEdit"></app-register-addEdit>
    <!--详情弹窗-->
    <app-register-detail v-if="isShowAppDetail"
                         :appId="appId"
                         @close="closeDetailApp"></app-register-detail>
  </div>
</template>

<script>
  import AppRegisterApi from '@/api/system/app/AppRegisterApi'
  export default {
    name: "app_manage",
    components:{
      AppRegisterAddEdit: resolve => require(['./components/app_register_addEdit'], resolve),
      AppRegisterDetail: resolve => require(['./components/app_register_detail'], resolve),
    },
    data(){
      return{
        appName:'', //搜索的应用名称
        tableLoading:false,
        dataList:[],
        pageNo: 1, //当前页面
        pageSize: 10, // 当前页数
        total: 0, // 总条数
        isShowAppAddEdit:false, //控制新增编辑弹窗是否显示
        appId:'', //传给新增编辑弹窗的id
        isShowAppDetail:false,//控制详情弹窗是否显示
        isShowAppTemplateConfig:false,//控制组织架构模板配置弹窗是否显示
        configLoading:false,
        rules:{tempId: [{required: true, message: '请选择组织模板', tirgger: 'change'}]},
        appConfigData:{
          tempId:'',
        },
        appConfigList:[], //组织模板列表
        isShowCompanyConfig:false, //控制公司配置弹窗是否显示
      }
    },
    created(){
      this.getData()
    },
    methods:{
      //获取表格数据源
      getData(){
        this.tableLoading = true
        let data = {
          appName:this.appName,
          pageNo:this.pageNo,
          pageSize:this.pageSize,
        }
        this.getTableListData(new AppRegisterApi(), data, 'list').then(res => {
          this.dataList = res.dataList
          this.pageNo = res.pageNo
          this.pageSize = res.pageSize
          this.total = res.total
          this.tableLoading = false
        })
      },
      // 当前页码修改时
      handleCurrentChange(pageNo){
        this.pageNo = pageNo
        this.getData()
      },
      // 当前页数修改时
      handleSizeChange(pageSize){
        this.pageSize = pageSize
        this.getData()
      },
      //搜索
      searchData(){
        this.pageNo = 1
        this.getData()
      },
      //打开新增弹窗
      openAppAdd(){
        this.isShowAppAddEdit = true
      },
      //关闭新增编辑弹窗
      closeAppAddEdit(){
        this.appId = ''
        this.isShowAppAddEdit = false
        this.getData()
      },
      //编辑弹窗
      editApp(id){
        this.appId = id
        this.isShowAppAddEdit = true
      },
      //详情
      detailApp(id){
        this.appId = id
        this.isShowAppDetail = true
      },
      //关闭详情
      closeDetailApp(){
        this.appId = ''
        this.isShowAppDetail = false
      },
      //禁用或启用
      disEnableApp(appId, text){
        const loading = this.$loading({
          lock: true,
          text: '请耐心等待.....',
          spinner: 'el-icon-loading',
          background: 'rgba(0, 0, 0, 0.7)'
        })
        let status = text === '禁用' ? 2 : 1
        let http = new AppRegisterApi()
        http.changeStatus({appId, status}).then(res => {
          if(res.code === 200){
            this.$message.success('操作成功!')
            this.getData()
          }else{
            this.$message.success('操作失败!')
          }
          loading.close()
        }).catch(e => {
          loading.close()
        })
      },
      //删除
      deleteApp(appId){
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
          let http = new AppRegisterApi()
          http.delete({appId}).then(res => {
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
      //组织架构模板配置弹窗
      async appTemplateConfig(appId){
        this.isShowAppTemplateConfig = true
        this.appId = appId
        this.configLoading = true
        let http = new AppRegisterApi()
        let appConfigList = await http.tempSelectList({})
        this.appConfigList = appConfigList.data
        let tempData = await http.appTemp({appId})
        this.appConfigData.tempId = tempData.data.tempId
        this.configLoading = false
      },
      //保存组织架构模板配置
      saveAppTemplateConfig(){
        this.$refs['appConfigData'].validate((valid) => {
          if (valid) {
            const loading = this.$loading({
              lock: true,
              text: '请耐心等待.....',
              spinner: 'el-icon-loading',
              background: 'rgba(0, 0, 0, 0.7)'
            })
            let http = new AppRegisterApi()
            http.saveTempForApp({appId:this.appId, tempId:this.appConfigData.tempId}).then(res => {
              loading.close()
              if(res.code === 200){
                this.$message.success('保存成功!')
                this.closeAppTemplateConfig()
              }else{
                this.$message.error('保存失败!')
              }
            }).catch(e => {
              loading.close()
            })
          }
        })
      },
      //关闭组织架构模板配置弹窗
      closeAppTemplateConfig(){
        this.isShowAppTemplateConfig = false
        this.appId = ''
      },
      //公司配置
      companyConfig(id){
        this.appId = id
        this.isShowCompanyConfig = true
      },
      //关闭公司配置弹窗
      closeCompanyConfig(){
        this.appId = ''
        this.isShowCompanyConfig = false
      },
    },
  }
</script>

<style scoped>

</style>

