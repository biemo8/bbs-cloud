<template>
  <div>
    <el-dialog :title="title" :width="width"
               :visible.sync="isShowVisible"
               center :close-on-click-modal="false"
               :before-close="() => $emit('close')">
      <el-card>
        <div class="text_box">
          <span>基本信息</span>
        </div>
        <div class="text_box_line"></div>
        <el-form label-width="100px" :model="buttonData" ref="buttonData" :rules="rules" v-loading="formLoading">
          <el-form-item label="按钮名称:" prop="buttonName" id="buttonName">
            <el-input v-model="buttonData.buttonName"></el-input>
          </el-form-item>
          <el-row>
            <el-col :span="12">
              <el-form-item label="按钮编码:" prop="buttonCode" id="buttonCode">
                <el-input v-model="buttonData.buttonCode" maxlength="64"></el-input>
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item label="关联资源:" prop="resCodeArr" id="resCodeArr">
                <el-cascader
                  style="width: 100%;"
                  v-model="buttonData.resCodeArr"
                  :options="resList"
                  filterable
                  :props="props"></el-cascader>
              </el-form-item>
            </el-col>
          </el-row>
          <el-form-item label="备注:">
            <el-input v-model="buttonData.description"></el-input>
          </el-form-item>
        </el-form>
      </el-card>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="saveButton">保存</el-button>
        <el-button @click="$emit('close')">取消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
  import ButtonMaintainApi from "@/api/system/role/ButtonMaintainApi";
  export default {
    name: "button_maintain_addEdit",
    props:{
      width:{
        type:String,
        default:'60vw'
      },
      buttonId:{
        type:String,
      },
      appId:{
        type:String,
      },
      menuId:{
        type:String,
      },
    },
    data(){
      return{
        title:'新增按钮权限',
        isShowVisible:true,
        formLoading:false, //formLoading动画
        buttonData:{
          resCode:'',
          resCodeStr:'',
          resCodeArr:[],
        },
        rules:{
          buttonName: [{required: true, message: '请填写按钮名称', tirgger: 'blur'}],
          buttonCode: [{required: true, message: '请填写按钮编码', tirgger: 'blur'}],
          resCodeArr: [{required: true, type:'array', message: '请选择关联资源', tirgger: 'change'}],
        },
        resList:[],
        props:{
          value:'resCode',
          label:'resName',
          children:'children',
        },
      }
    },
    created(){
      this.formLoading = true
      let http = new ButtonMaintainApi()
      http.getResources({appId:this.appId}).then(res => {
        this.resList = res.data
        if(this.buttonId){
          http.getDetail({buttonId:this.buttonId}).then(res => {
            this.buttonData = res.data
            this.buttonData.resCodeArr = res.data.resCodeStr.split(',')
            this.formLoading = false
          })
        }else {
          this.formLoading = false
        }
      })
    },
    methods:{
      saveButton(){
        this.$refs['buttonData'].validate((valid, object) => {
          if (valid) {
            const loading = this.$loading({
              lock: true,
              text: '请耐心等待.....',
              spinner: 'el-icon-loading',
              background: 'rgba(0, 0, 0, 0.7)'
            })
            let http = new ButtonMaintainApi()
            this.buttonData.resCode = this.buttonData.resCodeArr[this.buttonData.resCodeArr.length-1]
            this.buttonData.resCodeStr = this.buttonData.resCodeArr.join(',')
            this.buttonData.menuId = this.menuId
            //若有buttonId证明时编辑没有则时新增
            if(this.buttonId){
              http.update(this.buttonData).then(res => {
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
              http.add(this.buttonData).then(res => {
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
