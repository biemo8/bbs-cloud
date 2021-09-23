<template>
  <div>
    <el-dialog :title="title" :width="width"
               :visible.sync="isShowVisible"
               center :close-on-click-modal="false"
               :before-close="() => $emit('close')">
      <el-card>
        <el-form label-width="115px" :model="roleData" ref="roleData" :rules="rules" v-loading="formLoading">
          <el-row>
            <el-col :span="12">
              <el-form-item label="角色排序码:">
                <el-input-number v-model.number="roleData.orderNo"></el-input-number>
              </el-form-item>
            </el-col>
          </el-row>
          <el-form-item label="所属应用:" prop="appId" id="appId">
            <el-select v-model="roleData.appId" filterable placeholder="请选择">
              <el-option
                v-for="item in appList"
                :key="item.appId"
                :label="item.appName"
                :value="item.appId">
              </el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="角色名称:" prop="roleName" id="roleName">
            <el-input v-model="roleData.roleName" @blur="roleNameBlur" :disabled="!roleData.appId" placeholder="请先选择所属应用"></el-input>
          </el-form-item>
          <el-form-item label="角色编码:" prop="roleCode" id="roleCode">
            <el-input v-model="roleData.roleCode" @blur="roleCodeBlur" :disabled="!roleData.appId" placeholder="请先选择所属应用"></el-input>
          </el-form-item>
          <el-form-item label="角色描述:">
            <el-input type="textarea" :autosize="{ minRows: 2}" placeholder="请输入内容" v-model="roleData.roleDesc"></el-input>
          </el-form-item>
          <el-form-item label="状态:" prop="status" id="status">
            <el-radio-group v-model="roleData.status">
              <el-radio :label="1">启用</el-radio>
              <el-radio :label="2">禁用</el-radio>
            </el-radio-group>
          </el-form-item>
        </el-form>
      </el-card>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="saverole">保存</el-button>
        <el-button @click="$emit('close')">取消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
  import RoleManageApi from "@/api/system/role/RoleManageApi";
  import MenuManagementApi from "@/api/system/role/MenuManagementApi";
  export default {
    name: "role_manage_addEdit",
    props:{
      width:{
        type:String,
        default:'60vw'
      },
      roleId:{
        type:String,
      },
    },
    data(){
      return{
        title:'新增角色',
        isShowVisible:true,
        formLoading:false, //formLoading动画
        roleData:{
          roleType:'',
        },
        rules:{
          mgrFlag: [{required: true, message: '请选择所属公司类型', tirgger: 'change'}],
          appId: [{required: true, message: '请选择应用名称', tirgger: 'change'}],
          roleName: [{required: true, message: '请填写角色名称', tirgger: 'blur'}],
          roleCode: [{required: true, message: '请填写角色编码', tirgger: 'blur'}],
          status: [{type:'number', required: true, message: '请选择状态', tirgger: 'change'}],
          // roleType: [{required: true, message: '请选择角色类型', tirgger: 'change'}],
          // hideFlag: [{required: true, message: '请选择是否隐藏', tirgger: 'change'}],
        },
        appList:[],
        mgrFlagList:[
          {label:'管理公司角色', value:'1'},
          {label:'执行公司角色', value:'2'},
        ],
      }
    },
    created(){
      this.initData()
    },
    methods:{
      //初始化数据
      async initData(){
        this.formLoading = true
        let http = new MenuManagementApi()
        //获取应用名称下拉框数据源
        let appList = await http.getAppSelect({})
        this.appList = appList.data
        if(this.roleId){
          this.title = '修改角色'
          let https = new RoleManageApi()
          let roleData = await https.detail({roleId:this.roleId})
          this.roleData = roleData.data
        }
        this.formLoading = false
      },
      //保存
      saverole(){
        this.$refs['roleData'].validate((valid, object) => {
          if (valid) {
            const loading = this.$loading({
              lock: true,
              text: '请耐心等待.....',
              spinner: 'el-icon-loading',
              background: 'rgba(0, 0, 0, 0.7)'
            })
            let http = new RoleManageApi()
            //若roleId存在证明编辑否则新增
            if(this.roleId){
              http.update(this.roleData).then(res => {
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
              http.add(this.roleData).then(res => {
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
      //所属公司类型变化时
      mgrFlagChange(val){
        this.roleData.roleType = ''
      },
      //角色名称失去焦点时的回调
      roleNameBlur(val){
        if(this.roleData.roleName){
          let http = new RoleManageApi()
          http.name({appId:this.roleData.appId, roleName:this.roleData.roleName, roleId:this.roleId}).then(res => {
            if(res.code === 200){}else{
              this.$message.warning('角色名称验证不通过，请重新输入!')
              this.roleData.roleName = ''
            }
          })
        }
      },
      //角色编码失去焦点时的回调
      roleCodeBlur(val){
        if(this.roleData.roleCode){
          let http = new RoleManageApi()
          http.code({appId:this.roleData.appId, roleCode:this.roleData.roleCode, roleId:this.roleId}).then(res => {
            if(res.code === 200){}else{
              this.$message.warning('角色编码验证不通过，请重新输入!')
              this.roleData.roleCode = ''
            }
          })
        }
      },
    },
  }
</script>

<style scoped>

</style>
