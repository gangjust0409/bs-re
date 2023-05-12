<template>
  <div id="home">
    <menu-header>
      <template #cancel>
        <li style="display: inline-block; margin-right: 10px">
          <router-link to="/home" style="color: #ff0036">返回首页</router-link>
        </li>
      </template>
    </menu-header>
    <menu-search></menu-search>
    <!-- 店铺信息 -->
    <div class="product_content shop_content">
      <div class="shop" v-if="productForm.shop">
        <div class="shop_infos">
          <div class="shop_pic">
            <img :src="productForm.shop.shopPic" alt="" />
          </div>
          <div class="shop_info">
            <div class="shop_name">{{ productForm.shop.shopName }}</div>
            <div class="product_source">仿淘宝</div>
          </div>
        </div>
        <div class="shop_btns">
          <div class="shop_btn">
            <i class="iconfont icon-kefu"></i>
            <span>联系客服</span>
          </div>
          <div class="shop_btn">
            <i class="iconfont icon-dianpu"></i>
            <span>进入店铺</span>
          </div>
        </div>
      </div>
    </div>
    <!-- 商品详情信息 -->
    <div class="product_content">
      <div class="product_info_container">
        <!-- 商品信息 -->
        <div class="product_info_box">
          <div class="product_info_box_left">
            <!-- 缩略图 -->
            <div
              class="big_sl_img"
              ref="bSlRef"
              @mouseover="hoverSLFun"
              @mouseout="levelSLFun"
              @mousemove="moveTouch"
            >
              <img :src="bigUrl" alt="" />
              <!-- 遮罩层 -->
              <div
                class="mask"
                :style="{ left: x + 'px', top: y + 'px' }"
                v-show="mask"
              ></div>
            </div>
            <!-- 大图 -->
            <div class="big_bsl_img" v-show="mask">
              <div
                class="bigImgs"
                :style="{
                  backgroundImage: `url(${bigUrl})`,
                  backgroundPosition: xm + 'px ' + ym + 'px',
                }"
              ></div>
            </div>
            <!-- 切换图片 -->
            <div class="product_imgs">
              <div
                v-for="(sp, i) in productForm.smailPics"
                :class="{ product_img: true, active: sp == bigUrl }"
                :key="i"
              >
                <img
                  :src="sp"
                  alt=""
                  @click="handlerChangeSmailImg(null, sp)"
                />
              </div>
            </div>
          </div>
          <div class="product_info_box_right">
            <!-- 商品名称 -->
            <div class="product_title">
              {{ productForm.productTitle }}
            </div>
            <!-- 月销 -->
            <div class="moth">销量：{{ productForm.monthPin }}</div>
            <!-- 价格 -->
            <div class="detail_row">
              <label>价格：</label>
              <div class="price"><span>￥</span> {{ activePrice }}</div>
            </div>
            <!-- 选择商品 -->
            <div class="detail_row">
              <label>款式：</label>
              <div class="choose_product" ref="skusRef">
                <div
                  v-for="sku in productForm.skus"
                  :key="sku.skuId"
                  :class="{
                    choose_pro_item: true,
                    active:
                      sku.skuPic != null
                        ? bigUrl == sku.skuPic
                        : cartForm.skuId == sku.skuId,
                  }"
                  @click="handlerChangeSmailImg(sku, sku.skuPic)"
                >
                  <img v-if="sku.skuPic" :src="sku.skuPic" alt="" />
                  <span>{{ sku.skuName }}</span>
                </div>
              </div>
            </div>
            <!-- 属性 -->
            <div class="detail_attr_box" v-if="productForm.attrItem">
              <div
                class="detail_row"
                v-for="(e, i) in productForm.attrItems"
                :key="i"
              >
                <label>{{ e.title }}：</label>
                <div class="choose_product" ref="skusRef">
                  <div
                    v-for="(attr, i) in e.attrValues"
                    :key="attr.attrId"
                    :class="{
                      choose_pro_item: true,
                      active: i == 0,
                    }"
                  >
                    <span>{{ attr.attrValue }}</span>
                  </div>
                </div>
              </div>
            </div>
            <!-- 数量 -->
            <div class="detail_row">
              <label>数量：</label>
              <el-input-number v-model="count" :min="1" :max="1000" />
            </div>
            <!-- 加入购物车 -->
            <el-button type="primary" size="large" @click="addCart">
              <template #icon>
                <el-icon><IEpShoppingCartFull /></el-icon>
              </template>
              加入购物车
            </el-button>
          </div>
        </div>
        <!-- 宝贝详情 -->
        <div class="baby_detail">
          <div class="title"><span>宝贝详情</span> <i></i></div>
          <div class="sku_attrs">
            <div
              class="attr_item"
              v-for="(attr, i) in productForm.attrs"
              :key="i"
            >
              {{ attr }}
            </div>
            <!-- 详情展示图片 -->
            <div
              v-for="d in productForm.detailImages"
              :key="d"
              class="detail_imgs"
            >
              <img :src="d" alt="" />
            </div>
          </div>
        </div>
      </div>
    </div>
    <menu-footer></menu-footer>
  </div>
