<template>
  <div class="app-container">
    <!-- 条件 -->
    <el-form
      :inline="true"
      :model="queryParams"
      class="queryParams-form-inline"
    >
      <el-form-item label="商品标题">
        <el-input
          size="mini"
          v-model="queryParams.productTitle"
          placeholder="商品标题"
        ></el-input>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" size="mini" @click="loadSwiper">查询</el-button>
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
    <el-row>
      <el-col>
        <el-table
          :data="swipers"
          @selection-change="selectSwiperChange"
          ref="shopTable"
          tooltip-effect="dark"
          style="width: 100%"
        >
          <el-table-column type="selection" width="55"> </el-table-column>
          <el-table-column
            label="ID"
            prop="swiperId"
            width="80"
          ></el-table-column>
          <el-table-column prop="productTitle" label="商品名称" width="250">
          </el-table-column>
          <el-table-column label="轮播图" width="300">
            <template slot-scope="scope">
              <div class="img">
                <img :src="scope.row.swiperUrl" alt="" />
              </div>
            </template>
          </el-table-column>
          <el-table-column label="状态" width="120">
            <template slot-scope="scope">
              <el-tag v-if="scope.row.enable == 1" type="success">启用</el-tag>
              <el-tag v-else type="danger">禁用</el-tag>
            </template>
          </el-table-column>
          <el-table-column label="操作">
            <template slot-scope="scope">
              <el-button
                v-if="scope.row.enable == 2"
                type="success"
                size="mini"
                plain
                @click="handlerEnableSwiper(scope.row.swiperId, 1)"
                >启用</el-button
              >
              <el-button
                v-else
                type="primary"
                size="mini"
                plain
                @click="handlerEnableSwiper(scope.row.swiperId, 2)"
                >禁用</el-button
              >
              <el-button
                type="primary"
                size="mini"
                @click="editSwiper(scope.row.swiperId)"
                >修改</el-button
              >
              <el-button
                type="danger"
                size="mini"
                @click="handlerDelete(scope.row.swiperId)"
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
      :title="swiperAddOrUpdateTitle"
      :visible.sync="swiperVisible"
      width="30%"
      :before-close="handlerSwiperClose"
    >
      <el-form
        ref="swiperFormRef"
        :model="swiperForm"
        label-width="80px"
        size="mini"
      >
        <el-form-item label="商品">
          <el-select v-model="swiperForm.productId" placeholder="请选择商品">
            <el-option
              v-for="item in productOptions"
              :key="item.productId"
              :label="item.productTitle"
              :value="item.productId"
            >
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="轮播图">
          <!-- 修改展示的图片 -->
          <div class="edit_pic" v-show="swiperForm.swiperUrlPreview != null">
            <img :src="swiperForm.swiperUrlPreview" alt="" />
          </div>
          <image-upload
            ref="swiperUploadRef"
            uploadImgUrl="http://localhost:8001/product/upload/swiper"
            :value="swiperForm.swiperUrl"
            :fileType="['jpg', 'png', 'gif', 'webp', 'jpeg']"
          />
        </el-form-item>
      </el-form>
      <span slot="footer" class="dialog-footer">
        <el-button @click="handlerSwiperClose">取 消</el-button>
        <el-button type="primary" @click="submitSwiper">提 交</el-button>
      </span>
    </el-dialog>
  </div>
</template>

<script>
import { upProductList } from "@/api/bsapi/product";
import {
  addSwiper,
  swiperList,
  getSwiper,
  updateSwiper,
  enableSwiper,
  delSwiper,
} from "@/api/bsapi/swiper";
export default {
  data() {
    return {
      //查询条件
      queryParams: {
        productTitle: null,
        pageNum: 0,
        pageSize: 5,
      },
      //轮播图总条数
      total: 0,
      //轮播图列表
      swipers: [],
      //轮播图添加或修改标题
      swiperAddOrUpdateTitle: null,
      //控制轮播图添加或修改
      swiperVisible: false,
      //轮播图表单
      swiperForm: {
        swiperUrl: null,
      },
      //商品下拉框
      productOptions: [],
      //已选择的轮播图id，进行删除
      selectedSwiperIds: [],
    };
  },
  methods: {
    //多选
    selectSwiperChange(selecteds) {
      this.selectedSwiperIds = selecteds.map((se) => se.swiperId);
    },
    //加载轮播图数据
    async loadSwiper() {
      const { code, rows, total } = await swiperList(this.queryParams);
      if (code == 0) {
        this.swipers = rows;
        this.total = total;
      }
    },
    //分页
    changePage({ page }) {
      this.queryParams.pageNum = page;
      this.loadSwiper();
    },
    //关闭轮播图框
    handlerSwiperClose() {
      //清空表单
      this.resetForm("swiperFormRef");
      this.swiperVisible = false;
    },
    //点击展开添加
    handlerAdd() {
      this.loadListProduct();
      this.swiperVisible = true;
      this.swiperAddOrUpdateTitle = "添加商品轮播图";
    },
    //下拉框
    async loadListProduct() {
      const { code, data } = await upProductList(null);
      if (code == 200) {
        this.productOptions = data;
      }
    },
    //添加
    async submitSwiper() {
      this.$refs["swiperUploadRef"].submitUpload();
      //添加
      if (this.swiperAddOrUpdateTitle === "添加商品轮播图") {
        const { code } = await addSwiper(this.swiperForm);
        if (code == 200) {
          this.$modal.alertSuccess("添加成功！");
        }
      } else {
        //修改
        const { code } = await updateSwiper(this.swiperForm);
        if (code == 200) {
          this.$modal.alertSuccess("修改成功！");
        }
      }
      //关闭修改模态框
      this.handlerSwiperClose();
      //刷新数据
      this.loadSwiper();
    },
    //点击修改
    async editSwiper(swiperId) {
      await this.loadListProduct();
      const { data } = await getSwiper(swiperId);
      this.swiperForm = data;
      this.swiperAddOrUpdateTitle = "修改商品轮播图"
      this.swiperVisible = true;
    },
    //是否启用轮播图
    async handlerEnableSwiper(swiperId, status) {
      const { code } = await enableSwiper(swiperId + "-" + status);
      if (code == 200) {
        this.$modal.alertSuccess(status == 1 ? "启用成功！" : "禁用成功！");
        //刷新
        this.loadSwiper();
      }
    },
    //删除
    handlerDelete(swiperId) {
      if (swiperId) {
        //单个
        this.selectedSwiperIds.length = 0;
        this.selectedSwiperIds.push(swiperId);
      } else {
        //多个
        if (this.selectedSwiperIds.length == 0) {
          this.$modal.alertWarning("请选择删除内容！");
          return;
        }
      }
      this.$confirm(
        `确定是否删除【${this.selectedSwiperIds}】吗？`,
        "温馨提示",
        {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning",
        }
      )
        .then(async () => {
          const { code } = await delSwiper(this.selectedSwiperIds);
          if (code == 200) {
            this.$modal.alertSuccess("删除成功！");
            this.loadSwiper();
          }
        })
        .catch(() => {
          if (swiperId) {
            this.selectedSwiperIds.length = 0;
          }
        });
    },
  },
  beforeMount() {
    this.loadSwiper();
  },
};
</script>

<style lang="scss" scoped>
.img {
  width: 300px;
  height: 150px;
  img {
    width: 100%;
    height: 100%;
  }
}
</style>
