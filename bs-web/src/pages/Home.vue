<template>
  <div id="home">
    <menu-header></menu-header>
    <menu-search :keyword="keyword"></menu-search>
    <div class="product_content">
      <!-- 轮播图等 -->
      <div class="search_keyword_swiper_box">
        <!-- 搜索关键字面板 -->
        <div class="search_keyword_box">
          <!-- 第一模块 -->
          <div
            class="row"
            v-for="{ catelogName, catelogId, children,icon } in catelogs"
            :key="catelogId"
          >
            <!-- 显示搜索关键 -->
            <div class="title_box">
              <div class="title_box_left">
                <span><svg-icon :style="{ width: 20, height: 20}" :name="icon"></svg-icon></span>
                <span class="desc">{{ catelogName }}</span>
              </div>
              <div class="title_box_right">
                <a
                  v-for="a in children"
                  :key="a.catelogId"
                  href=""
                  @click="search($event, a.catelogName)"
                  >{{ a.catelogName }}</a
                >
              </div>
            </div>
            <!-- 隐藏显示轮播图展示区 -->
            <div class="content_box"></div>
          </div>
        </div>
        <!-- 轮播图面板 -->
        <div class="swiper_box">
          <el-carousel
            :interval="5000"
            ref="swiperRef"
            arrow="never"
            height="312px"
            trigger="click"
          >
            <el-carousel-item
              v-for="swiper in swipers"
              :key="swiper.swiperId"
              style="cursor: pointer"
              @click="toDetail(swiper.productId)"
            >
              <img :src="swiper.swiperUrl" alt="" />
            </el-carousel-item>
          </el-carousel>
          <div class="swiper_btn prev" @click="changeSwiper('prev')"></div>
          <div class="swiper_btn next" @click="changeSwiper('next')"></div>
        </div>
        <!-- 登录面板 -->
        <div class="login_box">
          <div class="user_info" v-if="nickName" @click="toUserInfo">
            <img :src="userPic" :alt="nickName" :title="nickName" />
            <h6>hi，你好 <br />{{ nickName }}</h6>
          </div>
          <div class="user_info" v-if="!nickName">
            <img
              src="https://wwc.alicdn.com/avatar/getAvatar.do?userId=2200535085316&width=160&height=160&type=sns"
              alt=""
            />
            <h6>hi，你好</h6>
          </div>
          <div class="login_register_btn" v-if="!nickName">
            <el-button type="primary" round @click="toLogin">登录</el-button>
            <el-button round @click="() => router.push('/register')"
              >注册</el-button
            >
          </div>
        </div>
      </div>
      <!-- 猜你喜欢 -->
      <div class="menu_title">猜你喜欢</div>
      <!-- 商品展示区 -->
      <product-info-list
        :list="products"
        @collectLoad="productStore.loadLoveProducts"
      ></product-info-list>
    </div>
    <el-backtop :right="100" :bottom="100" />
    <menu-footer></menu-footer>
  </div>
</template>

<script setup>
import { ref, computed, nextTick, onBeforeMount } from "vue";
import { useRouter, useRoute, onBeforeRouteLeave } from "vue-router";
import { catelogList } from "@/api/catelog";
import { swiperList } from "@/api/swiper";
import useUserStore from "@/store/user";
import useProductStore from "@/store/product";

const router = useRouter();
const userStore = useUserStore();
const productStore = useProductStore();

const keyword = ref();
const swiperRef = ref();
//分类列表
const catelogs = ref([]);
//轮播图列表
const swipers = ref([]);

//用户名
const nickName = computed(() => userStore.nickName);
//用户头像
const userPic = computed(() => userStore.userPic);
//猜你喜欢
const products = computed(() => productStore.loveProductList);

//跳转登录
function toLogin() {
  router.push("/login");
}
//跳转用户详情
function toUserInfo() {
  router.push("/my/user/info");
}
//跳转搜索
function search(e, catelogName) {
  keyword.value = catelogName;
  router.push({ path: "/search", query: { keyword: keyword.value } });
  e.preventDefault();
}

//切换轮播图
function changeSwiper(type) {
  if (type === "prev") {
    swiperRef.value.prev();
  } else if (type === "next") {
    swiperRef.value.next();
  }
}
//加载首页分类
(async () => {
  const { code, data } = await catelogList();
  if (code == 200) {
    catelogs.value = data;
  }
})();
//加载轮播图
(async () => {
  const { code, data } = await swiperList();
  if (code == 200) {
    swipers.value = data;
  }
})();
//加载猜你喜欢

//跳转到详情页
function toDetail(productId) {
  router.push({
    path: "/detail",
    query: {
      productId,
    },
  });
}
//请求登录后的用户信息
// onBeforeRouteLeave(async () => {
//   console.log("当路由改变之后");
//   //执行方法
//   if (token.value) {
//     console.log("进入" + token.value);
//     await nextTick();
//     userStore.login();
//   }
//   console.log(userStore);
// });
onBeforeMount(async () => {
  await nextTick();
  //执行方法
  userStore.login();
  //刷新猜你喜欢列表
  productStore.loadLoveProducts();
});
</script>

<style scoped lang="scss">
#home {
  .search_keyword_swiper_box {
    display: flex;
    .search_keyword_box {
      width: 290px;
      background-color: #f6f6f6;
      border-radius: 10px;
      .row {
        padding: 10px;
        border-bottom: 1px solid #eee;
        &:last-of-type {
          border-bottom: 0;
        }
        .title_box {
          display: flex;
          align-items: center;
          .title_box_left {
            display: flex;
            flex-direction: column;
            text-align: center;

            span {
              &.iconfont {
                font-size: 20px;
              }
              &.icon-nvren01 {
                font-size: 25px;
              }
              &.desc {
                font-size: 14px;
                font-weight: 100;
                color: #333;
              }
            }
          }
          .title_box_right {
            margin-left: 10px;
            flex: 3;
            height: 40px;
            overflow: hidden;
            a {
              display: inline-block;
              margin-right: 15px;
              color: #9b9b9b;
              &:hover {
                color: #ff0036;
              }
            }
          }
        }
      }
    }
    .swiper_box {
      width: 610px;
      margin-left: 10px;
      position: relative;
      img {
        width: 100%;
        height: 100%;
      }
      &:hover .swiper_btn {
        display: block;
      }
      :deep(.el-carousel__button) {
        width: 15px;
        height: 15px;
        border-radius: 50%;
      }
      .swiper_btn {
        width: 35px;
        height: 81px;
        position: absolute;
        top: 30%;
        display: none;
        opacity: 0.3;
        cursor: pointer;
        &.prev {
          background: url(@/assets/images/swiper_btn.png) no-repeat -72px 0;
        }
        &.next {
          background: url(@/assets/images/swiper_btn.png) no-repeat 0px 0;
          right: 0;
        }
      }
    }
    .login_box {
      flex: 1;
      padding: 50px 0;
      .user_info {
        display: flex;
        flex-direction: column;
        justify-content: center;
        text-align: center;
        cursor: pointer;
        img {
          width: 80px;
          height: 80px;
          border-radius: 50%;
          margin: 0 auto;
        }
        h6 {
          margin: 15px 0;
        }
      }
      .login_register_btn {
        text-align: center;
      }
    }
  }
}
.menu_title {
  font-size: 16px;
  font-weight: 500;
  color: #333;
  height: 22px;
  line-height: 22px;
  margin: 25px 0 5px 20px;
}

//暂时轮播图样式

.el-carousel__item:nth-child(2n) {
  border-radius: 10px;
}

.el-carousel__item:nth-child(2n + 1) {
  border-radius: 10px;
}
</style>