import request from '@/utils/request'

export default class MenuManagementApi {

  // 应用下拉列表
  getAppSelect(data) {
    let url = "/biemo-system-app/app/getAppSelect";
    return request({
      url: url,
      method: 'get',
      params:data
    })
  }
  //获取菜单列表
  list(data) {
    let url = "/biemo-system-app/menu/list";
    return request({
      url: url,
      method: 'post',
      data
    })
  }
  //新增菜单
  add(data) {
    let url = "/biemo-system-app/menu/add";
    return request({
      url: url,
      method: 'post',
      data
    })
  }
  //删除菜单
  delete(data) {
    let url = "/biemo-system-app/menu/delete";
    return request({
      url: url,
      method: 'post',
      data:data
    })
  }
  //更改状态
  changeStatus(data) {
    let url = "/biemo-system-app/menu/changeStatus";
    return request({
      url: url,
      method: 'get',
      params:data
    })
  }
  //获取菜单详情
  getMenuDetail(data) {
    let url = "/biemo-system-app/menu/getMenuDetail";
    return request({
      url: url,
      method: 'get',
      params:data
    })
  }
  //编辑菜单
  update(data) {
    let url = "/biemo-system-app/menu/update";
    return request({
      url: url,
      method: 'post',
      data
    })
  }
  //获取父级菜单列表
  getSelectMenuTreeList(data) {
    let url = "/biemo-system-app/menu/getSelectMenuTreeList";
    return request({
      url: url,
      method: 'get',
      params:data
    })
  }
  //获取资源下拉列表
  getResourceSelectList(data) {
    let url = "/biemo-system-app/resource/getResourceSelectList";
    return request({
      url: url,
      method: 'get',
      params:data
    })
  }
}
