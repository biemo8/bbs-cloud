<template>
  <div>
    <el-row :gutter="20" style="height: 100%">
      <el-col :span="5" style="height: 100%">
        <el-card style="height: 100%">
          <el-input
            style="margin-bottom: 20px"
            placeholder="输入关键字进行过滤"
            v-model="filterText">
          </el-input>
          <el-tree
            class="filter-tree"
            :data="companyTreeData"
            :props="defaultProps"
            default-expand-all
            @node-click="handleNodeClick"
            highlight-current
            :expand-on-click-node="false"
            :filter-node-method="filterNode"
            ref="companyTree">
          </el-tree>
        </el-card>
      </el-col>
      <el-col :span="19" style="padding-left: 0">
        <el-card>
          <el-form inline>
            <el-form-item label="部门名称:" class="mgb-0">
              <el-input v-model="deptName"></el-input>
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
            <el-button type="primary" @click="openDeptAdd">新增</el-button>
          </div>
          <el-table border :data="dataList" v-loading="tableLoading">
            <el-table-column label="序号" align="center" type="index" width="50"></el-table-column>
            <el-table-column label="部门名称" prop="deptName" align="center" show-overflow-tooltip></el-table-column>
            <el-table-column label="部门简称" prop="deptShortName" align="center" show-overflow-tooltip></el-table-column>
            <el-table-column label="部门编码" prop="deptCode" align="center" show-overflow-tooltip></el-table-column>
            <el-table-column label="状态" align="center" show-overflow-tooltip>
              <template slot-scope="scope">
                <el-tag v-if="scope.row.status === 1" type="success">启用</el-tag>
                <el-tag v-if="scope.row.status === 2" type="danger">禁用</el-tag>
              </template>
            </el-table-column>
            <el-table-column label="操作" align="center">
              <template slot-scope="scope">
                <el-button type="text" @click="openDeptDetail(scope.row.deptId)">详情</el-button>
                <el-button type="text" @click="openDeptEdit(scope.row.deptId)">编辑</el-button>
                <el-button type="text" style="color: red" v-if="scope.row.status === 1" @click="disEnable(scope.row.deptId, '禁用')">禁用</el-button>
                <el-button type="text" v-if="scope.row.status === 2" @click="disEnable(scope.row.deptId, '启用')">启用</el-button>
                <el-button type="text" style="color: red" @click="deleteDept(scope.row.deptId)">删除</el-button>
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
    <dept-addEdit v-if="isShowDeptAddEdit"
                     :deptId="deptId"
                     @close="closeDeptAddEdit"></dept-addEdit>
    <!--详情-->
    <dept-detail v-if="isShowDeptDetail"
                    :deptId="deptId"
                    @close="closeDeptDetail"></dept-detail>
  </div>
</template>

<script>
  import DeptManageApi from '@/api/system/orgBuild/DeptManageApi'
  import CompanyManageApi from '@/api/system/orgBuild/CompanyManageApi'
  import {flatTurnTree} from '@/utils/DataTypeConvert'
  export default {
    name: 'dept_manage',
    components:{
      DeptAddEdit: resolve => require(['./components/dept_addEdit'], resolve),
      DeptDetail: resolve => require(['./components/dept_detail'], resolve),
    },
    data(){
      return{
        filterText:'',
        companyTreeData:[],
        defaultProps:{
          children: 'children',
          label: 'nodeName'
        },
        deptName:'',
        companyId:'',
        tableLoading:false,
        dataList:[],
        page: 1, //当前页面
        pageSize: 10, // 当前页数
        totalRows: 0, // 总条数
        deptId:'',
        isShowDeptAddEdit:false,
        isShowDeptDetail:false,
      }
    },
    watch: {
      filterText(val) {
        this.$refs.companyTree.filter(val);
      }
    },
    async created(){
      let http = new CompanyManageApi()
      let companyTreeData = await http.queryCompTree({})
      this.companyTreeData = flatTurnTree(companyTreeData.data, 'nodeId', 'nodePid', -2)
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
        let http = new DeptManageApi()
        this.tableLoading = true
        let data = {
          deptName:this.deptName,
          companyId:this.companyId,
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
        this.deptName = ''
      },
      //打开新增弹窗
      openDeptAdd(){
        this.isShowDeptAddEdit = true
      },
      //打开编辑弹窗
      openDeptEdit(id){
        this.deptId = id
        this.isShowDeptAddEdit = true
      },
      //关闭新增编辑弹窗
      closeDeptAddEdit(){
        this.isShowDeptAddEdit = false
        this.deptId = ''
        this.getData()
      },
      //打开详情弹窗
      openDeptDetail(id){
        this.deptId = id
        this.isShowDeptDetail = true
      },
      //关闭详情弹窗
      closeDeptDetail(){
        this.deptId = ''
        this.isShowDeptDetail = false
      },
      //禁用或启用
      disEnable(deptId, text){
        const loading = this.$loading({
          lock: true,
          text: `${text}中，请耐心等待.....`,
          spinner: 'el-icon-loading',
          background: 'rgba(0, 0, 0, 0.7)'
        })
        let http = new DeptManageApi()
        let status = text === '禁用' ? 2 : 1
        http.changeStatus({deptId, status}).then(res => {
          this.$message.success(`${text}成功!`)
          loading.close()
          this.getData()
        }).catch(e => {
          loading.close()
        })
      },
      //删除
      deleteDept(deptId){
        this.$confirm('此操作将永久删除该部门, 是否继续?', '提示', {
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
          let http = new DeptManageApi()
          http.delete({deptId}).then(res => {
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

