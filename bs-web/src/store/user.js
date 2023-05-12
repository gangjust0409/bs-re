import { defineStore } from 'pinia'
import { getAuth, removeAuth } from "@/utils/auth";
import { userInfo, loginOut, addAddress, queryUserAddress, startAddress } from "@/api/user";


//用户 store
export default defineStore('useUserStore', {
    state: () => ({
        //已经登录的用户信息
        userForm: {},
        //地址列表
        addressList: [],
    }),
    actions: {
        //获取用户信息
        async login() {
            const { code, data } = await userInfo();
            if (code == 200) {
                this.userForm = data;
            }
            return code;
        },
        //注销
        async loginOut() {
            const { code } = await loginOut();
            if (code == 200) {
                //清除用户信息
                this.userForm.account = null;
                this.userForm.userPic = null;
                this.userForm.nickName = null;
                removeAuth();
            }
        },
        //新增地址
        async saveAddress(form) {
            const { code, data } = await addAddress(form);
            if (code == 200) {
                return true;
            }
            return false;
        },
        //获取当前用户的地址
        async getCurrentUserAddress() {
            const { code, data } = await queryUserAddress();
            if (code == 200) {
                this.addressList = data;
            }
            return code;
        },
        //改变用户地址状态
        async startAddress(addressId) {
            const { code } = await startAddress(addressId);
            if (code == 200) {
                return true;
            }
            return false;
        }
    },
    getters: {
        //用户名
        nickName: (state) => state.userForm.account == null ? state.userForm.nickName : state.userForm.account,
        //用户头像
        userPic: (state) => state.userForm.userPic == null ? '' : state.userForm.userPic,
        //token，用于判断是否登录
        token: () => getAuth(),
        //用户信息
        user: state => state.userForm,
        //地址getter
        addresss: (state) => state.addressList,

    }
})