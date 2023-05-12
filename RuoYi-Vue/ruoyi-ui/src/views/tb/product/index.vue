<template>
  <div class="app-container">
    <!-- 搜索 -->
    <el-form
      :inline="true"
      :model="queryParams"
      class="queryParams-form-inline"
    >
      <el-form-item label="商品名称">
        <el-input
          v-model="queryParams.productTitle"
          placeholder="商品名称"
        ></el-input>
      </el-form-item>
      <el-form-item label="商品状态">
        <el-select v-model="queryParams.status" placeholder="商品状态">
          <el-option label="全部" value="0"></el-option>
          <el-option label="已创建" value="1"></el-option>
          <el-option label="上架" value="2"></el-option>
          <el-option label="下架" value="3"></el-option>
        </el-select>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="loadProductList">查询</el-button>
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
          @click="handlerSelectedDelete"
          v-hasPermi="['bsapi:product:delete']"
          >删除</el-button
        >
      </el-col>
    </el-row>
    <!-- 商品表格 -->
    <el-row>
      <el-col :span="24">
        <el-table
          ref="multipleTable"
          :data="productList"
          tooltip-effect="dark"
          style="width: 100%"
          @selection-change="handlerSelectChange"
        >
          <el-table-column type="selection" width="25"> </el-table-column>
          <!-- 属性展示 -->
          <el-table-column type="expand" width="40">
            <template slot-scope="props">
              <el-form label-position="left" inline class="demo-table-expand">
                <el-form-item
                  v-for="attr in props.row.attrs"
                  :key="attr.attrId"
                  :label="attr.attrName"
                >
                  <span>{{ attr.attrValue }}</span>
                </el-form-item>
              </el-form>
            </template>
          </el-table-column>
          <el-table-column prop="productId" label="ID" width="50">
          </el-table-column>
          <el-table-column prop="productTitle" label="商品名称" width="200">
          </el-table-column>
          <el-table-column prop="shopName" label="店铺名称" width="120">
          </el-table-column>
          <el-table-column prop="catelogName" label="分类名称" width="100">
          </el-table-column>
          <el-table-column prop="productPrice" label="价格" width="90">
            <template slot-scope="scope">
              <span style="color: #c00000"
                >￥{{ scope.row.productPrice.toFixed(2) }}</span
              >
            </template>
          </el-table-column>
          <el-table-column prop="productPic" label="封面" width="180">
            <template slot-scope="scope">
              <div class="pic_box">
                <img
                  :src="scope.row.productPic"
                  :title="scope.row.productTitle"
                  :alt="scope.row.productTitle"
                />
              </div>
            </template>
          </el-table-column>
          <el-table-column prop="status" label="状态" width="80">
            <template slot-scope="scope">
              <div class="status_box">
                <el-tag v-if="scope.row.status === 1">已创建</el-tag>
                <el-tag v-else-if="scope.row.status === 2" type="success"
                  >已上架</el-tag
                >
                <el-tag v-else-if="scope.row.status === 3" type="danger"
                  >已下架</el-tag
                >
              </div>
            </template>
          </el-table-column>
          <el-table-column label="操作">
            <template slot-scope="scope">
              <el-dropdown
                split-button
                type="primary"
                size="mini"
                @command="handlerSetting($event, scope.row)"
              >
                更多操作
                <el-dropdown-menu slot="dropdown">
                  <el-dropdown-item command="addAttr"
                    >添加属性</el-dropdown-item
                  >
                  <el-dropdown-item command="query">查看</el-dropdown-item>
                  <el-dropdown-item command="edit">修改</el-dropdown-item>
                  <el-dropdown-item command="del">删除</el-dropdown-item>
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
          :pageSizes="[queryParams.pageSize]"
          @pagination="changePage"
        />
      </el-col>
    </el-row>
    <!-- 点击添加修改 -->
    <el-drawer
      :visible.sync="productAddEnable"
      :title="`${addUpdateProductTitle}商品`"
      size="85%"
      :before-close="handleClose"
    >
      <el-row>
        <el-col :span="22" :offset="1">
          <el-steps
            :active="stepStatus"
            finish-status="success"
            simple
            style="margin-top: 20px"
          >
            <el-step title="商品信息"></el-step>
            <el-step title="商品属性"></el-step>
            <el-step title="缩略详情图"></el-step>
            <el-step title="商品 sku"></el-step>
            <el-step title="添加完成"></el-step>
          </el-steps>
        </el-col>
      </el-row>
      <el-row style="margin: 20px 0">
        <el-col :span="22" :offset="1">
          <!-- 通过导航来填写 -->
          <el-tabs type="border-card" v-model="addUpdateStatus">
            <!-- 商品信息 -->
            <el-tab-pane label="商品信息" disabled name="one">
              <el-form label-width="120px" size="mini">
                <el-form-item label="商品名称">
                  <el-input v-model="productForm.productTitle"></el-input>
                </el-form-item>
                <el-form-item label="商品价格">
                  <el-input
                    placeholder="商品价格"
                    v-model="productForm.productPrice"
                  >
                    <template slot="prepend">￥</template>
                  </el-input>
                </el-form-item>
                <el-form-item label="商品封面">
                  <div class="add_updte_pic">
                    <img
                      v-if="addUpdateProductTitle === '修改商品'"
                      :src="productForm.activePic"
                      class="img"
                      alt=""
                    />
                    <div
                      class="delete_btn"
                      @click="
                        handlerDeletePic(productForm.activePic, 'productPic')
                      "
                    >
                      ×
                    </div>
                  </div>
                  <ImageUpload
                    :value="productForm.productPic"
                    :limit="1"
                    :fileType="fileType"
                    uploadImgUrl="http://localhost:8001/product/upload/product/pic"
                    ref="productPicUpload"
                  ></ImageUpload>
                </el-form-item>
                <el-form-item size="large">
                  <el-button type="primary" @click="changeStep('next')"
                    >下一步</el-button
                  >
                </el-form-item>
              </el-form>
            </el-tab-pane>
            <!-- 商品属性 -->
            <el-tab-pane label="商品属性" disabled name="two">
              <!-- 选择属性 -->
              <!-- 添加属性 -->
              <el-form label-width="120px" size="mini">
                <el-form-item label="属性名称">
                  <el-input v-model="attrForm.attrName"></el-input>
                </el-form-item>
                <el-form-item label="属性值">
                  <el-input v-model="attrForm.attrValue"></el-input>
                </el-form-item>
                <el-form-item label="所属分类">
                  <el-cascader
                    v-model="attrForm.catelogId"
                    :options="catelogs"
                    :props="defineProp"
                  ></el-cascader>
                </el-form-item>
                <el-form-item>
                  <el-button type="primary" @click="addAttr"
                    >添加属性</el-button
                  >
                </el-form-item>

                <el-form-item label="分类">
                  <el-cascader
                    v-model="catelogLevel3"
                    :options="catelogs"
                    :props="defineProp"
                  ></el-cascader>
                </el-form-item>
                <el-form-item v-for="attr in attrs" :key="attr.attrId">
                  <el-checkbox
                    :indeterminate="attr.ifIndeterminate"
                    v-model="attr.ifAll"
                    @change="handlerCheckAttrAllChange(attr)"
                    >{{ attr.title }}</el-checkbox
                  >
                  <div style="margin: 15px 0"></div>

                  <div class="attr_box">
                    <!-- <div class="attr_title">

                    </div> -->
                    <div class="attr_list">
                      <div
                        class="attr_item"
                        v-for="(attrValue, i) in attr.attrValues"
                        :key="i"
                      >
                        <el-checkbox-group
                          v-model="attrValue.checked"
                          @change="
                            handlerAttrChange(
                              attrValue.attrValue,
                              attrValue.checked
                            )
                          "
                        >
                          <el-checkbox :label="attrValue.attrValue">
                            {{ attrValue.attrValue }}
                          </el-checkbox>
                        </el-checkbox-group>
                      </div>
                    </div>
                  </div>
                </el-form-item>

                <el-form-item size="large">
                  <el-button @click="changeStep('prev')">上一步</el-button>
                  <el-button type="primary" @click="changeStep('next')"
                    >下一步</el-button
                  >
                </el-form-item>
              </el-form>
            </el-tab-pane>
            <!-- 商品缩略图 -->
            <el-tab-pane label="商品缩略、详情图" disabled name="three">
              <el-form label-width="120px" size="mini">
                <el-form-item label="商品缩略图">
                  <div
                    class="add_updte_pic"
                    v-for="img in productForm.smailPics"
                    :key="img"
                  >
                    <img
                      class="img"
                      v-show="addUpdateProductTitle === '修改商品'"
                      :src="img"
                      alt=""
                    />
                    <div
                      class="delete_btn"
                      @click="handlerDeletePic(img, 'thumbnail')"
                    >
                      ×
                    </div>
                  </div>
                  <ImageUpload
                    :value="productForm.productThumbnailUrl"
                    :limit="4"
                    :fileType="fileType"
                    uploadImgUrl="http://localhost:8001/product/upload/thumbnai/imgs"
                    ref="productThumbnaiUpload"
                    @change-file-size="(size) => productForm.productTsize"
                  ></ImageUpload>
                </el-form-item>
                <el-form-item label="商品详情图片">
                  <div
                    class="add_updte_pic"
                    v-for="img in productForm.detailImages"
                    :key="img"
                  >
                    <img
                      class="img"
                      v-show="addUpdateProductTitle === '修改商品'"
                      :src="img"
                      alt=""
                    />
                    <div
                      class="delete_btn"
                      @click="handlerDeletePic(img, 'detail')"
                    >
                      ×
                    </div>
                  </div>
                  <ImageUpload
                    :value="productForm.productDetailUrl"
                    :limit="10"
                    :fileType="fileType"
                    ref="productDetailUpload"
                    uploadImgUrl="http://localhost:8001/product/upload/detail/imgs"
                  ></ImageUpload>
                </el-form-item>
                <el-form-item size="large">
                  <el-button @click="changeStep('prev')">上一步</el-button>
                  <el-button type="primary" @click="changeStep('next')"
                    >下一步</el-button
                  >
                </el-form-item>
              </el-form>
            </el-tab-pane>
            <!-- sku信息 -->
            <el-tab-pane label="商品 sku" disabled name="for">
              <el-row style="margin: 10px 0 30px 0">
                <el-col :span="2" :offset="21">
                  <el-button type="primary" size="mini" @click="addSku"
                    >添加一个 sku</el-button
                  >
                </el-col>
              </el-row>
              <el-collapse v-model="activeNames">
                <el-collapse-item
                  v-for="item in skus"
                  :key="item.id"
                  :title="item.title"
                  :name="item.id"
                  v-show="skus.length > 0"
                >
                  <el-form label-width="120px" size="mini">
                    <el-form-item :label="`sku${item.id}名称`">
                      <el-input
                        :placeholder="`sku${item.id}名称`"
                        v-model="item.skuName"
                      ></el-input>
                    </el-form-item>
                    <el-form-item :label="`sku${item.id}价格`">
                      <el-input
                        :placeholder="`sku${item.id}价格`"
                        v-model="item.skuPrice"
                      >
                        <template slot="prepend">￥</template>
                      </el-input>
                    </el-form-item>
                    <el-form-item :label="`sku${item.id}封面`">
                      <el-input
                        :placeholder="`sku${item.id}封面`"
                        v-model="item.skuPic"
                      ></el-input>
                    </el-form-item>
                  </el-form>
                </el-collapse-item>
              </el-collapse>
              <el-form>
                <el-form-item label="sku封面`">
                  <div class="add_updte_pic">
                    <img
                      v-if="addUpdateProductTitle === '修改商品'"
                      class="img"
                      src="item.skuPicPreview"
                      alt=""
                    />
                    <div
                      class="delete_btn"
                      @click="handlerDeletePic(item.skuPicPreview, 'sku')"
                    >
                      ×
                    </div>
                  </div>
                  <ImageUpload
                    :value="productForm.skuImg"
                    uploadImgUrl="http://localhost:8001/product/upload/sku/pic"
                    :limit="100"
                    ref="productSkuPicUpload"
                    :fileType="fileType"
                    @uploadSuccess="skuImages"
                  ></ImageUpload>
                </el-form-item>
                <el-form-item>
                  <div class="skus_img">
                    <div class="sku_pic" v-for="url in skuImgs" :key="url">
                      <img :src="url" alt="" />
                      <div
                        class="overhid"
                        :data-clipboard-text="url"
                        @click="copyAddress(url)"
                      >
                        复制地址
                      </div>
                    </div>
                  </div>
                </el-form-item>
                <el-form-item>
                  <el-button type="success" @click="uploadSkuPic"
                    >上传 sku 图片</el-button
                  >
                </el-form-item>
                <el-form-item size="large">
                  <el-button @click="changeStep('prev')">上一步</el-button>
                  <el-button type="primary" @click="saveProduct()">{{
                    addUpdateProductTitle
                  }}</el-button>
                </el-form-item>
              </el-form> </el-tab-pane
            ><!-- 已完成 -->
            <el-tab-pane label="已完成" disabled name="success">
              <div class="success_box">
                <i class="el-icon-circle-check"></i>
                <span>已完成</span>
              </div>
            </el-tab-pane>
          </el-tabs>
        </el-col>
      </el-row>
    </el-drawer>

    <!-- 点击添加属性时 -->
    <el-dialog title="添加属性" :visible.sync="addAttrsVisible">
      <el-table :data="catelogAttsList">
        <el-table-column
          property="attrId"
          label="ID"
          width="150"
        ></el-table-column>
        <el-table-column
          property="attrName"
          label="属性名称"
          width="200"
        ></el-table-column>
        <el-table-column property="attrValue" label="属性值"></el-table-column>
        <el-table-column label="操作">
          <template slot-scope="scope">
            <el-button
              type="primary"
              round
              @click="productAddAttrs(scope.row)"
              size="mini"
              >添加</el-button
            >
          </template>
        </el-table-column>
      </el-table>
    </el-dialog>
    <!-- 修改 -->
  </div>
