import request from '@/utils/request';
let ssoName = process.env.VUE_APP_SSO_NAME;
export default class SessionManage {
  // 强制用户下线
  forcedOffline(params) {
    let url = ssoName+"/session/forcedOffline";
    return request({
      url: url,
      method: 'get',
      params:params
    })
  }
  // 获取在线用户信息
  pageList(params) {
    let url = ssoName+"/session/pageList";
    return request({
      url: url,
      method: 'get',
      params:params
    })
  }
}
