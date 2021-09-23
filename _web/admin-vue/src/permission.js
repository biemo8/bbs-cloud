import router from './router'
import store from './store'
import { Message } from 'element-ui'
import NProgress from 'nprogress'
import 'nprogress/nprogress.css'
import { getToken } from '@/utils/auth'
import {setToken} from "./utils/auth";
import StorageHandler from '@/utils/StorageHandler'
import SystemConfig from "./config/SystemConfig"

NProgress.configure({ showSpinner: false })
const whiteList = ['/login', '/auth-redirect', '/bind', '/register']
let storageHandler = new StorageHandler()
router.beforeEach((to, from, next) => {
  NProgress.start()
  //let token = getToken() || to.query.token
  let token = storageHandler.getStorage("token") || to.query.token
  let redirectUrl = to.query.redirectUrl
  if (token) {
    to.meta.title && store.dispatch('settings/setTitle', to.meta.title)
    if(redirectUrl){
      let url = ''
      if(redirectUrl.indexOf('?') === -1){
        url = `${redirectUrl}?token=${token}`
      }else{
        url = `${redirectUrl}&token=${token}`
      }
      window.location.href = url
      next({...to})
    }else {
      if(to.query.token){
        storageHandler.setStorage("token", to.query.token, SystemConfig.storate.sessionStorageKey)
      }
      next();
    }
    /* has token*/
    if (to.path === '/login') {
      next({ path: '/' })
      NProgress.done()
    } else {
      if (store.getters.roles.length === 0) {
        // 判断当前用户是否已拉取完user_info信息
        store.dispatch('GetInfo').then(() => {
          store.dispatch('GenerateRoutes').then(accessRoutes => {
            // 根据roles权限生成可访问的路由表
            router.addRoutes(accessRoutes) // 动态添加可访问路由表
            next({ ...to, replace: true }) // hack方法 确保addRoutes已完成
          })
        }).catch(err => {
            // store.dispatch('LogOut').then(() => {
            //   Message.error(err)
            //   next({ path: '/' })
            // })
          })
      } else {
        next()
      }
    }
  } else {
    // 没有token
    if (whiteList.indexOf(to.path) !== -1) {
      // 在免登录白名单，直接进入
      next()
    } else{
      if (to.path != "/login") {
        //如果地址栏中存在errCode则返回登陆页
        if(to.query.errCode){
          next("/login")
        }else {
          //next(`/login?redirect=${to.fullPath}`) // 否则全部重定向到登录页
          //NProgress.done()
          let baseUrl = process.env.VUE_APP_BASE_URL
          let ssoName = process.env.VUE_APP_SSO_NAME
          let appSsoId = process.env.VUE_APP_SSO_APP_ID
          window.location.href = `${baseUrl}${ssoName}/sso/login?redirectUrl=${encodeURIComponent(window.location.href)}&clientId=${appSsoId}`
        }
      } else {
        next()
      }
    }
  }
})

router.afterEach(() => {
  NProgress.done()
})
