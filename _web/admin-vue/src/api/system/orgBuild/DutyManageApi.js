import request from '@/utils/request'

export default class DutyManageApi {

  // 获取公司列表
  queryListPage(data) {
    let url = '/biemo-system-app/entDuty/queryListPage';
    return request({
      url: url,
      method: 'get',
      params:data
    })
  }
  // 新增
  add(data) {
    let url = '/biemo-system-app/entDuty/add';
    return request({
      url: url,
      method: 'post',
      data
    })
  }
  // 修改
  update(data) {
    let url = '/biemo-system-app/entDuty/update';
    return request({
      url: url,
      method: 'post',
      data
    })
  }
  // 详情
  detail(data) {
    let url = '/biemo-system-app/entDuty/detail';
    return request({
      url: url,
      method: 'get',
      params:data
    })
  }
  // 禁用启用部门
  changeStatus(data) {
    let url = '/biemo-system-app/entDuty/changeStatus';
    return request({
      url: url,
      method: 'get',
      params:data
    })
  }
  // 删除
  delete(data) {
    let url = '/biemo-system-app/entDuty/delete';
    return request({
      url: url,
      method: 'post',
      params:data
    })
  }
}
