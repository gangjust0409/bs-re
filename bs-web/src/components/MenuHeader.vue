<template>
  <header class="taobao_header">
    <ul>
      <li>
        <router-link to="/login" v-if="nickName == null"
          >亲，请先登录</router-link
        >
      </li>
      <li class="user_nav">
        <router-link to="/my/user/info" v-if="nickName != null">{{
          nickName
        }}</router-link>
        <div class="user_info_box">
          <div class="account">
            <span>{{ nickName }}</span>
            <a href="" @click="loginOut" v-if="nickName != null">退出</a>
          </div>
          <div class="user_pic">
            <img :src="userPic" alt="" />
          </div>
        </div>
      </li>
      <li>
        <router-link to="/register" v-if="nickName == null"
          >免费注册</router-link
        >
      </li>
    </ul>
    <ul>
      <slot name="cancel"></slot>
      <li class="my_taobao">
        <el-dropdown>
          <span class="el-dropdown-link">
            我的淘宝
            <el-icon class="el-icon--right">
              <IEpArrowDown />
            </el-icon>
          </span>
          <template #dropdown>
            <el-dropdown-menu>
              <el-dropdown-item @click="() => router.push('/my/collect')"
                >我的收藏</el-dropdown-item
              >
              <el-dropdown-item @click="() => router.push('/my/history')"
                >我的足迹</el-dropdown-item
              >
            </el-dropdown-menu>
          </template>
        </el-dropdown>
      </li>
      <li><router-link to="/cart">购物车</router-link></li>
    </ul>
  </header>
</template>

<script setup>
import { ref, computed } from "vue";
import { useRouter, useRoute } from "vue-router";
import useUserStore from "@/store/user";
import useProductStore from "@/store/product";

const productStore = useProductStore();

const router = useRouter();
const isShow = ref(false);
const userStore = useUserStore();

function myTaobaoMouseOver() {
  isShow.value = !isShow.value;
}
//显示账号名称
const nickName = computed(() => userStore.nickName);
const userPic = computed(() => userStore.userPic);

//刷新数据
(() => {
  userStore.login();
})();

//退出登录
function loginOut(e) {
  userStore.loginOut();
  productStore.loadLoveProducts();
  e.preventDefault();
}
</script>

<style lang="scss" scoped>
.taobao_header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 10px 80px;
  border-bottom: 1px solid #eee;
  ul {
    li {
      display: inline-block;
      &.my_taobao {
        cursor: pointer;
      }
      a {
        margin: 0 10px;
      }
      &.user_nav {
        position: relative;
        .user_info_box {
          position: absolute;
          top: 17px;
          left: 20px;
          height: 120px;
          text-align: center;
          padding: 5px 20px;
          background-color: #fff;
          display: none;
          .user_pic {
            width: 80px;
            height: 80px;
            margin: 0 auto;
            img {
              width: 100%;
              height: 100%;
              border-radius: 50%;
            }
          }
          .account {
            display: flex;
            justify-content: space-between;
            align-items: center;
            a {
              display: block;
              width: 50px;
            }
          }
        }
        &:hover {
          .user_info_box {
            display: block;
          }
        }
      }
    }
  }
}
:deep(span) {
  outline: none;
}
</style>