import request from '@/utils/request';
let ssoName = process.env.VUE_APP_SSO_NAME;
export default class GodKeyPasswordApi {
  // 启用禁用万能密码
  checkedStatus(params) {
    let url = ssoName+"/godKeyManager/checkedStatus";
    return request({
      url: url,
      method: 'get',
      params:params
    })
  }
  // 获取万能密码的值和状态
  getGodKey(params) {
    let url = ssoName+"/godKeyManager/getGodKey";
    return request({
      url: url,
      method: 'get',
      params:params
    })
  }
}
