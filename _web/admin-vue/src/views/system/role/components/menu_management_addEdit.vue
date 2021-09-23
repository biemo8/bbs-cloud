<template>
  <div>
    <el-dialog :title="title" :width="width"
               :visible.sync="isShowVisible"
               center :close-on-click-modal="false"
               :before-close="() => $emit('close')">
      <el-card>
        <el-form label-width="100px" :model="menuData" ref="menuData" :rules="rules" v-loading="formLoading">
          <el-row>
            <el-col :span="12">
              <el-form-item label="菜单名称:" prop="name" id="name">
                <el-input v-model="menuData.name"></el-input>
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item label="菜单编码:">
                <el-input v-model="menuData.code"></el-input>
              </el-form-item>
            </el-col>
          </el-row>
          <el-row>
            <el-col :span="12">
              <el-form-item label="应用名称:">
                <el-select v-model="menuData.appId" filterable @change="appIdChange" placeholder="请选择">
                  <el-option
                    v-for="item in appList"
                    :key="item.appId"
                    :label="item.appName"
                    :value="item.appId">
                  </el-option>
                </el-select>
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item label="图标:">
                <el-input v-model="menuData.icon"></el-input>
              </el-form-item>
            </el-col>
          </el-row>
          <el-row>
            <el-col :span="12">
              <el-form-item label="父菜单:">
                <el-select v-model="menuData.pid" :disabled="!menuData.appId" filterable placeholder="请选择">
                  <el-option
                    v-for="item in parentMenuList"
                    :key="item.id"
                    :label="item.name"
                    :value="item.id">
                  </el-option>
                </el-select>
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item label="资源:">
                <el-select v-model="menuData.resCode" :disabled="!menuData.appId" filterable placeholder="请选择">
                  <el-option
                    v-for="item in resourceList"
                    :key="item.resCode"
                    :label="item.name"
                    :value="item.resCode">
                  </el-option>
                </el-select>
              </el-form-item>
            </el-col>
          </el-row>
          <el-row>
            <el-col :span="12">
              <el-form-item label="是否隐藏:">
                <el-select v-model="menuData.hiddenFlag" filterable placeholder="请选择">
                  <el-option
                    v-for="item in hiddenList"
                    :key="item.value"
                    :label="item.label"
                    :value="item.value">
                  </el-option>
                </el-select>
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item label="状态:">
                <el-radio-group v-model="menuData.status">
                  <el-radio :label="1">启用</el-radio>
                  <el-radio :label="2">禁用</el-radio>
                </el-radio-group>
              </el-form-item>
            </el-col>
          </el-row>
          <el-form-item label="提示:">
            <el-input v-model="menuData.menuTips"></el-input>
          </el-form-item>
          <el-form-item label="请求地址:">
            <el-input v-model="menuData.url"></el-input>
          </el-form-item>
          <el-form-item label="组件地址:">
            <el-input v-model="menuData.component"></el-input>
          </el-form-item>
          <el-form-item label="排序:">
            <el-input-number v-model="menuData.orderNo"></el-input-number>
          </el-form-item>
        </el-form>
      </el-card>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="saveMenu">保存</el-button>
        <el-button @click="$emit('close')">取消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
  import MenuManagementApi from "@/api/system/role/MenuManagementApi";
  export default {
    name: "menu_management_addEdit",
    props:{
      width:{
        type:String,
        default:'60vw'
      },
      menuId:{
        type:String,
      }, //菜单id
    },
    data(){
      return{
        title:'新增菜单',
        isShowVisible:true,
        formLoading:false, //formLoading动画
        menuData:{
          pid:'',
          resCode:'',
        },
        rules:{
          name: [{required: true, message: '请填写菜单名称', tirgger: 'blur'}],
        },
        appList:[], //应用名称数据源
        parentMenuList:[], //父菜单数据源
        resourceList:[], //资源数据源
        hiddenList:[
          {label:'是', value:'Y'},
          {label:'否', value:'N'},
        ], //是否隐藏
      }
    },
    created(){
      this.initData()
    },
    methods:{
      //基础数据渲染
      async initData(){
        this.formLoading = true
        let http = new MenuManagementApi()
        //获取应用名称下拉框数据源
        let appList = await http.getAppSelect({})
        this.appList = appList.data
        if(this.menuId){
          this.title = '编辑菜单'
          let menuData = await http.getMenuDetail({menuId:this.menuId})
          this.menuData = menuData.data
          this.menuData.pid = String(menuData.data.pid)
          if(this.menuData.appId){
            this.getMenuOrResourceList(this.menuData.appId)
          }
        }
        this.formLoading = false
      },
      //选择应用名称变化时
      appIdChange(appId){
        this.menuData.pid = ''
        this.menuData.resCode = ''
        if(appId){
          this.getMenuOrResourceList(appId)
        }
      },
      //获取父菜单数据源和资源数据源
      async getMenuOrResourceList(appId){
        let http = new MenuManagementApi()
        let parentMenuList = await http.getSelectMenuTreeList({appId})
        this.parentMenuList = parentMenuList.data
        let resourceList = await http.getResourceSelectList({appId})
        this.resourceList = resourceList.data
      },
      //保存
      saveMenu(){
        this.$refs['menuData'].validate((valid, object) => {
          if (valid) {
            const loading = this.$loading({
              lock: true,
              text: '请耐心等待.....',
              spinner: 'el-icon-loading',
              background: 'rgba(0, 0, 0, 0.7)'
            })
            let http = new MenuManagementApi()
            //若有menuId证明时编辑没有则时新增
            if(this.menuId){
              http.update(this.menuData).then(res => {
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
              http.add(this.menuData).then(res => {
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
