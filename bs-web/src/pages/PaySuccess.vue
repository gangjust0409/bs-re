<template>
  <menu-header>
    <template #cancel>
      <li style="display: inline-block; margin-right: 10px">
        <router-link to="/home" style="color: #ff0036">返回首页</router-link>
      </li>
    </template>
  </menu-header>
  <menu-search></menu-search>
  <div class="product_content">
    <el-steps :active="2" finish-status="success">
      <el-step title="确定订单" :icon="List" />
      <el-step title="支付成功" :icon="SuccessFilled" />
    </el-steps>
    <div class="pay_success">
      <el-icon color="#67c23a"><IEpSuccessFilled /></el-icon>
      <span>支付成功！</span>
    </div>
    <div class="btn">
      <el-button type="primary" @click="router.replace('/my/buy')"
        >点击跳转到我的宝贝</el-button
      >
      <el-button type="primary" plain @click="router.replace('/home')"
        >返回首页</el-button
      >
    </div>
    <div class="mao">{{ seconds }} 秒自动跳转到我的宝贝！</div>
  </div>
  <el-backtop :right="100" :bottom="100" />
  <menu-footer></menu-footer>
</template>

<script setup>
import { ref, onDeactivated } from "vue";
import { useRouter } from "vue-router";
const router = useRouter();
//几秒后跳转到我的宝贝
const seconds = ref(5);

let timer = setInterval(() => {
  seconds.value--;
  if (seconds.value <= -1) {
    clearInterval(timer);
    router.replace("/my/buy");
  }
}, 1000);
onDeactivated(() => {
  clearInterval(timer);
});
</script>

<style scoped lang="scss">
.pay_success {
  font-size: 3em;
  display: flex;
  align-items: center;
  justify-content: center;
  i,
  svg {
    width: 2em;
    height: 2em;
  }
}
.btn {
  text-align: center;
}
.mao {
  width: 100%;
  text-align: right;
  font-size: 14px;
  padding-right: 100px;
}
</style>