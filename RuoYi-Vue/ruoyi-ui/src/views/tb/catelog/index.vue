<template>
  <div class="app-container">
    <!-- 添加一级分类 -->
    <el-row :gutter="10">
      <el-col :span="5">
        <el-input
          v-model="catelogForm.catelogName"
          placeholder="一级分类"
        ></el-input>
      </el-col>
      <el-col :span="5">
        <el-input
          v-model="catelogForm.icon"
          placeholder="一级分类的图标"
        ></el-input>
      </el-col>
      <el-col :span="1.5">
        <el-button type="primary" @click="saveCatelog(0, 0)">添加</el-button>
      </el-col>
    </el-row>
    <!-- 展示分类列表 -->
    <div class="show_catelog_list">
      <el-tree
        :data="treeList"
        :props="defaultProps"
        accordion
      >
        <div class="tree_list" slot-scope="{ node, data }">
          <div>{{ data.catelogName }}</div>
          <div>
            <el-tooltip
              class="item"
              effect="dark"
              content="添加分类"
              placement="top"
            >
              <el-button
                type="primary"
                icon="el-icon-circle-plus-outline"
                circle
                size="mini"
                @click="showCatalog(data.catelogId, node.level)"
              ></el-button>
            </el-tooltip>

            <el-tooltip
              class="item"
              effect="dark"
              content="修改分类"
              placement="top"
            >
              <el-button
                icon="el-icon-edit"
                circle
                size="mini"
                @click="updateShowCatelog(data)"
              ></el-button>
            </el-tooltip>
            <el-tooltip
              class="item"
              effect="dark"
              content="删除分类"
              placement="top"
            >
              <el-button
                type="danger"
                icon="el-icon-delete"
                size="mini"
                circle
                @click="removeCatelog(data)"
              ></el-button>
            </el-tooltip>
          </div>
        </div>
      </el-tree>
    </div>
    <!-- 二级三级分类 -->
    <el-dialog
      :title="`${updateAddTitle}分类`"
      :visible.sync="catelogVisible"
      width="30%"
      :before-close="handleClose"
    >
      <el-row :gutter="10">
        <el-col :span="24">
          <el-input
            v-model="catelogForm.catelogName2"
            placeholder="分名称类"
          ></el-input>
        </el-col>
      </el-row>
      <span slot="footer" class="dialog-footer">
        <el-button @click="handleClose">取 消</el-button>
        <el-button type="primary" @click="confrim">{{
          updateAddTitle
        }}</el-button>
      </span>
    </el-dialog>
  </div>
</template>

<script>
import {
  catelogTreelist,
  addCatelog,
  delCatelog,
  updateCatelog,
  getCatelog,
} from "@/api/bsapi/catelog";
export default {
  data() {
    return {
      //树形分类列表
      treeList: [],
      //自定义树形显示的label
      defaultProps: {
        children: "children",
        label: "catelogName",
      },
      catelogForm: {
        //一级分类值
        catelogName: null,
        catelogName2: null,
        //一级分类的图标
        icon: null,
      },
      //是否展示分类表单
      catelogVisible: false,
      //添加和修改分类标题
      updateAddTitle: "",
    };
  },
  methods: {
    //获取分类树形列表
    getTreeList() {
      catelogTreelist().then((res) => {
        if (res.code === 200) {
          this.treeList = res.data;
        }
      });
    },
    //添加分类
    saveCatelog(parentId, level) {
      this.catelogForm.parentId = parentId;
      this.catelogForm.level = ++level;
      //保存到数据库
      this.commonSaveCatelog();
    },
    //添加二级分类
    confrim() {
      this.catelogForm.catelogName = this.catelogForm.catelogName2;
      //保存到数据库
      this.commonSaveCatelog();
      //关闭模态框
      this.catelogVisible = false;
    },
    //显示模态框
    showCatalog(parentId, level) {
      this.catelogVisible = true;
      this.catelogForm.parentId = parentId;
      this.catelogForm.level = ++level;
      this.updateAddTitle = "添加";
    },
    //公共添加分类的方法
    commonSaveCatelog() {
      //判断是添加·1还是修改
      if (this.catelogForm.catelogId !== undefined) {
        updateCatelog(this.catelogForm).then((res) => {
          if (res.code == 200) {
            this.$modal.msgSuccess(`修改成功！`);
            this.catelogForm.catelogName = null;
            this.catelogForm.catelogName2 = null;
            this.catelogForm.icon = null;
            this.catelogForm.parentId = null;
            this.catelogForm.level = null;
            //刷新数据
            this.getTreeList();
          }
        });
      } else {
        addCatelog({
          ...this.catelogForm,
        }).then((res) => {
          if (res.code == 200) {
            this.$modal.msgSuccess(`添加${this.catelogForm.level}分类成功！`);
            this.catelogForm.catelogName = null;
            this.catelogForm.catelogName2 = null;
            this.catelogForm.icon = null;
            this.catelogForm.parentId = null;
            this.catelogForm.level = null;
            //刷新数据
            this.getTreeList();
          }
        });
      }
    },
    //关闭模态框
    handleClose() {
      this.catelogVisible = false;
    },
    //点击删除
    removeCatelog(catelog) {
      this.$confirm(`确认是否删除【${catelog.catelogName}】吗？`, "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning",
      })
        .then(async () => {
          //判断当前分类是否是一级分类
          if (catelog.children !== null && catelog.children.length > 0) {
            this.$modal.msgWarning("当前是分类还有子菜单，不能删除！");
            return;
          }
          const res = await delCatelog(catelog.catelogId);
          if (res.code == 200) {
            this.$modal.msgSuccess("删除成功！");

            //刷新数据
            this.getTreeList();
          }
        })
        .catch(() => {});
    },
    //点击展示修改分类
    async updateShowCatelog(catelog) {
      this.catelogForm.parentId = catelog.parentId;
      this.catelogForm.level = catelog.level;
      this.catelogForm.catelogId = catelog.catelogId;
      this.updateAddTitle = "修改";
      //获取当前分类id的分类名称
      const res = await getCatelog(catelog.catelogId);
      if (res.code === 200)
        this.catelogForm.catelogName2 = res.data.catelogName;
      this.catelogVisible = true;
    },
  },
  beforeMount() {
    this.getTreeList();
  },
};
</script>

<style scoped lang="scss">
.show_catelog_list {
  margin-top: 20px;
  .tree_list {
    width: 100%;
    display: flex;
    justify-content: space-between;
    padding: 0 20px 0 0;
    align-items: center;
    & > div {
      margin: 10px 0;
    }
    .el-tree-node__content {
      @extend .tree_list;
    }
  }
}
</style>