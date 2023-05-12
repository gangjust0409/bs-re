<template>
  <div class="login_container">
    <div class="login_header">
      <div class="logo">
        <img
          src="data:image/png;base64,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"
          alt=""
        />
      </div>
      <div><router-link to="/home">返回首页</router-link></div>
    </div>
    <div class="login_section">
      <!-- 注册框 -->
      <div class="login_box">
        <div class="title">
          <div class="tit">用户注册</div>
        </div>
        <div class="login_input">
          <el-form :model="userForm" :rules="userRules" ref="form">
            <el-form-item prop="nickName">
              <el-input
                placeholder="用户名"
                :prefix-icon="UserFilled"
                v-model="userForm.nickName"
              />
            </el-form-item>
            <el-form-item prop="password">
              <el-input
                placeholder="密码"
                type="password"
                :prefix-icon="Lock"
                v-model="userForm.password"
              />
            </el-form-item>
            <el-form-item prop="repassword">
              <el-input
                placeholder="再次输入密码"
                type="password"
                :prefix-icon="Lock"
                v-model="userForm.repassword"
              />
            </el-form-item>
            <el-form-item prop="email">
              <el-input
                placeholder="邮箱"
                :prefix-icon="Message"
                v-model="userForm.email"
              />
            </el-form-item>
          </el-form>
          <div class="msg">
            已有账号？<router-link style="color: #ff0036" to="/login"
              >去登录</router-link
            >
          </div>
          <el-button @click="() => router.back()">返回</el-button>
          <el-button type="primary" @click="register">注册</el-button>
        </div>
      </div>
    </div>
    <menu-footer></menu-footer>
  </div>
</template>

<script setup>
import { UserFilled, Lock, Message } from "@element-plus/icons-vue";
import { useRouter, useRoute } from "vue-router";
import { ref, reactive, getCurrentInstance } from "vue";
import { registerFun, validateUser, validateEmailHasExists } from "@/api/user";

const router = useRouter();
const { proxy } = getCurrentInstance();

const form = ref();

//form表单
const userForm = ref({
  nickName: null,
  password: null,
  repassword: null,
  email: null,
});

//验证
const userRules = reactive({
  nickName: [
    {
      validator: async (rule, value, callback) => {
        if (value === null || value == undefined || value == "") {
          callback(new Error("请再次用户名"));
        } else {
          const res = await validateUser(value);
          if (res.code == 200 && res.data) {
            callback(new Error("该用户名已存在！请重新输入"));
          } else {
            callback();
          }
        }
      },
      trigger: "blur",
    },
  ],
  password: [
    { required: true, message: "请输入密码", trigger: "blur" },
    { min: 6, max: 15, message: "密码必须是6位数以上", trigger: "blur" },
  ],
  repassword: [
    {
      validator: (rule, value, callback) => {
        if (value === null || value == "") {
          callback(new Error("请再次输入密码"));
        } else if (value !== userForm.value.password) {
          callback(new Error("两次输入的密码不一致"));
        } else {
          callback();
        }
      },
      trigger: "blur",
    },
  ],
  email: [
    {
      validator: async (rule, value, callback) => {
        var emailRegex =
          /^(([^<>()[\]\\.,;:\s@"]+(\.[^<>()[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
        if (!emailRegex.test(value)) {
          callback("邮箱格式不正确，请检查重试！");
        } else {
          const { code, data } = await validateEmailHasExists(value);
          if (code == 200) {
            if (data){
              proxy.$msg.error('该邮箱已经被绑定了，请换个邮箱试试！');
              callback("该邮箱已经被绑定了，请换个邮箱试试！");
            }
          } else {
            callback();
          }
        }
      },
      trigger: "blur",
    },
  ],
});

//点击注册
function register() {
  form.value.validate((valid) => {
    if (valid) {
      registerFun(userForm.value).then((res) => {
        if (res.code === 200) {
          proxy.$msg.success("注册成功！");
          setTimeout(() => {}, 15000);
          router.replace("/login");
        }
      });
    }
  });
}
</script>

<style scoped lang="scss">
.login_container {
  .login_header {
    padding: 15px 50px;
    background-color: #fff;
    display: flex;
    justify-content: space-between;
    align-items: center;
    .logo {
      width: 100px;
      height: 100%;
      img {
        width: 100%;
        height: 100%;
      }
    }
  }
  .login_section {
    width: 100%;
    height: 600px;
    background: url(@/assets/images/login_background.jpg) no-repeat center
      center;
    position: relative;
    .login_box {
      width: 400px;
      padding: 0 20px;
      background-color: hsla(0, 0%, 100%, 0.9);
      position: absolute;
      top: 30%;
      right: 5%;
      padding: 25px 20px;
      .title {
        display: flex;
        .tit {
          cursor: pointer;
          font-size: 18px;
          margin-right: 10px;
          color: #3c3c3c;
          padding-bottom: 10px;
          margin-bottom: 20px;
          &.active {
            border-bottom: 1px solid #000;
          }
        }
      }
      .login_input {
        .login_button {
          padding: 10px;
          background-color: #f40;
          border-radius: 5px;
          text-align: center;
          color: #fff;
          margin-bottom: 20px;
        }
      }
    }
  }
}
.icon {
  width: 1.5em;
  height: 1.5em;
  vertical-align: -0.15em;
  fill: currentColor;
  overflow: hidden;
}
</style>