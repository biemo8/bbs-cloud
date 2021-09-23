<template>
  <div class="app-container">
  <el-card>
      <el-form :inline="true" label-width="100px">
        <el-form-item class="mgb-0" label="类型名称:">
          <el-input v-model="s_dictTypeName" placeholder="类型名称"></el-input>
        </el-form-item>
        <el-form-item class="mgb-0" label="类型编码:">
          <el-input v-model="s_dictTypeCode" placeholder="类型编码"></el-input>
        </el-form-item>
        <el-form-item class="mgb-0" label="类型描述:">
          <el-input v-model="s_dictTypeDesc" placeholder="类型描述"></el-input>
        </el-form-item>
        <el-form-item class="mgb-0">
          <el-button type="primary" icon="el-icon-search" @click="findTypePage('init')"></el-button>
          <el-button type="success" icon="el-icon-refresh" @click="clearSearch"></el-button>
        </el-form-item>
      </el-form>
    </el-card>
    <el-card style="margin-top: 10px">
      <el-button style="margin-bottom: 10px" type="primary" @click="showDictTypeDlg()">新增</el-button>
      <el-table :data="list" border class="resource-table" ref='table'>
        <el-table-column label="字典分类" prop="dictTypeClass" align="center" show-overflow-tooltip>
          <template slot-scope="scope">
            {{formatTypeClass(scope.row.dictTypeClass)}}
          </template>
        </el-table-column>
        <el-table-column label="字典类型编码" prop="dictTypeCode" align="center" show-overflow-tooltip></el-table-column>
        <el-table-column label="类型名称" prop="dictTypeName" align="center" show-overflow-tooltip></el-table-column>
        <el-table-column label="字典类型描述" prop="dictTypeDesc" align="center" show-overflow-tooltip></el-table-column>
        <el-table-column label="排序" prop="orderNo" align="center"></el-table-column>
        <el-table-column label="状态" width="100" align="center">
          <template slot-scope="scope">
            {{scope.row.status == 1 ? '启用': '禁用'}}
          </template>
        </el-table-column>
        <el-table-column label="操作" width="280" align="center">
          <template slot-scope="scope">
            <el-button-group>
              <el-button type="text" @click="changeTypeStatus(scope.row)">
                {{scope.row.status=== 1 ? '禁用' : '启用'}}&nbsp;&nbsp;&nbsp;&nbsp;
              </el-button>
              <el-button type="text" @click="showDictTypeDlg(scope.row)">
                编辑&nbsp;&nbsp;&nbsp;&nbsp;
              </el-button>
              <el-button type="text" @click="handleDeleteType(scope.row)">
                删除&nbsp;&nbsp;&nbsp;&nbsp;
              </el-button>
              <el-button type="text" @click="showDictMngDialog(scope.row)">字典管理</el-button>
            </el-button-group>
          </template>
        </el-table-column>
      </el-table>
      <el-pagination
        style="float:right; margin: 10px 0;"
        @size-change="changeSize"
        @current-change="changePage"
        :current-page="currentPage1"
        :page-sizes="[10, 15, 20, 50]"
        :page-size="pageSize1"
        layout="total, sizes, prev, pager, next, jumper"
        prev-text="上一页"
        next-text="下一页"
        :total="total1">
      </el-pagination>
    </el-card>
    <el-dialog :visible.sync="vis" @close="closeTypeDialog('form1')" :close-on-click-modal="false" center
               :title="editType === 'add' ? '添加字典类型' : '编辑字典类型'">
      <el-card>
        <el-form :model="form" :rules="codeTypeRules" label-width="150px" ref="form1">
          <el-form-item label="字典类型名称" prop="dictTypeName" class="fr">
            <el-input v-model="form.dictTypeName"></el-input>
          </el-form-item>
          <el-form-item label="字典类型编码" prop="dictTypeCode" class="fl">
            <el-input v-model="form.dictTypeCode" :placeholder="idFormType"
                      :disabled="editType!='add'"></el-input>
          </el-form-item>
          <el-form-item label="字典类型描述" prop="dictTypeDesc" style="clear: both">
            <el-input v-model="form.dictTypeDesc"></el-input>
          </el-form-item>
          <el-form-item label="字典分类" prop="dictTypeClass" style="clear: both">
            <el-radio-group v-model="form.dictTypeClass">
              <el-radio label="0">业务类</el-radio>
              <el-radio label="1">系统类</el-radio>
              <el-radio label="2">基础类</el-radio>
            </el-radio-group>
          </el-form-item>
          <el-form-item label="字典类型状态" prop="status" style="clear: both">
            <el-radio v-model="form.status" label="1">启用</el-radio>
            <el-radio v-model="form.status" label="2">禁用</el-radio>
          </el-form-item>
          <el-form-item label="排序码" style="clear: both">
            <el-input-number v-model.number="form.orderNo"></el-input-number>
          </el-form-item>
        </el-form>
      </el-card>
      <el-row slot="footer">
        <el-button type="primary" @click="saveType('form1')">确定</el-button>
        <el-button @click="closeTypeDialog('form1')">取消</el-button>
      </el-row>
    </el-dialog>
    <el-dialog :visible.sync="cmvis" :close-on-click-modal="false" width="70%" top="3vh"
               @close="closeDictDialog"
               title="字典管理">
      <el-container>
        <el-aside>
          <el-card class="box-card">
            <el-tree :data="dictTreeData" :props="treeProp"
                     node-key="dictTypeId" ref="trees"
                     default-expand-all :highlight-current="true">
            </el-tree>
          </el-card>
        </el-aside>
        <el-main>
          <el-card>
            <el-row :gutter="20" style="margin-bottom: 20px;">
              <el-col :span="6">
                <el-input placeholder="字典编码" clearable v-model="dictId"></el-input>
              </el-col>
              <el-col :span="6">
                <el-input placeholder="字典名称" clearable v-model="dictName"></el-input>
              </el-col>
              <el-col :span="2">
                <el-button type="primary" @click="findDictPage('init')">搜索</el-button>
              </el-col>
              <el-col :span="2">
                <el-button type="primary" @click="showDictDialog()">新增</el-button>
              </el-col>
            </el-row>
            <el-table :data="cmList" :border='true' ref='table2'>
              <el-table-column type="selection" align="center"></el-table-column>
              <el-table-column label="排序" prop="orderNo" width="50" align="center"></el-table-column>
              <el-table-column label="字典类型" prop="dictTypeName" show-overflow-tooltip align="center"></el-table-column>
              <el-table-column label="字典编码" prop="dictCode" show-overflow-tooltip align="center"></el-table-column>
              <el-table-column label="字典名称" prop="dictName" show-overflow-tooltip align="center"></el-table-column>
              <el-table-column label="字典简称" prop="dictShortName" show-overflow-tooltip align="center"></el-table-column>
              <el-table-column label="简拼" prop="dictShortCode" show-overflow-tooltip align="center"></el-table-column>
              <el-table-column label="上级字典" prop="parentName" show-overflow-tooltip align="center"></el-table-column>
              <el-table-column label="状态" align="center">
                <template slot-scope="scope">
                  {{scope.row.status == 1 ? '启用': '禁用'}}
                </template>
              </el-table-column>
              <el-table-column label="操作" width="200" align="center">
                <template slot-scope="scope">
                  <el-button-group>
                    <el-button type="text" @click="showDictDialog(scope.row)">
                      编辑&nbsp;&nbsp;&nbsp;&nbsp;
                    </el-button>
                    <el-button type="text" @click="changeDictStatus(scope.row)">
                      {{scope.row.status === "2" ? '禁用': '启用'}}&nbsp;&nbsp;&nbsp;&nbsp;
                    </el-button>
                    <el-button type="text" @click="handleDeleteDict(scope.row)">删除</el-button>
                  </el-button-group>
                </template>
              </el-table-column>
            </el-table>
            <el-pagination
              style="margin-top: 10px; text-align: right"
              layout="total,sizes,prev,pager,next,jumper" :page-sizes="[10,20,50,100]"
              :page-size="pageSize2" :total="total2" :current-page="currentPage2"
              @size-change='changeTableSize' @current-change="changeTablePage"></el-pagination>
          </el-card>
        </el-main>
      </el-container>
    </el-dialog>
    <el-dialog :visible.sync="addcvis" :close-on-click-modal="false" :title="isAdd === 'add' ? '添加字典' : '编辑字典'"
               @close="hideDictMngDialog('codeform')" center>
      <el-card>
        <el-form :model="codeForm" :rules="crules" label-width="83px" ref="codeform">
          <el-form-item label="字典类型:" prop="dictTypeName">
            <el-input v-model="codeForm.dictTypeName" :disabled="true"></el-input>
          </el-form-item>
          <el-form-item label="字典编码:" prop="dictCode">
            <el-input v-model="codeForm.dictCode" :disabled="isAdd !== 'add'"></el-input>
          </el-form-item>
          <el-form-item label="字典名称:" prop="dictName">
            <el-input v-model="codeForm.dictName"></el-input>
          </el-form-item>
          <el-form-item label="字典简称:" prop="dictShortName">
            <el-input v-model="codeForm.dictShortName"></el-input>
          </el-form-item>
          <el-form-item label="简拼:" prop="dictShortCode">
            <el-input v-model="codeForm.dictShortCode"></el-input>
          </el-form-item>
          <el-form-item label="上级字典:" prop="parentId">
            <el-select v-model="codeForm.parentId" filterable placeholder="请选择">
              <el-option
                v-for="item in parentIdOptions"
                :key="item.dictId"
                :label="item.dictName"
                :value="item.dictId">
              </el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="字典状态:" prop="status">
            <el-radio v-model="codeForm.status" label="1">启用</el-radio>
            <el-radio v-model="codeForm.status" label="2">禁用</el-radio>
          </el-form-item>
          <el-form-item label="排序:">
            <el-input-number v-model.number="codeForm.orderNo"></el-input-number>
          </el-form-item>
        </el-form>
      </el-card>
      <el-row slot="footer">
        <el-button type="primary" @click="saveDict('codeform')">确认</el-button>
        <el-button @click="hideDictMngDialog('codeform')">取消</el-button>
      </el-row>
    </el-dialog>
  </div>
