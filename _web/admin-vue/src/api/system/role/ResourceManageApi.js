import request from '@/utils/request'

export default class ResourceManageApi {

  // 获取资源列表
  list(data) {
    let url = "/biemo-system-app/resource/list";
    return request({
      url: url,
      method: 'get',
      params:data
    })
  }
}
