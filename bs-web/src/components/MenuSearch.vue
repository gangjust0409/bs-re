<template>
  <div class="search_box">
    <!-- logo -->
    <div class="logo">
      <img
        src="https://img.alicdn.com/imgextra/i1/O1CN01BqHq2Z28Ns0s0KLHa_!!6000000007921-2-tps-126-54.png"
        alt="淘宝"
      />
    </div>
    <div class="search">
      <!-- 搜索框 -->
      <div class="search_top">
        <el-input v-model="kv" placeholder="" size="large" clearable />
        <div class="search_btn" @click="search">搜索</div>
      </div>
      <!-- 下面文字 -->
      <div class="search_bottom">
        <a
          v-for="val in getterHistory"
          :key="val"
          href=""
          @click="search($event, val)"
          >{{ val }}</a
        >
      </div>
    </div>
    <div class="searhc_empty"></div>
  </div>
</template>

<script setup>
import { useRouter } from "vue-router";
import { useSearchStore } from "@/store/search";
import { ref, reactive, defineProps, watch, computed, onMounted } from "vue";

const router = useRouter();
const searchStore = useSearchStore();

const { keyword } = defineProps({
  keyword: {
    String,
    default: null,
  },
});
//输入文本框的值
const kv = ref(keyword);
//搜索历史列表
const getterHistory = computed(() => searchStore.getHistoryList);
//点击搜索
function search(e, val) {
  if (!!val) {
    kv.value = val;
  }
  router.push({ path: "/search", query: { keyword: kv.value } });
  e.preventDefault();
}
//监听事件
watch(
  kv,
  (val) => {
    //保存
    searchStore.setStore(val);
  },
  {
    deep: true,
    immediate: true,
  }
);
</script>

<style scoped lang="scss">
.search_box {
  display: flex;
  align-items: center;
  padding: 25px 80px;

  .logo {
    padding: 0 80px;
  }
  .search {
    width: 150px;
    flex: 1;
    .search_top {
      display: flex;
      justify-content: space-between;
      position: relative;
      :deep(.el-input__wrapper) {
        border-radius: 20px;
        border: 2px solid #ff0036;
      }
      .search_btn {
        position: absolute;
        right: 3px;
        top: 3px;
        font-size: 15px;
        padding: 9px 25px;
        border-radius: 20px;
        color: #fff;
        background: linear-gradient(90deg, #fe6076 0%, #ff0036 93%);
        cursor: pointer;
      }
    }
    .search_bottom {
      display: flex;
      margin: 5px 0;
      flex-wrap: wrap;
      overflow: hidden;
      height: 20px;
      white-space: nowrap;

      a {
        display: block;
        margin-right: 15px;
        &:hover {
          color: #ff0036;
        }
      }
    }
  }
  .searhc_empty {
    width: 250px;
  }
}
</style>