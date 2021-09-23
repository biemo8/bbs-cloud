<template>
  <div>
    <el-dialog @close="closeDialog" :visible.sync="isShowDialog"
               :width="width" center :close-on-click-modal="false">
      <h3 slot="title">过滤器工厂信息</h3>

      <el-card v-loading="detailLoading">
        <el-row>
          <el-col :span="24">
            <pre>{{detailForm}}</pre>
          </el-col>
        </el-row>
      </el-card>

      <el-row slot="footer">
        <el-button @click="closeDialog">关闭</el-button>
      </el-row>
    </el-dialog>
  </div>
</template>

<script>
  import {mapGetters} from "vuex";
  import RoutingMaintenanceIndex from '@/api/auth/routingMaintenance/RoutingMaintenanceIndexApi'

  export default {
    name: "filter-plant-detail",
    components: {},

    computed: {
      ...mapGetters([])
    },

    props: {
      width: {
        type: String,
        default: "60vw"
      }, // 弹窗宽度
    },

    data() {
      return {
        isShowDialog: true,
        detailForm: [],
        detailLoading: false,
      }
    },

    created() {
      this.opendetailDg()
    },

    methods: {

      //打开详情弹窗
      opendetailDg() {
        this.detailLoading = true;
        let routingMaintenanceIndex = new RoutingMaintenanceIndex();
        routingMaintenanceIndex.routefilters({}).then(res => {
          if (res.code == 200) {
            this.detailForm = res.data;
            this.detailLoading = false;
          }
        });
      },

      //关闭弹窗
      closeDialog() {
        this.$emit("close");
      }

    }

  }
</script>

<style scoped lang="scss">

</style>
