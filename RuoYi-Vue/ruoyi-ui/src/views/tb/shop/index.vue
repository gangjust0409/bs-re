<template>
  <div class="app-container">
    <el-form
      :inline="true"
      :model="queryParams"
      class="queryParams-form-inline"
    >
      <el-form-item label="店铺名称">
        <el-input
          size="mini"
          v-model="queryParams.shopName"
          placeholder="店铺名称"
        ></el-input>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" size="mini" @click="loadShopList">查询</el-button>
      </el-form-item>
    </el-form>
    <!-- 按钮 -->
    <el-row :gutter="10">
      <el-col :span="1.5">
        <el-button
          type="primary"
          plain
          icon="el-icon-plus"
          size="mini"
          @click="handlerAdd"
          v-hasPermi="['bsapi:user:add']"
          >新增</el-button
        >
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          plain
          icon="el-icon-edit"
          size="mini"
          @click="handlerDelete()"
          v-hasPermi="['bsapi:user:delete']"
          >删除</el-button
        >
      </el-col>
    </el-row>
    <!-- 表格 -->
    <el-row>
      <el-col>
        <el-table
          :data="shopReleaseProductList"
          @selection-change="selectShopChange"
          ref="shopTable"
          tooltip-effect="dark"
          style="width: 100%"
        >
          <el-table-column type="selection" width="55"> </el-table-column>
          <el-table-column type="expand">
            <template slot-scope="props" v-if="props.row.products.length > 0">
              <el-table :data="props.row.products" style="width: 100%">
                <el-table-column label="商品ID" prop="productId">
                </el-table-column>
                <el-table-column label="商品标题" prop="productTitle">
                </el-table-column>
                <el-table-column label="商品价格" prop="productPrice">
                </el-table-column>
                <el-table-column label="商品封面">
                  <template slot-scope="scope">
                    <div class="product_pic">
                      <img :src="scope.row.productPic" alt="" />
                    </div>
                  </template>
                </el-table-column>
                <el-table-column label="操作">
                  <template slot-scope="scope">
                    <el-button
                      @click="handlerShopAddProduct(scope.row.productId, 3)"
                      >下 架</el-button
                    >
                  </template>
                </el-table-column>
              </el-table>
            </template>
          </el-table-column>
          <el-table-column
            label="ID"
            prop="shopId"
            width="80"
          ></el-table-column>
          <el-table-column prop="shopName" label="店铺名称" width="200">
          </el-table-column>
          <el-table-column prop="address" label="店铺图标" width="120">
            <template slot-scope="scope">
              <div class="img">
                <img :src="scope.row.shopIcon" alt="" />
              </div>
            </template>
          </el-table-column>
          <el-table-column prop="createdDate" label="店铺创建时间" width="150">
          </el-table-column>
          <el-table-column label="操作">
            <template slot-scope="scope">
              <el-button
                type="primary"
                plain
                size="mini"
                @click="loadShopAddProduct(scope.row.shopId)"
                >添加商品</el-button
              >
              <el-button
                type="primary"
                size="mini"
                @click="editShop(scope.row.shopId)"
                >修改</el-button
              >
              <el-button
                type="danger"
                size="mini"
                @click="handlerDelete(scope.row.shopId, scope.row.products)"
                >删除</el-button
              >
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
    <!-- 添加或者修改模态框 -->
    <el-dialog
      :title="shopAddOrUpdateTitle"
      :visible.sync="shopVisible"
      width="30%"
      :before-close="handlerShopClose"
    >
      <el-form ref="form" :model="shopForm" label-width="80px" size="mini">
        <el-form-item label="店铺名称">
          <el-input v-model="shopForm.shopName"></el-input>
        </el-form-item>
        <el-form-item label="店铺头像">
          <!-- 修改展示的图片 -->
          <div class="edit_pic" v-show="shopForm.shopPicPreview != null">
            <img :src="shopForm.shopPicPreview" alt="" />
          </div>
          <image-upload
            ref="shopIconUploadRef"
            uploadImgUrl="http://localhost:8001/product/upload/shop/icon"
            :value="shopForm.shopPic"
            :fileType="['jpg', 'png', 'gif', 'webp', 'jpeg']"
          />
        </el-form-item>
      </el-form>
      <span slot="footer" class="dialog-footer">
        <el-button @click="handlerShopClose">取 消</el-button>
        <el-button type="primary" @click="submitAdd">提 交</el-button>
      </span>
    </el-dialog>
    <!-- 添加商品 -->
    <el-dialog
      title="添加商品"
      :before-close="handlerShopAddProductClose"
      :visible.sync="shopAddProductVisible"
    >
      <el-table :data="shopAddProductList">
        <el-table-column
          property="productId"
          label="ID"
          width="80"
        ></el-table-column>
        <el-table-column
          property="productTitle"
          label="商品标题"
          width="300"
        ></el-table-column>
        <el-table-column label="价格">
          <template slot-scope="scope">
            <span>￥{{ scope.row.productPrice }}</span>
          </template>
        </el-table-column>
        <el-table-column label="操作">
          <template slot-scope="scope">
            <el-button
              size="mini"
              type="primary"
              @click="handlerShopAddProduct(scope.row.productId, 2)"
              >添加</el-button
            >
          </template>
        </el-table-column>
      </el-table>
    </el-dialog>
  </div>
