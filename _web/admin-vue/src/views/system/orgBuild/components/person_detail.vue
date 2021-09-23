<template>
  <div>
    <el-dialog :title="title" :width="width"
               :visible.sync="isShowVisible"
               center :close-on-click-modal="false"
               :before-close="() => $emit('close')">
      <el-card v-loading="isLoading">
        <el-form label-width="125px">
          <el-row>
            <el-col :span="12">
              <el-form-item label="姓（中文）:">
                <span>{{personData.lastName}}</span>
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item label="名（中文）:">
                <span>{{personData.firstName}}</span>
              </el-form-item>
            </el-col>
          </el-row>
          <el-row>
            <el-col :span="12">
              <el-form-item label="姓（英文）:">
                <span>{{personData.lastNamePinyin}}</span>
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item label="名（英文）:">
                <span>{{personData.firstNamePinyin}}</span>
              </el-form-item>
            </el-col>
          </el-row>
          <el-row>
            <el-col :span="12">
              <el-form-item label="性别:">
                <span v-if="personData.sex === 'M'">男</span>
                <span v-if="personData.sex === 'F'">女</span>
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item label="生日:">
                <span>{{personData.birthday}}</span>
              </el-form-item>
            </el-col>
          </el-row>
          <el-row>
            <el-col :span="12">
              <el-form-item label="学历:">
                <base-select dictTypeCode="EDUCATION" class="select-disabled" disabled v-model="personData.education"></base-select>
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item label="身份证号:">
                <span>{{personData.idCard}}</span>
              </el-form-item>
            </el-col>
          </el-row>
          <el-row>
            <el-col :span="12">
              <el-form-item label="籍贯:">
                <span>{{personData.birthplace}}</span>
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item label="民族:">
                <span>{{personData.nation}}</span>
              </el-form-item>
            </el-col>
          </el-row>
          <el-row>
            <el-col :span="12">
              <el-form-item label="婚姻状况:">
                <base-select dictTypeCode="MARITAL" class="select-disabled" disabled v-model="personData.matrimony"></base-select>
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item label="政治面貌:">
                <base-select dictTypeCode="POLICY" class="select-disabled" disabled v-model="personData.political"></base-select>
              </el-form-item>
            </el-col>
          </el-row>
          <div style="height: 2px;width: 100%; background: #2b2f3a; margin-bottom: 20px"></div>
          <div v-for="(item, index) in entUserDepts" :key="index">
            <el-row>
              <el-col :span="8">
                <el-form-item label="公司:">
                  <el-select v-model="item.companyId" class="select-disabled" disabled placeholder="请选择">
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
                <el-form-item label="部门:">
                  <el-select v-model="item.deptId" class="select-disabled" disabled filterable placeholder="请选择">
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
                <el-form-item label="是否是默认部门:">
                  <span v-if="item.defaultFlag === 'Y'">是</span>
                  <span v-if="item.defaultFlag === 'N'">否</span>
                </el-form-item>
              </el-col>
              <el-col :span="8">
                <el-form-item label="职务:">
                  <el-select v-model="item.dutyId" class="select-disabled" disabled placeholder="请选择">
                    <el-option
                      v-for="item in dutyList"
                      :key="item.dutyId"
                      :label="item.dutyName"
                      :value="item.dutyId">
                    </el-option>
                  </el-select>
                </el-form-item>
              </el-col>
            </el-row>
            <div style="height: 2px;width: 100%; background: #2b2f3a; margin-bottom: 20px"></div>
          </div>
          <el-row>
            <el-col :span="12">
              <el-form-item label="邮箱:">
                <span>{{personData.email}}</span>
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item label="电话:">
                <span>{{personData.phone}}</span>
              </el-form-item>
            </el-col>
          </el-row>
          <el-row>
            <el-col :span="12">
              <el-form-item label="手机号:">
                <span>{{personData.mobilePhone}}</span>
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item label="排序:">
                <span>{{personData.orderNo}}</span>
              </el-form-item>
            </el-col>
          </el-row>
          <el-row>
            <el-col :span="12">
              <el-form-item label="账号:">
                <span>{{personData.account}}</span>
              </el-form-item>
            </el-col>
          </el-row>
        </el-form>
      </el-card>
      <div slot="footer" class="dialog-footer">
        <el-button @click="$emit('close')">关闭</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
  import PersonManageApi from '@/api/system/orgBuild/PersonManageApi'
  export default {
    name: 'person_detail',
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
      return{
        title:'人员详情',
        isShowVisible:true,
        isLoading:false,
        personData:{},
        entUserDepts:[
          {
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
      let personData = await http.detail({userId:this.userId})
      this.personData = personData.data
      this.entUserDepts = this.personData.entUserDepts
      this.personData.entUserDepts.forEach(async (item, index) => {
        let deptList = await http.getDeptSelect({companyId:item.companyId})
        this.$set(this.entUserDepts[index],'deptList', deptList.data)
      })
      this.isLoading = false
    },
  }
</script>

<style scoped>

</style>
