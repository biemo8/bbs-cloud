<template>
  <el-dialog :title="title" :width="width"
             :visible.sync="isShowVisible"
             center :close-on-click-modal="false"
             :before-close="() => $emit('close')">
    <el-card v-loading="isLoading">
      <el-form label-width="105px">
        <el-row>
          <el-col :span="24">
            <el-form-item label="公司名称:">
              <span>{{companyData.name}}</span>
            </el-form-item>
          </el-col>
<!--          <el-col :span="12">-->
<!--            <el-form-item label="公司编码:">-->
<!--              <span>{{companyData.cpCode}}</span>-->
<!--            </el-form-item>-->
<!--          </el-col>-->
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="公司简称:">
              <span>{{companyData.shortName}}</span>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="排序:">
              <span>{{companyData.orderNo}}</span>
            </el-form-item>
          </el-col>
        </el-row>
        <el-form-item label="上级公司:">
<!--          <span>{{companyData.parentId}}</span>-->
          <el-select v-model="companyData.parentId" disabled class="select-disabled">
            <el-option
              v-for="item in companyList"
              :key="item.nodeId"
              :label="item.nodeName"
              :value="item.nodeId">
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="公司门户地址:">
          <span>{{companyData.url}}</span>
        </el-form-item>
        <el-form-item label="公司描述:">
          <span>{{companyData.description}}</span>
        </el-form-item>
      </el-form>
    </el-card>
    <div slot="footer" class="dialog-footer">
      <el-button @click="$emit('close')">关闭</el-button>
    </div>
  </el-dialog>
</template>

<script>
  import CompanyManageApi from '@/api/system/orgBuild/CompanyManageApi'
  export default {
    name: 'company_detail',
    props:{
      width:{
        type:String,
        default:'60vw'
      },
      companyId:{
        type:String,
      },
    },
    data(){
      return{
        title:'公司详情',
        isShowVisible:true,
        isLoading:false,
        companyData:{},
        companyList:[]
      }
    },
    async created(){
      this.isLoading = true
      let http = new CompanyManageApi()
      let companyList = await http.queryCompTree({})
      this.companyList = companyList.data
      let companyData = await http.detail({companyId:this.companyId})
      this.companyData = companyData.data
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
