<template>
  <div class="product_list">
    <div
      class="product_item"
      v-for="(product, i) in list"
      :key="i"
      @click="toDetail(product.productId)"
    >
      <div class="product_pic">
        <img :src="product.productPic" alt="" />
      </div>
      <div class="product_price">
        <div class="price">
          {{ parseFloat(product.productPrice).toFixed(2) }}
        </div>
        <div class="old_price" v-if="product.productOldPrice">
          {{ parseFloat(product.productOldPrice).toFixed(2) }}
        </div>
      </div>
      <div class="product_title">
        {{ product.productTitle }}
      </div>
      <div class="delete" v-if="isDelete">
        <el-icon><IEpDeleteFilled /></el-icon>
      </div>
    </div>
  </div>
</template>

<script setup>
import { defineProps } from "vue";
import { useRouter } from "vue-router";

const router = useRouter();
const { list } = defineProps({
  list: {
    type: Array,
  },
  isDelete: {
    type: Boolean,
    default: true,
  },
});
//跳转到详情页
function toDetail(productId) {
  router.push({
    path: "/detail",
    query: {
      productId,
    },
  });
}
</script>

<style scoped lang="scss">
.product_list {
  margin-top: 20px;
  display: flex;
  flex-wrap: wrap;
  padding: 0 20px;
  .product_item {
    width: 205px;
    margin-right: 49px;
    margin-bottom: 20px;
    position: relative;
    &:hover .delete {
      display: block;
    }
    .product_pic {
      width: 200px;
      height: 200px;
      cursor: pointer;
      img {
        width: 100%;
        height: 100%;
      }
    }
    .product_price {
      display: flex;
      align-items: center;
      margin: 5px 0;
      .old_price {
        margin-left: 10px;
        color: #b0b0b0;
        text-decoration: line-through;
      }
    }
    .product_title {
      white-space: nowrap;
      text-overflow: ellipsis;
      overflow: hidden;
      cursor: pointer;
    }
    .delete {
      width: 30px;
      height: 30px;
      border-radius: 0 0 0px 10px;
      color: #fff;
      padding-top: 2px;
      background-color: rgba($color: #000000, $alpha: 0.7);
      text-align: center;
      position: absolute;
      top: 0;
      right: 5px;
      cursor: pointer;
      display: none;
      i,
      svg {
        width: 1.5em;
        height: 1.5em;
      }
    }
  }
}
</style>