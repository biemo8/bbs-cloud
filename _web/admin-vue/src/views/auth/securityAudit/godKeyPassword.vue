<template>
    <div>
      <el-card>
        <div class="text_box">
          <span>基本信息</span>
        </div>
        <div class="text_box_line"></div>
        <el-form label-width="100px">
          <el-form-item label="万能密码:">
            <el-row>
              <el-col :span="12">
                <div>
                  <span v-show="!isShowPassword">******</span>
                  <span v-show="isShowPassword">{{godKeyValue}}</span>
                </div>
                <div>
                  {{expireTime}}s后失效
                </div>
              </el-col>
              <el-col :span="12">
                <el-button :type="isShowPassword ? '' : 'primary'"
                           :disabled="!isShowPassword && !godKeyStatus"
                           @click="showPassword">{{isShowPassword ? '隐藏' : '显示'}}</el-button>
              </el-col>
            </el-row>
          </el-form-item>
          <el-form-item label="状态:">
            <el-switch
              @change="godKeyStatusChange"
              v-model="godKeyStatus"
              active-color="#13ce66"
              inactive-color="#ff4949">
            </el-switch>
          </el-form-item>
        </el-form>
      </el-card>
    </div>
</template>

<script>
  import GodKeyPasswordApi from '@/api/auth/securityAudit/GodKeyPasswordApi'
  export default {
    name: 'godKeyPassword',
    data(){
      return{
        isShowPassword:false,
        godKeyValue:'',
        expireTime:0,
        godKeyStatus:true,
      }
    },
    created(){
      this.getGodKey()
    },
    methods:{
      showPassword(){
        this.isShowPassword = !this.isShowPassword
      },
      getGodKey(){
        const loading = this.$loading({
          lock: true,
          text: '获取密码中，请耐心等待.....',
          spinner: 'el-icon-loading',
          background: 'rgba(0, 0, 0, 0.7)'
        })
        let http = new GodKeyPasswordApi()
        http.getGodKey({}).then(res => {
          loading.close()
          this.godKeyValue = res.data.godKeyValue
          this.godKeyStatus = res.data.godKeyStatus === 'enable' ? true : false
          this.expireTime = res.data.expireTime.toFixed(0)
          let timer = setInterval(() => {
            if(this.expireTime > 0){
              this.expireTime -= 1
            }else {
              window.clearInterval(timer)
              this.getGodKey()
            }
          }, 1000)
        }).catch(e => {
          loading.close()
        })
      },
      godKeyStatusChange(val){
        const loading = this.$loading({
          lock: true,
          text: '变更中，请耐心等待.....',
          spinner: 'el-icon-loading',
          background: 'rgba(0, 0, 0, 0.7)'
        })
        let http = new GodKeyPasswordApi()
        http.checkedStatus({}).then(res => {
          loading.close()
          this.$message.success('变更成功!')
        }).catch(e => {
          loading.close()
          this.godKeyStatus = !this.godKeyStatus
        })
      },
    }
  }
</script>

<style scoped>

</style>
