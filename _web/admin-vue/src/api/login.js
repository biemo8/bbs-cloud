import request from '@/utils/request'
import JSEncrypt from 'jsencrypt'

export function getAuth(data) {
  let encrypt = new JSEncrypt();
  let publicKey = 'MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDt30AcO8CSAfzSa5L8ikVrfehH6aFw9KyL85NzOAduOfnPcbiAGLjLWEKOkOhkYrlSAfU5s+pa3OQTsgpfCkVVm56dEQh8sajIR4uyGbhv0/CdvPTZS5o3sP6Yi9TemWZ443+QNjajN6MSCTmTY86ZoR9jmTcJtV4kNTQWDov6qQIDAQAB'
  encrypt.setPublicKey(publicKey)	//	 publicKey为公钥
  data.password = encrypt.encrypt(data.password)
  return request({
    url: '/biemo-auth-app/auth/accessCode/',
    method: 'post',
    data: data
  })
}

// 登录方法
export function login(username, password, code, uuid) {
  const data = {
    username,
    password,
    code,
    uuid
  }
  return request({
    url: '/login',
    method: 'post',
    data: data
  })
}

// 获取用户详细信息
export function getInfo() {
  return request({
    url: '/biemo-system-app/system/getUserInfo',
    method: 'get'
  })
}

// 退出方法
export function logout() {
  return request({
    url: '/logout',
    method: 'post'
  })
}

// 获取验证码
export function getCodeImg() {
  return request({
    url: '/captchaImage',
    method: 'get'
  })
}
