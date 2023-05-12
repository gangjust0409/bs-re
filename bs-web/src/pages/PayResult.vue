<template>
  <div class="pay_result_container">
    <menu-header>
      <template #cancel>
        <li style="display: inline-block; margin-right: 10px">
          <router-link to="/home" style="color: #ff0036">返回首页</router-link>
        </li>
      </template>
    </menu-header>
    <menu-search></menu-search>
    <div class="product_content">
      <el-steps :active="1">
        <el-step title="确定订单" :icon="List" />
        <el-step title="支付成功" :icon="SuccessFilled" />
        <el-step title="支付失败" :icon="CircleCloseFilled" />
      </el-steps>
      <!-- 地址 -->
      <div class="address_box" v-if="addrssList.length > 0">
        <div
          v-for="(address, i) in addrssList"
          :key="i"
          :class="{ address_item: true, default: address.defaultAddress == 1 }"
        >
          <div class="address_info">
            <el-icon v-if="address.defaultAddress == 1"
              ><IEpCircleCheckFilled /></el-icon
            >{{ address.address }}
          </div>
          <el-button
            v-if="address.defaultAddress == 2"
            type="primary"
            size="small"
            @click="startAddress(address)"
            >启用</el-button
          >
        </div>
      </div>
      <router-link v-else to="/my/address">点击添加地址</router-link>
      <!-- 商品信息 -->
      <div class="product_confirm">
        <div class="title">确认订单信息</div>
        <div class="product_info">
          <div class="product_titles">
            <div class="product_title">店铺宝贝</div>
            <div class="product_title">商品属性</div>
            <div class="product_title">单价</div>
            <div class="product_title">数量</div>
            <div class="product_title">小计</div>
          </div>
          <div class="product_list">
            <div
              class="product_item"
              v-for="(shop, i) in resultInfo.shops"
              :key="i"
            >
              <div class="shop_info">
                店铺：<a href="">{{ shop.shopName }}</a>
              </div>
              <div class="products">
                <div
                  class="product_item"
                  v-for="(product, i) in shop.products"
                  :key="i"
                >
                  <div class="product_pic">
                    <img :src="product.productPic" alt="" />
                  </div>
                  <div class="product_title">
                    <a href=""> {{ product.productTitle }}</a>
                    <span>发货时间：现货，付款后48小时内发货</span>
                  </div>
                  <div class="attrs">
                    <div
                      class="attr"
                      v-for="(attr, i) in product.attrs"
                      :key="i"
                    >
                      {{ attr.attrName }}：{{ attr.attrValue }}
                    </div>
                  </div>
                  <div class="product_price">
                    ￥{{ parseFloat(product.productPrice).toFixed(2) }}
                  </div>
                  <div class="count">x{{ product.productCount }}</div>
                  <div class="price">
                    ￥{{ parseFloat(product.subtotal).toFixed(2) }}
                  </div>
                </div>
              </div>
              <div class="fare">
                运费：<span class="price">￥{{ shop.fare }}</span>
              </div>
              <div class="product_total">
                合计（包括运费）：<span class="price"
                  >￥{{ shop.totalMoney }}</span
                >
              </div>
            </div>
          </div>
        </div>
      </div>
      <!-- 按钮 -->
      <div class="submit_info">
        <div class="shifuk">
          实付款：<span class="price"
            >￥{{ parseFloat(resultInfo.payPrice).toFixed(2) }}</span
          >
        </div>
        <div class="address_user">
          <div>
            寄送至：
            <span>{{ addressInfo.address }}</span>
          </div>
          <div>收货人：{{ addressInfo.userInfo }}</div>
        </div>
        <PayType :payType="payType" @payTypeChange="changePayType"></PayType>
        <div class="sub_btn">
          <router-link to="/cart" class="cancel">
            <el-icon><IEpArrowLeft /></el-icon>
            <span>返回购物车</span>
          </router-link>
          <el-button type="primary" size="large" round @click="paySubmit"
            >提交</el-button
          >
        </div>
      </div>
    </div>
    <el-backtop :right="100" :bottom="100" />
    <menu-footer></menu-footer>
  </div>
</template>
<script setup>
import { ref, computed, reactive, getCurrentInstance } from "vue";
import { useRouter } from "vue-router";
import {
  SuccessFilled,
  CircleCloseFilled,
  UplListoad,
} from "@element-plus/icons-vue";
import { cartResultProducts, submitProduct } from "@/api/product";
import useUserStore from "@/store/user";
import useOrderStore from "@/store/order";
import { dateTableEmits } from "element-plus/es/components/calendar/src/date-table";
import PayType from "@/components/PayType.vue";
const { proxy } = getCurrentInstance();
//路由
const router = useRouter();
const userStore = useUserStore();
const orderStore = useOrderStore();
//结算信息
const resultInfo = ref({});
//当前地址信息
const addressInfo = reactive({
  userInfo: null,
  address: null,
});
//支付方式
const payType = ref(0);
const payForm = reactive({});

