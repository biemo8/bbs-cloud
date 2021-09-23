import request from '@/utils/request'

export default class ButtonMaintainApi {


  // 分页查询列表
  queryListPage(data) {
    let url = "/biemo-system-app/sysButton/queryListPage";
    return request({
      url: url,
      method: 'get',
      params:data
    })
  }
  // 新增
  add(data) {
    let url = "/biemo-system-app/sysButton/add";
    return request({
      url: url,
      method: 'post',
      data
    })
  }
  // 修改
  update(data) {
    let url = "/biemo-system-app/sysButton/update";
    return request({
      url: url,
      method: 'post',
      params:data
    })
  }
  // 查询详情
  getDetail(data) {
    let url = "/biemo-system-app/sysButton/getDetail";
    return request({
      url: url,
      method: 'get',
      params:data
    })
  }
  // 按钮启用禁用
  changeStatus(data) {
    let url = "/biemo-system-app/sysButton/changeStatus";
    return request({
      url: url,
      method: 'get',
      params:data
    })
  }
  // 新增时获取资源
  getResources(data) {
    let url = "/biemo-system-app/sysButton/getResources";
    return request({
      url: url,
      method: 'get',
      params:data
    })
  }
  // 获取用户权限的按钮
  buttonPermission(data) {
    let url = "/biemo-system-app/sysButton/buttonPermission";
    return request({
      url: url,
      method: 'get',
      params:data
    })
  }
  // 删除
  delete(data) {
    let url = "/biemo-system-app/sysButton/delete";
    return request({
      url: url,
      method: 'post',
      params:data
    })
  }

  //获取应用按钮状态
  getAppButtonStatus(data) {
    let url = "/biemo-system-app/permission/getAppButtonStatus";
    return request({
      url: url,
      method: 'get',
      params:data
    })
  }

}
