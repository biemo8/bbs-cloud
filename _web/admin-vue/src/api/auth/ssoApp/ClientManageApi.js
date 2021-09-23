import request from '@/utils/request';
let ssoName = process.env.VUE_APP_SSO_NAME;
export default class ClientManageApi {
  // 获取单点客户端分页列表
  pageList(params) {
    let url = ssoName+"/authClient/pageList";
    return request({
      url: url,
      method: 'get',
      params:params
    })
  }
  // 获取应用下拉列表
  getAppSelectList(params) {
    let url = ssoName+"/authClient/getAppSelectList";
    return request({
      url: url,
      method: 'get',
      params:params
    })
  }
  // 客户端新增
  add(params) {
    let url = ssoName+"/authClient/add";
    return request({
      url: url,
      method: 'post',
      data:params
    })
  }
  // 客户端编辑
  edit(params) {
    let url = ssoName+"/authClient/edit";
    return request({
      url: url,
      method: 'post',
      data:params
    })
  }
  // 客户端删除
  delete(params) {
    let url = ssoName+"/authClient/delete";
    return request({
      url: url,
      method: 'post',
      data:params
    })
  }
  // 客户端详情
  detail(params) {
    let url = ssoName+"/authClient/detail";
    return request({
      url: url,
      method: 'get',
      params:params
    })
  }
}
