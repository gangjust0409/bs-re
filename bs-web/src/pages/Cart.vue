<template>
  <div class="cart_container">
    <menu-header>
      <template #cancel>
        <li style="display: inline-block; margin-right: 10px">
          <router-link to="/home" style="color: #ff0036">返回首页</router-link>
        </li>
      </template>
    </menu-header>
    <menu-search></menu-search>
    <div class="product_content" v-if="cartInfo.count > 0">
      <!-- 头部结算 -->
      <div class="cart_header">
        <div class="title">购物车（全部 {{ cartInfo.count }} ）</div>
        <div class="right">
          <span>不包含运费</span>
          <span class="price"
            >￥{{ parseFloat(cartInfo.totalPrice).toFixed(2) }}</span
          >
          <el-button size="large" type="primary" round @click="result"
            >结算</el-button
          >
        </div>
      </div>
      <!-- 商品cart -->
      <div class="cart_content">
        <div class="cart_content_titles">
          <div>
            <el-checkbox
              v-model="cartInfo.ifCheckedAll"
              :indeterminate="cartInfo.indeterminate"
              @change="handlerCartChange($event)"
              >全选</el-checkbox
            >
          </div>
          <div>商品信息</div>
          <div>单价</div>
          <div>数量</div>
          <div>金额</div>
          <div>操作</div>
        </div>
        <div class="cart_content_box">
          <div class="cart_list">
            <div
              class="cart_list_item"
              v-for="(shop, i) in cartInfo.shopItemTos"
              :key="i"
              v-show="shop.carts.length > 0"
            >
              <div class="shop">
                <el-checkbox
                  v-model="shop.ifCheckedAll"
                  :indeterminate="shop.indeterminate"
                  @change="handlerCartChange($event, shop)"
                ></el-checkbox>
                <span>店铺：{{ shop.shopName }}</span>
              </div>
              <div :class="{ cart_list_box: true, active: shop.ifCheckedAll }">
                <div
                  v-for="(cart, i) in shop.carts"
                  :key="i"
                  :class="{ cart_item: true, active: cart.checked }"
                >
                  <div class="cart_check">
                    <el-checkbox
                      v-model="cart.checked"
                      @change="handlerCheckedCart($event, shop, cart)"
                    ></el-checkbox>
                  </div>
                  <div class="cart_info">
                    <img
                      :src="cart.productPic"
                      :alt="cart.productTitle"
                      :title="cart.productTitle"
                    />
                    <a href="">{{ cart.productTitle }}</a>
                    <div class="attrs" v-for="(attr, i) in cart.attrs" :key="i">
                      {{ attr.attrName }}：{{ attr.attrValue }}<br />
                    </div>
                  </div>
                  <div class="dan_price">
                    ￥{{ parseFloat(cart.price).toFixed(2) }}
                  </div>
                  <div class="count">
                    <el-input-number
                      v-model="cart.count"
                      size="small"
                      :min="1"
                      :max="1000"
                      @change="cartCountChange(shop, cart)"
                    />
                  </div>
                  <div class="price">
                    ￥{{ parseFloat(cart.totalMoney).toFixed(2) }}
                  </div>
                  <div>
                    <a href="" @click="delCartProduct($event, cart.productId)"
                      >删除</a
                    >
                  </div>
                </div>
              </div>
            </div>
          </div>
        </div>
        <!-- 底部结算 -->
        <el-affix position="bottom" :offset="0">
          <div class="bottom_result">
            <div class="left">
              <div>
                <el-checkbox
                  v-model="cartInfo.ifCheckedAll"
                  :indeterminate="cartInfo.indeterminate"
                  @change="handlerCartChange($event)"
                  >全选</el-checkbox
                >
              </div>
              <div class="delete">
                <a href="" @click="delCartProduct">删除</a>
              </div>
            </div>
            <div class="right">
              <div>已选商品 {{ cartInfo.checkedCount }} 件</div>
              <div>
                合计（不含运费）：<span class="price"
                  >￥{{ parseFloat(cartInfo.totalPrice).toFixed(2) }}</span
                >
              </div>
              <el-button size="large" type="primary" round @click="result"
                >结算</el-button
              >
            </div>
          </div>
        </el-affix>
      </div>
    </div>
    <!-- 空的购物车 -->
    <div v-else>
      <el-empty
        description="暂无商品信息"
        image="http://localhost:81/upload/tb/cart_empty.png"
      >
        还没有商品？<router-link to="/home">去购物</router-link>
      </el-empty>
    </div>
    <el-backtop :right="100" :bottom="100" />
    <menu-footer></menu-footer>
  </div>
