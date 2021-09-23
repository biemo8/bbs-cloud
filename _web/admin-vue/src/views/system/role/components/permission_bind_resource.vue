<template>
  <div>
    <el-dialog :title="title" :width="width"
               :visible.sync="isShowVisible" append-to-body
               center :close-on-click-modal="false"
               :before-close="() => $emit('close')">
      <el-card>
        <div style="font-size: 16px">
          <span style="color: green">绿色</span>是菜单资源，<span style="color: red">红色</span>是按钮资源，<span style="color: #409eff">蓝色</span>是普通接口资源。
        </div>
        <el-table border :data="subscriptList">
          <el-table-column label="模块" align="right" width="210">
            <template slot-scope="scope">
              <el-checkbox :indeterminate="scope.row.isIndeterminate"
                           v-model="scope.row.checkAll"
                           @change="handleCheckAllChange(scope.row)">{{scope.row.label}}</el-checkbox>
            </template>
          </el-table-column>
          <el-table-column label="方法" align="left">
            <template slot-scope="scope">
              <el-checkbox-group v-model="scope.row.checked" @change="handleCheckedCitiesChange(scope.row)">
                <el-checkbox v-for="item in scope.row.value"
                             :class="{'isButton' : item.isButton,'isMenu' : item.isMenu}"
                             :label="item.resourceId"
                             :key="item.resourceId">{{item.name}}</el-checkbox>
              </el-checkbox-group>
            </template>
          </el-table-column>
        </el-table>
      </el-card>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="saveBindResource">确认绑定</el-button>
        <el-button @click="$emit('close')">取消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
  import PermissionManageApi from "@/api/system/role/PermissionManageApi";
  export default {
    name: "permission_bind_resource",
    props:{
      width:{
        type:String,
        default:'60vw'
      },
      appId:{
        type:String,
      },
      permissionId:{
        type:String,
      },
    },
    data(){
      return{
        title:'绑定资源',
        isShowVisible:true,
        formLoading:false, //formLoading动画
        subscriptList:[],
        checkAll:false,
        checkedCities:[],
      }
    },
    created(){
      this.initData()
    },
    methods:{
      //初始化资源
      initData(){
        let http = new PermissionManageApi
        http.getResourceList4Permission({permissionId:this.permissionId, appId:this.appId}).then(res => {
          for (let item in res.data) {
            let isIndeterminate = true //控制是否全选
            let checked = [] //保存已经选择的资源
            res.data[item].forEach(t => {
              if(t.isBindResource){
                checked.push(t.resourceId)
              }
            })
            //判断是否全部绑定资源或则全部没有绑定资源
            if(checked.length === res.data[item].length || checked.length === 0){
              isIndeterminate = false
            }
            let obj = {
              label:item,
              value:res.data[item],
              isIndeterminate,
              checkAll:checked.length === res.data[item].length,
              checked,
            }
            this.subscriptList.push(obj)
          }
        })
      },
      //模块选择全选时的回调
      handleCheckAllChange(val){
        let checked = []
        if(val.checkAll){
          val.value.forEach(item => {
            checked.push(item.resourceId)
          })
        }else{
          val.value.forEach(item => {
          })
        }
        val.checked = checked
        val.isIndeterminate = false
      },
      //方法选择时的回调
      handleCheckedCitiesChange(val){
        let checkedCount = val.checked.length
        val.checkAll = checkedCount === val.value.length
        val.isIndeterminate = checkedCount > 0 && checkedCount < val.value.length
      },
      //保存
      saveBindResource(){
        let resourceIds = []
        this.subscriptList.forEach(item => {
          resourceIds = resourceIds.concat(item.checked)
        })
        const loading = this.$loading({
          lock: true,
          text: '请耐心等待.....',
          spinner: 'el-icon-loading',
          background: 'rgba(0, 0, 0, 0.7)'
        })
        let http = new PermissionManageApi
        http.permissionBindResource({permissionId: this.permissionId, resourceIds}).then(res => {
          this.$emit('close')
          this.$message.success('保存成功!')
          loading.close()
        }).catch(e => {
          loading.close()
        })
      },
    },
  }
</script>

<style scoped lang="scss">
  .isButton{
     .el-checkbox__label{
      color: red;
    }
  }
  .isMenu{
    .el-checkbox__label{
      color: green;
    }
  }
</style>
