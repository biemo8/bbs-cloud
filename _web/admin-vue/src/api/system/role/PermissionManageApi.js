import request from '@/utils/request'

export default class PermissionManageApi {
  // 新增
  add(data) {
    let url = "/biemo-system-app/permission/add";
    return request({
      url: url,
      method: 'post',
      data
    })
  }
  // 解除权限绑定资源
  cancelPermissionBindResource(data) {
    let url = "/biemo-system-app/permission/cancelPermissionBindResource";
    return request({
      url: url,
      method: 'post',
      params:data
    })
  }
  // 获取所有权限列表
  getPermissionList(data) {
    let url = "/biemo-system-app/permission/getPermissionList";
    return request({
      url: url,
      method: 'get',
      params:data
    })
  }
  // 分级加载权限
  getPermissionList4Level(data) {
    let url = "/biemo-system-app/permission/getPermissionList4Level";
    return request({
      url: url,
      method: 'get',
      params:data
    })
  }
  // 分页获取权限列表
  getPermissionPageList(data) {
    let url = "/biemo-system-app/permission/getPermissionPageList";
    return request({
      url: url,
      method: 'get',
      params:data
    })
  }
  // 获取权限绑定的资源
  getPermissionResourceList(data) {
    let url = "/biemo-system-app/permission/getPermissionResourceList";
    return request({
      url: url,
      method: 'get',
      params:data
    })
  }
  // 获取权限资源的关系
  getPermissionResourceRelation(data) {
    let url = "/biemo-system-app/permission/getPermissionResourceRelation";
    return request({
      url: url,
      method: 'get',
      params:data
    })
  }
  // 获取权限树
  getPermissionTree(data) {
    let url = "/biemo-system-app/permission/getPermissionTree";
    return request({
      url: url,
      method: 'get',
      params:data
    })
  }
  // 获取权限资源列表
  getResourceList4Permission(data) {
    let url = "/biemo-system-app/permission/getResourceList4Permission";
    return request({
      url: url,
      method: 'get',
      params:data
    })
  }
  // 权限绑定资源
  permissionBindResource(data) {
    let url = "/biemo-system-app/permission/permissionBindResource";
    return request({
      url: url,
      method: 'post',
      data
    })
  }
  // 更改权限状态
  setPermissionStatus(data) {
    let url = "/biemo-system-app/permission/setPermissionStatus";
    return request({
      url: url,
      method: 'post',
      data
    })
  }

  //删除权限
  removePermission(data){
    let url = "/biemo-system-app/permission/removePermission";
    return request({
      url: url,
      method: 'get',
      params:data
    })
  }
}
