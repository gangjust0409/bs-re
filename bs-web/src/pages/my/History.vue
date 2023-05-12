<template>
  <div class="history_container">
    <div class="product_content" v-for="(history, i) in historyList" :key="i">
      <el-divider :content-position="i % 2 == 0 ? 'left' : 'right'">{{
        history.date
      }}</el-divider>
      <my-product-info
        :list="history.historys"
        :isDelete="false"
      ></my-product-info>
    </div>
    <el-backtop :right="100" :bottom="100" />
  </div>
</template>

<script setup>
import MyProductInfoVue from "@/components/MyProductInfo.vue";
import { myHistoryList } from "@/api/user";
import { ref } from "vue";
import { useRouter } from "vue-router";
const router = useRouter();
//我的浏览历史
const historyList = ref([]);

//加载我的历史
async function loadMyHistoryList() {
  const { code, data } = await myHistoryList();
  if (code == 200) {
    historyList.value = data;
  } else if (code == 403) {
    //跳转到登录页
    router.replace("/login");
  }
}
loadMyHistoryList();
</script>

<style scoped lang="scss">
.history_container {
  .product_content {
    margin: 20px 0;
    :deep(.el-divider__text) {
      font-size: 30px;
    }
  }
}
</style>