</template>

<script setup>
import { ref, reactive, defineProps, getCurrentInstance } from "vue";
import { useRouter, useRoute } from "vue-router";
import { productDetail, addCartProduct } from "@/api/product";

const route = useRoute();
const { proxy } = getCurrentInstance();
//商品id
const productId = ref(0);
//商品展示
const productForm = ref({});
//数量
const count = ref(1);
//需要添加进购物车的表单
const cartForm = reactive({});
//遮罩层
const mask = ref(false);
//当前选中的大图地址
//获取大图
const bigUrl = ref(null);
//当前选中的价格
const activePrice = ref(0);
// sku dom元素
const skusRef = ref();
// 鼠标坐标
const x = ref(0);
const y = ref(0);
const xm = ref(0);
const ym = ref(0);

//鼠标移入
function hoverSLFun(e) {
  mask.value = true;
}
//鼠标移出
function levelSLFun() {
  mask.value = false;
}
//鼠标移动时间
function moveTouch(e) {
  x.value = e.pageX - 200;
  y.value = e.pageY - 400;
  if (x.value <= 0) {
    x.value = 0;
  } else if (x.value >= 182) {
    x.value = 182;
  }
  if (y.value >= 248) {
    y.value = 248;
  } else if (y.value <= 0) {
    y.value = 0;
  }
  xm.value = -x.value - 50;
  ym.value = -y.value - 50;
}

//请求当前详情信息
(async () => {
  productId.value = route.query.productId;
  const { code, data } = await productDetail(productId.value);
  if (code == 200) {
    productForm.value = data;
    bigUrl.value = productForm.value.activePic;
    activePrice.value = parseFloat(productForm.value.productPrice).toFixed(2);
  }
})();
//点击切换大图
function handlerChangeSmailImg(sku, url) {
  if (url != null || url != undefined) {
    bigUrl.value = url;
  }
  if (sku != null) {
    cartForm.skuId = sku.skuId;
    activePrice.value = parseFloat(sku.skuPrice).toFixed(2);
  }
}
//点击加入购物车
async function addCart() {
  if (cartForm.skuId === undefined) {
    proxy.$msg.warning("请选择一种款式！");
    return;
  }
  //组装数据
  cartForm.productId = productForm.value.productId;
  cartForm.count = count.value;
  cartForm.shopId = productForm.value.shop.shopId;
  const { code, msg } = await addCartProduct(cartForm);
  if (code == 200) {
    proxy.$msg.success("添加购物车成功！");
  } else {
    proxy.$msg.warning(msg);
  }
}
</script>

