import request from '@/utils/request'

export default class PersonManageApi {

  // 获取公司列表
  queryListPage(data) {
    let url = '/biemo-system-app/entUser/queryListPage';
    return request({
      url: url,
      method: 'get',
      params:data
    })
  }
  // 新增
  add(data) {
    let url = '/biemo-system-app/entUser/add';
    return request({
      url: url,
      method: 'post',
      data
    })
  }
  // 修改
  update(data) {
    let url = '/biemo-system-app/entUser/update';
    return request({
      url: url,
      method: 'post',
      data
    })
  }
  // 详情
  detail(data) {
    let url = '/biemo-system-app/entUser/detail';
    return request({
      url: url,
      method: 'get',
      params:data
    })
  }
  // 禁用启用人员
  changeStatus(data) {
    let url = '/biemo-system-app/entUser/changeStatus';
    return request({
      url: url,
      method: 'get',
      params:data
    })
  }
  // 删除
  delete(data) {
    let url = '/biemo-system-app/entUser/delete';
    return request({
      url: url,
      method: 'post',
      params:data
    })
  }
  //获取公司列表
  getCompanySelect(data) {
    let url = '/biemo-system-app/entUser/getCompanySelect';
    return request({
      url: url,
      method: 'get',
      params:data
    })
  }
  //获取部门列表
  getDeptSelect(data) {
    let url = '/biemo-system-app/entUser/getDeptSelect';
    return request({
      url: url,
      method: 'get',
      params:data
    })
  }
  //获取职务列表
  getDutySelect(data) {
    let url = '/biemo-system-app/entUser/getDutySelect';
    return request({
      url: url,
      method: 'get',
      params:data
    })
  }
  //人员配置角色时获取角色列表
  getRoles(data) {
    let url = '/biemo-system-app/entUser/getRoles';
    return request({
      url: url,
      method: 'get',
      params:data
    })
  }
  //保存人员配置好的角色
  saveRoles(data) {
    let url = '/biemo-system-app/entUser/saveRoles';
    return request({
      url: url,
      method: 'post',
      data
    })
  }
  // 重置密码
  resetPassword(data) {
    let url = '/biemo-system-app/entUser/resetPassword';
    return request({
      url: url,
      method: 'get',
      params:data
    })
  }
}
