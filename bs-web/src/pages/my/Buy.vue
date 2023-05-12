<template>
  <div class="buy_container">
    <!-- 导航 -->
    <div class="product_content">
      <div class="buy_navbar">
        <div
          v-for="(nav, i) in orderNavbarList"
          :key="i"
          :class="{ buy_navbar_item: true, active: nav.active }"
          @click="changeNavbar(nav)"
        >
          {{ nav.title }}
        </div>
      </div>
    </div>
    <!-- 目标 -->
    <div class="product_content" v-if="orders.length > 0">
      <!-- 搜索 -->
      <el-input
        placeholder="搜索订单号"
        v-model="orderSn"
        class="input-with-select"
      >
        <template #append>
          <el-button :icon="Search" @click="searchOrder" />
        </template>
      </el-input>
      <!-- 表头 -->
      <div class="order_titles">
        <div class="order_title long">宝贝</div>
        <div class="order_title">单价</div>
        <div class="order_title count">数量</div>
        <div class="order_title shifuk">实付款</div>
        <div class="order_title">交易状态</div>
        <!-- <div class="order_title">交易操作</div> -->
      </div>
      <!-- 订单信息 -->
      <div
        class="order_content_box"
        v-for="order in orders"
        :key="order.orderSn"
      >
        <div class="order_content_title">
          <div class="order_content_title_left">
            <el-checkbox></el-checkbox>
            <span
              >{{ order.createDate
              }}<small>订单号：{{ order.orderSn }}</small></span
            >
          </div>
          <div class="order_content_title_right" @click="deleteOrder(order)">
            <el-button type="primary" size="small" circle>
              <template #icon>
                <el-icon><IEpDeleteFilled /></el-icon>
              </template>
            </el-button>
          </div>
        </div>
        <!-- 商品信息 -->
        <div class="product_info_item">
          <div class="product_list">
            <div
              class="product_item"
              v-for="item in order.items"
              :key="item.productId"
            >
              <div class="product_pic">
                <img :src="item.productPic" alt="" />
              </div>
              <div class="product_info">
                <div class="product_title">
                  {{ item.productTitle }}
                </div>
                <div class="attrs" v-for="(attr, i) in item.attrs" :key="i">
                  {{ attr.attrName }}：{{ attr.attrValue }}
                </div>
              </div>
              <div class="product_price">
                ￥{{ parseFloat(item.productPrice).toFixed(2) }}
              </div>
              <div class="count">x{{ item.productCount }}</div>
            </div>
          </div>
          <!-- 总的金额 -->
          <div class="sum_box">
            <div class="left_border">
              <div class="total_price">
                ￥{{ parseFloat(order.totalPrice).toFixed(2) }}
                <span>（含运费 {{ parseFloat(order.fare).toFixed(2) }}）</span>
              </div>
            </div>
            <div class="pay_status">
              <el-tag class="ml-2" type="success">{{
                getStatusName(order.status)
              }}</el-tag>
            </div>
          </div>
        </div>
        <!-- 过期时间 -->
        <div class="expire_time_box" v-if="order.time > 1">
          <span
            >支付时间还剩
            <el-countdown
              :value="Date.now() + order.time * 1000"
              @finish="orderStore.myBuy({})"
            />
            秒，</span
          >
          <el-button type="primary" size="small" @click="goPay(order)"
            >去支付</el-button
          >
        </div>
      </div>
    </div>
    <!-- 空的 -->
    <el-empty v-else description="暂无订单信息！">
      <el-link @click="router.replace('/home')">去添加！</el-link>
    </el-empty>
    <el-backtop :right="100" :bottom="100" />
    <!-- 支付方式 -->
    <el-dialog
      v-model="payTypeVisible"
      title="支付方式"
      width="30%"
      :before-close="handlerPayTypeClose"
    >
      <PayType @payTypeChange="payTypeChange"></PayType>
      <template #footer>
        <span class="dialog-footer">
          <el-button @click="handlerPayTypeClose">取 消</el-button>
          <el-button type="primary" @click="payOrder"> 支 付 </el-button>
        </span>
      </template>
    </el-dialog>
  </div>
</template>

<script setup>
import { ref, reactive, computed, onMounted, getCurrentInstance } from "vue";
import { Search } from "@element-plus/icons-vue";
import useOrderStore from "@/store/order";
import { deleteOrderData } from "@/api/product";
import { useRouter } from "vue-router";
import PayType from "@/components/PayType.vue";
//order store
const orderStore = useOrderStore();
const router = useRouter();
const { proxy } = getCurrentInstance();
const list = ref([]);
const payForm = reactive({});

//订单头部导航
const orderNavbarList = computed(() => orderStore.orderNavList);
//我的订单
const orders = computed(() => orderStore.orderList);
//搜索订单号
const orderSn = ref("");
//控制支付方式
const payTypeVisible = ref(false);

