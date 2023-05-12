import { defineStore } from 'pinia'
import { productList } from "@/api/product";

export default defineStore('useProductStore', {
    state: () => ({
        //猜你喜欢商品列表
        loveList: [],
    }),
    actions: {
        async loadLoveProducts() {
            const { code, data } = await productList();
            if (code == 200) {
                this.loveList = data;
            }
        }
    },
    getters: {
        loveProductList: (state) => state.loveList,
    }
})
