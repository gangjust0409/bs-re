package com.ruoyi.bsapi.vo;

import com.ruoyi.bsapi.domain.Product;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.List;

/**
 * 我的记录
 */
public class ProductHistory {

    private String date;

    private List<Product> historys;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public List<Product> getHistorys() {
        return historys;
    }

    public void setHistorys(List<Product> historys) {
        this.historys = historys;
    }
}
