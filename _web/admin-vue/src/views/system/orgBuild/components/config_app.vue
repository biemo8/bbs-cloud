<template>
  <el-dialog :title="title" :width="width"
             :visible.sync="isShowVisible"
             center :close-on-click-modal="false"
             :before-close="() => $emit('close')">
    <el-card>
      <div class="text_box">
        <span>应用列表</span>
      </div>
      <div class="text_box_line"></div>
      <el-tree
        :props="defaultProps"
        :data="dataTree"
        default-expand-all
        node-key="appId"
        :default-checked-keys="defaultCheckedList"
        show-checkbox
        @check-change="handleCheckChange">
      </el-tree>
    </el-card>
    <div slot="footer" class="dialog-footer">
      <el-button type="primary" @click="save">保存</el-button>
      <el-button @click="$emit('close')">取消</el-button>
    </div>
  </el-dialog>
</template>

<script>
  import CompanyManageApi from '@/api/system/orgBuild/CompanyManageApi'
  export default {
    name: 'config_app',
    props:{
      width:{
        type:String,
        default:'60vw'
      },
      companyId:{
        type:String,
      },
    },
    data(){
      return{
        title:'配置应用',
        isShowVisible:true,
        dataTree:[],
        defaultProps: {
          label: 'appName'
        },
        defaultCheckedList:[], //已经分配的角色
      }
    },
    created(){
      let http = new CompanyManageApi()
      http.getAppList({companyId:this.companyId}).then(res => {
        this.dataTree = res.data
        this.dataTree.forEach(item => {
          if(item.configFlag === 'Y'){
            this.defaultCheckedList.push(item.appId)
          }
        })
      })
    },
    methods:{
      //选择节点时
      handleCheckChange(data, checked) {
        if(checked){
          //若是选择则往已经选择的应用里加一条
          this.defaultCheckedList.push(data.appId)
        }else{
          //若是取消选择则在已经选择的应用里删除这一条
          this.defaultCheckedList.splice(this.defaultCheckedList.indexOf(data.appId), 1)
        }
      },
      save(){
        if(this.defaultCheckedList.length === 0){
          this.$message.warning('请选择需要配置的应用!')
        }else{
          const loading = this.$loading({
            lock: true,
            text: '请耐心等待.....',
            spinner: 'el-icon-loading',
            background: 'rgba(0, 0, 0, 0.7)'
          })
          let http = new CompanyManageApi()
          let params = []
          this.defaultCheckedList.forEach(item => {
            params.push({companyId:this.companyId, appId:item})
          })
          http.saveCompApp({params}).then(res => {
            this.$message.success('保存成功!')
            this.$emit('close')
            loading.close()
          }).catch(e => {
            loading.close()
          })
        }
      },
    },
  }
</script>

<style scoped>

</style>
