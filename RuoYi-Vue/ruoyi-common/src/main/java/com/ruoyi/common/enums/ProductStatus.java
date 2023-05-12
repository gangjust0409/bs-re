package com.ruoyi.common.enums;

/**
 * 商品状态 1 已创建 2 上架  3 下架
 */
public enum ProductStatus {
    CREATED(1L, "已创建"),PUT_SHELVES(2L, "上架"), TAKE_OFF_SHELVES(3L, "下架");

    ProductStatus(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    private Long id;
    private String name;

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
