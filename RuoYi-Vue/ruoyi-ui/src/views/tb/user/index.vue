<template>
  <div class="app-container">
    <!-- 搜索 -->
    <el-form
      :inline="true"
      :model="queryParams"
      class="queryParams-form-inline"
    >
      <el-form-item label="账号/真实姓名">
        <el-input v-model="keyword" placeholder="账号/真实姓名"></el-input>
      </el-form-item>
      <el-form-item label="性别">
        <el-select v-model="queryParams.sex" placeholder="选择性别">
          <el-option label="全部" value="0"></el-option>
          <el-option label="男" value="1"></el-option>
          <el-option label="女" value="2"></el-option>
          <el-option label="保密" value="3"></el-option>
        </el-select>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="searchParams">查询</el-button>
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
          @click="handleAdd"
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
          @click="handleDel"
          v-hasPermi="['bsapi:user:delete']"
          >删除</el-button
        >
      </el-col>
    </el-row>
    <!-- 显示的数据 -->
    <div class="user_container">
      <el-checkbox
        :indeterminate="isIndeterminate"
        v-model="checkAll"
        @change="handleCheckAllChange"
        >全选</el-checkbox
      >
      <div class="user_list">
        <div
          v-for="user in list"
          :key="user.userId"
          :class="{ user_item: true, active: user.isCheck }"
          @mouseover="changeUserItem(user)"
          @mouseout="changeUserItemLevel(user)"
          @dblclick="checkUserItem(user)"
        >
          <div class="user_pic">
            <img
              :src="user.userPic"
              :alt="user.account"
              :title="user.account"
            />
          </div>
          <div class="user_info">
            <div class="user_account">账号：{{ user.account }}</div>
            <div class="overhidden" v-show="user.isSpread">
              <div class="user_account">姓名：{{ user.nickName }}</div>
              <div class="sex">性别：
              <span v-if="user.sex === 1">男</span>
                <span v-else-if="user.sex === 2">女</span>
                <span v-else>保密</span>
              </div>
              <div class="concart">
                <div class="phone" v-if="user.phone">
                  手机号：{{ user.phone }}
                </div>
                <div class="email" v-if="user.email">
                  邮箱：{{ user.email }}
                </div>
              </div>
              <div class="date">创建日期：{{ user.createDate }}</div>
            </div>

            <a href="" @click="spreadChange($event, user)">
              {{ user.spreadText }}&nbsp;<i :class="user.spreadIcon"></i
            ></a>
          </div>
          <!-- 操作 -->
          <Transition>
            <div class="setting_box" v-show="user.overEnable">
              <el-button
                size="mini"
                icon="el-icon-edit"
                circle
                @click="getUserById(user.userId)"
              ></el-button>
              <el-button
                size="mini"
                type="danger"
                icon="el-icon-delete"
                circle
                @click="deleteUser(user)"
              ></el-button>
            </div>
          </Transition>
        </div>
      </div>
      <!-- 分页 -->
      <Pagination
        :total="total"
        :page="queryParams.pageNum"
        :limit="queryParams.pageSize"
        @pagination="changePage"
      />
    </div>

    <!-- 添加或修改 -->
    <el-dialog
      :title="addOrUpdateText + '用户'"
      :visible.sync="addOrUpdateStatue"
      width="30%"
      :before-close="closeModel"
    >
      <el-form
        ref="userFormRef"
        :model="userForm"
        :rules="userRules"
        label-width="120px"
      >
        <el-form-item label="账号">
          <el-input v-model="userForm.account" disabled></el-input>
        </el-form-item>
        <el-form-item label="用户名" prop="nickName">
          <el-input v-model="userForm.nickName"></el-input>
        </el-form-item>
        <el-form-item label="密码" prop="password">
          <el-input v-model="userForm.password" type="password"></el-input>
        </el-form-item>
        <el-form-item label="再次输入密码" prop="repassword">
          <el-input v-model="userForm.repassword" type="password"></el-input>
        </el-form-item>
        <el-form-item label="性别">
          <el-radio-group v-model="userForm.sex">
            <el-radio :label="1">男</el-radio>
            <el-radio :label="2">女</el-radio>
            <el-radio :label="3">保密</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="邮箱" prop="email">
          <el-input v-model="userForm.email"></el-input>
        </el-form-item>
      </el-form>
      <span slot="footer" class="dialog-footer">
        <el-button @click="closeModel">取 消</el-button>
        <el-button type="primary" @click="submit">{{
          addOrUpdateText
        }}</el-button>
      </span>
    </el-dialog>
  </div>
