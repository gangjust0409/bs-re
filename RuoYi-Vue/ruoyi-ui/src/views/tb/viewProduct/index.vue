<template>
  <div class="app-container">
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
              <img :src="detailForm.activePic" alt="" />
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
                  backgroundImage: `url(${detailForm.activePic})`,
                  backgroundPosition: xm + 'px ' + ym + 'px',
                }"
              ></div>
            </div>
            <!-- 切换图片 -->
            <div class="product_imgs">
              <div
                class="product_img"
                v-for="img in detailForm.smailPics"
                :key="img"
              >
                <img :src="img" alt="" />
              </div>
            </div>
          </div>
          <div class="product_info_box_right">
            <!-- 商品名称 -->
            <div class="product_title">
              {{ detailForm.productTitle }}
            </div>
            <!-- 价格 -->
            <div class="detail_row">
              <label>价格：</label>
              <div class="price">
                <span>￥</span>{{ detailForm.productPrice.toFixed(2) }}
              </div>
            </div>
            <!-- 选择商品 -->
            <div class="detail_row">
              <label>款式：</label>
              <div class="choose_product">
                <div
                  v-for="sku in detailForm.skus"
                  :key="sku.skuId"
                  :class="{
                    choose_pro_item: true,
                    active: currentId === sku.skuId,
                  }"
                >
                  <img :src="sku.skuPic" alt="" />
                  <span>{{ sku.skuName }}</span>
                </div>
              </div>
            </div>
          </div>
        </div>
        <!-- 宝贝详情 -->
        <div class="baby_detail">
          <div class="title"><span>宝贝详情</span> <i></i></div>
          <div class="sku_attrs">
            <div
              class="attr_item"
              v-for="attr in detailForm.attrs"
              :key="attr"
              v-show="attr != null"
            >
              <span>{{ attr }}</span>
            </div>
            <!-- 详情展示图片 -->
            <div class="detail_imgs">
              <img
                v-for="img in detailForm.detailImages"
                :key="img"
                :src="img"
                alt=""
              />
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import { detail } from "@/api/bsapi/product";
export default {
  data() {
    return {
      mask: false,
      x: 0,
      y: 0,
      xm: 0,
      ym: 0,
      //详情表单
      detailForm: {},
      //当前选中的id
      currentId: 0,
    };
  },
  methods: {
    //鼠标移入
    hoverSLFun(e) {
      this.mask = true;
    },
    //鼠标移出
    levelSLFun() {
      this.mask = false;
    },
    //鼠标移动时间
    moveTouch(e) {
      this.x = e.pageX - 200;
      this.y = e.pageY - 400;
      if (this.x <= 0) {
        this.x = 0;
      } else if (this.x >= 182) {
        this.x = 182;
      }
      if (this.y >= 248) {
        this.y = 248;
      } else if (this.y <= 0) {
        this.y = 0;
      }
      this.xm = -this.x - 50;
      this.ym = -this.y - 50;
    },
    //获取详情信息
    getDetail(productId) {
      detail(productId).then((res) => {
        if (res.code === 200) {
          this.detailForm = res.data;
          this.currentId = this.detailForm.productId;
        }
      });
    },
  },
  beforeMount() {
    //获取详情信息
    this.getDetail(this.$route.query.productId);
  },
};
</script>

<style scoped lang="scss">
.product_info_container {
  .product_info_box {
    display: flex;

    .product_info_box_left {
      width: 450px;
      margin-right: 25px;
      position: relative;
      .big_sl_img {
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
          &:first-child,
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
</style>