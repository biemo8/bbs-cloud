<template>
    <div>
      <el-dialog :title="title" :width="width"
                 :visible.sync="isShowVisible"
                 center :close-on-click-modal="false"
                 :before-close="() => $emit('close')">
        <el-card v-loading="isLoading">
          <el-form label-width="125px" :model="personData" ref="personData" :rules="rules">
            <el-row>
              <el-col :span="12">
                <el-form-item label="姓（中文）:" prop="lastName" id="lastName">
                  <el-input v-model="personData.lastName"></el-input>
                </el-form-item>
              </el-col>
              <el-col :span="12">
                <el-form-item label="名（中文）:" prop="firstName" id="firstName">
                  <el-input v-model="personData.firstName"></el-input>
                </el-form-item>
              </el-col>
            </el-row>
            <el-row>
              <el-col :span="12">
                <el-form-item label="姓（英文）:" prop="lastNamePinyin" id="lastNamePinyin">
                  <el-input v-model="personData.lastNamePinyin"></el-input>
                </el-form-item>
              </el-col>
              <el-col :span="12">
                <el-form-item label="名（英文）:" prop="firstNamePinyin" id="firstNamePinyin">
                  <el-input v-model="personData.firstNamePinyin"></el-input>
                </el-form-item>
              </el-col>
            </el-row>
            <el-row>
              <el-col :span="12">
                <el-form-item label="性别:" prop="sex" id="sex">
                  <el-radio-group v-model="personData.sex">
                    <el-radio label="M">男</el-radio>
                    <el-radio label="F">女</el-radio>
                  </el-radio-group>
                </el-form-item>
              </el-col>
              <el-col :span="12">
                <el-form-item label="生日:" prop="birthday" id="birthday">
                  <el-date-picker
                    v-model="personData.birthday"
                    type="date"
                    value-format="yyyy-MM-dd"
                    placeholder="选择日期">
                  </el-date-picker>
                </el-form-item>
              </el-col>
            </el-row>
            <el-row>
              <el-col :span="12">
                <el-form-item label="学历:" prop="education" id="education">
                  <base-select dictTypeCode="EDUCATION" v-model="personData.education"></base-select>
                </el-form-item>
              </el-col>
              <el-col :span="12">
                <el-form-item label="身份证号:" prop="idCard" id="idCard">
                  <el-input v-model="personData.idCard"></el-input>
                </el-form-item>
              </el-col>
            </el-row>
            <el-row>
              <el-col :span="12">
                <el-form-item label="籍贯:" prop="birthplace" id="birthplace">
                  <el-input v-model="personData.birthplace"></el-input>
                </el-form-item>
              </el-col>
              <el-col :span="12">
                <el-form-item label="民族:" prop="nation" id="nation">
                  <el-input v-model="personData.nation"></el-input>
                </el-form-item>
              </el-col>
            </el-row>
            <el-row>
              <el-col :span="12">
                <el-form-item label="婚姻状况:" prop="matrimony" id="matrimony">
                  <base-select dictTypeCode="MARITAL" v-model="personData.matrimony"></base-select>
                </el-form-item>
              </el-col>
              <el-col :span="12">
                <el-form-item label="政治面貌:" prop="political" id="political">
                  <base-select dictTypeCode="POLICY" v-model="personData.political"></base-select>
                </el-form-item>
              </el-col>
            </el-row>
            <el-button type="primary" @click="addEntUserDepts" style="margin-bottom: 20px">新增</el-button>
            <div style="height: 2px;width: 100%; background: #2b2f3a; margin-bottom: 20px"></div>
            <div v-for="(item, index) in entUserDepts" :key="index">
              <el-row>
                <el-col :span="8">
                  <el-form-item label="公司:" required>
                    <el-select v-model="item.companyId" @change="companyIdChange(item.companyId,index)" filterable placeholder="请选择">
                      <el-option
                        v-for="item in companyList"
                        :key="item.companyId"
                        :label="item.companyName"
                        :value="item.companyId">
                      </el-option>
                    </el-select>
                  </el-form-item>
                </el-col>
                <el-col :span="8">
                  <el-form-item label="部门:" required>
                    <el-select v-model="item.deptId" :disabled="!item.companyId" filterable placeholder="请选择">
                      <el-option
                        v-for="item in item.deptList"
                        :key="item.deptId"
                        :label="item.deptName"
                        :value="item.deptId">
                      </el-option>
                    </el-select>
                  </el-form-item>
                </el-col>
              </el-row>
              <el-row>
                <el-col :span="8">
                  <el-form-item label="是否是默认部门:" required>
                    <el-radio-group v-model="item.defaultFlag">
                      <el-radio label="Y">是</el-radio>
                      <el-radio label="N">否</el-radio>
                    </el-radio-group>
                  </el-form-item>
                </el-col>
                <el-col :span="8">
                  <el-form-item label="职务:" required>
                    <el-select v-model="item.dutyId" filterable placeholder="请选择">
                      <el-option
                        v-for="item in dutyList"
                        :key="item.dutyId"
                        :label="item.dutyName"
                        :value="item.dutyId">
                      </el-option>
                    </el-select>
                  </el-form-item>
                </el-col>
                <el-col :span="8">
                  <el-form-item>
                    <el-button type="danger" @click="deleteEntUserDepts(index)">删除</el-button>
                  </el-form-item>
                </el-col>
              </el-row>
              <div style="height: 2px;width: 100%; background: #2b2f3a; margin-bottom: 20px"></div>
            </div>
            <el-row>
              <el-col :span="12">
                <el-form-item label="邮箱:" prop="email" id="email">
                  <el-input v-model="personData.email"></el-input>
                </el-form-item>
              </el-col>
              <el-col :span="12">
                <el-form-item label="电话:" prop="phone" id="phone">
                  <el-input v-model="personData.phone"></el-input>
                </el-form-item>
              </el-col>
            </el-row>
            <el-row>
              <el-col :span="12">
                <el-form-item label="手机号:" prop="mobilePhone" id="mobilePhone">
                  <el-input v-model="personData.mobilePhone"></el-input>
                </el-form-item>
              </el-col>
              <el-col :span="12" v-if="userId">
                <el-form-item label="排序:" prop="orderNo" id="orderNo">
                  <el-input-number v-model.number="personData.orderNo"></el-input-number>
                </el-form-item>
              </el-col>
            </el-row>
            <el-row>
              <el-col :span="12">
                <el-form-item label="账号:" prop="account" id="account">
                  <el-input v-model="personData.account" :readonly="userId !== ''"></el-input>
                </el-form-item>
              </el-col>
              <el-col :span="12" v-if="!userId">
                <el-form-item label="密码:" prop="password" id="password">
                  <el-input v-model="personData.password"></el-input>
                </el-form-item>
              </el-col>
            </el-row>
          </el-form>
        </el-card>
        <div slot="footer" class="dialog-footer">
          <el-button type="primary" @click="savePerson">保存</el-button>
          <el-button @click="$emit('close')">取消</el-button>
        </div>
      </el-dialog>
    </div>