</template>

<script>
import {
  attrsCatelogs,
  createProduct,
  queryProductList,
  delProduct,
  detail,
  update,
  deleteEditImg,
  loadSkuImg,
} from "@/api/bsapi/product";
import {
  addAttrs,
  updateAttrStatus,
  delAttrs,
  queryAttrsByCatelogId,
} from "@/api/bsapi/attrs";
import { catelogTreelist } from "@/api/bsapi/catelog";
import { addProduct } from "@/api/bsapi/attrsProduct.js";
import Clipboard from "clipboard";
export default {
  data() {
    return {
      //控制右侧框展示
      productAddEnable: false,
      //添加还是修改
      addUpdateProductTitle: "",
      multipleSelection: [],
      //分类
      options: [
        {
          value: "选项1",
          label: "黄金糕",
        },
        {
          value: "选项2",
          label: "双皮奶",
        },
        {
          value: "选项3",
          label: "蚵仔煎",
        },
        {
          value: "选项4",
          label: "龙须面",
        },
        {
          value: "选项5",
          label: "北京烤鸭",
        },
      ],
      value: "",
      //属性新增表单
      attrForm: {
        attrName: null,
        attrValue: null,
        catelogId: null,
      },
      //用于控制添加或修改时点击下一步
      addUpdateStatus: "one",
      //所有添加和修改步骤数组
      steps: ["one", "two", "three", "for", "success"],
      //点击下一步或者上一步时，状态栏要跟着改变
      stepStatus: 0,
      //折叠面板
      activeNames: ["1", "100"],
      //sku
      skus: [],
      //attr
      attrs: [],
      //003A8860.png重复
      attrsTemplate: [],
      //catelog
      catelogs: [],
      checkAttrAll: false,
      checkedAttrValues: [],
      isIndeterminate: false,
      //重新设置分类属性
      defineProp: {
        label: "catelogName",
        value: "catelogId",
        children: "children",
      },
      //选择分类
      catelogLevel3: null,
      //暂存分类id，用于修改
      catelogUpdateId: null,
      //新增商品表单
      productForm: {
        productId: null,
        productTitle: null,
        productPrice: null,
        productPic: null,
        catelogId: null,
        attrId: null,
        productDetailUrl: [],
        productThumbnailUrl: [],
        skuImg: [],
        skus: [
          {
            skuName: null,
            skuPrice: null,
            skuPic: null,
          },
        ],
      },
      //上传文件后缀名
      fileType: ["webp", "png", "jpg", "jpeg", "gif", "avif"],
      //搜索条件
      queryParams: {
        pageNum: 1,
        pageSize: 5,
        productTitle: null,
        status: null,
      },
      //商品列表
      productList: [],
      //商品总条数
      total: 0,
      //控制添加属性框
      addAttrsVisible: false,
      //根据分类id获取属性列表
      catelogAttsList: [],
      //暂存商品id
      templateProductId: 0,
      //选中的商品id
      selectedProductIds: [],
      //选择sku图片
      skuImgs: [],
    };
  },
  methods: {
    //点击关闭右侧框
    handleClose() {
      this.productAddEnable = false;
      //清除数据
      this.reset();
    },
    //点击添加
    handlerAdd() {
      this.productAddEnable = true;
      this.addUpdateProductTitle = "新增";
    },
    //点击更改步骤
    changeStep(type) {
      //遍历所有的步骤条信息
      for (let i = 0; i < this.steps.length; i++) {
        //下一步
        if (type === "next") {
          //当前步骤和某个一致时
          if (this.addUpdateStatus === this.steps[i]) {
            //控制当前步骤，往当前步骤的下一个步骤走
            this.addUpdateStatus = this.steps[i + 1];
            //为了最后一步样式能正常显示
            this.stepStatus = i + 1;
            //如果是最后一个那么
            if (this.addUpdateStatus === "success") {
              //设置当前状态为步骤数组长度
              this.stepStatus = this.steps.length;
            }
            return;
          }
          //上一步
        } else if (type === "prev") {
          if (this.addUpdateStatus === this.steps[i]) {
            this.addUpdateStatus = this.steps[i - 1];
            this.stepStatus = i - 1;
            return;
          }
        }
      }
    },
    //上传成功清除数据
    reset() {
      this.stepStatus = 0;
      this.addUpdateStatus = "one";
    },
    //添加一个sku
    addSku() {
      this.skus.push({
        id: this.skus.length + 1,
        title: "sku " + (this.skus.length + 1),
      });
    },
    //添加时加载属性和分类列表 把属性做成 key list 类型, 如 颜色[红色、蓝色] 尺寸[1.23,1.34]
    async loadAttrsCatelogs(catelogId) {
      this.attrs.length = 0;
      //可能响应数据比较慢，所以，等 dom元素加载完毕后进行
      await this.$nextTick();
      const res = await attrsCatelogs(catelogId);
      if (res.code === 200) {
        this.attrs = res.data.attrItems;
      }
    },
    //加载分类
    loadCatelogTreelist() {
      catelogTreelist().then((res) => {
        if (res.code === 200) {
          this.catelogs = res.data;
        }
      });
    },
    // 全选属性
    handlerCheckAttrAllChange(attr) {
      let checkAll = attr.ifAll;
      //遍历所有的属性值
      let attrs = attr.attrValues.map((e) => {
        //将所有的单选框赋值
        e.checked = checkAll;
        return e;
      });
      //如果为true，则设置 Indeterminate 为false
      if (checkAll) {
        attr.ifIndeterminate = false;
      }
      //重新设置进数组
      this.attrs = this.attrs.map((e) => {
        if (e.title === attr.title) {
          //重新设置单选框
          e.attrValues = attrs;
        }
        return e;
      });
      //转成json，进行修改
      let json = JSON.stringify(this.attrs);
      updateAttrStatus(this.catelogUpdateId, json).then((res) => {
        if (res.code === 200) {
          //刷新属性列表控件
          this.loadAttrsCatelogs(this.catelogUpdateId);
        }
      });
    },
    //选择属性
    handlerAttrChange(val, isChecked) {
      //遍历所有的属性
      this.attrs = this.attrs.map((e) => {
        //传入的名称和当前属性名称一致
        if (val === e.attrValue) {
          //设置 true
          e.checked = isChecked;
        }
        return e;
      });
      let json = JSON.stringify(this.attrs);
      //改变 选中样式
      updateAttrStatus(this.catelogUpdateId, json).then((res) => {
        if (res.code === 200) {
          //刷新属性列表控件
          this.loadAttrsCatelogs(this.catelogUpdateId);
        }
      });
    },
    //添加属性
    async addAttr() {
      if (this.addAttrValidate()) {
        this.attrForm.catelogId =
          this.attrForm.catelogId[this.attrForm.catelogId.length - 1];
        const res = await addAttrs(this.attrForm);
        if (res.code === 200) {
          this.$modal.msgSuccess("添加属性成功！");
          this.attrForm.attrName = null;
          this.attrForm.attrValue = null;
          this.attrForm.catelogId = null;
        }
      }
    },
    //验证属性
    addAttrValidate() {
      if (this.attrForm.attrName === null || this.attrForm.attrName === "") {
        this.$modal.msgWarning("请输入属性名称！");
        return false;
      } else if (
        this.attrForm.attrValue === null ||
        this.attrForm.attrValue === ""
      ) {
        this.$modal.msgWarning("请输入属性值！");
        return false;
      } else if (
        this.attrForm.catelogId === null ||
        this.attrForm.catelogId === ""
      ) {
        this.$modal.msgWarning("请选择属性所属分类！");
        return false;
      } else {
        return true;
      }
    },
    //清空表单
    resetForm() {
      this.productForm.productTitle = null;
      this.productForm.productPrice = null;
      this.productForm.productPic = null;
      this.productForm.catelogId = null;
      this.productForm.productDetailUrl = null;
      this.productForm.productThumbnailUrl = null;
      this.skus.length = 0;
      this.skus = [
        {
          skuName: null,
          skuPrice: null,
          skuPic: null,
        },
      ];
    },
    //上传 sku 图片
    uploadSkuPic() {
      //sku 图片提交
      this.$refs.productSkuPicUpload.submitUpload();
    },
    //点击新增或者修改商品
    saveProduct() {
      //设置树形分类id
      this.productForm.catelogId =
        this.catelogLevel3[this.catelogLevel3.length - 1];
      this.productForm.skus = this.skus;
      //将表单数据转成json进行提交
      let json = JSON.stringify(this.productForm);
      //商品封面图上传文件
      this.$refs.productPicUpload.submitUpload();
      //商品详情图上传文件
      this.$refs.productDetailUpload.submitUpload();
      //商品缩略图上传文件
      this.$refs.productThumbnaiUpload.submitUpload();
      //判断是添加还是修改
      if (this.addUpdateProductTitle === "新增") {
        //像数据发起保存商品的方法
        createProduct(json).then((res) => {
          if (res.code == 200) {
            //提示
            this.$modal.msgSuccess("新增商品成功！");
            //步骤条往下走
            this.changeStep("next");
            //清除表单
            this.resetForm();
            //刷新列表
            this.loadProductList();
          }
        });
      } else if ((this.addUpdateProductTitle = "修改商品")) {
        update(json).then((res) => {
          if (res.code == 200) {
            //提示
            this.$modal.msgSuccess("修改商品成功！");
            //步骤条往下走
            this.changeStep("next");
            //清除表单
            this.resetForm();
            //刷新列表
            this.loadProductList();
          }
        });
      }
    },
    //获取商品信息
    async loadProductList() {
      const res = await queryProductList({ ...this.queryParams });
      if (res.code === 200) {
        this.productList = res.rows;
        this.total = res.total;
      }
    },
    //点击更多操作
    handlerSetting(type, product) {
      if (type == "addAttr") {
        //添加属性
        this.templateProductId = product.productId;
        this.showAddAttrDialog(product.catelgoId);
      } else if ("query" === type) {
        //查看
        this.queryProductInfo(product.productId);
      } else if ("del" === type) {
        //删除
        this.deleteProduct(product.productId);
      } else if ("edit" === type) {
        //修改
        this.editProduct(product.productId);
      }
    },
    //点击属性
    showAddAttrDialog(catelogId) {
      this.addAttrsVisible = true;
      queryAttrsByCatelogId(catelogId, this.templateProductId).then((res) => {
        if (res.code === 200) {
          this.catelogAttsList = res.data.filter((e) => e != null);
        }
      });
    },
    //点击添加属性
    productAddAttrs(attr) {
      addProduct({
        productId: this.templateProductId,
        attrId: attr.attrId,
      }).then((res) => {
        if (res.code == 200) {
          //提示
          this.$modal.msgSuccess("新增属性成功！");
          //刷新列表
          this.loadProductList();
          //关闭新增属性框
          this.addAttrsVisible = false;
        }
      });
    },
    //分页
    changePage({ page }) {
      this.queryParams.pageNum = page;
      //刷新数据
      this.loadProductList();
    },
    //查看当前商品信息
    queryProductInfo(productId) {
      this.$router.push({ path: `/view/product`, query: { productId } });
    },
    //删除商品信息
    deleteProduct(productId) {
      this.$confirm(`确定是否删除该id【${productId}】吗`, "温馨提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning",
      })
        .then(() => {
          delProduct(productId).then((res) => {
            if (res.code === 200) {
              this.$modal.msgSuccess("删除成功！");
              //刷新数据
              this.loadProductList();
            }
          });
        })
        .catch(() => {});
    },
    //修改商品信息
    async editProduct(productId) {
      //请求商品的详情信息
      const res = await detail(productId);
      if (res.code == 200) {
        //获取的数据赋值给表单
        this.productForm = res.data;
        this.catelogLevel3 = res.data.catelogIds;
        this.addUpdateProductTitle = "修改商品";
        this.skus = this.productForm.skus.map((sku, index) => {
          return {
            id: ++index,
            title: "sku " + index,
            skuName: sku.skuName,
            skuPicPreview: sku.skuPic,
            skuPrice: sku.skuPrice,
          };
        });
        //打开右侧框
        this.productAddEnable = true;
      }
    },
    //删除图片
    handlerDeletePic(url, type) {
      this.$confirm(`确定是否删除该图片吗？`, "温馨提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning",
      })
        .then(async () => {
          const res = await deleteEditImg({ img: url, type });
          if (res.code === 200) {
            this.$modal.msgSuccess("删除成功！");
            //刷新数据
            this.editProduct(this.productForm.productId);
          }
        })
        .catch(() => {});
    },
    //选择某一项
    handlerSelectChange(selecteds) {
      this.selectedProductIds = selecteds.map((e) => e.productId);
    },
    //批量删除
    handlerSelectedDelete() {
      if (this.selectedProductIds.length == 0) {
        this.$modal.alertWarning("必须选择要删除的数据！");
        return;
      }
      this.$confirm(
        `确定是否删除 【${this.selectedProductIds}】吗？`,
        "温馨提示",
        {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning",
        }
      )
        .then(async () => {
          const res = await delProduct(this.selectedProductIds);
          if (res.code === 200) {
            this.$modal.msgSuccess("删除成功！");
            //刷新数据
            this.loadProductList();
          }
        })
        .catch(() => {});
    },
    //点击上传成功后回调
    async skuImages(fileList) {
      const { code, data } = await loadSkuImg();
      if (code == 200) {
        this.skuImgs = data;
      }
    },
    //复制地址
    copyAddress(url) {
      console.log(url);
      var clipboard = new Clipboard(".overhid");
      clipboard.on("success", (e) => {
        this.$modal.msgSuccess("复制成功");
        // 释放内存
        clipboard.destroy();
      });
      clipboard.on("error", (e) => {
        // 不支持复制
        this.$modal.msgError("该浏览器不支持自动复制");
        // 释放内存
        clipboard.destroy();
      });
    },
  },
  //监听
  watch: {
    //监听点击分类
    catelogLevel3(val) {
      this.catelogUpdateId = val[val.length - 1];
      this.loadAttrsCatelogs(this.catelogUpdateId);
    },
  },
  beforeMount() {
    //加载属性分类信息
    this.loadCatelogTreelist();
    //加载商品列表信息
    this.loadProductList();
  },
};
</script>

