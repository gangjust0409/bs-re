//search store
import { defineStore } from 'pinia'

export const useSearchStore = defineStore("searchStore", {
    state: () => ({
        //搜索历史
        history: JSON.parse(localStorage.getItem('history')) || []
    }),
    actions: {
        setStore(val) {
            //添加之前查看是否重复
            this.history.forEach((e, i) => {
                if (e == val) {
                    this.history.splice(i, 1);
                    //删除之后要把它移到第一位

                }
            })
            //新旧数据一起保留，唯独不能保留空的数据
            this.history = [val, ...this.history].filter(e => e != null);
            localStorage.setItem('history', JSON.stringify(this.history));
        }
    },
    getters: {
        getHistoryList() {
            return this.history;
        }
    }
});