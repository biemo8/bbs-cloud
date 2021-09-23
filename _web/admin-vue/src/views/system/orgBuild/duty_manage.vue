<template>
  <div>
    <el-row :gutter="20">
<!--      <el-col :span="6">-->
<!--        <el-card>-->
<!--          <el-input-->
<!--            placeholder="输入关键字进行过滤"-->
<!--            v-model="filterText">-->
<!--          </el-input>-->
<!--          <el-tree-->
<!--            class="filter-tree"-->
<!--            :data="companyTreeData"-->
<!--            :props="defaultProps"-->
<!--            default-expand-all-->
<!--            @node-click="handleNodeClick"-->
<!--            highlight-current-->
<!--            :expand-on-click-node="false"-->
<!--            :filter-node-method="filterNode"-->
<!--            ref="companyTree">-->
<!--          </el-tree>-->
<!--        </el-card>-->
<!--      </el-col>-->
      <el-col :span="24">
        <el-card>
          <el-form inline>
            <el-form-item label="职务名称:" class="mgb-0">
              <el-input v-model="dutyName"></el-input>
            </el-form-item>
            <el-form-item label="职务编码:" class="mgb-0">
              <el-input v-model="dutyCode"></el-input>
            </el-form-item>
            <el-form-item class="mgb-0">
              <el-button type="primary" icon="el-icon-search" @click="searchData"></el-button>
              <el-button type="success" icon="el-icon-refresh" @click="clearData"></el-button>
            </el-form-item>
          </el-form>
        </el-card>
        <el-card style="margin-top: 10px">
          <div style="margin-bottom: 10px">
            <el-button type="primary" @click="openDutyAdd">新增</el-button>
          </div>
          <el-table border :data="dataList" v-loading="tableLoading">
            <el-table-column label="序号" align="center" type="index" width="50"></el-table-column>
            <el-table-column label="职务名称" prop="dutyName" align="center" show-overflow-tooltip></el-table-column>
            <el-table-column label="职务编码" prop="dutyCode" align="center" show-overflow-tooltip></el-table-column>
            <el-table-column label="职务描述" prop="description" align="center" show-overflow-tooltip></el-table-column>
            <el-table-column label="状态" align="center" show-overflow-tooltip>
              <template slot-scope="scope">
                <el-tag v-if="scope.row.status === 1" type="success">启用</el-tag>
                <el-tag v-if="scope.row.status === 2" type="danger">禁用</el-tag>
              </template>
            </el-table-column>
            <el-table-column label="操作" align="center">
              <template slot-scope="scope">
                <el-button type="text" @click="openDutyDetail(scope.row.dutyId)">详情</el-button>
                <el-button type="text" @click="openDutyEdit(scope.row.dutyId)">编辑</el-button>
                <el-button type="text" style="color: red" v-if="scope.row.status === 1" @click="disEnable(scope.row.dutyId, '禁用')">禁用</el-button>
                <el-button type="text" v-if="scope.row.status === 2" @click="disEnable(scope.row.dutyId, '启用')">启用</el-button>
                <el-button type="text" style="color: red" @click="deleteDuty(scope.row.dutyId)">删除</el-button>
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
      </el-col>
    </el-row>
    <!--新增-->
    <duty-addEdit v-if="isShowDutyAddEdit"
                  :dutyId="dutyId"
                  @close="closeDutyAddEdit"></duty-addEdit>
    <!--详情-->
    <duty-detail v-if="isShowDutyDetail"
                 :dutyId="dutyId"
                 @close="closeDutyDetail"></duty-detail>
  </div>
</template>

<script>
  import DutyManageApi from '@/api/system/orgBuild/DutyManageApi'
  import CompanyManageApi from '@/api/system/orgBuild/CompanyManageApi'
  import {flatTurnTree} from '@/utils/DataTypeConvert'
  export default {
    name: 'duty_manage',
    components:{
      DutyAddEdit: resolve => require(['./components/duty_addEdit'], resolve),
      DutyDetail: resolve => require(['./components/duty_detail'], resolve),
    },
    data(){
      return{
        filterText:'',
        companyTreeData:[],
        defaultProps:{
          children: 'children',
          label: 'nodeName'
        },
        dutyName:'',
        dutyCode:'',
        companyId:'',
        tableLoading:false,
        dataList:[],
        page: 1, //当前页面
        pageSize: 10, // 当前页数
        totalRows: 0, // 总条数
        dutyId:'',
        isShowDutyAddEdit:false,
        isShowDutyDetail:false,
      }
    },
    // watch: {
    //   filterText(val) {
    //     this.$refs.companyTree.filter(val);
    //   }
    // },
    async created(){
      // let http = new CompanyManageApi()
      // let companyTreeData = await http.queryCompTree({})
      // this.companyTreeData = flatTurnTree(companyTreeData.data, 'nodeId', 'nodePid', -2)
      this.getData()
    },
    methods:{
      filterNode(value, data) {
        if (!value) return true;
        return data.nodeName.indexOf(value) !== -1;
      },
      handleNodeClick(val){
        if(val.nodeId === -1){
          this.companyId = ''
        }else{
          this.companyId = val.nodeId
        }
        this.getData()
      },
      //获取表格数据源
      getData(){
        let http = new DutyManageApi()
        this.tableLoading = true
        let data = {
          dutyName:this.dutyName,
          dutyCode:this.dutyCode,
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
        this.dutyName = ''
        this.dutyCode = ''
      },
      //打开新增弹窗
      openDutyAdd(){
        this.isShowDutyAddEdit = true
      },
      //打开编辑弹窗
      openDutyEdit(id){
        this.dutyId = id
        this.isShowDutyAddEdit = true
      },
      //关闭新增编辑弹窗
      closeDutyAddEdit(){
        this.isShowDutyAddEdit = false
        this.dutyId = ''
        this.getData()
      },
      //打开详情弹窗
      openDutyDetail(id){
        this.dutyId = id
        this.isShowDutyDetail = true
      },
      //关闭详情弹窗
      closeDutyDetail(){
        this.dutyId = ''
        this.isShowDutyDetail = false
      },
      //禁用或启用
      disEnable(dutyId, text){
        const loading = this.$loading({
          lock: true,
          text: `${text}中，请耐心等待.....`,
          spinner: 'el-icon-loading',
          background: 'rgba(0, 0, 0, 0.7)'
        })
        let http = new DutyManageApi()
        let status = text === '禁用' ? 2 : 1
        http.changeStatus({dutyId, status}).then(res => {
          this.$message.success(`${text}成功!`)
          loading.close()
          this.getData()
        }).catch(e => {
          loading.close()
        })
      },
      //删除
      deleteDuty(dutyId){
        this.$confirm('此操作将永久删除该职务, 是否继续?', '提示', {
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
          let http = new DutyManageApi()
          http.delete({dutyId}).then(res => {
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
    },
  }
</script>

<style scoped>

</style>


