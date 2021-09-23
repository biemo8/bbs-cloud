<template>
  <div>
    <el-dialog :title="title" :width="width"
               :visible.sync="isShowVisible"
               center :close-on-click-modal="false"
               :before-close="() => $emit('close')">
      <el-card v-loading="isLoading">
        <div class="text_box">
          <span>基本信息</span>
        </div>
        <div class="text_box_line"></div>
        <el-form label-width="150px" :model="clientData" ref="clientData" :rules="rules">
          <el-form-item label="所属应用:" prop="appId" id="appId">
            <el-select v-model="clientData.appId" filterable placeholder="请选择">
              <el-option
                v-for="item in appList"
                :key="item.appId"
                :label="item.appName"
                :value="item.appId">
              </el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="设备类型:" prop="clientType" id="clientType">
            <el-select v-model="clientData.clientType" filterable placeholder="请选择">
              <el-option
                v-for="item in clientTypeList"
                :key="item.value"
                :label="item.label"
                :value="item.value">
              </el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="客户端名称:" prop="name" id="name">
            <el-input v-model="clientData.name"></el-input>
          </el-form-item>
          <el-form-item label="token接收地址:" prop="ssoUrl" id="ssoUrl">
            <el-input v-model="clientData.ssoUrl" style="width: 60%"></el-input>
          </el-form-item>
          <el-form-item label="登录类型:" prop="loginType" id="loginType">
            <el-radio-group v-model="clientData.loginType">
              <el-radio :label="1">应用自定义页面</el-radio>
              <el-radio :label="2">统一登录页面</el-radio>
            </el-radio-group>
          </el-form-item>
          <el-row>
            <el-col :span="12">
              <el-form-item label="私钥:">
                <span>{{clientData.privateKey}}</span>
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-tooltip class="item" effect="dark" content="刷新私钥" placement="top-start">
                <el-button type="primary" size="small" icon="el-icon-refresh" @click="creatPrivateKey('私钥')"></el-button>
              </el-tooltip>
            </el-col>
          </el-row>
          <el-row>
            <el-col :span="12">
              <el-form-item label="签名私钥:">
                <span>{{clientData.signPrivateKey}}</span>
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-tooltip class="item" effect="dark" content="刷新签名私钥" placement="top-start">
                <el-button type="primary" size="small" icon="el-icon-refresh" @click="creatPrivateKey('签名私钥')"></el-button>
              </el-tooltip>
            </el-col>
          </el-row>
          <el-row>
            <el-col :span="12">
              <el-form-item label="数据私钥:">
                <span>{{clientData.dataPrivateKey}}</span>
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-tooltip class="item" effect="dark" content="刷新数据私钥" placement="top-start">
                <el-button type="primary" size="small" icon="el-icon-refresh" @click="creatPrivateKey('数据私钥')"></el-button>
              </el-tooltip>
            </el-col>
          </el-row>
          <el-form-item label="token过期时间:" prop="tokenExp" id="tokenExp">
            <el-input-number style="width: 50%" v-model.number="clientData.tokenExp" placeholder="请输入token过期时间(秒)"></el-input-number>
          </el-form-item>
          <el-form-item label="刷新token过期时间:" prop="refreshTokenExp" id="refreshTokenExp">
            <el-input-number style="width: 50%" v-model.number="clientData.refreshTokenExp" placeholder="请输入刷新token过期时间(秒)"></el-input-number>
          </el-form-item>
          <el-form-item label="退出登录的url:" prop="loginOutUr" id="loginOutUr">
            <el-input v-model="clientData.loginOutUrl" style="width: 60%" placeholder="若是内部应用则不填"></el-input>
          </el-form-item>
        </el-form>
      </el-card>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="saveClient">保存</el-button>
        <el-button @click="$emit('close')">取消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
  import ClientManageApi from '@/api/auth/ssoApp/ClientManageApi'
  import {validateHttpURL} from '@/utils/validate'
  import {ramdomStrKey} from '@/utils/randomStr'
  export default {
    name: "client_addEdit",
    props:{
      width:{
        type:String,
        default:'60vw'
      },
      clientId:{
        type:String || Number,
      },
    },
    data(){
      let validateUrl = (rule, value, callback) => {
        if (value === '') {
          callback()
        } else {
          if(validateHttpURL(value)) callback()
          callback(new Error('请输入合法的URL!'))
        }
      }
      let validateSsoUrl = (rule, value, callback) => {
        if (value === '') {
          callback(new Error('请输入token接收地址'))
        } else {
          if(validateHttpURL(value)) callback()
          callback(new Error('请输入合法的URL!'))
        }
      }
      return{
        title:'新增',
        isShowVisible:true,
        isLoading:false,
        rules:{
          appId: [{required: true, message: '请选择应用', tirgger: 'change'}],
          clientType: [{required: true, type:'number', message: '请选择设备类型', tirgger: 'change'}],
          ssoUrl: [{required: true,validator: validateSsoUrl, tirgger: 'blur'}],
          name: [{required: true, message: '请输入客户端名称', tirgger: 'blur'}],
          loginType: [{required: true, type:'number', message: '请选择登录类型', tirgger: 'change'}],
          tokenExp: [{required: true, type:'number', message: '请输入token过期时间', tirgger: 'blur'}],
          refreshTokenExp: [{required: true, type:'number', message: '请输入刷新token过期时间', tirgger: 'blur'}],
          loginOutUrl:[{validator: validateUrl, trigger: 'blur'}]
        },
        clientData:{
          privateKey:ramdomStrKey(32),
          signPrivateKey:ramdomStrKey(32),
          dataPrivateKey:ramdomStrKey(32),
          tokenExp:3600,
          refreshTokenExp:3600*24,
        },
        appList:[],
        clientTypeList:[
          {value:1, label:'浏览器端'},
          {value:2, label:'手机端'},
        ],
      }
    },
    async created(){
      this.isLoading = true
      let http = new ClientManageApi()
      let appList = await http.getAppSelectList({})
      this.appList = appList.data
      if(this.clientId){
        this.title = '编辑'
        let clientData = await http.detail({clientId:this.clientId})
        this.clientData = clientData.data
        this.isLoading = false
      }else {
        this.isLoading = false
      }
    },
    methods:{
      saveClient(){
        this.$refs['clientData'].validate((valid, object) => {
          if (valid) {
            const loading = this.$loading({
              lock: true,
              text: '请耐心等待.....',
              spinner: 'el-icon-loading',
              background: 'rgba(0, 0, 0, 0.7)'
            })
            let http = new ClientManageApi()
            //若有Id证明时编辑没有则时新增
            if(this.clientId){
              http.edit(this.clientData).then(res => {
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
              this.clientData.delFlag = 'N'
              http.add(this.clientData).then(res => {
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
      //生成私钥
      creatPrivateKey(text){
        switch (text) {
          case '私钥' :
            this.clientData.privateKey = uuId.v1(16)
            break
          case '签名私钥' :
            this.clientData.signPrivateKey = uuId.v1(16)
            break
          case '数据私钥' :
            this.clientData.dataPrivateKey = uuId.v1(16)
            break
        }
      },
    },
  }
</script>

<style scoped>

</style>
