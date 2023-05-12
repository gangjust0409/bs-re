<template>
  <div class="user_info">
    <div class="product_content">
      <!-- <div class="show_big_pic">
        <img
          src="https://wwc.alicdn.com/avatar/getAvatar.do?userId=2200535085316&width=160&height=160&type=sns"
          alt=""
        />
      </div> -->
      <div class="user_pic">
        <img :src="userInfo.userPic" alt="" />
        <el-upload
          :headers="headers"
          v-model:file-list="fileList"
          action="http://localhost:8001/app/user/upload/pic"
          :limit="1"
          :on-success="handlerSuccess"
          :show-file-list="false"
        >
          <span class="editPic">修改头像</span>
        </el-upload>
      </div>
      <div class="user_row">
        <div class="label">昵称：{{ nickName }}</div>
        <el-icon @click="handlerUpdateUserInfo('nickName')"
          ><IEpEdit
        /></el-icon>
      </div>
      <div class="user_row">
        <div class="label">我的钱包：</div>
        <div class="price">￥{{ myPrice }}</div>
      </div>
      <div class="user_row">
        <div class="label">性别：</div>
        <el-radio-group v-model="userInfo.sex" @change="changeSex">
          <el-radio :label="1">男</el-radio>
          <el-radio :label="2">女</el-radio>
          <el-radio :label="3">保密</el-radio>
        </el-radio-group>
      </div>
      <div class="user_row">
        <div class="label">邮箱：{{ userInfo.email }}</div>
        <el-icon @click="handlerUpdateUserInfo('email')"><IEpEdit /></el-icon>
      </div>
    </div>
    <div class="product_content chongzhi">
      <el-input placeholder="price" v-model="price" clearable />
      <el-button type="primary" plain @click="topUp">充 值</el-button>
    </div>
    <!-- 点击 tb账号或修改邮箱 -->
    <el-dialog
      v-model="updateUserInfoVisible"
      :title="`修改${updateUserInfoTitle}`"
      width="30%"
      :before-close="handlerUpdateInfoClose"
    >
      <el-form :inline="true" :model="userForm" class="demo-form-inline">
        <el-form-item :label="updateUserInfoTitle">
          <el-input
            v-model="userForm.value"
            :placeholder="`请输入${updateUserInfoTitle}`"
            @blur="validateAccountInput"
          />
        </el-form-item>
      </el-form>
      <template #footer>
        <span class="dialog-footer">
          <el-button @click="handlerUpdateInfoClose">取 消</el-button>
          <el-button type="primary" @click="updateUserInfoSubmit">
            确 定
          </el-button>
        </span>
      </template>
    </el-dialog>
  </div>
</template>

<script setup>
import {
  ref,
  reactive,
  computed,
  getCurrentInstance,
  onBeforeMount,
  nextTick,
} from "vue";
import useUserStore from "@/store/user";
import { useRouter } from "vue-router";
import { validateAccountUnique, editUser } from "@/api/user";
const { proxy } = getCurrentInstance();
const userStore = useUserStore();
const router = useRouter();
//用户信息
const userInfo = computed(() => userStore.user);
const nickName = computed(() => userStore.nickName);
const myPrice = computed(() =>
  parseFloat(userStore.user.totalPrice).toFixed(2)
);

//updateUserInfoVisible
const updateUserInfoVisible = ref(false);
//是修改名称还是邮箱
const updateUserInfoTitle = ref("");
//充值的金额
const price = ref(null);

