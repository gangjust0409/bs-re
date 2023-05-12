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
    <el-steps :active="2" finish-status="error">
      <el-step title="确定订单" :icon="List" />
      <el-step title="支付失败" :icon="CircleCloseFilled" />
    </el-steps>
    <div class="pay_success">
      <el-icon color="#ff0036"><IEpCircleCloseFilled /></el-icon>
      <span>支付失败！</span>
    </div>
    <div class="btn">
      <el-button type="primary" plain>返回首页</el-button>
    </div>
    <div class="mao">5 秒自动跳转到首页！</div>
  </div>
  <el-backtop :right="100" :bottom="100" />
  <menu-footer></menu-footer>
</template>

<script setup>
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