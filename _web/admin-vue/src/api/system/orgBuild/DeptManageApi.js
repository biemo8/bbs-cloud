import request from '@/utils/request'

export default class DeptManageApi {


  // 获取公司列表
  queryListPage(data) {
    let url = '/biemo-system-app/entDept/queryListPage';
    return request({
      url: url,
      method: 'get',
      params:data
    })
  }
  // 新增
  add(data) {
    let url = '/biemo-system-app/entDept/add';
    return request({
      url: url,
      method: 'post',
      data
    })
  }
  // 修改
  update(data) {
    let url = '/biemo-system-app/entDept/update';
    return request({
      url: url,
      method: 'post',
      data
    })
  }
  // 详情
  detail(data) {
    let url = '/biemo-system-app/entDept/detail';
    return request({
      url: url,
      method: 'get',
      params:data
    })
  }
  // 获取部门树列表
  queryDeptTree(data) {
    let url = '/biemo-system-app/entDept/queryDeptTree';
    return request({
      url: url,
      method: 'get',
      params:data
    })
  }
  // 禁用启用部门
  changeStatus(data) {
    let url = '/biemo-system-app/entDept/changeStatus';
    return request({
      url: url,
      method: 'get',
      params:data
    })
  }
  // 删除
  delete(data) {
    let url = '/biemo-system-app/entDept/delete';
    return request({
      url: url,
      method: 'post',
      params:data
    })
  }
}
