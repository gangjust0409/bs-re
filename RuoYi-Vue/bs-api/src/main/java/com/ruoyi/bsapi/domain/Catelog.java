package com.ruoyi.bsapi.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

import java.util.List;

/**
 * 分类对象 catelog
 * 
 * @author lqg
 * @date 2023-03-13
 */
public class Catelog extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** $column.columnComment */
    private Long catelogId;

    /** 分类名称 */
    @Excel(name = "分类名称")
    private String catelogName;

    /** 分类父级id */
    @Excel(name = "分类父级id")
    private Long parentId;

    /** 当前分类层级 */
    @Excel(name = "当前分类层级")
    private Long level;

    /** 给一级菜单的 icon */
    @Excel(name = "给一级菜单的 icon")
    private String icon;

    /**
     * 树形菜单的子菜单
     */
    private List<Catelog> children;

    public void setCatelogId(Long catelogId) 
    {
        this.catelogId = catelogId;
    }

    public Long getCatelogId() 
    {
        return catelogId;
    }
    public void setCatelogName(String catelogName) 
    {
        this.catelogName = catelogName;
    }

    public String getCatelogName() 
    {
        return catelogName;
    }
    public void setParentId(Long parentId) 
    {
        this.parentId = parentId;
    }

    public Long getParentId() 
    {
        return parentId;
    }
    public void setLevel(Long level) 
    {
        this.level = level;
    }

    public Long getLevel() 
    {
        return level;
    }
    public void setIcon(String icon) 
    {
        this.icon = icon;
    }

    public String getIcon() 
    {
        return icon;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("catelogId", getCatelogId())
            .append("catelogName", getCatelogName())
            .append("parentId", getParentId())
            .append("level", getLevel())
            .append("icon", getIcon())
            .toString();
    }

    public List<Catelog> getChildren() {
        return children;
    }

    public void setChildren(List<Catelog> children) {
        this.children = children;
    }
}
