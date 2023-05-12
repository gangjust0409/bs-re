import { createApp } from 'vue'
import App from './App.vue'
import { ElMessage, ElMessageBox } from 'element-plus'
import 'element-plus/theme-chalk/el-message.css'
import 'element-plus/theme-chalk/el-message-box.css'
import router from './router'
import { createPinia } from 'pinia'
//公用组件
import menuHeader from '@/components/MenuHeader.vue'
import menuFooter from '@/components/MenuFooter.vue'
import menuSearch from '@/components/MenuSearch.vue'
import productInfoList from '@/components/ProductInfoList.vue'
import svgIcon from '@/components/svgIcon'
//引入 js
require('@/assets/js/iconfont.js');
//引入 css
// import '@/style/element/index.scss'
import '@/assets/css/iconfont.css'

const app = createApp(App);
const store = createPinia();
//公用组件
app.component('menu-header', menuHeader)
app.component('menu-footer', menuFooter)
app.component('menu-search', menuSearch)
app.component('product-info-list', productInfoList)
app.component('svg-icon', svgIcon)

//设置 config
app.config.globalProperties.$msg = ElMessage;
app.config.globalProperties.$msgbx = ElMessageBox;

//use
app.use(router);
app.use(store);
app.mount('#app')
