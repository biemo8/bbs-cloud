import request from '@/utils/request';
let ssoName = process.env.VUE_APP_SSO_NAME;
export default class ResourceMaintainIndexApi {
  // 网关路由列表
  gatewayRoutes(params) {
    let url = ssoName+"/gateway/routes";
    return request({
      url: url,
      method: 'get',
      params:params
    })
  }

  // 新增路由
  addRoute(params) {
    let url = ssoName+"/gateway/addRoute";
    return request({
      url: url,
      method: 'post',
      data:params
    })
  }

  // 根据id查询路由信息
  routeInfoById(params) {
    let url = ssoName+"/gateway/routeInfoById";
    return request({
      url: url,
      method: 'get',
      params:params
    })
  }

  // 删除指定路由
  deleteRoute(params) {
    let url = ssoName+"/gateway/deleteRoute";
    return request({
      url: url,
      method: 'get',
      params:params
    })
  }

  // 过滤器列表
  globalfilters(params) {
    let url = ssoName+"/gateway/globalfilters";
    return request({
      url: url,
      method: 'get',
      params:params
    })
  }

  // 所有的过滤器工厂
  routefilters(params) {
    let url = ssoName+"/gateway/routefilters";
    return request({
      url: url,
      method: 'get',
      params:params
    })
  }

  // 清空路由缓存
  refreshRoute(params) {
    let url = ssoName+"/gateway/refreshRoute";
    return request({
      url: url,
      method: 'post',
      data:params
    })
  }

}
