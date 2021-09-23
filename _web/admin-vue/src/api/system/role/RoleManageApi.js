import request from '@/utils/request'

export default class RoleManageApi {

  // 获取角色列表（分页）
  page(data) {
    let url = "/biemo-system-app/role/page";
    return request({
      url: url,
      method: 'post',
      data
    })
  }
  // 新增
  add(data) {
    let url = "/biemo-system-app/role/add";
    return request({
      url: url,
      method: 'post',
      data
    })
  }
  // 删除
  delete(data) {
    let url = "/biemo-system-app/role/delete";
    return request({
      url: url,
      method: 'post',
      data:data
    })
  }
  // 获取角色详情
  detail(data) {
    let url = "/biemo-system-app/role/detail";
    return request({
      url: url,
      method: 'post',
      data:data
    })
  }
  // 更换角色
  update(data) {
    let url = "/biemo-system-app/role/update";
    return request({
      url: url,
      method: 'post',
      data
    })
  }
  // 校验角色名称
  name(data) {
    let url = "/biemo-system-app/role/validate/name";
    return request({
      url: url,
      method: 'post',
      data:data
    })
  }
  // 校验角色编码
  code(data) {
    let url = "/biemo-system-app/role/validate/code";
    return request({
      url: url,
      method: 'post',
      data:data
    })
  }
  // 设置角色状态
  status(data) {
    let url = "/biemo-system-app/role/status";
    return request({
      url: url,
      method: 'post',
      data:data
    })
  }
  // 查询角色分配的权限
  permissions(data) {
    let url = "/biemo-system-app/role/permissions";
    return request({
      url: url,
      method: 'get',
      params:data
    })
  }
  // 查询角色分配的所有权限
  findRoleAllPermissions(data) {
    let url = "/biemo-system-app/role/findRoleAllPermissions";
    return request({
      url: url,
      method: 'post',
      data
    })
  }
  // 为角色分配权限
  permission(data) {
    let url = "/biemo-system-app/role/assign/permission";
    return request({
      url: url,
      method: 'post',
      data
    })
  }
}
