<template>
  <div>
    <el-select v-model="text"
               :size="size"
               :filterable = "filterable"
               :multiple="multiple"
               :placeholder="placeholder"
               @change="handleChange"
               :disabled="disabled"
               :clearable="clearable"
               style="width: 100%">
      <el-option
        v-for="item in dataSource"
        :key="item.dictId"
        :label="item[labelKey]"
        :value="item[valueKey]">
      </el-option>
    </el-select>
  </div>
</template>

<script>
  import DictApi from '@/api/system/dict/type';
  import _ from 'lodash'
  export default {
    name: 'ForeignSelect',
    data() {
      return {
        dataSource: [],
        text: this.multiple ? [] : ''
      }
    },
    props: {
      value:[String, Number, Array],
      size: {
        type: String,
        default: 'medium'
      },

      disabled: {
        type: Boolean,
        default: false
      },

      multiple: {
        type: Boolean,
        default: false
      },

      clearable: {
        type: Boolean,
        default: false
      },

      filterable: {
        type: Boolean,
        default: false
      },

      placeholder: {
        type: String,
        default: '请选择'
      },

      labelKey: {
        type: String,
        default: 'dictName'
      },
      valueKey: {
        type: String,
        default: 'dictId',
        // default: 'dictCode',
      },
      dictTypeCode: {
        type: String
      },
      showAll:{
        type: Boolean,
        default: false
      },
      parentCode: {
        type: String,
        default: '-1'
      }
    },

    methods: {
      findDict() {
        let params = {};
        params.dictTypeCode = this.dictTypeCode;
        let dictApi = new DictApi();
        if(this.showAll){
          // 如果现实该字典类型全部数据
          dictApi.getDictListByTypeCode(params).then(res=>{
            this.dataSource = res.data;
          });
        }else{
          // 否则现实指定父级下的数据
          params.parentCode = this.parentCode;
          dictApi.getListByTypeCodeAndPCode(params).then(res=>{
            this.dataSource = res.data;
          });
        }

      },

      //选择回调
      handleChange(val) {
        this.text = val;
        this.$emit("input", val);

        let valueKey = this.valueKey;
        if(_.isArray(val)){
          // 如果是多选
          let selectVals = [];
          this.dataSource.forEach(function(item) {
            if(val.indexOf(item[valueKey]) != -1){
              selectVals.push(item);
            }
          });
          this.$emit("on-change", selectVals);
        }else{
          // 如果是单选
          let dict = {};
          this.dataSource.forEach(function(item) {
            if(item[valueKey] == val){
              dict = item;
            }
          });
          this.$emit("on-change", dict);
        }

      }


    },
    created() {
      this.findDict();
      this.text = this.value;
    },
    watch:{
      value(curVal,oldVal){
        this.text = curVal;
      },
      parentCode(curVal,oldVal){
        this.findDict();
      }
    },
    computed: {

    }
  }
</script>

<style lang="scss">
</style>
