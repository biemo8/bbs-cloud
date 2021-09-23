const isProduction = process.env.NODE_ENV === 'production'
const isDocker = process.env.NODE_ENV === 'docker'

export default {
  server: {
    port: 3000,
    host: '0.0.0.0',
    timing: {
      total: true,
    },
  },
  //target: "static",
  modern: true,
  /*
   ** Headers of the page
   */
  head: {
    htmlAttrs: {
      lang: 'zh-cmn-Hans',
      xmlns: 'http://www.w3.org/1999/xhtml',
    },
    title: '',
    meta: [
      { charset: 'utf-8' },
      {
        name: 'viewport',
        content:
          'width=device-width, initial-scale=1, maximum-scale=1, minimum-scale=1, user-scalable=no, minimal-ui',
      },
      { name: 'window-target', content: '_top' },
    ],
    link: [
      { rel: 'icon', type: 'image/x-icon', href: '/favicon.ico' },
      {
        rel: 'alternate',
        type: 'application/atom+xml',
        title: '文章',
        href: '/atom.xml',
      },
      {
        rel: 'alternate',
        type: 'application/atom+xml',
        title: '话题',
        href: '/topic_atom.xml',
      },
      {
        rel: 'alternate',
        type: 'application/atom+xml',
        title: '开源项目',
        href: '/project_atom.xml',
      },
      {
        rel: 'stylesheet',
        href: '//cdn.staticfile.org/bulma/0.8.0/css/bulma.min.css',
      },
      {
        rel: 'stylesheet',
        href: '//at.alicdn.com/t/font_1142441_q49if9pd6wo.css',
      },
    ],
  },
  /*
   ** Customize the progress-bar color
   */
  loading: { color: '#FFB90F' },
  /*
   ** Global CSS
   */
  css: [
    'element-ui/lib/theme-chalk/index.css',
    { src: '~/assets/styles/main.scss', lang: 'scss' },
  ],
  /*
   ** Plugins to load before mounting the App
   */
  plugins: [
    '~/plugins/element-ui',
    '~/plugins/filters',
    '~/plugins/axios',
    '~/plugins/bbs',
    { src: '~/plugins/vue-lazyload', ssr: false },
    { src: '~/plugins/viewer.js', ssr: false },
  ],
  /*
   ** Auto import components
   ** See https://nuxtjs.org/api/configuration-components
   */
  components: true,
  /*
   ** Router property -  https://nuxtjs.org/docs/2.x/features/file-system-routing#the-router-property
   */
  router: {
    middleware: ['resetEnv'],
  },
  /*
   ** Nuxt.js dev-modules
   */
  buildModules: [
    // Doc: https://github.com/nuxt-community/eslint-module
    '@nuxtjs/eslint-module',
  ],
  /*
   ** Nuxt.js modules
   */
  modules: [
    // Doc:https://github.com/nuxt-community/modules/tree/master/packages/bulma
    // '@nuxtjs/bulma',
    // Doc: https://axios.nuxtjs.org/usage
    '@nuxtjs/axios',
    '@nuxtjs/eslint-module',
    ['cookie-universal-nuxt', { alias: 'cookies' }],
  ],
  /*
   ** Axios module configuration
   ** See https://axios.nuxtjs.org/options
   */
  axios: {
    proxy: true,
    credentials: true,
  },

  proxy: {
    '/api/': isProduction
      ? 'http://localhost:800/biemo-bbs-app'
      : isDocker
      ? 'http://localhost:8080'
      : 'http://localhost:8080',
  },

  /*
   ** Build configuration
   */
  build: {
    //publicPath: 'https://file.mlog.club/static/nuxtclient/',
    //publicPath: '/_nuxt/',
    analyze: false,
    optimizeCSS: true,
    extractCSS: true,
    splitChunks: {
      layouts: true,
      pages: true,
      commons: true,
    },
    postcss: {
      // Add plugin names as key and arguments as value
      // Install them before as dependencies with npm or yarn
      plugins: {},
      preset: {},
    },
    babel: {
      plugins: [['@babel/plugin-proposal-private-methods', { loose: true }]],
    },
  },
  babel: {
    plugins: [
      [
        'component',
        {
          libraryName: 'element-ui',
          styleLibraryName: 'theme-chalk',
        }
      ],
    ],
    presets(env, [preset, options]) {
      return [['@nuxt/babel-preset-app', options]]
    },
  },
}