//根据状态标识获取对应状态名称
function getStatusName(status) {
  return orderStore.getStatusName(status);
}

//更改导航
function changeNavbar(navbar) {
  orderStore.persistence(navbar);
}
//搜索订单
function searchOrder() {
  orderStore.myBuy({ orderSn: orderSn.value });
}
//删除订单
function deleteOrder(order) {
  proxy.$msgbx
    .confirm(`确定是否删除订单号为【${order.orderSn}】吗？`, "温馨提示", {
      confirmButtonText: "确 定",
      cancelButtonText: "取 消",
      type: "warning",
    })
    .then(async () => {
      const { code } = await deleteOrderData(order.orderSn);
      if (code == 200) {
        proxy.$msg.success("删除订单成功！");
        //删除成功后，刷新数据
        orderStore.myBuy({});
      }
    })
    .catch(() => {});
}
//关闭支付
function handlerPayTypeClose() {
  payTypeVisible.value = false;
}
//点击支付
async function payOrder() {
  const { code, msg } = await orderStore.pay(payForm);
  if (code == 200) {
    router.replace("/pay/success");
  } else {
    proxy.$msg.error(msg);
  }
}
//点击支付
function goPay(order) {
  payTypeVisible.value = true;
  payForm.orderSn = order.orderSn;
}
//接收支付方式
function payTypeChange(type) {
  if (type == 1) {
    //支付宝
  } else if (type == 2) {
    //本地支付
    payForm.payType = type;
  }
}
(async () => {
  //加载我的宝贝
  const code = await orderStore.myBuy({});
  if (code == 403) {
    //跳转到登录页
    router.replace("/login");
  }
})();
</script>

<style scoped lang="scss">
.buy_container {
  .product_content {
    margin-bottom: 20px;
    .buy_navbar {
      display: flex;

      .buy_navbar_item {
        margin: 0 10px;
        font-size: 16px;
        padding: 5px 0;
        cursor: pointer;
        &.active {
          border-bottom: 2px solid #f40;
        }
      }
    }
    .order_titles {
      display: flex;
      align-items: center;
      margin: 20px 0;
      background-color: #f5f5f5;
      border: 1px solid #e8e8e8;
      .order_title {
        padding: 5px 10px;
        color: #3c3c3c;
        text-align: center;
        cursor: pointer;
        &.long {
          width: 300px;
        }
        &.count {
          width: 80px;
        }
        &.shifuk {
          width: 110px;
        }
      }
    }
    .order_content_box {
      border: 1px solid #e8e8e8;
      margin-bottom: 20px;
      .order_content_title {
        display: flex;
        justify-content: space-between;
        align-items: center;
        background-color: #f5f5f5;
        padding: 5px 10px;
        .order_content_title_left {
          display: flex;
          align-items: center;
          span {
            margin-left: 10px;
            small {
              color: #000;
              opacity: 0.7;
              margin-left: 5px;
            }
          }
        }
      }
      .product_info_item {
        display: flex;
        padding: 0px 10px;
        .product_list {
          .product_item {
            display: flex;
            padding: 20px 0;
            border-bottom: 1px solid #e8e8e8;
            &:last-of-type {
              margin-bottom: 0;
              border-bottom: 0;
            }
            .product_pic {
              margin-right: 10px;
              width: 100px;
              height: 100px;
              img {
                width: 100%;
                height: 100%;
              }
            }
            .product_info {
              width: 145px;
              .product_title {
                font-size: 12px;
              }
              .attrs {
                font-size: 12px;
                color: #9e9e9e;
              }
            }
            .product_price {
              margin-left: 34px;
              line-height: 33px;
            }
            .count {
              width: 50px;
              margin-left: 20px;
              line-height: 32px;
            }
          }
        }
        .sum_box {
          display: flex;
          align-items: center;
          .left_border {
            width: 112px;
            height: 100%;
            border-left: 1px solid #e8e8e8;
            border-right: 1px solid #e8e8e8;
            .total_price {
              padding-top: 50px;
              display: flex;
              flex-direction: column;
              text-align: center;
              color: #c00000;
              font-size: 15px;
              span {
                font-size: 12px;
                color: #9e9e9e;
                display: block;
              }
            }
          }
          .pay_status {
            width: 104px;
            height: 100%;
            padding-top: 55px;
            text-align: center;
          }
        }
      }
      .expire_time_box {
        display: flex;
        align-items: center;
        justify-content: end;
        background-color: #f5f5f5;
        padding: 5px 10px;
        :deep(.el-statistic) {
          display: inline-block;
        }
      }
    }
  }
}
:deep(.pay_status) {
  justify-content: start;
}
</style>