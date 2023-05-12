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
      <!-- 登录框 -->
      <div class="login_box">
        <div class="title">
          <div
            :class="{ tit: true, active: !isActive }"
            @click="changeLoginType(false)"
          >
            密码登录
          </div>
          <div
            :class="{ tit: true, active: isActive }"
            @click="changeLoginType(true)"
          >
            邮箱登录
          </div>
        </div>
        <div class="login_input" v-show="!isActive">
          <el-form ref="ruleFormRef">
            <el-form-item prop="name">
              <el-input
                placeholder="仿淘宝账号"
                v-model="userForm.account"
                :prefix-icon="UserFilled"
              />
            </el-form-item>
            <el-form-item prop="name">
              <el-input
                placeholder="密码"
                v-model="userForm.password"
                type="password"
                :prefix-icon="Lock"
              />
            </el-form-item>
          </el-form>
          <div class="login_button" @click="doLogin">登录</div>
        </div>
        <div class="login_input" v-show="isActive">
          <el-form ref="ruleFormRef">
            <el-form-item prop="name">
              <el-input
                placeholder="手机号/邮箱"
                v-model="userForm.phoneEmail"
                :prefix-icon="UserFilled"
              />
            </el-form-item>
            <el-form-item prop="validate_code">
              <el-input
                style="width: 185px; margin-right: 20px"
                placeholder="验证码"
                v-model="userForm.validateCode"
                :prefix-icon="Iphone"
              />
              <el-button
                v-if="validatorCode == 0"
                plain
                @click="getEmailValidateCode"
                >{{ valdatorCodeText }}</el-button
              >
              <div class="code_box" v-else>
                <el-countdown :value="senonds" @finish="senondsFinish" />
                <span>秒后重新发送</span>
              </div>
            </el-form-item>
          </el-form>
          <div class="login_button" @click="doLogin">登录</div>
        </div>
        <div class="msg">
          还没有账号？<router-link style="color: #ff0036" to="/register"
            >去注册</router-link
          >
        </div>
        <!-- <div class="ouathor2">
          <a href="">
            <svg class="icon" aria-hidden="true">
              <use xlink:href="#icon-QQ"></use>
            </svg>
            邮箱登录
          </a>
        </div> -->
      </div>
    </div>
    <menu-footer></menu-footer>
  </div>
</template>

<script setup>
import { UserFilled, Lock, Iphone } from "@element-plus/icons-vue";
import { ref, reactive, getCurrentInstance, nextTick } from "vue";
import { useRouter } from "vue-router";
import { sendEmail, clientLogin, validateEmailHasExists } from "@/api/user";
import { setAuth } from "@/utils/auth";
//获取当前实例
const { proxy } = getCurrentInstance();
//路由
const router = useRouter();
//按钮文本
const valdatorCodeText = ref("获取验证码");
//控制按钮显示
const validatorCode = ref(0);
//秒
const senonds = ref(0);

//是否是密码登录或者邮箱登录，默认密码登录
const isActive = ref(false);
//用户表单
const userForm = reactive({
  account: null,
  password: null,
  phoneEmail: null,
  validateCode: null,
});

//当点击切换
function changeLoginType(val) {
  isActive.value = val;
  userForm.account = null;
  userForm.password = null;
  userForm.phoneEmail = null;
  userForm.validateCode = null;
}
//点击登录时
async function doLogin() {
  //验证输入是否正确
  if (checkedLogin()) {
    const { code, msg } = await clientLogin(userForm);
    if (code == 200 && msg) {
      //保存 cookie
      setAuth(msg);
      proxy.$msg.success("登录成功！");
      router.push("/home");
    } else {
      let msg = "";
      if (!isActive.value) {
        msg = "账号或密码错误！";
      } else {
        msg = "验证码错误或已过期！";
      }
      proxy.$msg.error(msg);
    }
  }
}
//获取验证码
async function getEmailValidateCode() {
  //验证邮箱
  if (!(await hasValidateEmial())) {
    proxy.$msg.warning("输入的邮箱不存在,请检查是否绑定邮箱？");
  } else {
    senonds.value = Date.now() + 1000 * 60;
    validatorCode.value = 1;
    const { code } = await sendEmail(userForm.phoneEmail);
    if (code == 200) {
      proxy.$msg.success("验证码发送成功！");
    } else {
      proxy.$msg.warning("验证码发送超时或系统异常，请重试！");
    }
  }
}
//当30 秒结束时
function senondsFinish() {
  senonds.value = 0;
  validatorCode.value = 0;
  valdatorCodeText.value = "重新发送验证码";
}
//验证邮箱
async function hasValidateEmial() {
  const { code, data } = await validateEmailHasExists(userForm.phoneEmail);
  if (code === 200) {
    return data;
  }
}
//验证输入是否正确
function checkedLogin() {
  const regx =
    /^(([^<>()[\]\\.,;:\s@"]+(\.[^<>()[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
  if (!isActive.value) {
    //密码登录
    if (
      userForm.account == null ||
      userForm.account == undefined ||
      userForm.account == ""
    ) {
      proxy.$msg.warning("请输入账号");
      return false;
    } else if (
      userForm.password == null ||
      userForm.password == undefined ||
      userForm.password == ""
    ) {
      proxy.$msg.warning("请输入密码");
      return false;
    } else if (userForm.password.length < 6) {
      proxy.$msg.warning("密码必须是6位数");
      return false;
    } else {
      return true;
    }
  } else {
    //验证码登录
    if (
      userForm.phoneEmail == null ||
      userForm.phoneEmail == undefined ||
      userForm.phoneEmail == ""
    ) {
      proxy.$msg.warning("请输入电子邮箱");
      return false;
    } else if (!regx.test(userForm.phoneEmail)) {
      proxy.$msg.warning("电子邮箱格式不正确");
      return false;
    } else if (
      userForm.validateCode == null ||
      userForm.validateCode == undefined ||
      userForm.validateCode == ""
    ) {
      proxy.$msg.warning("请输入验证码");
      return false;
    } else {
      return true;
    }
  }
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
      .ouathor2 {
        display: flex;
        a {
          display: flex;
          align-items: center;
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
.code_box {
  display: flex;
  align-items: center;
  height: 40px;
  line-height: 40px;
  span {
    margin-left: 5px;
    padding-top: 5px;
    font-size: 12px;
  }
}
</style>