</template>

<script>
import {
  listUser,
  addUser,
  getUser,
  updateUser,
  delUser,
  batchUserDelete,
} from "@/api/bsapi/user";
export default {
  dicts: ["sys_normal_disable"],
  data() {
    return {
      //搜索条件
      keyword: null,
      queryParams: {
        nickName: this.keyword,
        account: this.keyword,
        sex: null,
        pageNum: 1,
        pageSize: 10,
      },

      //用户列表
      list: [],
      total: 0,
      //是否全选
      checkAll: false,
      //选中的用户
      checkUsers: [],
      //添加或者修改
      addOrUpdateStatue: false,
      addOrUpdateText: "添加",
      //添加或者修改表单
      userForm: {
        userId: null,
        sex: 1,
      },
      //验证表单
      userRules: {
        nickName: [
          { required: true, message: "请输入用户名", trigger: "blur" },
        ],
        password: [
          { required: true, message: "请输入密码", trigger: "blur" },
          { min: 6, message: "密码长度不能小于6位", trigger: "blur" },
        ],
        repassword: [
          {
            validator: (rule, value, callback) => {
              if (value === null || value === undefined) {
                callback(new Error("请再次输入密码"));
              } else if (value !== this.userForm.password) {
                callback(new Error("两次输入密码不一致"));
              } else {
                callback();
              }
            },
            trigger: "blur",
          },
        ],
      },
      //负责多选控制样式，true 框内类似有一个 - 号，反之，就是
      isIndeterminate: false,
    };
  },
  methods: {
    //搜索
    searchParams() {
      this.queryParams.nickName = this.keyword;
      this.queryParams.account = this.keyword;
      this.userList();
    },
    //展开
    spreadChange(e, user) {
      user.isSpread = !user.isSpread;
      if (user.isSpread) {
        user.spreadIcon = "el-icon-arrow-up";
        user.spreadText = "收起";
      } else {
        user.spreadIcon = "el-icon-arrow-down";
        user.spreadText = "展开其他信息";
      }
      e.preventDefault();
    },
    //鼠标移到用户信息
    changeUserItem(user) {
      user.overEnable = true;
    },
    //鼠标移走
    changeUserItemLevel(user) {
      user.overEnable = false;
    },
    //加载用户列表
    userList() {
      listUser(this.queryParams).then((res) => {
        let temp = {
          //展开替换图标
          spreadIcon: "el-icon-arrow-down",
          //展开文本
          spreadText: "展开其他信息",
        };
        if (res.code == 200) {
          this.list.length = 0;
          res.rows.forEach((row) => {
            this.list.push({
              ...row,
              overEnable: false,
              isSpread: false,
              isCheck: false,
              ...temp,
            });
          });
          this.total = res.total;
        }
      });
    },
    //点击添加
    handleAdd() {
      this.addOrUpdateStatue = true;
    },
    //分页
    changePage({ page, limit }) {
      this.queryParams.pageNum = page;
      this.queryParams.pageSize = limit;
      this.userList();
    },
    //全选
    handleCheckAllChange() {
      this.list.forEach((user) => {
        user.isCheck = this.checkAll;
        if (this.checkAll) {
          this.checkUsers.push(user);
        } else {
          this.checkUsers.length = 0;
        }
        this.isIndeterminate = false;
      });
    },
    //双击选中
    checkUserItem(user) {
      user.isCheck = !user.isCheck;
      if (user.isCheck) {
        this.checkUsers.push(user);
      } else {
        for (let i = 0; i < this.checkUsers.length; i++) {
          if (this.checkUsers[i].userId === user.userId) {
            this.checkUsers.splice(i, 1);
          }
        }
      }
      //判断双击是否已经全部选中
      this.checkAll = this.checkUsers.length === this.list.length;
      this.isIndeterminate = !this.checkAll;
    },
    //关闭模态框
    closeModel() {
      this.addOrUpdateStatue = false;
    },
    //重置表单
    reset() {
      this.userForm.userId = null;
      this.userForm.nickName = null;
      this.userForm.password = null;
      this.userForm.repassword = null;
      this.userForm.email = null;
      this.userForm.phone = null;
    },
    //根据id获取用户信息
    getUserById(userId) {
      getUser(userId).then((res) => {
        if (res.code == 200) {
          this.addOrUpdateStatue = true;
          this.addOrUpdateText = "修改";
          this.userForm = res.data;
          this.userForm.repassword = this.userForm.password;
        }
      });
    },
    //提交表单
    submit() {
      this.$refs.userFormRef.validate(async (validate) => {
        if (validate) {
          if (this.userForm.userId === null) {
            // 添加
            const res = await addUser(this.userForm);
            if (res.code === 200) {
              this.$modal.msgSuccess(this.addOrUpdateText + "成功");
              this.closeModel();
              this.userList();
              this.reset();
            }
          } else {
            //修改
            const res = await updateUser(this.userForm);
            if (res.code === 200) {
              this.$modal.msgSuccess(this.addOrUpdateText + "成功");
              this.closeModel();
              this.userList();
              this.reset();
            }
          }
        }
      });
    },
    //删除
    deleteUser(user) {
      this.$confirm(`确定删除账号为【${user.account}】吗？`, "温馨提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning",
      })
        .then(async () => {
          const res = await delUser(user.userId);
          if (res.code == 200) {
            this.$modal.msgSuccess("删除成功！");
            this.userList();
          }
        })
        .catch(() => {});
    },
    //删除多个
    handleDel() {
      if (this.checkUsers.length === 0) {
        this.$modal.msgWarning("请先选择，点击全选或者双击用户信息进行删除！");
        return;
      }
      let ids = this.checkUsers.map((user) => user.userId);
      this.$confirm(`确定删除【${ids}】吗？`, "温馨提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning",
      })
        .then(() => {
          batchUserDelete(ids).then((res) => {
            if (res.code == 200) {
              this.$modal.msgSuccess("批量删除成功！");
              this.userList();
              //清空已选择的
              this.checkUsers.length = 0;
            }
          });
        })
        .catch(() => {});
    },
  },
  beforeMount() {
    this.userList();
  },
};
</script>

