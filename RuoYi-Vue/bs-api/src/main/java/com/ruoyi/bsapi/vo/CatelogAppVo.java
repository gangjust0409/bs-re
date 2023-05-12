package com.ruoyi.bsapi.vo;

import java.util.List;

/**
 * 首页分类模型
 */
public class CatelogAppVo {

    private Long catelogId;

    private String catelogName;

    private String icon;

    private List<CatelogAppVo> children;

    public Long getCatelogId() {
        return catelogId;
    }

    public void setCatelogId(Long catelogId) {
        this.catelogId = catelogId;
    }

    public String getCatelogName() {
        return catelogName;
    }

    public void setCatelogName(String catelogName) {
        this.catelogName = catelogName;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public List<CatelogAppVo> getChildren() {
        return children;
    }

    public void setChildren(List<CatelogAppVo> children) {
        this.children = children;
    }
}
