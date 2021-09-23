<template>
  <div>
    <!--搜索条件-->
    <el-card>
      <el-form :inline="true" :model="searchForm">
        <el-form-item label="路由id：" class="mgb-0">
          <el-input v-model="searchForm.routeId" clearable></el-input>
        </el-form-item>
        <el-form-item class="mgb-0">
          <el-tooltip class="item" effect="dark" content="搜索" placement="bottom">
            <el-button type="primary" @click="searchPageList" class="search_button"
                       icon="el-icon-search"></el-button>
          </el-tooltip>
          <el-tooltip class="item" effect="dark" content="清空搜索条件" placement="bottom">
            <el-button icon="el-icon-refresh" type="success" @click="clearSearchItem"></el-button>
          </el-tooltip>
          <el-button type="primary" @click="openFilterDg">查看所有过滤器</el-button>
          <el-button type="primary" @click="openFilterPlantDg">查看所有过滤器工厂</el-button>
        </el-form-item>
      </el-form>
    </el-card>

    <!--列表-->
    <el-card style="margin-top: 10px">
      <el-row style="margin:0 0 16px 0">
        <el-button type="primary" @click="openAddDg">新增</el-button>
        <el-button type="warning" @click="clearCache">刷新缓存</el-button>
      </el-row>
      <el-table :data="listData" :border='true' ref='table' v-loading="listLoading">
        <el-table-column type="index" label="序号" width="50" align="center"></el-table-column>
        <el-table-column label="路由ID" prop="routeId" align="center" show-overflow-tooltip></el-table-column>
        <el-table-column label="排序号" prop="order" align="center" show-overflow-tooltip></el-table-column>
        <el-table-column label="操作" align="center" width="120">
          <template slot-scope="scope">
            <el-button type="text" @click="openDetailDg(scope.row)">详情</el-button>
            <el-button type="text" @click="deleteList(scope.row)" style="color: red">删除</el-button>
          </template>
        </el-table-column>
      </el-table>
    </el-card>

    <!--新增编辑弹窗-->
    <routing-maintenance-index-add v-if="isShowAddDg"
                                   @close="closeAddDg"></routing-maintenance-index-add>

    <!--详情弹窗-->
    <routing-maintenance-index-detail v-if="isShowDetailDg"
                                      @close="closeDetailDg"
                                      :formValue="formValue"></routing-maintenance-index-detail>
    <!--过滤器弹窗-->
    <filter-detail v-if="isShowFilterDetailDg" @close="closeFilterDg"></filter-detail>

    <!--过滤器工厂弹窗-->
    <filter-Plant-detail v-if="isShowFilterPlantDetailDg" @close="closeFilterPlantDg"></filter-Plant-detail>

  </div>
</template>

<script>
  import RoutingMaintenanceIndex from '@/api/auth/routingMaintenance/RoutingMaintenanceIndexApi'

  export default {
    name: "routing_maintenance_index",
    components: {
      routingMaintenanceIndexAdd: resolve => require(['./components/routing-maintenance-index-add'], resolve),
      routingMaintenanceIndexDetail: resolve => require(['./components/routing-maintenance-index-detail'], resolve),
      filterDetail: resolve => require(['./components/filter-detail'], resolve),
      filterPlantDetail: resolve => require(['./components/filter-plant-detail'], resolve),
    },

    data() {
      return {
        searchForm: {
          routeId: '', //路由ID
        },
        listData: [],
        listLoading: false,
        formValue: {},
        isShowAddDg: false,
        isShowDetailDg: false,
        isShowFilterDetailDg: false,
        isShowFilterPlantDetailDg: false,
      }
    },

    created() {
      this.searchPageList()
    },

    methods: {

      //搜索列表
      searchPageList() {
        this.listLoading = true
        let data = {
          routeId: this.searchForm.routeId
        }
        let routingMaintenanceIndex = new RoutingMaintenanceIndex();
        routingMaintenanceIndex.gatewayRoutes(data).then(res => {
          if (res.code == 200) {
            this.listData = res.data
            this.listLoading = false
          }
        })
      },


      //清空搜索条件
      clearSearchItem() {
        this.searchForm.routeId = ''
      },

      //打开新增弹窗
      openAddDg() {
        this.addEditDgType = 'add'
        this.isShowAddDg = true
      },

      //关闭新增弹窗
      closeAddDg() {
        this.searchPageList();
        this.isShowAddDg = false
      },

      //打开详情弹窗
      openDetailDg(row) {
        this.formValue = row
        this.isShowDetailDg = true
      },

      //关闭详情弹窗
      closeDetailDg() {
        this.isShowDetailDg = false
      },

      //删除
      deleteList(row) {
        this.$confirm('是否确认删除?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          let routingMaintenanceIndex = new RoutingMaintenanceIndex();
          let data = {
            routeId: row.routeId
          }
          routingMaintenanceIndex.deleteRoute(data).then(res => {
            if (res.code == 200) {
              this.searchPageList();
              this.$message({
                message: '删除成功',
                type: 'success'
              });
            }
          })
        }).catch(() => {
          this.$notify({
            type: 'info',
            message: '已取消删除'
          });
        })
      },

      //打开过滤器弹窗
      openFilterDg() {
        this.isShowFilterDetailDg = true
      },

      //关闭过滤器弹窗
      closeFilterDg() {
        this.isShowFilterDetailDg = false
      },

      //打开过滤器工厂弹窗
      openFilterPlantDg() {
        this.isShowFilterPlantDetailDg = true
      },

      //关闭过滤器工厂弹窗
      closeFilterPlantDg() {
        this.isShowFilterPlantDetailDg = false
      },

      //刷新缓存
      clearCache() {
        let routingMaintenanceIndex = new RoutingMaintenanceIndex();
        routingMaintenanceIndex.refreshRoute({}).then(res => {
          if (res.code == 200) {
            this.$message.success('刷新成功')
          }
        })
      },

    }

  }
</script>

<style scoped lang="scss">

</style>
