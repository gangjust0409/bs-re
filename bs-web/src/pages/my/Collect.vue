<template>
  <div class="collect_container">
    <div class="product_content">
      <div class="title">我的收藏</div>
      <my-product-info :list="collects"></my-product-info>
    </div>
    <el-backtop :right="100" :bottom="100" />
  </div>
</template>

<script setup>
import MyProductInfoVue from "@/components/MyProductInfo.vue";
import { ref } from "vue";
import { useRouter } from "vue-router";
import { collectList } from "@/api/user";
//收藏列表
const collects = ref([]);
const router = useRouter();

//加载收藏列表
(async () => {
  const { code, data } = await collectList();
  if (code == 200) {
    collects.value = data.filter((e) => e != null);
  } else if (code == 403) {
    //跳转到登录页
    router.replace("/login");
  }
})();
</script>

<style scoped lang="scss">
.collect_container {
  .product_content {
    margin: 0;
    .title {
      font-size: 20px;
    }
  }
}
</style>