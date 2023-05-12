<template>
  <div class="app-container">
    <!-- 搜索 -->
    <el-form
      :inline="true"
      :model="queryParams"
      class="queryParams-form-inline"
    >
      <el-form-item label="订单号">
        <el-input
          v-model="queryParams.orderSn"
          placeholder="订单号"
          size="mini"
        ></el-input>
      </el-form-item>
      <el-form-item label="订单状态">
        <el-select v-model="queryParams.logistics" placeholder="订单状态" size="mini">
          <el-option label="全部" value="0"></el-option>
          <el-option label="待付款" value="1"></el-option>
          <el-option label="待发货" value="2"></el-option>
          <el-option label="待收货" value="3"></el-option>
          <el-option label="已完成" value="4"></el-option>
        </el-select>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="queryOrderList" size="mini">查询</el-button>
      </el-form-item>
    </el-form>
    <!-- 按钮 -->
    <el-row :gutter="10">
<!--      <el-col :span="1.5">
        <el-button
          type="primary"
          plain
          icon="el-icon-plus"
          size="mini"
          @click="handlerAdd"
          v-hasPermi="['bsapi:user:add']"
        >新增</el-button
        >
      </el-col>-->
      <el-col :span="1.5">
        <el-button
          type="danger"
          plain
          icon="el-icon-edit"
          size="mini"
          @click="handlerSelectedDelete"
          v-hasPermi="['bsapi:order:delete:ordersn']"
        >删除</el-button
        >
      </el-col>
    </el-row>
    <!-- 表格 -->
    <el-row>
      <el-col>
        <el-table
          :data="orderList"
          @selection-change="selectOrderChange"
          ref="shopTable"
          tooltip-effect="dark"
          style="width: 100%"
        >
          <el-table-column type="selection" width="45"> </el-table-column>
          <el-table-column type="expand">
            <template slot-scope="props" v-if="props.row.items.length > 0">
              <el-table :data="props.row.items" style="width: 100%">
                <el-table-column label="商品ID" prop="productId">
                </el-table-column>
                <el-table-column label="商品标题" prop="productTitle">
                </el-table-column>
                <el-table-column label="商品价格">
                  <template slot-scope="scope">
                    <b class="price"> ￥{{scope.row.productPrice.toFixed(2)}}</b>
                  </template>
                </el-table-column>
                <el-table-column label="商品数量" prop="productCount">
                </el-table-column>
                <el-table-column label="商品封面">
                  <template slot-scope="scope">
                    <div class="product_pic">
                      <img :src="scope.row.productPic" alt="" />
                    </div>
                  </template>
                </el-table-column>
                <el-table-column label="店铺名称" prop="shopName">
                </el-table-column>
              </el-table>
            </template>
          </el-table-column>
          <el-table-column
            label="订单号"
            prop="orderSn"
            width="180"
          ></el-table-column>
          <el-table-column label="最终支付" width="130">
            <template slot-scope="scope">
             <b class="price"> ￥{{scope.row.payPrice.toFixed(2)}}</b>
            </template>
          </el-table-column>
          <el-table-column prop="consignName" label="收货人" width="130">
            <template slot-scope="scope">
              <el-popover trigger="hover" placement="top">
                <p>姓名: {{ scope.row.consignName }}</p>
                <p>住址: {{ scope.row.address }}</p>
                <div slot="reference" class="name-wrapper">
                  <el-tag size="medium">{{ scope.row.consignName }}</el-tag>
                </div>
              </el-popover>
            </template>
          </el-table-column>
          <el-table-column prop="consignPhone" label="收货人手机号" width="150">
          </el-table-column>
