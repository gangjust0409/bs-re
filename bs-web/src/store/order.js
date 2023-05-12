import { defineStore } from 'pinia'
import { useRouter } from "vue-router";
import { queryMyBuy, pay } from '@/api/product'

const NARBAR_STORE = "order_navbar";
const router = useRouter();

//order store
export default defineStore('useOrderStore', {
    state: () => ({
        //order title
        orderNavbarList: JSON.parse(localStorage.getItem(NARBAR_STORE)) ||
            [{ title: '全部', active: true }, { title: '待付款', active: false },
            { title: '待发货', active: false },
            { title: '待收货', active: false }, { title: '已完成', active: false }],
        //order list
        orderList: [],
    }),
    actions: {
        //持久化保存到 浏览器
        persistence(nav) {
            this.orderNavbarList = this.orderNavbarList.map(navbar => {
                navbar.active = false;
                if (navbar.title == nav.title) {
                    navbar.active = true;
                }
                return navbar;
            })
            //请求数据
            this.myBuy({});
            //保存
            localStorage.setItem(NARBAR_STORE, JSON.stringify(this.orderNavbarList));
        },
        //请求我购买的宝贝
        async myBuy(orderForm) {
            let form = {};
            //获取当前的状态
            this.orderNavbarList.forEach(ot => {
                if (ot.active)
                    form.status = this.getStatus(ot.title);
            })
            const { code, data } = await queryMyBuy({ ...form, ...orderForm });
            if (code == 200) {
                this.orderList = data;
            }
            return code;
        },
        //获取当前状态转化为代表的数字
        getStatus(title) {
            switch (title) {
                case '全部':
                    return 0;
                case '待付款':
                    return 1;
                case '待发货':
                    return 2;
                case '待收货':
                    return 3;
                case '已完成':
                    return 4;
            }
        },
        //根据数字转为状态名称
        getStatusName(num) {
            switch (num) {
                case 0:
                    return '全部';
                case 1:
                    return '待付款';
                case 2:
                    return '待发货';
                case 3:
                    return '待收货';
                case 4:
                    return '已完成';
            }
        },
        //加载计时
        loadSetTimeout() {
            this.orderList = this.orderList.map((order) => {
                if (order.time != null) {
                    let times = order.time.split(":");
                    order.t = "00";
                    order.h--;
                    if (order.h < 10) {
                        order.h = "0" + times[1];
                    } else {
                        order.h = times[1];
                    }
                    order.s--;
                    if (order.s < 0) {
                        order.h--;
                        order.s = 60;
                    }
                    if (order.s < 10) {
                        order.s = "0" + times[2];
                    } else {
                        order.s = times[2];
                    }
                    return order;
                }
            });
        },
        //支付
        async pay(params) {
            const res = await pay(params);
            return res;
        },
    },
    getters: {
        orderNavList: (state) => state.orderNavbarList,
        orders: (state) => state.orderList,
    }
})