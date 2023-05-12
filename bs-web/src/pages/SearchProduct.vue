<template>
  <div id="home">
    <menu-header>
      <template #cancel>
        <li style="display: inline-block; margin-right: 10px">
          <router-link to="/home" style="color: #ff0036">返回首页</router-link>
        </li>
      </template>
    </menu-header>
    <menu-search :keyword="searchParams.keyword"></menu-search>
    <div class="product_content" v-if="searchProductList.length > 0">
      <!-- 筛选区 -->
      <div class="product_shaixuan">
        <div class="row">
          <div class="row_shaixuan_btn">综合排序</div>
          <div class="row_shaixuan_btn">
            销量<el-icon><IEpCaretBottom /></el-icon>
          </div>
          <div class="row_shaixuan_btn">
            <span>价格</span>
            <div class="icon">
              <el-icon><IEpCaretTop /></el-icon>
              <el-icon><IEpCaretBottom /></el-icon>
            </div>
          </div>
          <div class="row_shaixuan_btn">
            <el-row :gutter="20">
              <el-col :span="8">
                <el-input
                  class="w-50 m-2"
                  placeholder="price min..."
                  suffix-icon="IEpCalendar"
                  v-model="searchParams.minPrice"
                />
              </el-col>
              <el-col style="margin-top: 8px" :span="1">-</el-col>
              <el-col :span="8">
                <el-input
                  class="w-50 m-2"
                  placeholder="price max..."
                  prefix-icon="IEpSearch"
                  v-model="searchParams.maxPrice"
                /> </el-col
              ><el-col :span="2" class="text-center" style="margin-top: 5px">
                <el-button type="primary" size="small" @click="confirm"
                  >确定</el-button
                >
              </el-col>
            </el-row>
          </div>
        </div>
      </div>
      <!-- 商品展示区 -->
      <product-info-list :list="searchProductList"></product-info-list>
    </div>
    <el-empty v-else description="暂无对应的商品信息！" />
    <el-backtop :right="100" :bottom="100" />
    <menu-footer></menu-footer>
  </div>
</template>

<script setup>
import { useRoute, onBeforeRouteUpdate } from "vue-router";
import { reactive, ref, nextTick } from "vue";
import { searchProduct } from "@/api/product";

const route = useRoute();
//搜索条件
const searchParams = reactive({
  keyword: null,
  minPrice: null,
  maxPrice: null,
});
//搜索来的商品列表
const searchProductList = ref([]);

//搜索商品方法
async function loadSearchProduct(keyword) {
  searchParams.keyword = keyword;
  const { code, data } = await searchProduct(searchParams);
  if (code == 200) {
    searchProductList.value = data;
    await nextTick();
    //赋值，防止文本框没有数据
    searchParams.keyword = route.query.keyword;
  }
}
//点击确定时
function confirm() {
  // if (searchParams.maxPrice != null || searchParams.minPrice != null) {
  //拼接价格
  searchParams.priceStr = `${
    searchParams.minPrice == null || searchParams.minPrice == ""
      ? 0
      : searchParams.minPrice
  }-${
    searchParams.maxPrice == null || searchParams.maxPrice == ""
      ? 0
      : searchParams.maxPrice
  }`;
  // }
  //搜索商品信息
  loadSearchProduct(route.query.keyword);
}

//加载搜索商品
loadSearchProduct(route.query.keyword);

//当路由改变时
onBeforeRouteUpdate((to, from, next) => {
  //加载数据
  loadSearchProduct(to.query.keyword);
  next();
});
</script>

<style scoped lang="scss">
.product_shaixuan {
  background-color: #f8f8f8;
  border-radius: 10px;
  padding: 15px 25px;
  .row {
    display: flex;

    .row_shaixuan_btn {
      margin-right: 20px;
      display: flex;
      color: #777;
      align-items: center;
      .icon {
        display: flex;
        flex-direction: column;
      }
      i,
      svg {
        width: 8px;
        height: 8px;
      }
      i {
        margin-left: 3px;
      }
    }
  }
}
</style>