</template>

<script>
  import PersonManageApi from '@/api/system/orgBuild/PersonManageApi'
  export default {
    name: 'person_addEdit',
    props:{
      width:{
        type:String,
        default:'60vw'
      },
      userId:{
        type:String,
      },
    },
    data(){
      let validatePassword = (rule, value, callback) => {
        if (value.length < 6) {
          callback(new Error('密码长度最少6位！'))
        } else {
          callback()
        }
      }
      return{
        title:'新增人员',
        isShowVisible:true,
        isLoading:false,
        rules:{
          lastName: [{required: true, message: '请填写姓（中文）', tirgger: 'blur'}],
          firstName: [{required: true, message: '请填写名（中文）', tirgger: 'blur'}],
          lastNamePinyin: [{required: true, message: '请填写姓（英文）', tirgger: 'blur'}],
          firstNamePinyin: [{required: true, message: '请填写名（英文）', tirgger: 'blur'}],
          sex: [{required: true, message: '请选择性别', tirgger: 'change'}],
          birthday: [{required: true, message: '请选择生日', tirgger: 'change'}],
          education: [{required: true, message: '请选择学历', tirgger: 'change'}],
          idCard: [{required: true, message: '请填写身份证号', tirgger: 'blur'}],
          birthplace: [{required: true, message: '请填写籍贯', tirgger: 'blur'}],
          nation: [{required: true, message: '请填写民族', tirgger: 'blur'}],
          matrimony: [{required: true, message: '请选择婚姻状况', tirgger: 'change'}],
          political: [{required: true, message: '请选择政治面貌', tirgger: 'change'}],
          companyId: [{required: true, message: '请选择公司', tirgger: 'change'}],
          deptId: [{required: true, message: '请选择部门', tirgger: 'change'}],
          defaultFlag: [{required: true, message: '请选择是否是默认部门', tirgger: 'change'}],
          dutyId: [{required: true, message: '请选择职务', tirgger: 'change'}],
          email: [{required: true, message: '请填写邮箱', tirgger: 'blur'}],
          phone: [{required: true, message: '请填写电话', tirgger: 'blur'}],
          mobilePhone: [{required: true, message: '请填写手机号', tirgger: 'blur'}],
          orderNo: [{required: true, type:'number', message: '请填写排序', tirgger: 'blur'}],
          account: [{required: true, message: '请填写账号', tirgger: 'blur'}],
          password: [{ required: true, trigger: 'blur', validator: validatePassword }]
        },
        personData:{},
        entUserDepts:[
          {
            deptId:'',
            deptList:[],
          }
        ], //部门职务关联列表
        companyList:[],
        dutyList:[],
      }
    },
    async created(){
      this.isLoading = true
      let http = new PersonManageApi()
      let companyList = await http.getCompanySelect({})
      this.companyList = companyList.data
      let dutyList = await http.getDutySelect({})
      this.dutyList = dutyList.data
      if(this.userId){
        this.title = '编辑人员'
        let personData = await http.detail({userId:this.userId})
        this.personData = personData.data
        this.entUserDepts = this.personData.entUserDepts
        this.personData.entUserDepts.forEach(async (item, index) => {
          let deptList = await http.getDeptSelect({companyId:item.companyId})
          this.$set(this.entUserDepts[index],'deptList', deptList.data)
        })
        this.isLoading = false
      }else{
        this.isLoading = false
      }
    },
    methods:{
      addEntUserDepts(){
        this.entUserDepts.push({deptList:[], deptId:'',})
      },
      deleteEntUserDepts(index){
        this.entUserDepts.splice(index, 1)
      },
      async companyIdChange(companyId,index){
        let http = new PersonManageApi()
        this.entUserDepts[index].deptId = ''
        let deptList = await http.getDeptSelect({companyId})
        this.entUserDepts[index].deptList = deptList.data
      },
      savePerson(){
        this.$refs['personData'].validate((valid, object) => {
          if (valid) {
            const loading = this.$loading({
              lock: true,
              text: '请耐心等待.....',
              spinner: 'el-icon-loading',
              background: 'rgba(0, 0, 0, 0.7)'
            })
            this.personData.entUserDepts = this.entUserDepts
            let http = new PersonManageApi()
            //若有userId证明时编辑没有则时新增
            if(this.userId){
              http.update(this.personData).then(res => {
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
              http.add(this.personData).then(res => {
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
