<template>
  <el-dialog :title="title" :width="width"
             :visible.sync="isShowVisible"
             center :close-on-click-modal="false"
             :before-close="() => $emit('close')">
    <el-card v-loading="isLoading">
      <el-form label-width="105px" :model="dutyData" ref="dutyData" :rules="rules">
        <el-row>
          <el-col :span="24">
            <el-form-item label="职务名称:" prop="dutyName" id="dutyName">
              <el-input v-model="dutyData.dutyName"></el-input>
            </el-form-item>
          </el-col>
        </el-row>
        <el-form-item label="职务状态:">
          <el-radio-group v-model="dutyData.status">
            <el-radio :label="1">启用</el-radio>
            <el-radio :label="2">禁用</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="职务排序:">
          <el-input-number v-model.number="dutyData.orderNo"></el-input-number>
        </el-form-item>
        <el-form-item label="职务描述:">
          <el-input v-model="dutyData.description"></el-input>
        </el-form-item>
      </el-form>
    </el-card>
    <div slot="footer" class="dialog-footer">
      <el-button type="primary" @click="saveDuty">保存</el-button>
      <el-button @click="$emit('close')">取消</el-button>
    </div>
  </el-dialog>
</template>

<script>
  import DutyManageApi from '@/api/system/orgBuild/DutyManageApi'
  export default {
    name: 'duty_addEdit',
    props:{
      width:{
        type:String,
        default:'60vw'
      },
      dutyId:{
        type:String,
      },
    },
    data(){
      return{
        title:'新增职务',
        isShowVisible:true,
        isLoading:false,
        rules:{
          dutyName: [{required: true, message: '请填写职务名称', tirgger: 'blur'}],
        },
        dutyData:{},
      }
    },
    created(){
      if(this.dutyId){
        this.isLoading = true
        this.title = '编辑职务'
        let http = new DutyManageApi()
        http.detail({dutyId: this.dutyId}).then(res => {
          this.dutyData = res.data
          this.isLoading = false
        })
      }
    },
    methods:{
      saveDuty(){
        this.$refs['dutyData'].validate((valid, object) => {
          if (valid) {
            const loading = this.$loading({
              lock: true,
              text: '请耐心等待.....',
              spinner: 'el-icon-loading',
              background: 'rgba(0, 0, 0, 0.7)'
            })
            let http = new DutyManageApi()
            //若有dutyId证明时编辑没有则时新增
            if(this.dutyId){
              http.update(this.dutyData).then(res => {
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
              http.add(this.dutyData).then(res => {
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
