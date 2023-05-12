const Icons = require('unplugin-icons/webpack')
const IconsResolver = require('unplugin-icons/resolver')
const { defineConfig } = require('@vue/cli-service')
const AutoImport = require('unplugin-auto-import/webpack')
const Components = require('unplugin-vue-components/webpack')
const { ElementPlusResolver } = require('unplugin-vue-components/resolvers')
// const ElementPlus = require('unplugin-element-plus/webpack')

module.exports = defineConfig({
  transpileDependencies: true,
  lintOnSave: false,
  //引入自定义主题样式
  // 1 
  css: {
    loaderOptions: {
      scss: {
        additionalData: `@use "~/style/element/index.scss" as *;`,
      },
    },
  },
  // webpack 相关配置
  configureWebpack: {
    plugins: [
      AutoImport({
        resolvers: [ElementPlusResolver(),
        // 自动导入图标组件
        IconsResolver({
          prefix: 'Icon',
        }),],
      }),
      Components({
        resolvers: [ElementPlusResolver({
          //2 必须是 sass
          importStyle: "sass",
        }),
        // 自动注册图标组件
        IconsResolver({
          enabledCollections: ['ep'],
        }),],
      }),
      Icons({
        autoInstall: true,
      }),
      // ElementPlus({
      //   useSource: true,
      // }),
    ],
    // devServer: {
    //   port: 8080,
    //   proxy: {
    //     // 带选项写法：http://localhost:5173/api/bar -> http://jsonplaceholder.typicode.com/bar
    //     '/address': {
    //       target: 'http://localhost:81',
    //       changeOrigin: true,
    //       rewrite: (path) => path.replace(/^\/address/, '')
    //     },
    //   }
    // }
  }
})
