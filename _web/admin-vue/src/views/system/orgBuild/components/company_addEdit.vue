<template>
  <el-dialog :title="title" :width="width"
             :visible.sync="isShowVisible"
             center :close-on-click-modal="false"
             :before-close="() => $emit('close')">
    <el-card>
      <el-form label-width="105px" :model="companyData" ref="companyData" :rules="rules">
        <el-row>
          <el-col :span="24">
            <el-form-item label="公司名称:" prop="name" id="name">
              <el-input v-model="companyData.name"></el-input>
            </el-form-item>
          </el-col>
<!--          <el-col :span="12">-->
<!--            <el-form-item label="公司编码:" prop="cpCode" id="cpCode">-->
<!--              <el-input v-model="companyData.cpCode"></el-input>-->
<!--            </el-form-item>-->
<!--          </el-col>-->
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="公司简称:" prop="shortName" id="shortName">
              <el-input v-model="companyData.shortName"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="排序:" prop="orderNo" id="orderNo">
              <el-input-number v-model.number="companyData.orderNo"></el-input-number>
            </el-form-item>
          </el-col>
        </el-row>
        <el-form-item label="上级公司:" prop="parentId" id="parentId">
          <el-select v-model="companyData.parentId" filterable placeholder="请选择">
            <el-option
              v-for="item in companyList"
              :key="item.nodeId"
              :label="item.nodeName"
              :value="item.nodeId">
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="公司门户地址:">
          <el-input v-model="companyData.url"></el-input>
        </el-form-item>
        <el-form-item label="公司描述:">
          <el-input v-model="companyData.description"></el-input>
        </el-form-item>
      </el-form>
    </el-card>
    <div slot="footer" class="dialog-footer">
      <el-button type="primary" @click="saveCompany">保存</el-button>
      <el-button @click="$emit('close')">取消</el-button>
    </div>
  </el-dialog>
</template>

<script>
  import CompanyManageApi from '@/api/system/orgBuild/CompanyManageApi'
  export default {
    name: 'company_addEdit',
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
      const validateParentId = (rule, value, callback) => {
        if (value === '') {
          callback(new Error('请选择上级公司'))
        } else {
          callback()
        }
      };
      return{
        title:'新增公司',
        isShowVisible:true,
        rules:{
          name: [{required: true, message: '请填写公司名称', tirgger: 'blur'}],
          cpCode: [{required: true, message: '请填写公司编码', tirgger: 'blur'}],
          orderNo: [{type:'number', required: true, message: '请填写排序', tirgger: 'change'}],
          shortName: [{required: true, message: '请填写公司简称', tirgger: 'blur'}],
          parentId: [{required: true, validator: validateParentId, tirgger: 'change'}],
        },
        companyData:{},
        companyList:[],
      }
    },
    async created(){
      this.isLoading = true
      let http = new CompanyManageApi()
      let companyList = await http.queryCompTree({})
      this.companyList = companyList.data
      if(this.companyId){
        this.title = '编辑公司'
        let companyData = await http.detail({companyId:this.companyId})
        this.companyData = companyData.data
        this.isLoading = false
      }else{
        this.isLoading = false
      }
    },
    methods:{
      saveCompany(){
        this.$refs['companyData'].validate((valid, object) => {
          if (valid) {
            const loading = this.$loading({
              lock: true,
              text: '请耐心等待.....',
              spinner: 'el-icon-loading',
              background: 'rgba(0, 0, 0, 0.7)'
            })
            let http = new CompanyManageApi()
            //若有companyId证明时编辑没有则时新增
            if(this.companyId){
              http.update(this.companyData).then(res => {
                loading.close()
                if(res.code === 200){
                  this.$message.success('保存成功!')
                  this.$emit('close')
                }else{
                  this.$message.error('保存失败!')
                }
              }).catch(e => {
                loading.close()
              })
            }else{
              http.add(this.companyData).then(res => {
                loading.close()
                if(res.code === 200){
                  this.$message.success('保存成功!')
                  this.$emit('close')
                }else{
                  this.$message.error('保存失败!')
                }
              }).catch(e => {
                loading.close()
              })
            }
          } else {
            let validArr = []
            Object.keys(object).forEach(function (key) {
              validArr.push(key)
            });
            let validVal = "#" + validArr[0];
            document.querySelector(validVal).scrollIntoView(true);
          }
        })
      },
    },
  }
</script>

<style scoped>

</style>
