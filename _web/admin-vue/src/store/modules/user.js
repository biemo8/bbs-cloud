import { login, logout, getInfo,getAuth } from '@/api/login'
import { getToken, setToken, removeToken } from '@/utils/auth'

const user = {
  state: {
    token: getToken(),
    name: '',
    avatar: '',
    roles: [],
    permissions: []
  },

  mutations: {
    SET_TOKEN: (state, token) => {
      state.token = token
    },
    SET_NAME: (state, name) => {
      state.name = name
    },
    SET_AVATAR: (state, avatar) => {
      state.avatar = avatar
    },
    SET_ROLES: (state, roles) => {
      state.roles = roles
    },
    SET_PERMISSIONS: (state, permissions) => {
      state.permissions = permissions
    }
  },

  actions: {

    GetAuth({ commit }, userInfo) {
      return new Promise((resolve, reject) => {
        getAuth(userInfo).then(res => {
          let appSsoId = process.env.VUE_APP_SSO_APP_ID
          let baseUrl = process.env.VUE_APP_BASE_URL
          let ssoAuthName = process.env.VUE_APP_SSO_NAME
          window.location.href = `${baseUrl}${ssoAuthName}/sso/login?clientId=${appSsoId}&code=${res.data.code}`;
          // setToken(res.token)
          // commit('SET_TOKEN', res.token)
          resolve()
        }).catch(error => {
          reject(error)
        })
      })
    },
    // 登录
    Login({ commit }, userInfo) {
      const account = userInfo.account.trim()
      const password = userInfo.password
      const code = userInfo.code
      const uuid = userInfo.uuid
      return new Promise((resolve, reject) => {
        login(account, password, code, uuid).then(res => {
          setToken(res.token)
          commit('SET_TOKEN', res.token)
          resolve()
        }).catch(error => {
          reject(error)
        })
      })
    },

    // 获取用户信息
    GetInfo({ commit, state }) {
      return new Promise((resolve, reject) => {
        getInfo().then(res => {
          const user = res.data;
          const avatar = user.avatar == "" ? require("@/assets/images/profile.jpg") : process.env.VUE_APP_BASE_API + user.avatar;
          if (res.data.roleIds && res.data.roleIds.length > 0) { // 验证返回的roles是否是一个非空数组
            commit('SET_ROLES', user.roleIds)
            //commit('SET_PERMISSIONS', res.permissions)
            commit('SET_PERMISSIONS', "*:*:*")
          } else {
            commit('SET_ROLES', ['ROLE_DEFAULT'])
          }
          commit('SET_NAME', user.account)
          commit('SET_AVATAR', avatar)
          resolve(res)
        }).catch(error => {
          reject(error)
        })
      })
    },

    // 退出系统
    LogOut({ commit, state }) {
      return new Promise((resolve, reject) => {
        logout(state.token).then(() => {
          commit('SET_TOKEN', '')
          commit('SET_ROLES', [])
          commit('SET_PERMISSIONS', [])
          removeToken()
          resolve()
        }).catch(error => {
          reject(error)
        })
      })
    },

    // 前端 登出
    FedLogOut({ commit }) {
      return new Promise(resolve => {
        commit('SET_TOKEN', '')
        removeToken()
        resolve()
      })
    }
  }
}

export default user