//切换支付方式，可以存在数据库中进行保存
function changePayType(type) {
  payType.value = type;
  payForm.payType = type;
}

//计算属性
const addrssList = computed(() => userStore.addresss);
(async () => {
  //加载地址信息
  userStore.getCurrentUserAddress();
  //加载结算商品信息
  const { code, data } = await cartResultProducts();
  if (code == 200) {
    resultInfo.value = data;
  }
  //当前选中的地址信息
  addrssList.value.forEach((addr) => {
    if (addr.defaultAddress == 1) {
      const ads = addr.address.split("-");
      addressInfo.userInfo = ads[0] + ads[1];
      addressInfo.address = ads[2];
    }
  });
})();

//提交订单
async function paySubmit() {
  //是否已经填写地址
  if (addrssList.value.length <= 0 ) {
    proxy.$msg.warning('请先填写地址信息！');
    return;
  }
  //确定是否立即支付
  if (payType.value === 0) {
    proxy.$msgbx
      .confirm(`由于您未选择支付方式，过一会在支付？`, "提示", {
        confirmButtonText: "确 定",
        cancelButtonText: "取 消",
        type: "warning",
      })
      .then(async () => {
        const { code, msg } = await submitProduct({ payType: payType.value });
        if (code == 200) {
          router.replace("/pay/success");
        }
      })
      .catch(() => {});
  } else {
    //提交并支付
    const {code,msg} = await orderStore.pay(payForm);
    if (code == 200) {
      router.replace("/pay/success");
    } else {
      proxy.$msg.error(msg);
    }
  }
}
//启用地址
async function startAddress({ addressId }) {
  const res = await userStore.startAddress(addressId);
  if (res) {
    //加载地址信息
    userStore.getCurrentUserAddress();
  }
}
</script>

<style scoped lang="scss">
.product_content {
  .address_box {
    margin: 20px 0;
    .address_item {
      display: flex;
      align-items: center;
      justify-content: space-between;
      padding: 5px 10px;
      margin: 5px 0;
      cursor: pointer;

      &.default,
      &:hover {
        background-color: rgba($color: #ff0036, $alpha: 0.5);
        color: #fff;
        border-radius: 10px;
      }
      .address_info {
        display: flex;
        align-items: center;
        i {
          margin-right: 10px;
        }
      }
    }
  }
  .product_confirm {
    .title {
      font-size: 15px;
      margin: 15px 0;
      font-family: "黑体";
      font-weight: 600;
    }
    .product_info {
      .product_titles {
        display: flex;
        align-items: center;
        .product_title {
          width: 225px;
          padding: 5px 0;
          margin-right: 2px;
          border-bottom: 3px solid #b2d1ff;
          text-align: center;
        }
      }
      .product_list {
        .product_item {
          .shop_info {
            margin: 10px 0;
          }
          .products {
            .product_item {
              padding: 5px 0;
              border-top: 1px dashed #b2d1ff;
              border-bottom: 1px dashed #b2d1ff;
              display: flex;
              align-items: center;
              .product_pic {
                width: 50px;
                height: 50px;
                img {
                  width: 100%;
                  height: 100%;
                }
              }
              .product_title {
                width: 170px;
                margin-left: 5px;

                display: flex;
                flex-direction: column;
                a {
                  display: block;
                  overflow: hidden;
                  text-overflow: ellipsis;
                  white-space: nowrap;
                }
                span {
                  font-size: 12px;
                  color: #6c6c6c;
                }
              }
              .attrs {
                width: 220px;

                .attr {
                  text-align: center;
                  font-size: 12px;
                }
              }
              .product_price {
                width: 218px;
                text-align: center;
                font-size: 12px;
              }
              .count {
                @extend .product_price;
                width: 220px;
              }
              .price {
                @extend .product_price;
                font-size: 16px;
              }
            }
          }
          .fare {
            text-align: right;
            padding: 15px 10px;
            font-size: 16px;
            background-color: rgb(242, 247, 255);
            border: 1px solid #fff;
          }
          .product_total {
            @extend .fare;
            border-bottom: 1px solid #b2d1ff;
          }
        }
      }
    }
  }
  .submit_info {
    text-align: right;
    display: flex;
    flex-direction: column;
    justify-content: end;
    line-height: 2;
    .shifuk {
      font-size: 18px;
    }
    .address_user {
      margin: 15px 0;
      div,
      span {
        font-size: 16px;
      }
    }

    .sub_btn {
      display: flex;
      justify-content: end;
      margin: 25px 10px;
      .cancel {
        display: flex;
        align-items: center;
        margin-right: 15px;
      }
    }
  }
}
</style>