<style scoped lang="scss">
.attr_box {
  .attr_list {
    display: flex;

    .attr_item {
      margin: 0 10px;
    }
  }
}
.success_box {
  display: flex;
  justify-content: center;
  font-size: 20px;
  align-items: center;
  margin: 30px 0;
  color: #67c23a;
  span {
    margin-left: 5px;
  }
}
//商品表格
.demo-table-expand {
  font-size: 0;
  display: flex;
  flex-wrap: wrap;
  label {
    width: 90px;
    color: #99a9bf;
  }
  .el-form-item {
    margin-right: 0;
    margin-bottom: 0;
    width: 33%;
    padding-left: 30px;
  }
}
//商品封面
.pic_box {
  width: 150px;
  height: 150px;
  img {
    width: 100%;
    height: 100%;
  }
}
.add_updte_pic {
  @extend .pic_box;
  position: relative;
  display: inline-block;
  margin-right: 15px;
  img {
    width: 100%;
    height: 100%;
  }
  &:hover {
    .delete_btn {
      display: block;
    }
  }
  .delete_btn {
    width: 25px;
    height: 25px;
    line-height: 25px;
    background-color: rgba($color: #000000, $alpha: 0.7);
    border-radius: 5px 0 0 25px;
    position: absolute;
    top: 0;
    right: 0px;
    color: #fff;
    font-size: 15px;
    text-align: center;
    display: none;
    cursor: pointer;
  }
}
.butom_box {
}
.skus_img {
  display: flex;
  flex-wrap: wrap;
  .sku_pic {
    width: 120px;
    height: 120px;
    margin-right: 15px;
    cursor: pointer;
    position: relative;
    .overhid {
      width: 120px;
      height: 120px;
      background-color: rgba($color: #000000, $alpha: 0.5);
      color: #fff;
      position: absolute;
      top: 0;
      left: 0;
      text-align: center;
      line-height: 120px;
      display: none;
    }
    &:hover .overhid {
      display: block;
    }
    img {
      width: 100%;
      height: 100%;
    }
  }
}
</style>
