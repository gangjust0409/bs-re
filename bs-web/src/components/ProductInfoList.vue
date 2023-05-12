<template>
  <!-- 商品展示区 -->
  <div class="product_info_list">
    <div
      class="product_item"
      v-for="product in list"
      :key="product.productId"
      
    >
      <div class="product_pic" @click="toDetail(product)">
        <img :src="product.productPic" alt="" />
      </div>
      <div class="product_title" @click="toDetail(product)">
        <span>淘宝</span>
        <strong v-html="product.productTitle"></strong>
      </div>
      <div class="info">
        <p @click="toDetail(product)"><el-tag size="small">猜你喜欢</el-tag></p>
        <p @click="toDetail(product)" class="price">￥{{ parseFloat(product.productPrice).toFixed(2) }}</p>
        <p>
          <span class="moth" @click="toDetail(product)">销量：{{ product.monthPin }}</span
          >
          <div>
            <el-tooltip
            class="box-item"
            effect="dark"
            :content="product.ifCollected?'取消收藏':'点击收藏'"
            placement="bottom"
          >
            <el-button type="primary" circle :plain="!product.ifCollected"
            @click="collect(product.productId)">
              <template #icon>
                <el-icon :size="25"><IEpStar /></el-icon>
              </template>
            </el-button>
          </el-tooltip>
          <el-tooltip
            class="box-item"
            effect="dark"
            content="点击加入购物车"
            placement="bottom"
          >
            <el-button type="primary" circle @click="toDetail(product)">
              <template #icon>
                <el-icon :size="25"><IEpShoppingCartFull /></el-icon>
              </template>
            </el-button>
          </el-tooltip>
          </div>
        </p>
      </div>
    </div>
  </div>
</template>

<script setup>
import { useRouter } from "vue-router";
import { defineProps, defineEmits, getCurrentInstance } from "vue";
import { clickCollect } from "@/api/user";

const router = useRouter();
const { proxy } = getCurrentInstance();

const { list } = defineProps({
  list: {
    type: Array,
  },
});
const emits = defineEmits(["collect-load"]);
//跳转详情页
function toDetail(product) {
  router.push({
    path: "/detail",
    query: {
      productId: product.productId,
    },
  });
}
//点击收藏
async function collect(productId) {
  const { code, msg } = await clickCollect(productId);
  if (code == 200) {
    proxy.$msg.success(msg);
    emits("collect-load");
  } else {
    proxy.$msg.warning(msg);
  }
}
</script>

<style scoped lang="scss">
.product_info_list {
  display: flex;
  flex-wrap: wrap;
  .product_item {
    width: 208px;
    border: 1px solid #eee;
    border-radius: 10px;
    margin: 10px 10px 0px 0;
    padding: 10px;
    cursor: pointer;
    &:hover {
      border-color: #ff0036;
    }
    &:last-of-type {
      margin-right: 0;
    }
    .product_pic {
      margin: 0 auto;
      width: 187px;
      height: 210px;
      img {
        width: 100%;
        height: 100%;
      }
    }
    .product_title {
      overflow: hidden;
      text-overflow: ellipsis;
      white-space: nowrap;
      margin: 10px 0;
      font-size: 15px;
      span {
        padding: 1px;
        font-size: 12px;
        background-color: #c00000;
        color: #fff;
      }
      strong {
        margin-left: 10px;
      }
    }
    .info {
      p {
        &:last-of-type {
          display: flex;
          justify-content: space-between;
          align-items: center;
        }
        svg {
          width: 1.3em;
          height: 1.3em;
        }
      }
    }
  }
}
</style>