</template>

<script>
  import DictApi from "@/api/system/dict/type";

  export default {
    name: 'dict_manage',
    data() {
      return {
        dictApi: new DictApi(),
        total1: 0,
        total2: 0,
        pageSize1: 10,
        pageSize2: 10,
        page1: 1,
        page2: 1,
        currentPage1: 1,
        currentPage2: 1,
        list: [],
        idFormType: '',
        s_dictTypeCode: '',
        s_dictTypeDesc: '',
        s_dictTypeName: '',
        s_status: '',
        form: {
          dictTypeId: '',
          dictTypeCode: '',
          dictTypeName: '',
          dictTypeClass: '0',
          dictTypeDesc: '',
          orderNo: '',
          status: '1'
        },
        codeTypeRules: {
          dictTypeCode: [
            {required: true, message: '此项为必填', trigger: 'blur'}
          ],
          dictTypeName: [
            {required: true, message: '此项为必填', trigger: 'blur'}
          ],
          dictTypeClass: [
            {required: true, message: '此项为必填', trigger: 'blur'}
          ],
          status: [
            {required: true, message: '此项为必填', trigger: 'change'}
          ]
        },
        searchQuery: '',
        vis: false,
        editType: 'add',
        cmvis: false,
        dictTreeData: [],
        addcvis: false,
        cmList: [],
        codeForm: {
          dictId: '',
          dictType: '',
          dictTypeCode: '',
          dictTypeName: '',
          dictCode: '',
          dictName: '',
          dictShortName: '',
          dictShortCode: '',
          parentId: '-1',
          status: '1',
          orderNo: ''
        },
        parentIdOptions: [],
        crules: {
          dictId: [
            {required: true, message: '此项为必填', trigger: 'blur'}
          ],
          dictType: [
            {required: true, message: '此项为必填', trigger: 'blur'}
          ],
          dictCode: [
            {required: true, message: '此项为必填', trigger: 'blur'}
          ],
          dictName: [
            {required: true, message: '此项为必填', trigger: 'blur'}
          ],
          status: [
            {required: true, message: '此项为必填', trigger: 'change'}
          ]
        },
        treeProp: {
          label: 'dictName'
        },
        dictTypeId: '',
        dictTypeCode: '',
        dictTypeName: '',
        dictId: '',
        dictName: '',
        isAdd: 'add'
      }
    },
    computed: {},
    methods: {
      changePage(val) {
        this.currentPage1 = val
        this.findTypePage()
      },
      changeSize(val) {
        this.pageSize1 = val
        this.findTypePage()
      },
      changeTablePage(val) {
        this.currentPage2 = val
        this.findDictPage()
      },
      changeTableSize(val) {
        this.pageSize2 = val
        this.findDictPage()
      },
      formatTypeClass(val) {
        if (val == '0') {
          return '业务类'
        } else if (val == '1') {
          return '系统类'
        } else if (val == '2') {
          return '基础类'
        } else {
          return ''
        }
      },
      // 清空数据
      resetData() {
        this.form = {
          dictTypeId: '',
          dictTypeCode: '',
          dictTypeName: '',
          dictTypeClass: '0',
          dictTypeDesc: '',
          dictTypeSort: '',
          status: '1'
        }
        this.codeForm = {
          dictId: '',
          dictType: '',
          dictTypeName: '',
          dictTypeCode: '',
          dictCode: '',
          dictName: '',
          dictShortName: '',
          dictShortCode: '',
          parentId: '-1',
          status: '1',
          dictSort: ''
        }
      },
      //清空搜索
      clearSearch(){
        this.s_dictTypeName = ''
        this.s_dictTypeCode = ''
        this.s_dictTypeDesc = ''
      },
      // 搜索字典类型
      findTypePage(type) {
        this.currentPage1 = type == 'init' ? 1 : this.currentPage1
        this.pageSize1 = type == 'init' ? 10 : this.pageSize1
        const data = {
          dictTypeCode: this.s_dictTypeCode,
          dictTypeName: this.s_dictTypeName,
          dictTypeDesc: this.s_dictTypeDesc,
          status: this.s_status,
          pageNo: this.currentPage1,
          pageSize: this.pageSize1
        }
        this.dictApi.getList(data).then(res => {
          this.list = res.data.records
          this.total1 = res.data.total
        })
      },
      // 搜索字典
      findDictPage(type) {
        this.currentPage2 = type == 'init' ? 1 : this.currentPage2
        this.pageSize2 = type == 'init' ? 10 : this.pageSize2
        const data = {
          dictTypeCode: this.dictTypeCode,
          pageNo: this.currentPage2,
          pageSize: this.pageSize2,
          dictCode: this.dictId,
          dictName: this.dictName
        }
        this.dictApi.findDictPage(data).then(res => {

          this.cmList = res.data.records
          this.total2 = res.data.total
        })
      },

      // 显示添加/编辑窗口
      showDictTypeDlg(row) {
        if (row == undefined) {
          this.editType = 'add'
          this.vis = true
        } else {
          // console.log(row)
          this.editType = 'edit'
          this.form.dictTypeId = row.dictTypeId
          this.form.dictTypeCode = row.dictTypeCode
          this.form.dictTypeName = row.dictTypeName
          this.form.dictTypeClass = row.dictTypeClass.toString()
          this.form.orderNo = row.orderNo
          this.form.dictTypeDesc = row.dictTypeDesc
          this.form.status = row.status.toString()
          this.vis = true
        }
      },
      // 确定新增/修改字典类型
      saveType(form1) {
        this.$refs[form1].validate((valid) => {
          if (valid) {
            let data = {
              dictTypeCode: this.form.dictTypeCode,
              dictTypeId: this.form.dictTypeId
            }
            this.dictApi.checkCodeType(data).then(res => {
              if (res.code == this.HttpConstant.SUCCESS_CODE) {
                if (res.data == true) {
                  if (this.editType == 'add') {
                    this.dictApi.addDictType(this.form).then(res => {
                      if (res.code == this.HttpConstant.SUCCESS_CODE) {
                        this.findTypePage()
                        this.$message(this.HttpConstant.ADD_SUCCESS_MSG)
                        this.vis = false
                      }
                    })
                  } else {
                    this.dictApi.updateDictType(this.form).then(res => {
                      if (res.code == this.HttpConstant.SUCCESS_CODE) {
                        this.findTypePage()
                        this.$message(this.HttpConstant.UPDATE_SUCCESS_MSG)
                        this.vis = false
                      }
                    })
                  }
                } else {
                  this.$message.error('Code重复');
                }
              }
            })
          }
        })
      },

      // 删除字典类型
      handleDeleteType(row) {
        this.$confirm("是否确认删除该数据?", "提示", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }).then(() => {
          this.dictApi.deleteDictType({dictTypeId: row.dictTypeId}).then(res => {
            if (res.code == this.HttpConstant.SUCCESS_CODE) {
              this.$message(this.HttpConstant.DELETE_SUCCESS_MSG);
              this.findTypePage();
            }
          })
        });
      },
      // 更改字典类型状态
      changeTypeStatus(row) {
        var dictStatus = ''
        if (row.status.toString() == '1') {
          dictStatus = '2'
        } else {
          dictStatus = '1'
        }
        this.dictApi.updateTypeStatus({dictTypeId: row.dictTypeId, dictStatus: dictStatus}).then(res => {
          // console.log(res)
          if (res.code == this.HttpConstant.SUCCESS_CODE) {
            this.$message.success('变更成功')
            this.findTypePage()
          }
        })
      },

      // 关闭新增/编辑字典类型弹窗
      closeTypeDialog(form1) {
        this.resetData()
        this.$refs[form1].resetFields()
        this.editType = 'add'
        this.vis = false
      },

      // 字典管理
      showDictMngDialog(row) {
        this.dictTypeId = row.dictTypeId
        this.dictTypeCode = row.dictTypeCode
        this.dictTypeName = row.dictTypeName
        this.findDictPage();
        this.dictApi.getDictListByTypeCode({dictTypeCode: this.dictTypeCode}).then(res => {
          this.dictTreeData = this.buildDictTree(res.data);
        })
        this.cmvis = true
      },

      // 扁平数据转树
      buildDictTree(list) {
        let temp = {}
        let ans = []
        for (let i in list) {
          temp[list[i].dictId] = list[i]
        }
        for (let i in temp) {
          if (temp[i].parentId != -1 && temp[i].parentId && temp[i].parentId) {
            if (!temp[temp[i].parentId].children) {
              temp[temp[i].parentId].children = new Array()
            }
            temp[temp[i].parentId].children.push(temp[i])
          } else {
            ans.push(temp[i])
          }
        }
        return ans
      },

      // 关闭新增弹窗
      hideDictMngDialog(codeform) {
        this.$refs[codeform].resetFields()
        this.resetData()
        // this.isAdd = 'add'
        this.addcvis = false
      },

      // 点击新增/编辑
      showDictDialog(row) {
        this.addcvis = true
        this.codeForm.dictTypeCode = this.dictTypeCode
        this.codeForm.dictTypeName = this.dictTypeName
        this.dictApi.getDictListByTypeCode({dictTypeCode: this.dictTypeCode}).then(res => {
          this.parentIdOptions = res.data
          this.parentIdOptions.unshift({dictId: '-1', dictName: '无'})
        })
        if (row == undefined) {
          this.isAdd = 'add'
        } else {
          this.isAdd = 'edit'
          this.codeForm = row
          // this.codeForm.dictId = row.dictId
          // this.codeForm.dictType = row.dictTypeName
          // this.codeForm.dictCode = row.dictCode
          // this.codeForm.dictName = row.dictName
          // this.codeForm.dictShortName = row.dictShortName
          // this.codeForm.dictShortCode = row.dictShortCode
          // this.codeForm.parentId = row.parentId
          // this.codeForm.dictTypeCode = row.dictTypeCode
          // this.codeForm.status = row.status.toString()
          // this.codeForm.orderNo = row.orderNo
        }
      },
      // 确认新增/编辑
      saveDict(codeform) {
        this.$refs[codeform].validate((valid) => {
          if (valid) {
            let data = {
              dictCode: this.codeForm.dictCode,
              dictId: this.codeForm.dictId
            }
            this.dictApi.checkCode(data).then(res => {
              if (res.code == this.HttpConstant.SUCCESS_CODE) {
                if (res.data == true) {
                  if (this.isAdd == 'add') {
                    if (this.codeForm.parentId == -1) {
                      this.codeForm.parentId = null
                    }
                    this.dictApi.addCode(this.codeForm).then(res => {
                      if (res.code == this.HttpConstant.SUCCESS_CODE) {
                        this.findDictPage()
                        this.dictApi.getDictListByTypeCode({dictTypeCode: this.dictTypeCode}).then(res => {
                          this.dictTreeData = this.buildDictTree(res.data)
                        })
                        this.$message(this.HttpConstant.ADD_SUCCESS_MSG)
                        this.addcvis = false
                      }
                    })
                  } else {
                    if (this.codeForm.parentId == -1) {
                      this.codeForm.parentId = null
                    }
                    this.dictApi.updateCode(this.codeForm).then(res => {
                      if (res.code == this.HttpConstant.SUCCESS_CODE) {
                        this.findDictPage()
                        this.dictApi.getDictListByTypeCode({dictTypeCode: this.dictTypeCode}).then(res => {
                          this.dictTreeData = this.buildDictTree(res.data)
                        })
                        this.$message(this.HttpConstant.UPDATE_SUCCESS_MSG)
                        this.addcvis = false
                      }
                    })
                  }
                } else {
                  this.$message.error('Code已存在');
                }
              }
            })
          }
        })
      },
      // 更改字典状态
      changeDictStatus(row) {
        var status = ''
        if (row.status.toString() == '1') {
          status = '2'
        } else {
          status = '1'
        }
        this.dictApi.updateDictStatus({dictId: row.dictId, status: status}).then(res => {
          // console.log(res)
          if (res.code == this.HttpConstant.SUCCESS_CODE) {
            this.$message(this.HttpConstant.UPDATE_SUCCESS_MSG)
            this.findDictPage()
          }
        })
      },
      // 删除字典
      handleDeleteDict(row) {
        this.$confirm("是否确认删除该数据?", "提示", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }).then(() => {
          this.dictApi.deleteCode({dictId: row.dictId}).then(res => {
            if (res.code == this.HttpConstant.SUCCESS_CODE) {
              this.dictApi.getDictListByTypeCode({dictTypeCode: this.dictTypeCode}).then(res => {
                this.dictTreeData = this.buildDictTree(res.data)
              })
              this.findDictPage();
              this.$message(this.HttpConstant.DELETE_SUCCESS_MSG);
            }
          })
        });
      },
      // 关闭字典管理弹窗
      closeDictDialog() {
        this.cmvis = false
        this.dictTypeId = ''
      }
    },
    created() {
      this.findTypePage()
    }
  }
</script>


<style scoped lang="scss">
  .resource-table{
     .cell{
      line-height: 2;
    }
  }
</style>