<style scoped lang="scss">
.user_container {
  padding: 15px 0px;
  .user_list {
    display: flex;
    flex-wrap: wrap;
    .user_item {
      width: 200px;

      margin: 20px 10px 20px 0;
      font-size: 14px;
      line-height: 2;
      padding: 10px;
      border-radius: 10px;
      border: 1px solid #eee;
      font-family: PingFangSC-Regular;
      .user_pic {
        width: 120px;
        height: 120px;
        margin: 0 auto;
        img {
          width: 100%;
          height: 100%;
          border-radius: 10px;
        }
      }
      .user_info {
        padding: 0 5px;
        .user_account {
        }
        a {
          display: block;
          text-align: right;
        }
        .overhidden {
          .sex {
          }
          .concart {
            .phone {
              font-size: 12px;
            }
            .email {
              font-size: 12px;
            }
          }
          .date {
          }
        }
      }
      .setting_box {
        text-align: center;
        margin-top: 10px;
      }
      &:hover,
      &.active {
        border-color: #ff0036;
      }
    }
  }
}
/* 下面我们会解释这些 class 是做什么的 */
.v-enter-active,
.v-leave-active {
  transition: opacity 0.5s ease;
}

.v-enter-from,
.v-leave-to {
  opacity: 0;
}
</style>