<!--          <el-table-column label="地址" width="250">-->
<!--            <template slot-scope="scope">-->
<!--                <el-tag>{{scope.row.address}}</el-tag>-->
<!--            </template>-->
<!--          </el-table-column>-->
          <el-table-column label="物流状态" width="100">
            <template slot-scope="scope">
              <el-tag v-if="scope.row.logistics == 1">待付款</el-tag>
              <el-tag v-else-if="scope.row.logistics == 2">待发货</el-tag>
              <el-tag v-else-if="scope.row.logistics == 3">待收货</el-tag>
              <el-tag v-else-if="scope.row.logistics == 4">已完成</el-tag>
            </template>
          </el-table-column>
          <el-table-column prop="createDate" label="订单创建时间" width="130">
          </el-table-column>
          <el-table-column label="操作">
            <template slot-scope="scope">
              <el-dropdown size="medium" @command="more($event, scope.row)">
              <el-button type="primary">
                更多<i class="el-icon-arrow-down el-icon--right"></i>
              </el-button>
              <el-dropdown-menu slot="dropdown">
                <el-dropdown-item command="updateLogistics"
                                  v-hasPermi="['bsapi:order:delete:ordersn']">更新物流状态</el-dropdown-item>
                <el-dropdown-item command="deleteOrder">删除订单</el-dropdown-item>
              </el-dropdown-menu>
              </el-dropdown>
            </template>
          </el-table-column>
        </el-table>
      </el-col>
    </el-row>
    <el-row>
      <el-col>
        <!-- 分页 -->
        <Pagination
          :total="total"
          :page="queryParams.pageNum"
          :limit="queryParams.pageSize"
          @pagination="changePage"
        />
      </el-col>
    </el-row>
  </div>
</template>
<script>
import {queryOrderList,updateLogistics,deleteOrderByOrderSn} from '@/api/bsapi/order'
export default {
  data(){
    return{
      //搜索条件
      queryParams: {
        pageNum:1,
        pageSize: 5,
        orderSn: null,
        logistics: null,
      },
      //订单列表
      orderList: [],
      //订单总条数
      total: 0,
      //要删除的订单号列表
      selectOrders: [],
    }
  },
  methods: {
    //查询订单信息
    async queryOrderList(){
      const {code,total,rows} = await queryOrderList(this.queryParams);
      if (code == 0) {
        this.orderList = rows;
        this.total = total;
      }
    },
    //分页
    changePage({page}) {
      this.queryParams.pageNum = page;
      this.queryOrderList();
    },
    //多选
    selectOrderChange(selection){
      //清除数组中旧的订单号
      this.selectOrders.length = 0;
      this.selectOrders = selection.map(x => x.orderSn);
    },
    //删除
    handlerSelectedDelete(){
      if (this.selectOrders.length <= 0) {
        this.$modal.alertWarning("必须选择某个订单！");
        return;
      }
      this.$modal.confirm(`确定是否删除订单号为【${this.selectOrders}】吗？`).then(async () => {
        const res = await deleteOrderByOrderSn(this.selectOrders);
        if (res.code == 200 && res.data == 1) {
          this.$modal.alertSuccess("删除订单成功！");
          this.queryOrderList();
        } else {
          this.$modal.alertSuccess("删除订单失败！");
        }
      }).catch(() => {
        this.selectOrders.length = 0; //防止误删
      })
    },
    //更多
    async more(commend, {orderSn, logistics}){
      switch (commend) {
        case 'updateLogistics':
          const {code, data} = await updateLogistics(orderSn + "-" + logistics);
          if (code == 200) {
            if (data == 1) {
              this.$modal.alertSuccess("物流更新成功！");
              this.queryOrderList();
            } else if(data == 0) {
              this.$modal.alertWarning("物流状态已完成，点击无效！");
            }
          }
          break;
        case 'deleteOrder':
          //清除数组中旧的订单号
          this.selectOrders.length = 0;
          this.selectOrders.push(orderSn);
          this.handlerSelectedDelete();
          break;
      }
    },
  },
  beforeMount() {
    this.queryOrderList();
  }
};
</script>
<style scoped lang="scss">
.product_pic{
  width: 100px;
  height: 80px;
  img{
    width: 100%;
    height: 100%;
  }
}
.price{
  color: #c00000;
  font-size: 16px;
}
</style>
