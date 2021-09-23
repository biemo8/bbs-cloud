import request from '@/utils/request';
let ssoName = process.env.VUE_APP_SSO_NAME;
export default class ResourceMaintainIndexApi {
  // 新增资源缓存
  addResCache(params) {
    let url = ssoName+"/resCacheManager/addResCache";
    return request({
      url: url,
      method: 'post',
      data:params
    })
  }
  // 修改资源缓存
  editResCache(params) {
    let url = ssoName+"/resCacheManager/editResCache";
    return request({
      url: url,
      method: 'post',
      data:params
    })
  }
  // 获取应用下拉列表
  getAppSelect(params) {
    let url = ssoName+"/resCacheManager/getAppSelect";
    return request({
      url: url,
      method: 'get',
      params:params
    })
  }
  // 获取缓存资源的详情
  getDetail(params) {
    let url = ssoName+"/resCacheManager/getDetail";
    return request({
      url: url,
      method: 'get',
      params:params
    })
  }
  // 获取http请求下拉列表
  getHttpRequestSelect(params) {
    let url = ssoName+"/resCacheManager/getHttpRequestSelect";
    return request({
      url: url,
      method: 'get',
      params:params
    })
  }
  // 获取资源缓存分页
  getResCacheList(params) {
    let url = ssoName+"/resCacheManager/getResCacheList";
    return request({
      url: url,
      method: 'get',
      params:params
    })
  }
  // 获取资源所属模块下拉列表
  getResModuleSelect(params) {
    let url = ssoName+"/resCacheManager/getResModuleSelect";
    return request({
      url: url,
      method: 'get',
      params:params
    })
  }
  // 删除资源缓存
  removeResCache(params) {
    let url = ssoName+"/resCacheManager/removeResCache";
    return request({
      url: url,
      method: 'get',
      params:params
    })
  }
}
