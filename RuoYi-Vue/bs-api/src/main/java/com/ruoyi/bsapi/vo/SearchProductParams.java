package com.ruoyi.bsapi.vo;

/**
 * 商品搜索，参数
 */
public class SearchProductParams {

    /**
     * 可是商品名称，可是分类
     */
    private String keyword;

    /**
     * 最低价格和最高价格组合
     */
    private String priceStr;

    /**
     * 销量
     */
    private String monthPin;

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public String getPriceStr() {
        return priceStr;
    }

    public void setPriceStr(String priceStr) {
        this.priceStr = priceStr;
    }

    public String getMonthPin() {
        return monthPin;
    }

    public void setMonthPin(String monthPin) {
        this.monthPin = monthPin;
    }
}
