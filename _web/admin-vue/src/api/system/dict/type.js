import request from '@/utils/request'


export default class DictManageApi {

  // 获取代码类型列表
  getList(data) {
    let url = '/biemo-system-app/dictType/getDictTypeList';
    return request({
      url: url,
      method: 'post',
      //params: data
      data
    })
  }


  // 新增代码类型
  addDictType(data) {
    let url = '/biemo-system-app/dictType/addDictType';
    return request({
      url: url,
      method: 'post',
      //params: data
      data
    })
  }

  // 编辑代码类型
  updateDictType(data) {
    let url = '/biemo-system-app/dictType/updateDictType';
    return request({
      url: url,
      method: 'post',
      //params: data
      data
    })
  }

  // 删除子弹类型
  deleteDictType(params){
    let url = '/biemo-system-app/dictType/deleteDictType';
    return request({
      url: url,
      method: 'post',
      params: data
      //data
    })
  }

  // 更改状态
  updateTypeStatus(data) {
    let url = '/biemo-system-app/dictType/updateStatus';
    return request({
      url: url,
      method: 'post',
      params: data
      //data
    })
  }

  // 删除代码类型
  deleteCodeType(data) {
    let url = '/biemo-system-app/dict/deleteDictType';
    return request({
      url: url,
      method: 'post',
      //params: data
      data
    })
  }


  // 获取代码列表
  findDictPage(data) {
    let url = '/biemo-system-app/dict/getDictPage';
    return request({
      url: url,
      method: 'post',
      //params: data
      data
    })
  }


  // 新增代码
  addCode(data) {
    let url = '/biemo-system-app/dict/addDict';
    return request({
      url: url,
      method: 'post',
      //params: data
      data
    })
  }

  // 编辑代码
  updateCode(data) {
    let url = '/biemo-system-app/dict/updateDict';
    return request({
      url: url,
      method: 'post',
      //params: data
      data
    })
  }

  // 获取上级代码
  getDictListByTypeCode(data) {
    let url = '/biemo-system-app/dict/getDictListByTypeCode';
    return request({
      url: url,
      method: 'post',
      params: data
    })
  }

  // 根据字典类型code和父id获取下级字典
  getListByTypeCodeAndPCode(data) {
    let url = '/biemo-system-app/dict/getListByTypeCodeAndPCode';
    return request({
      url: url,
      method: 'post',
      params: data
      //data
    })
  }

  // 更改代码状态
  updateDictStatus(data) {
    let url = '/biemo-system-app/dict/updateDictStatus';
    return request({
      url: url,
      method: 'post',
      params: data
    })
  }

  // 删除代码
  deleteCode(data) {
    let url = '/biemo-system-app/dict/deleteDict';
    return request({
      url: url,
      method: 'post',
      params: data
    })
  }

  // 基础数据类型校验
  checkCodeType(data) {
    let url = '/biemo-system-app/dictType/checkCode';
    return request({
      url: url,
      method: 'post',
      //params: data
      data
    })

  }

// 基础数据校验
  checkCode(data) {

    let url = '/biemo-system-app/dict/checkCode';
    return request({
      url: url,
      method: 'post',
      params: data
    })

  }
}
