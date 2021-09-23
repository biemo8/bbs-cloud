<template>
  <div>
    <el-dialog :title="title" :width="width"
               :visible.sync="isShowVisible"
               center :close-on-click-modal="false"
               :before-close="() => $emit('close')">
      <el-card>
        <el-form label-width="153px" :model="resourceData" ref="resourceData" :rules="rules" v-loading="formLoading">
          <div class="text_box">
            <span>基本信息</span>
          </div>
          <div class="text_box_line"></div>
          <el-row>
            <el-col :span="12">
              <el-form-item label="应用标识:" prop="appCode" id="appCode">
                <el-select v-model="resourceData.appCode" filterable placeholder="请选择">
                  <el-option
                    v-for="item in appList"
                    :key="item.appCode"
                    :label="item.appName"
                    :value="item.appCode">
                  </el-option>
                </el-select>
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item label="控制器类名:" prop="className" id="className">
                <el-input v-model="resourceData.className"></el-input>
              </el-form-item>
            </el-col>
          </el-row>
          <el-row>
            <el-col :span="12">
              <el-form-item label="控制器中的方法名称:" prop="methodName" id="methodName">
                <el-input v-model="resourceData.methodName"></el-input>
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item label="资源所属模块:" prop="modularCode" id="modularCode">
                <el-select v-model="resourceData.modularCode" filterable placeholder="请选择" @change="modularCodeChange">
                  <el-option
                    v-for="item in modularList"
                    :key="item.modularCode"
                    :label="item.modularName"
                    :value="item.modularCode">
                  </el-option>
                </el-select>
              </el-form-item>
            </el-col>
          </el-row>
          <el-row>
            <el-col :span="12">
              <el-form-item label="资源标识:" prop="code" id="code">
                <el-input v-model="resourceData.code"></el-input>
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item label="资源名称:" prop="name" id="name">
                <el-input v-model="resourceData.name"></el-input>
              </el-form-item>
            </el-col>
          </el-row>
          <el-form-item label="资源的请求路径:" prop="url" id="url">
            <el-input v-model="resourceData.url" :disabled="url !== ''"></el-input>
          </el-form-item>
          <el-row>
            <el-col :span="12">
              <el-form-item label="http请求方法:" prop="httpMethod" id="httpMethod">
                <el-select v-model="resourceData.httpMethod" filterable placeholder="请选择">
                  <el-option
                    v-for="item in httpMethodList"
                    :key="item"
                    :label="item"
                    :value="item">
                  </el-option>
                </el-select>
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item label="是否是菜单:" prop="menuFlag" id="menuFlag">
                <el-radio-group v-model="resourceData.menuFlag">
                  <el-radio :label="true">是</el-radio>
                  <el-radio :label="false">否</el-radio>
                </el-radio-group>
              </el-form-item>
            </el-col>
          </el-row>
          <el-row>
            <el-col :span="12">
              <el-form-item label="是否需要登录:" prop="requiredLogin" id="requiredLogin">
                <el-radio-group v-model="resourceData.requiredLogin">
                  <el-radio :label="true">是</el-radio>
                  <el-radio :label="false">否</el-radio>
                </el-radio-group>
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item label="是否需要鉴权:" prop="requiredPermission" id="requiredPermission">
                <el-radio-group v-model="resourceData.requiredPermission">
                  <el-radio :label="true">是</el-radio>
                  <el-radio :label="false">否</el-radio>
                </el-radio-group>
              </el-form-item>
            </el-col>
          </el-row>
          <el-form-item label="资源添加日期:" prop="createTime" id="createTime">
            <el-date-picker
              v-model="resourceData.createTime"
              type="date"
              value-format="yyyy-MM-dd"
              placeholder="选择日期">
            </el-date-picker>
          </el-form-item>
          <el-form-item label="资源初始化服务器的ip地址:" prop="ipAddress" id="ipAddress" label-width="193px">
            <el-input v-model="resourceData.ipAddress"></el-input>
          </el-form-item>
        </el-form>
      </el-card>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="saveResource">保存</el-button>
        <el-button @click="$emit('close')">取消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
  import ResourceMaintainIndexApi from '@/api/auth/resourceMaintain/ResourceMaintainIndexApi'
  export default {
    name: "resourceAddEdit",
    props:{
      width:{
        type:String,
        default:'60vw'
      },
      url:{
        type:String,
      },
    },
    data(){
      return{
        title:'新增资源',
        isShowVisible:true,
        formLoading:false, //formLoading动画
        resourceData:{},
        appList:[],
        modularList:[],
        httpMethodList:[],
        rules:{
          appCode: [{required: true, message: '请选择应用标识', tirgger: 'change'}],
          className: [{required: true, message: '请填写控制器类名', tirgger: 'blur'}],
          methodName: [{required: true, message: '请填写控制器中的方法名称', tirgger: 'blur'}],
          modularCode: [{required: true, message: '请选择资源所属模块', tirgger: 'change'}],
          code: [{required: true, message: '请填写资源标识', tirgger: 'blur'}],
          name: [{required: true, message: '请填写资源名称', tirgger: 'blur'}],
          url: [{required: true, message: '请填写资源的请求路径', tirgger: 'blur'}],
          httpMethod: [{required: true, message: '请选择http请求方法', tirgger: 'change'}],
          menuFlag: [{required: true, type:'boolean', message: '请选择是否是菜单', tirgger: 'change'}],
          requiredLogin: [{required: true, type:'boolean', message: '请选择是否需要登录', tirgger: 'change'}],
          requiredPermission: [{required: true, type:'boolean', message: '请选择是否需要鉴权', tirgger: 'change'}],
          createTime: [{required: true, message: '请选择资源添加日期', tirgger: 'change'}],
          ipAddress: [{required: true, message: '请填写资源初始化服务器的ip地址', tirgger: 'blur'}],
        },
      }
    },
    async created(){
      this.formLoading = true
      let http = new ResourceMaintainIndexApi()
      //获取应用标识下拉框数据源
      let appList = await http.getAppSelect({})
      this.appList = appList.data
      let httpMethodList = await http.getHttpRequestSelect({})
      this.httpMethodList = httpMethodList.data
      let modularList = await http.getResModuleSelect({})
      this.modularList = modularList.data
      if(this.url){
        this.title = '修改资源'
        let resourceData = await http.getDetail({cacheKey: this.url})
        this.resourceData = resourceData.data
        this.formLoading = false
      }else{
        this.formLoading = false
      }
    },
    methods:{
      //资源模块选择时
      modularCodeChange(){
        if(this.resourceData.modularCode){
          this.modularList.forEach(item => {
            if(item.modularCode == this.resourceData.modularCode){
              this.resourceData.modularName = item.modularName
            }
          })
        }else {
          this.resourceData.modularName = ''
        }
      },
      //保存
      saveResource(){
        this.$refs['resourceData'].validate((valid, object) => {
          if (valid) {
            const loading = this.$loading({
              lock: true,
              text: '请耐心等待.....',
              spinner: 'el-icon-loading',
              background: 'rgba(0, 0, 0, 0.7)'
            })
            let http = new ResourceMaintainIndexApi()
            if(this.url){
              http.editResCache(this.resourceData).then(res => {
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
              http.addResCache(this.resourceData).then(res => {
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
