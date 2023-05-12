<template>
  <div class="address">
    <div class="product_content">
      <div class="title">
        <span>我的地址：</span>
        <el-button type="primary" plain size="small" @click="showAddRessDiabox"
          >添加地址</el-button
        >
      </div>
      <div class="address_list">
        <div
          v-for="(addre, i) in addressList"
          :key="i"
          :class="{ address_item: true, default: addre.defaultAddress == 1 }"
        >
          <div class="address_info">
            <el-icon v-if="addre.defaultAddress == 1"
              ><IEpCircleCheckFilled /></el-icon
            >{{ addre.address }}
          </div>
          <div class="address_setting">
            <el-button
              type="primary"
              size="small"
              plain
              class="edit"
              @click="editShowAddress(addre)"
              >修改</el-button
            >
            <el-button
              type="primary"
              size="small"
              plain
              class="edit"
              @click="deleteAddress(addre)"
              >删除</el-button
            >
            <el-button
              v-if="addre.defaultAddress == 2"
              type="primary"
              size="small"
              @click="startAddress(addre)"
              >启用</el-button
            >
          </div>
        </div>
      </div>
    </div>
    <!-- 添加地址 -->
    <el-dialog
      v-model="addressVisible"
      :title="`${addEditTitle}添加收货地址`"
      width="50%"
      :before-close="handleAddressClose"
    >
      <el-form
        ref="addressFormRef"
        label-width="140px"
        class="demo-ruleForm"
        size="small"
        status-icon
        :model="addressForm"
        :rules="addressRuleForm"
      >
        <el-form-item label="收件人姓名" prop="userName">
          <el-input v-model="addressForm.userName" placeholder="收件人姓名" />
        </el-form-item>

        <el-form-item label="收件人手机号" prop="phone">
          <el-input v-model="addressForm.phone" placeholder="收件人手机号" />
        </el-form-item>
        <el-form-item label="选择省、市【区】" prop="addressNames">
          <el-cascader
            v-model="addressForm.addressNames"
            placeholder="选择省、市【区】"
            :options="addressOptions"
            :props="props"
          />
        </el-form-item>
        <el-form-item label="详细地址" prop="descAddress">
          <el-input
            v-model="addressForm.descAddress"
            :autosize="{ minRows: 2, maxRows: 4 }"
            type="textarea"
            placeholder="详细地址（从县到门牌号）"
          />
        </el-form-item>
      </el-form>
      <template #footer>
        <span class="dialog-footer">
          <el-button @click="handleAddressClose">取 消</el-button>
          <el-button type="primary" @click="saveAddressInfo"> 确 定 </el-button>
        </span>
      </template>
    </el-dialog>
  </div>
</template>

<script setup>
import { reactive, ref, computed, getCurrentInstance } from "vue";
import { useRouter } from "vue-router";
import useUserStore from "@/store/user";
import axios from "axios";
import { editAddress, delAddress } from "@/api/user";

const router = useRouter();
const userStore = useUserStore();
const { proxy } = getCurrentInstance();

//这个是获取地址列表的方法
const addressOptions = ref([]);
//控制添加地址模态框
const addressVisible = ref(false);
const addressFormRef = ref();
//地址表单
const addressForm = reactive({
  userName: null,
  phone: null,
  descAddress: null,
  addressNames: null,
  addressName: "",
});
//验证地址表单
const addressRuleForm = reactive({
  userName: [
    { required: true, message: "请输入收件人的名字", trigger: "blur" },
  ],
  phone: [
    {
      validator: (rule, value, callback) => {
        const NumPhoneRegx = /^(?:(?:\+|00)86)?1[3-9]\d{9}$/;
        if (value == null || value == "" || value == undefined) {
          callback(new Error("请输入收件人手机号"));
        } else if (!NumPhoneRegx.test(value)) {
          callback(new Error("收件人手机号格式不正确"));
        } else {
          callback();
        }
      },
      trigger: "blur",
    },
  ],
  addressNames: [{ required: true, message: "请选择地区", trigger: "change" }],
  descAddress: [
    { required: true, message: "请输入收件人的详细地址", trigger: "blur" },
  ],
});
//下拉地址框
const props = ref({ value: "label" });
//修改添加地址标题
const addEditTitle = ref("");
//地址列表
const addressList = computed(() => userStore.addresss);

