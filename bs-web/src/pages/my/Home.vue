<template>
  <div class="my_home">
    <!-- 头部 -->
    <el-affix :offset="0">
      <div class="my_home_header">
        <div class="logo">
          <img
            src="https://img.alicdn.com/imgextra/i2/O1CN015xANTA1iq6KMo02E5_!!6000000004463-2-tps-140-34.png"
            alt=""
          />
        </div>
        <div class="user_info">
          <router-link to="/home" class="cancel">返回首页</router-link>
          <a class="user">{{ nickName }}</a>
          <a href="" class="logout" @click="loginOu">退出</a>
        </div>
      </div>
    </el-affix>

    <div class="my_section">
      <!-- 侧边栏 -->
      <div class="navbar">
        <div class="product_content">
          <div class="aside_menus">
            <router-link to="/my/user/info" class="aside_menu"
              >个人资料</router-link
            >
            <router-link to="/my/buy" class="aside_menu"
              >我购买的宝贝</router-link
            >
            <router-link to="/cart" class="aside_menu">我的购物车</router-link>
            <router-link to="/my/address" class="aside_menu"
              >收货地址</router-link
            >
            <router-link to="/my/collect" class="aside_menu"
              >我的收藏</router-link
            >
            <router-link to="/my/history" class="aside_menu"
              >我的足迹</router-link
            >
          </div>
        </div>
      </div>
      <!-- 展示区 -->
      <div class="my_zhanshi">
        <router-view></router-view>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, computed } from "vue";
import { useRouter } from "vue-router";
import useUserStore from "@/store/user";

const userStore = useUserStore();
const router = useRouter();

//登录名称
const nickName = computed(() => userStore.nickName);

//自执行函数
function loginOu(e) {
  userStore.loginOut();
  router.push("/home");
  e.preventDefault();
}
</script>

<style scoped lang="scss">
.my_home {
  .my_home_header {
    background-color: #f40;
    padding: 10px 40px;
    margin-bottom: 20px;
    display: flex;
    justify-content: space-between;
    align-items: center;
    color: #fff;

    .user_info {
      display: flex;
      .cancel,
      a {
        margin-right: 20px;
        color: #fff;
      }
    }
  }
  .my_section {
    display: flex;
    .navbar {
      height: 230px;
      .product_content {
        position: fixed;
        .aside_menus {
          margin-right: 15px;
          display: flex;
          flex-direction: column;
          .aside_menu {
            font-size: 18px;
            font-family: PingFangSC-Regular;
            line-height: 2;
          }
        }
      }
    }
    .my_zhanshi {
      width: 853px;
      padding: 5px;
      margin-left: 325px;
    }
    .router-link-active,
    a:hover {
      color: #f40;
    }
  }
}
</style>