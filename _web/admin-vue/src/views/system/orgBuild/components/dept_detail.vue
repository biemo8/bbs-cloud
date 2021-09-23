<template>
  <el-dialog :title="title" :width="width"
             :visible.sync="isShowVisible"
             center :close-on-click-modal="false"
             :before-close="() => $emit('close')">
    <el-card v-loading="isLoading">
      <el-form label-width="105px">
        <el-row>
          <el-col :span="24">
            <el-form-item label="部门名称:">
              <span>{{deptData.deptName}}</span>
            </el-form-item>
          </el-col>
<!--          <el-col :span="12">-->
<!--            <el-form-item label="部门编码:">-->
<!--              <span>{{deptData.cpCode}}</span>-->
<!--            </el-form-item>-->
<!--          </el-col>-->
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="部门简称:">
              <span>{{deptData.deptShortName}}</span>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="排序:">
              <span>{{deptData.orderNo}}</span>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="公司:" class="select-disabled">
              <el-select v-model="deptData.companyId" disabled placeholder="请选择">
                <el-option
                  v-for="item in companyTreeData"
                  :key="item.nodeId"
                  :label="item.nodeName"
                  :value="item.nodeId">
                </el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="上级部门:">
              <el-select v-model="deptData.parentId" disabled class="select-disabled">
                <el-option
                  v-for="item in deptList"
                  :key="item.nodeId"
                  :label="item.nodeName"
                  :value="item.nodeId">
                </el-option>
              </el-select>
            </el-form-item>
          </el-col>
        </el-row>
        <el-form-item label="部门描述:">
          <span>{{deptData.description}}</span>
        </el-form-item>
      </el-form>
    </el-card>
    <div slot="footer" class="dialog-footer">
      <el-button @click="$emit('close')">关闭</el-button>
    </div>
  </el-dialog>
</template>

<script>
  import DeptManageApi from '@/api/system/orgBuild/DeptManageApi'
  import CompanyManageApi from '@/api/system/orgBuild/CompanyManageApi'
  export default {
    name: 'dept_detail',
    props:{
      width:{
        type:String,
        default:'60vw'
      },
      deptId:{
        type:String,
      },
    },
    data(){
      return{
        title:'部门详情',
        isShowVisible:true,
        isLoading:false,
        deptData:{},
        deptList:[],
        companyTreeData:[],
      }
    },
    async created(){
      this.isLoading = true
      let https = new CompanyManageApi()
      let companyTreeData = await https.queryCompTree({})
      companyTreeData.data.forEach((item, index) => {
        if(item.nodeId === -1){
          companyTreeData.data.splice(index, 1)
        }
      })
      this.companyTreeData = companyTreeData.data
      let http = new DeptManageApi()
      this.title = '编辑部门'
      let deptData = await http.detail({deptId:this.deptId})
      this.deptData = deptData.data
      let deptList = await http.queryDeptTree({companyId:this.deptData.companyId})
      this.deptList = deptList.data
      this.isLoading = false
    },
  }
</script>

<style scoped lang="scss">
  .select-disabled{
     .is-disabled{
      .el-input__inner{
        color: #000;
      }
    }
  }

</style>