//userForm
const userForm = reactive({
  value: null,
});
//上传头像请求头
const headers = reactive({
  "X-Token": userStore.token,
});
//上传文件保存的文件
const fileList = ref([]);
//关闭修改用户信息模态框
function handlerUpdateInfoClose() {
  updateUserInfoVisible.value = false;
}
//点击打开模态框
function handlerUpdateUserInfo(type) {
  this.updateUserInfoVisible = true;
  if ("nickName" == type) {
    updateUserInfoTitle.value = "tb 账号";
  } else {
    updateUserInfoTitle.value = "电子邮箱";
  }
}
//提交用户信息
const updateUserInfoSubmit = async () => {
  if (checkUserInfoValidate()) {
    const form = {};
    if (updateUserInfoTitle.value == "电子邮箱") {
      form.email = userForm.value;
    } else {
      form.account = userForm.value;
    }
    //修改
    const { code } = await editUser(form);
    if (code == 200) {
      proxy.$msg.success("修改成功！");
      //关闭模态框
      handlerUpdateInfoClose();
      userForm.value = null;
      //刷新数据
      userStore.login();
    }
  }
};
//验证用户信息
const checkUserInfoValidate = () => {
  let emailRegx =
    /^(([^<>()[\]\\.,;:\s@"]+(\.[^<>()[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
  if (userForm.value == null || userForm.value.trim() == "") {
    proxy.$msg.warning(`请输入${updateUserInfoTitle.value}`);
    return false;
  } else if (updateUserInfoTitle.value == "tb 账号") {
    //名称不能重复
    return validateAccountInput();
  } else if (
    updateUserInfoTitle.value == "电子邮箱" &&
    !emailRegx.test(userForm.value)
  ) {
    //电子邮箱
    proxy.$msg.warning(`电子邮箱格式不正确！`);
    return false;
  } else {
    return true;
  }
};
//当鼠标移开文本框时
async function validateAccountInput() {
  if (updateUserInfoTitle.value == "tb 账号") {
    const { code, data } = await validateAccountUnique(userForm.value);
    if (code == 200 && data) {
      proxy.$msg.warning(`该账号已被使用过，请重新想想！`);
      return !data;
    }
  }
}
//点击修改性别
async function changeSex(sex) {
  const form = {
    sex,
  };
  const { code } = await editUser(form);
  if (code == 200) {
    proxy.$msg.success("修改性别成功！");
  }
}
//充值
async function topUp() {
  const form = {
    totalPrice: price.value,
  };
  const { code } = await editUser(form);
  if (code == 200) {
    proxy.$msg.success("充值成功！");
    price.value = null;
    //刷新数据
    userStore.login();
  }
}
//上传头像成功回调
function handlerSuccess({ code }) {
  if (code == 200) {
    fileList.value.length = 0;
    userStore.login();
    proxy.$msg.success("上传成功了耶！☺");
  }
}
//刷新数据
onBeforeMount(async () => {
  await nextTick();
  const code = await userStore.login();
  if (code == 403) {
    //跳转到登录页
    router.replace("/login");
  }
});
</script>

<style lang="scss" scoped>
.user_info {
  .product_content {
    margin: 0;
    padding-left: 80px;
    .show_big_pic :deep(body) {
      position: absolute;
      width: 100%;
      height: 100vh;
      z-index: 99;
      background-color: rgba($color: #000000, $alpha: 0.5);
      img {
        width: 50%;
        height: 100%;
        z-index: 899;
        position: absolute;
      }
    }
    .user_pic {
      width: 100px;
      height: 100px;
      border-radius: 50%;
      padding: 3px;
      position: relative;
      img {
        width: 100%;
        height: 100%;
        border-radius: 50%;
      }
      &:hover .editPic {
        display: block;
      }
      .editPic {
        width: 92px;
        height: 40px;
        border-radius: 0 0 45px 45px;
        background-color: rgba($color: #000000, $alpha: 0.5);
        color: #fff;
        position: absolute;
        top: 56px;
        left: 4px;
        cursor: pointer;
        line-height: 2;
        text-align: center;
        display: none;
      }
    }
    .user_row {
      display: flex;
      align-items: center;
      margin: 15px 0;
      .label {
        margin-right: 10px;
      }
      i {
        cursor: pointer;
      }
    }
  }
  .chongzhi {
    display: flex;
    margin-top: 20px;
    :deep(.el-input) {
      margin-right: 15px;
    }
  }
}
</style>