import request from '@/utils/request'

// 获取路由
export const getRouters = (params) => {
  return request({
    url: '/biemo-system-app/menu/getLeftMenuList',
    method: 'get',
    params
  })
}