<style scoped lang="scss">
.product_content {
  &.shop_content {
    border-radius: 100px;
    margin-bottom: 25px;
  }
  .shop {
    display: flex;
    justify-content: space-between;
    align-items: center;

    .shop_infos {
      display: flex;
      .shop_pic {
        width: 60px;
        height: 60px;
        border-radius: 50%;
        margin-right: 10px;
        padding: 3px;
        border: 2px solid #000;
        img {
          width: 100%;
          height: 100%;
          border-radius: 50%;
        }
      }
      .shop_info {
        .shop_name {
          font-size: 16px;
          font-family: "黑体";
          font-weight: 800;
        }
        .product_source {
          width: 65px;
          padding: 2px 10px;
          background-color: #ff0036;
          color: #fff;
          border-radius: 20px;
          margin-top: 15px;
        }
      }
    }
    .shop_btns {
      display: flex;
      .shop_btn {
        display: flex;
        margin-right: 20px;
        align-items: center;
        padding: 7px 10px;
        border: 1px solid #eee;
        border-radius: 30px;
        cursor: pointer;
        i {
          margin-right: 10px;
        }
      }
    }
  }
  .product_info_container {
    .product_info_box {
      display: flex;

      .product_info_box_left {
        width: 450px;
        margin-right: 25px;
        position: relative;
        .big_sl_img {
          width: 400px;
          height: 450px;
          border-radius: 10px;
          margin-bottom: 10px;
          position: relative;
          overflow: hidden;
          .mask {
            width: 200px;
            height: 200px;
            background: rgba($color: #ff0036, $alpha: 0.3);
            position: absolute;
            top: 0;
            left: 0;
            cursor: move;
          }
          img {
            border-radius: 10px;
          }
        }
        .big_bsl_img {
          width: 450px;
          height: 450px;
          position: absolute;
          top: 0;
          left: 400px;
          border-radius: 10px;
          z-index: 999;
          .bigImgs {
            width: 100%;
            height: 100%;
            position: relative;
            overflow: hidden;
            background-repeat: no-repeat;
            background-size: 800px 800px;
          }
        }
        .product_imgs {
          display: flex;
          padding-left: 10px;
          .product_img {
            width: 60px;
            height: 60px;
            margin-right: 15px;
            border-radius: 10px;
            cursor: pointer;
            &.active {
              padding: 1px;
              border: 1px solid #ff0036;
            }
            img {
              border-radius: 10px;
            }
          }
        }
      }
      .product_info_box_right {
        i,
        svg {
          width: 1.3em;
          height: 1.3em;
        }
        .product_title {
          font-size: 25px;
          font-weight: 800;
          font-family: "黑体";
          margin-bottom: 45px;
        }

        .detail_row {
          display: flex;
          align-items: center;
          margin: 20px 0;
          label {
            font-size: 15px;
            font-family: PingFangSC-Regular;
            color: #7f7f7f;
          }
          .price {
            font-size: 30px;
            span {
              font-size: 18px;
            }
          }
          .choose_product {
            flex: 2;
            display: flex;
            flex-wrap: wrap;
            margin-left: 15px;
            .choose_pro_item {
              font-size: 22px;
              display: flex;
              align-items: center;
              border: 1px solid #eee;
              padding: 5px;
              cursor: pointer;
              margin-right: 10px;
              margin-bottom: 10px;
              &.active {
                border: 1px solid #ff0036;
                color: #ff0036;
                background-color: rgba($color: #ff0036, $alpha: 0.1);
              }
              img {
                width: 30px;
                height: 30px;
                margin-right: 5px;
              }
            }
          }
        }
      }
      img {
        width: 100%;
        height: 100%;
      }
    }
    .baby_detail {
      margin-top: 30px;
      padding: 20px 0;

      .title {
        height: 40px;
        line-height: 15px;
        border-bottom: 1px solid #eee;
        position: relative;
        span {
          font-family: PingFang SC;
          font-size: 18px;
        }
        i {
          display: block;
          width: 15px;
          height: 2px;
          position: absolute;
          background-color: #ff0036;
          bottom: 0;
          left: 28px;
        }
      }
      .sku_attrs {
        display: flex;
        padding: 30px 180px 0 180px;
        flex-wrap: wrap;
        .attr_item {
          width: 33.33%;
          font-family: PingFangSC-Regular;
          font-size: 16px;
          line-height: 2;
          color: #7f7f7f;
          white-space: nowrap;
          overflow: hidden;
          text-overflow: ellipsis;
        }
        .detail_imgs {
          width: 100%;
          img {
            width: 100%;
          }
        }
      }
    }
  }
}
</style>