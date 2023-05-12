package com.ruoyi.bsapi.to;

import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.List;

/**
 * 接收按照日期进行分组商品信息
 */
public class ProductHistoryTo {

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date date;

    private String pros;
    private List<String> productIds;

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getPros() {
        return pros;
    }

    public void setPros(String pros) {
        this.pros = pros;
    }

    public List<String> getProductIds() {
        return productIds;
    }

    public void setProductIds(List<String> productIds) {
        this.productIds = productIds;
    }

    @Override
    public String toString() {
        return "ProductHistoryTo{" +
                "date=" + date +
                ", productIds=" + productIds +
                '}';
    }
}
