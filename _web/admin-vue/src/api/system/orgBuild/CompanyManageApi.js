import request from '@/utils/request'

export default class CompanyManageApi {

  // 获取公司列表
  queryListPage(data) {
    let url = '/biemo-system-app/entCompany/queryListPage';
    return request({
      url: url,
      method: 'get',
      params:data
    })
  }
  // 新增
  add(data) {
    let url = '/biemo-system-app/entCompany/add';
    return request({
      url: url,
      method: 'post',
      data
    })
  }
  // 修改
  update(data) {
    let url = '/biemo-system-app/entCompany/update';
    return request({
      url: url,
      method: 'post',
      data
    })
  }
  // 详情
  detail(data) {
    let url = '/biemo-system-app/entCompany/detail';
    return request({
      url: url,
      method: 'get',
      params:data
    })
  }
  // 获取公司树列表
  queryCompTree(data) {
    let url = '/biemo-system-app/entCompany/queryCompTree';
    return request({
      url: url,
      method: 'get',
      params:data
    })
  }
  // 禁用启用公司
  changeStatus(data) {
    let url = '/biemo-system-app/entCompany/changeStatus';
    return request({
      url: url,
      method: 'get',
      params:data
    })
  }
  // 删除
  delete(data) {
    let url = '/biemo-system-app/entCompany/delete';
    return request({
      url: url,
      method: 'post',
      params:data
    })
  }
  //获取应用列表
  getAppList(data) {
    let url = '/biemo-system-app/entCompany/getAppList';
    return request({
      url: url,
      method: 'get',
      params:data
    })
  }
  //保存公司配置的应用
  saveCompApp(data) {
    let url = '/biemo-system-app/entCompany/saveCompApp';
    return request({
      url: url,
      method: 'post',
      data
    })
  }
}
