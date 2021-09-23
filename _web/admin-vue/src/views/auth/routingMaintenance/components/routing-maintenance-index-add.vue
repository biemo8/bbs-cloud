<template>
  <div>
    <el-dialog @close="closeDialog" :visible.sync="isShowDialog"
               :width="width" center :close-on-click-modal="false">
      <h3 slot="title">新增路由</h3>
      <el-form :model="addEditForm" ref="addEditForm" label-width="100px"
               :rules="rules">

        <el-card>
          <el-row>
            <el-col :span="24">
              <el-form-item label="路由id：" prop="routeId" id="routeId">
                <el-input v-model="addEditForm.routeId" clearable></el-input>
              </el-form-item>
            </el-col>
          </el-row>
          <el-row>
            <el-col :span="24">
              <el-form-item label="路由信息：" prop="routeInfo" id="routeInfo">
                <el-input type='textarea' v-model="addEditForm.routeInfo" :autosize="{minRows: 10}"></el-input>
              </el-form-item>
            </el-col>
          </el-row>
        </el-card>

      </el-form>
      <el-row slot="footer">
        <el-button type="primary" @click="submitAddEdit('addEditForm')">确定</el-button>
        <el-button @click="closeDialog">取消</el-button>
      </el-row>
    </el-dialog>

  </div>
</template>

<script>
  import {mapGetters} from "vuex";
  import RoutingMaintenanceIndex from '@/api/auth/routingMaintenance/RoutingMaintenanceIndexApi'

  export default {
    name: "routing-maintenance-index-add",
    components: {},

    computed: {
      ...mapGetters([]),
    },

    data() {
      return {
        rules: {
          routeId: [{required: true, message: "请填写路由id", tigger: "blur"}],
          routeInfo: [{required: true, message: "请填写路由信息", tigger: "blur"}],
        },
        isShowDialog: true,
        addEditForm: {},
      };
    },

    props: {
      width: {
        type: String,
        default: "60vw"
      }, // 弹窗宽度
    },

    created() {

    },

    methods: {

      submitAddEdit(addEditForm) {
        this.$refs[addEditForm].validate((valid, object) => {
          if (valid) {
            const loading = this.$loading({
              lock: true,
              text: "请耐心等待.....",
              spinner: "el-icon-loading",
              background: "rgba(0, 0, 0, 0.7)"
            });
            let data = {
              routeId: this.addEditForm.routeId,
              routeInfo: this.addEditForm.routeInfo,
            }
            let routingMaintenanceIndex = new RoutingMaintenanceIndex();
            routingMaintenanceIndex.addRoute(data).then(res => {
              if (res.code == 200) {
                loading.close();
                this.$message({message: '新增成功', type: "success"});
                this.closeDialog();
              }
            }).catch(res => {
              loading.close();
            });
          }
        })
      },

      //关闭弹窗
      closeDialog() {
        this.$emit("close");
      },

    }
  };
</script>

<style scoped lang="scss">

</style>
