<template>
  <el-dialog :title="title" :width="width"
             :visible.sync="isShowVisible"
             center :close-on-click-modal="false"
             :before-close="() => $emit('close')">
    <el-card v-loading="isLoading">
      <el-form label-width="105px">
        <el-row>
          <el-col :span="24">
            <el-form-item label="职务名称:" prop="dutyName" id="dutyName">
              <span>{{dutyData.dutyName}}</span>
            </el-form-item>
          </el-col>
        </el-row>
        <el-form-item label="职务状态:">
          <span v-if="dutyData.status === 1">启用</span>
          <span v-if="dutyData.status === 2">禁用</span>
        </el-form-item>
        <el-form-item label="职务排序:">
          <span>{{dutyData.orderNo}}</span>
        </el-form-item>
        <el-form-item label="职务描述:">
          <span>{{dutyData.description}}</span>
        </el-form-item>
      </el-form>
    </el-card>
    <div slot="footer" class="dialog-footer">
      <el-button @click="$emit('close')">关闭</el-button>
    </div>
  </el-dialog>
</template>

<script>
  import DutyManageApi from '@/api/system/orgBuild/DutyManageApi'
  export default {
    name: 'duty_detail',
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
        title:'职务详情',
        isShowVisible:true,
        isLoading:false,
        dutyData:{},
      }
    },
    created(){
      this.isLoading = true
      this.title = '编辑职务'
      let http = new DutyManageApi()
      http.detail({dutyId: this.dutyId}).then(res => {
        this.dutyData = res.data
        this.isLoading = false
      })
    },
  }
</script>

<style scoped>

</style>