</template>

<script setup>
import { ref } from "vue";
import { useRouter } from "vue-router";
import {
  queryCurrentUserCarts,
  changeCartCheckedCount,
  checkedAllOrShop,
  delCart,
} from "@/api/product";

const router = useRouter();
//购物车列表
const cartInfo = ref({});

//加载购物车信息
async function loadQueryCurrentUserCarts() {
  const { code, data } = await queryCurrentUserCarts();
  if (code == 200) {
    cartInfo.value = data;
  }
}
//点击商品改变数量
function cartCountChange({ shopId }, { productId, count }) {
  changeCart({ shopId, productId, count });
}

//改变选中状态和改变数量
async function changeCart(form) {
  const { code } = await changeCartCheckedCount(form.shopId, form);
  //刷新数据
  if (code == 200) {
    loadQueryCurrentUserCarts();
  }
}
//点击全选和选中店铺时
async function handlerCartChange(checkAll, shop) {
  const form = { checkAll };
  if (shop != undefined) {
    form.shopId = shop.shopId;
  }
  const { code } = await checkedAllOrShop(form);
  //刷新数据
  if (code == 200) {
    loadQueryCurrentUserCarts();
  }
}
//点击选中商品框
function handlerCheckedCart(isChecked, { shopId }, { productId }) {
  changeCart({ isChecked, shopId, productId });
}
//点击删除
async function delCartProduct(e, cartId) {
  e.preventDefault();
  const { code } = await delCart(cartId);
  //刷新数据
  if (code == 200) {
    loadQueryCurrentUserCarts();
  }
}

//点击结算
function result() {
  router.push("/pay/result");
}
//以下加载信息
loadQueryCurrentUserCarts();
</script>

<style scoped lang="scss">
.cart_container {
  .product_content {
    margin: 0 155px;
    padding: 0;
    border-radius: 20px;
    .cart_header {
      display: flex;
      align-items: center;
      justify-content: space-between;
      padding: 10px 0;
      border-bottom: 1px solid #e6e6e6;
      padding: 20px 20px 10px 20px;
      .title {
        font-size: 16px;
      }
      .right {
        display: flex;
        align-items: center;
        span {
          @extend .title;
          margin-right: 15px;
        }
      }
    }
    .cart_content {
      padding: 20px;
      .cart_content_titles {
        display: flex;
        justify-content: space-between;
        align-items: center;
        padding: 0 15px;
        div {
          &:nth-of-type(2) {
            width: 385px;
          }
        }
      }
      .cart_content_box {
        .cart_list {
          .cart_list_item {
            .shop {
              display: flex;
              align-items: center;
              margin: 10px 0;
              span {
                margin-left: 10px;
              }
            }
            .cart_list_box {
              border-radius: 10px 10px;
              background-color: #f5f5f5;
              .cart_item {
                display: flex;
                padding: 15px;
                justify-content: space-between;

                .cart_check {
                  width: 10px;
                }
                .cart_info {
                  width: 500px;
                  display: flex;
                  img {
                    width: 70px;
                    height: 70px;
                    margin-right: 20px;
                  }
                  a:hover {
                    text-decoration: underline;
                    color: #ff0036;
                  }
                }
                .attrs {
                  width: 300px;
                  line-height: 2;
                  font-size: 12px;
                  color: #9c9c9c;
                  margin: 0 15px 0 10px;
                }
                .count {
                  width: 100px;
                }
                &.active {
                  background-color: #ffe6eb;
                  &:first-of-type {
                    border-radius: 10px 10px 0 0;
                  }
                  &:last-of-type {
                    border-radius: 0 0 10px 10px;
                  }
                }
              }
              &.active {
                border-radius: 10px 10px;
                background-color: #ffe6eb;
                border: 1px solid #ff0036;
              }
            }
          }
        }
      }
      .bottom_result {
        display: flex;
        align-items: center;
        justify-content: space-between;
        padding: 15px 0;
        background-color: #fff;
        border-top: 1px solid #e6e6e6;
        .left {
          display: flex;
          align-items: center;
          div {
            margin-right: 15px;
          }
        }
        .right {
          @extend .left;
          div {
            margin-right: 15px;
          }
        }
      }
    }
  }
}
</style>