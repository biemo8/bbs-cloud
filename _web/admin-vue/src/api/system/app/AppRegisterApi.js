import request from '@/utils/request'

export default class AppRegisterApi {


  // 获取列表
  list(data) {
    let url = "/biemo-system-app/app/list";
    return request({
      url: url,
      method: 'get',
      params: data
    })
  }
  // 新增
  add(data) {
    let url = "/biemo-system-app/app/add";
    return request({
      url: url,
      method: 'post',
      data
    })
  }
  // 编辑
  update(data) {
    let url = "/biemo-system-app/app/update";
    return request({
      url: url,
      method: 'post',
      data
    })
  }
  // 详情
  detail(data) {
    let url = "/biemo-system-app/app/detail";
    return request({
      url: url,
      method: 'get',
      params: data
    })
  }
  // 删除
  delete(data) {
    let url = "/biemo-system-app/app/delete";
    return request({
      url: url,
      method: 'get',
      params: data
    })
  }
  // 禁用或启用
  changeStatus(data) {
    let url = "/biemo-system-app/app/changeStatus";
    return request({
      url: url,
      method: 'get',
      params: data
    })
  }
  // 获取应用下拉列表
  getAppSelect(data) {
    let url = "/biemo-system-app/app/getAppSelect";
    return request({
      url: url,
      method: 'get',
      params: data
    })
  }
}