//关闭添加地址模态框的方法
const handleAddressClose = () => {
  addressForm.phone = null;
  addressForm.addressName = "";
  addressForm.userName = null;
  addressForm.descAddress = null;
  addressForm.addressNames = null;
  addressVisible.value = false;
};
//添加地址
const showAddRessDiabox = () => {
  //加载地址列表
  loadAddressList();
  //打开添加模态框
  addressVisible.value = true;
  addEditTitle.value = "添加";
};
//加载地址
async function loadAddressList() {
  const { status, data } = await axios.get("/address.json");
  if (status === 200) {
    addressOptions.value = data;
  }
}
//点击提交
const saveAddressInfo = () => {
  addressFormRef.value.validate(async (valid) => {
    if (valid) {
      addressForm.addressNames.forEach((val) => {
        addressForm.addressName += " ";
        addressForm.addressName += val;
      });
      if (addEditTitle.value == "添加") {
        //执行添加地址的方法
        if (await userStore.saveAddress(addressForm)) {
          proxy.$msg.success("添加地址成功！");
        }
      } else {
        //修改
        const { code } = await editAddress(addressForm);
        if (code == 200) {
          proxy.$msg.success("修改成功！");
        }
      }
      await userStore.getCurrentUserAddress();
      //关闭模态框
      handleAddressClose();
    }
  });
};
//点击展示修改
const editShowAddress = (address) => {
  addEditTitle.value = "修改";
  //加载地址列表
  loadAddressList();
  const ads = address.address.split("-");
  addressForm.phone = ads[1];
  addressForm.addressId = address.addressId;
  addressForm.userName = ads[0];
  const citys = ads[2].split(" ");
  console.log(citys);
  addressForm.descAddress = citys[3];
  addressForm.addressNames = [citys[1], citys[2]];
  addressVisible.value = true;
};
//删除地址
function deleteAddress(address) {
  proxy.$msgbx
    .confirm(`确定是否删除【${address.addressId}】吗？`, "温馨提示", {
      confirmButtonText: "确 定",
      cancelButtonText: "取 消",
      type: "warning",
    })
    .then(async () => {
      const { code } = await delAddress(address.addressId);
      if (code == 200) {
        proxy.$msg.success("删除成功！");
        await userStore.getCurrentUserAddress();
      }
    })
    .catch(() => {});
}
//启用地址
const startAddress = async (address) => {
  if (await userStore.startAddress(address.addressId)) {
    proxy.$msg.success("启用成功！");
    await userStore.getCurrentUserAddress();
  }
};
//加载地址列表的方法
(async () => {
  const code = await userStore.getCurrentUserAddress();
  console.log(code);
  if (code == 403) {
    //跳转到登录页
    router.replace("/login");
  }
})();
</script>

<style scoped lang="scss">
.address {
  .product_content {
    margin: 0;
    .title {
      font-size: 20px;
    }
    .address_list {
      padding-top: 20px;
      .address_item {
        line-height: 2;
        font-size: 16px;
        border: 1px solid #eee;
        padding: 5px 10px;
        margin: 5px 0;
        display: flex;
        justify-content: space-between;
        align-items: center;
        .address_info {
          display: flex;
          align-items: center;
          i {
            margin-right: 5px;
          }
        }
        .address_setting {
          :deep(.edit) {
            display: none;
          }
        }
        &.default,
        &:hover {
          border-color: #ff0036;
          color: #ff0036;
          background-color: rgba($color: #ff0036, $alpha: 0.1);
        }
        &:hover {
          :deep(.edit) {
            display: inline-block;
          }
        }
      }
    }
  }
}
</style>