</template>
<script>
import {
  addShop,
  listShop,
  getShop,
  updateShop,
  delShop,
} from "@/api/bsapi/shop";
import { shopAddProducts } from "@/api/bsapi/product";
import { addProduct } from "@/api/bsapi/shopProduct";
export default {
  data() {
    return {
      //查询条件
      queryParams: {
        shopName: null,
        pageNum: 1,
        pageSize: 5,
      },
      //是否展示店铺模态框
      shopVisible: false,
      //店铺表单
      shopForm: {
        shopName: null,
        shopPic: null,
      },
      //店铺添加或修改标题
      shopAddOrUpdateTitle: "",
      //店铺和店铺关联商品信息
      shopReleaseProductList: [],
      //店铺和店铺关联商品总条数信息
      total: 0,
      //删除店铺id列表
      selectedShopIds: [],
      //控制店铺添加商品
      shopAddProductVisible: false,
      //店铺添加时展示的列表
      shopAddProductList: [],
      //店铺添加商品表单
      shopProductForm: {},
    };
  },
  methods: {
    //关闭添加或修改的模态框
    handlerShopClose() {
      this.shopVisible = false;
      this.shopForm.shopId = null;
      this.shopForm.shopName = null;
      this.shopForm.shopPicPreview = null;
      this.shopForm.shopPic = null;
    },
    //点击展示店铺添加或者修改模态框
    handlerAdd() {
      this.shopVisible = true;
      this.shopAddOrUpdateTitle = "添加店铺";
    },
    //提交添加或者修改
    async submitAdd() {
      //手动提交文件
      this.$refs["shopIconUploadRef"].submitUpload();
      //添加
      if (this.shopAddOrUpdateTitle == "添加店铺") {
        //添加操作
        const res = await addShop(this.shopForm);
        if (res.code == 200) {
          this.$modal.msgSuccess("添加成功！");
        }
      } else {
        const res = await updateShop(this.shopForm);
        if (res.code == 200) {
          this.$modal.msgSuccess("修改成功！");
        }
      }
      this.handlerShopClose();
      //刷新数据
      this.loadShopList();
    },
    //获取数据
    async loadShopList() {
      const res = await listShop(this.queryParams);
      if (res.code == 0) {
        this.shopReleaseProductList = res.rows;
        this.total = res.total;
      }
    },
    //点击弹出修改店铺模态框
    async editShop(shopId) {
      this.shopAddOrUpdateTitle = "修改店铺";
      const res = await getShop(shopId);
      if (res.code === 200) {
        this.shopForm.shopId = res.data.shopId;
        this.shopForm.shopName = res.data.shopName;
        this.shopForm.shopPicPreview = res.data.shopPic;
      }
      //查询数据
      this.shopVisible = true;
    },
    //点击分页
    changePage({ page }) {
      this.queryParams.pageNum = page;
      //刷新数据
      this.loadShopList();
    },
    //选中表格某项
    selectShopChange(selecteds) {
      this.selectedShopIds = selecteds.map((e) => e.shopId);
    },
    //点击删除操作
    handlerDelete(shopId, products) {
      if (products != null && products.length > 0) {
        this.$modal.notify(
          "该店铺还有商品，不能被删除，如要继续，请上传店铺相关证明！"
        );
        return;
      }
      //点击单个删除
      if (shopId) {
        //这个只能删除前，所有必须先清空数组
        this.selectedShopIds.length = 0;
        this.selectedShopIds.push(shopId);
      } else {
        //删除多个
        //判断多选是否选中
        if (this.selectedShopIds.length == 0) {
          this.$modal.msgWarning("请选择要删除的数据！");
          return;
        }
      }
      this.$confirm(`确定是否删除【${this.selectedShopIds}】吗？`, "温馨提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning",
      })
        .then(async () => {
          const res = await delShop(this.selectedShopIds);
          if (res.code === 200) {
            this.$modal.alertSuccess("删除成功！");
            //刷新数据
            this.loadShopList();
          }
        })
        .catch(() => {
          if (shopId) {
            //这个只能删除前，所有必须先清空数组
            this.selectedShopIds.length = 0;
          }
        });
    },
    //关闭店铺添加商品框
    handlerShopAddProductClose() {
      this.loadShopList();
      this.shopAddProductVisible = false;
    },
    //店铺点击商品时，展示
    async loadShopAddProduct(shopId) {
      this.shopProductForm.shopId = shopId;
      const { code, data } = await shopAddProducts();
      if (code === 200) {
        this.shopAddProductList = data;
      }
      this.shopAddProductVisible = true;
    },
    //点击添加商品
    async handlerShopAddProduct(productId, status) {
      this.shopProductForm.productId = productId;
      const { code } = await addProduct(this.shopProductForm, status);
      if (code == 200) {
        this.$modal.alertSuccess(status == 2 ? "上架成功！" : "下架成功！");
        if (status == 2) {
          this.loadShopAddProduct(productId);
        } else {
          this.loadShopList();
        }
      }
    },
  },
  beforeMount() {
    this.loadShopList();
  },
};
</script>
<style scoped lang="scss">
.product_pic {
  width: 150px;
  height: 150px;
  img {
    width: 100%;
    height: 100%;
  }
}
</style>
