import request from '@/utils/request';
let ssoName = process.env.VUE_APP_SSO_NAME;

export default class LoginLogApi {
  // 日志详情
  detail(params) {
    let url = ssoName+"/authLoginLog/detail";
    return request({
      url: url,
      method: 'get',
      params:params
    })
  }
  // 查看日志列表（分页）
  pageList(params) {
    let url = ssoName+"/authLoginLog/pageList";
    return request({
      url: url,
      method: 'get',
      params:params
    })
  }
}
