<template>
  <el-dialog :title="title" :width="width"
             :visible.sync="isShowVisible"
             center :close-on-click-modal="false"
             :before-close="() => $emit('close')">
    <el-card>
      <el-form label-width="105px" :model="deptData" ref="deptData" :rules="rules">
        <el-row>
          <el-col :span="24">
            <el-form-item label="部门名称:" prop="deptName" id="deptName">
              <el-input v-model="deptData.deptName"></el-input>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="部门简称:" prop="deptShortName" id="deptShortName">
              <el-input v-model="deptData.deptShortName"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="排序:" prop="orderNo" id="orderNo">
              <el-input-number v-model.number="deptData.orderNo"></el-input-number>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="请选择公司:" prop="companyId" id="companyId">
              <el-select v-model="deptData.companyId" @change="companyChange" filterable placeholder="请选择">
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
            <el-form-item label="上级部门:" prop="parentId" id="parentId">
              <el-select v-model="deptData.parentId" filterable :disabled="!deptData.companyId" placeholder="请先选择公司">
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
          <el-input v-model="deptData.description"></el-input>
        </el-form-item>
      </el-form>
    </el-card>
    <div slot="footer" class="dialog-footer">
      <el-button type="primary" @click="saveDept">保存</el-button>
      <el-button @click="$emit('close')">取消</el-button>
    </div>
  </el-dialog>
</template>

<script>
  import DeptManageApi from '@/api/system/orgBuild/DeptManageApi'
  import CompanyManageApi from '@/api/system/orgBuild/CompanyManageApi'
  export default {
    name: 'dept_addEdit',
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
      const validateParentId = (rule, value, callback) => {
        if (value === '') {
          callback(new Error('请选择上级部门'))
        } else {
          callback()
        }
      };
      const validateCompanyId = (rule, value, callback) => {
        if (value === '') {
          callback(new Error('请选择公司'))
        } else {
          callback()
        }
      };
      return{
        title:'新增部门',
        isShowVisible:true,
        rules:{
          deptName: [{required: true, message: '请填写部门名称', tirgger: 'blur'}],
          deptCode: [{required: true, message: '请填写部门编码', tirgger: 'blur'}],
          orderNo: [{type:'number', required: true, message: '请填写排序', tirgger: 'change'}],
          deptShortName: [{required: true, message: '请填写部门简称', tirgger: 'blur'}],
          companyId: [{required: true, validator: validateCompanyId, tirgger: 'change'}],
          parentId: [{required: true, validator: validateParentId, tirgger: 'change'}],
        },
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
      if(this.deptId){
        let http = new DeptManageApi()
        this.title = '编辑部门'
        let deptData = await http.detail({deptId:this.deptId})
        this.deptData = deptData.data
        let deptList = await http.queryDeptTree({companyId:this.deptData.companyId})
        this.deptList = deptList.data
        this.isLoading = false
      }else{
        this.isLoading = false
      }
    },
    methods:{
      async companyChange(){
        let http = new DeptManageApi()
        let deptList = await http.queryDeptTree({companyId:this.deptData.companyId})
        this.deptList = deptList.data
      },
      saveDept(){
        this.$refs['deptData'].validate((valid, object) => {
          if (valid) {
            const loading = this.$loading({
              lock: true,
              text: '请耐心等待.....',
              spinner: 'el-icon-loading',
              background: 'rgba(0, 0, 0, 0.7)'
            })
            let http = new DeptManageApi()
            //若有deptId证明时编辑没有则时新增
            if(this.deptId){
              http.update(this.deptData).then(res => {
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
              http.add(this.deptData).then(res => {
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
