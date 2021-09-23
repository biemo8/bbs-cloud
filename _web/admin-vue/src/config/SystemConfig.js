module.exports = {
  title: "快速开发平台", // 项目名称
  homePage: {
    name: "首页",
    path: "/index"
  },
  auth: false, // 是否校验登录
  sign: false, // 是否开启签名
  sso: true, // 是否开启单点登录
  storate: {
    expireTime: 1000 * 60 * 60 * 3,
    sessionStorageKey: "SESSION",
    localStorageKey: "LOCAL"
  },
  ras: {
    open: true,
  },
  defaultColor:{
    mainColor:'#3E464C',
    secondColor:'#24262F',
  },
}
