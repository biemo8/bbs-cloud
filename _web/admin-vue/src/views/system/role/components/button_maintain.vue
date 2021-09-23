<template>
  <div>
    <el-dialog :title="title" :width="width"
               :visible.sync="isShowVisible"
               center :close-on-click-modal="false"
               :before-close="() => $emit('close')">
      <el-card>
        <el-form inline>
          <el-form-item label="菜单名称:">
            <span>{{menuData.name}}</span>
          </el-form-item>
          <el-form-item label="按钮名称:">
            <el-input clearable v-model="buttonName"></el-input>
          </el-form-item>
          <el-form-item>
            <el-button type="primary" icon="el-icon-search" @click="searchList"></el-button>
          </el-form-item>
        </el-form>
        <div style="margin-bottom: 10px">
          <el-button type="primary" @click="addbutton">新增</el-button>
          <el-button type="danger" @click="deletebutton">删除</el-button>
        </div>
        <el-table border  :data="dataList" v-loading="tableLoading"
                  :show-overflow-tooltip="true" @selection-change="handleSelectionChange">
          <el-table-column align="center" type="selection" width="55"></el-table-column>
          <el-table-column align="center" type="index" width="50" label="序号"></el-table-column>
          <el-table-column label="按钮名称" prop="buttonName" align="center" show-overflow-tooltip></el-table-column>
          <el-table-column label="按钮编码" prop="buttonCode" align="center" show-overflow-tooltip></el-table-column>
<!--          <el-table-column label="所属应用编码" prop="appCode" align="center" show-overflow-tooltip></el-table-column>-->
          <el-table-column label="控制器名称" prop="controllerName" align="center" show-overflow-tooltip></el-table-column>
          <el-table-column label="关联资源名称" prop="resourceName" align="center" show-overflow-tooltip></el-table-column>
          <el-table-column label="关联资源id" prop="resourceId" align="center" show-overflow-tooltip></el-table-column>
          <el-table-column label="状态" align="center" show-overflow-tooltip>
            <template slot-scope="scope">
              <el-tag v-if="scope.row.status === 1" type="success">启用</el-tag>
              <el-tag v-if="scope.row.status === 2" type="danger">禁用</el-tag>
            </template>
          </el-table-column>
          <el-table-column label="操作" align="center">
            <template slot-scope="scope">
              <el-button type="text" @click="editbutton(scope.row.buttonId)">编辑</el-button>
              <el-button type="text" style="color: red" v-if="scope.row.status === 1" @click="disEnable(scope.row, '禁用')">禁用</el-button>
              <el-button type="text" v-if="scope.row.status === 2" @click="disEnable(scope.row, '启用')">启用</el-button>
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
      <div slot="footer" class="dialog-footer">
        <el-button @click="$emit('close')">关闭</el-button>
      </div>
    </el-dialog>
    <!--新增编辑弹窗-->
    <button-maintain-addEdit v-if="isShowButtonAddEdit"
                             :buttonId="buttonId"
                             :appId="menuData.appId"
                             :menuId="menuData.menuId"
                             @close="closeButtonAddEdit"></button-maintain-addEdit>
  </div>
</template>

<script>
  import ButtonMaintainApi from "@/api/system/role/ButtonMaintainApi";
  export default {
    name: "button_maintain",
    props:{
      width:{
        type:String,
        default:'80vw'
      },
      menuData:{
        type:Object,
      }, //菜单数据对象
    },
    components:{
      ButtonMaintainAddEdit: resolve => require(['./button_maintain_addEdit'], resolve),
    },
    data(){
      return{
        title:'按钮维护',
        isShowVisible:true,
        buttonName:'',
        tableLoading:false,
        dataList:[],
        pageNo: 1, //当前页面
        pageSize: 10, // 当前页数
        total: 0, // 总条数
        isShowButtonAddEdit:false, //控制新增编辑弹窗是否显示
        buttonId:'',
        selectButtonList:[], //选择的button
      }
    },
    created(){
      this.getData()
    },
    methods:{
      //获取表格数据源
      getData(){
        this.tableLoading = true
        let http = new ButtonMaintainApi()
        let data = {
          menuId :this.menuData.menuId,
          buttonName:this.buttonName,
          pageNo:this.pageNo,
          pageSize:this.pageSize,
        }
        http.queryListPage(data).then(res => {
          this.dataList = res.data.rows
          this.pageNo = res.data.page
          this.pageSize = res.data.pageSize
          this.total = res.data.totalRows
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
      searchList(){
        this.pageNo = 1
        this.getData()
      },
      //新增
      addbutton(){
        this.isShowButtonAddEdit = true
      },
      //编辑
      editbutton(id){
        this.buttonId = id
        this.isShowButtonAddEdit = true
      },
      //关闭新增编辑弹窗
      closeButtonAddEdit(){
        this.buttonId = ''
        this.isShowButtonAddEdit = false
        this.getData()
      },
      //选择的按钮
      handleSelectionChange(val){
        this.selectButtonList = val
      },
      //删除
      deletebutton(){
        if(this.selectButtonList.length === 0){
          this.$message.warning('请先选择需要删除的按钮！')
        }else{
          this.$confirm("是否确认删除?", "提示", {
            confirmButtonText: "确定",
            cancelButtonText: "取消",
            type: "warning"
          }).then(() => {
            const loading = this.$loading({
              lock: true,
              text: '正在删除,请耐心等待.....',
              spinner: 'el-icon-loading',
              background: 'rgba(0, 0, 0, 0.7)'
            })
            let buttonIds = this.selectButtonList.map(item => item.buttonId)
            let http = new ButtonMaintainApi()
            http.delete({buttonIds}).then(res => {
              if(res.code === 200){
                this.$message.success(`删除成功!`)
                this.getData()
              }else{
                this.$message.success(`删除失败!`)
              }
              loading.close()
            }).catch(e => {
              loading.close()
            })
          }).catch(e => {
            this.$message.info('已取消删除！')
          })
        }
      },
      //禁用或启用
      disEnable(val, text){
        const loading = this.$loading({
          lock: true,
          text: '请耐心等待.....',
          spinner: 'el-icon-loading',
          background: 'rgba(0, 0, 0, 0.7)'
        })
        let http = new ButtonMaintainApi()
        let status = text === '启用' ? 1 : 2
        http.changeStatus({buttonId:val.buttonId, status}).then(res => {
          if(res.code === 200){
            this.$message.success(`${text}成功!`)
            this.getData()
          }else{
            this.$message.success(`${text}失败!`)
          }
          loading.close()
        }).catch(e => {
          loading.close()
        })
      },
    },
  }
</script>

<style scoped>

</style>
