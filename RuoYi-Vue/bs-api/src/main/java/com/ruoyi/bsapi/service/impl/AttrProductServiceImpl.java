package com.ruoyi.bsapi.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import com.alibaba.fastjson2.JSON;
import com.ruoyi.bsapi.domain.Attrs;
import com.ruoyi.bsapi.service.IAttrsService;
import com.ruoyi.bsapi.to.ProductAttrItemTo;
import com.ruoyi.bsapi.utils.RedisConsant;
import com.ruoyi.bsapi.vo.AttrProductGroupByVo;
import com.ruoyi.bsapi.vo.ProductAttrItemVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.BoundHashOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import com.ruoyi.bsapi.mapper.AttrProductMapper;
import com.ruoyi.bsapi.domain.AttrProduct;
import com.ruoyi.bsapi.service.IAttrProductService;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;

/**
 * 属性和商品关联Service业务层处理
 *
 * @author lqg
 * @date 2023-03-17
 */
@Service
public class AttrProductServiceImpl implements IAttrProductService {
    @Autowired
    private AttrProductMapper attrProductMapper;

    @Autowired
    private IAttrsService attrsService;

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    /**
     * 查询属性和商品关联
     *
     * @param attrProductId 属性和商品关联主键
     * @return 属性和商品关联
     */
    @Override
    public AttrProduct selectAttrProductByAttrProductId(Long attrProductId) {
        return attrProductMapper.selectAttrProductByAttrProductId(attrProductId);
    }

    /**
     * 查询属性和商品关联列表
     *
     * @param attrProduct 属性和商品关联
     * @return 属性和商品关联
     */
    @Override
    public List<AttrProduct> selectAttrProductList(AttrProduct attrProduct) {
        return attrProductMapper.selectAttrProductList(attrProduct);
    }

    /**
     * 新增属性和商品关联
     *
     * @param attrProduct 属性和商品关联
     * @return 结果
     */
    @Override
    public int insertAttrProduct(AttrProduct attrProduct) {
        return attrProductMapper.insertAttrProduct(attrProduct);
    }

    /**
     * 修改属性和商品关联
     *
     * @param attrProduct 属性和商品关联
     * @return 结果
     */
    @Override
    public int updateAttrProduct(AttrProduct attrProduct) {
        return attrProductMapper.updateAttrProduct(attrProduct);
    }

    /**
     * 批量删除属性和商品关联
     *
     * @param attrProductIds 需要删除的属性和商品关联主键
     * @return 结果
     */
    @Override
    public int deleteAttrProductByAttrProductIds(Long[] attrProductIds) {
        return attrProductMapper.deleteAttrProductByAttrProductIds(attrProductIds);
    }

    /**
     * 删除属性和商品关联信息
     *
     * @param attrProductId 属性和商品关联主键
     * @return 结果
     */
    @Override
    public int deleteAttrProductByAttrProductId(Long attrProductId) {
        return attrProductMapper.deleteAttrProductByAttrProductId(attrProductId);
    }

    @Override
    public void saveAttrs(Long productId, Long catelogId) {
        Long finalProductId = productId;
        List<AttrProduct> attrProducts = getAddProductAttrs(catelogId).stream()
                .filter(attrId -> attrId != null)
                .map(attrId -> {
                    AttrProduct attrProduct = new AttrProduct();
                    attrProduct.setAttrId(attrId);
                    attrProduct.setProductId(finalProductId);
                    return attrProduct;
                }).collect(Collectors.toList());
        for (AttrProduct attrProduct : attrProducts) {
            this.insertAttrProduct(attrProduct);
        }
    }

    @Override
    public List<Attrs> getAttrsReleaseProduct(Long productId) {
        List<Long> attrids = new ArrayList<>();
        //获取attrid
        AttrProduct params = new AttrProduct();
        params.setProductId(productId);
        List<AttrProduct> attrProducts = this.selectAttrProductList(params);
        if (!CollectionUtils.isEmpty(attrProducts)) {
            attrids = attrProducts.stream().map(AttrProduct::getAttrId).collect(Collectors.toList());
        }
        //获取属性
        List<Attrs> attrsList = attrids.stream().map(attrId -> attrsService.selectAttrsByAttrId(attrId)).collect(Collectors.toList());
        return attrsList;
    }

    @Override
    public void deleteProductId(Long productId) {
        attrProductMapper.deleteProductId(productId);
    }

    @Override
    public List<ProductAttrItemVo> selectAttrsByGroupByProductId(Long productId) {
        //查询商品关联属性信息
        AttrProduct attrProductParams = new AttrProduct();
        attrProductParams.setProductId(productId);
        List<Long> attrIds = attrProductMapper.selectAttrProductList(attrProductParams).stream().map(AttrProduct::getAttrId).collect(Collectors.toList());
        //查询属性信息
        List<AttrProductGroupByVo> attrProductGroupByVos = attrsService.selectAttrsByGroupByProductId(attrIds);
        System.out.println(attrProductGroupByVos);
        List<ProductAttrItemVo> productAttrItemVos = attrProductGroupByVos.stream().map(attrProductGroupByVo -> {
            //分割属性
            String[] attrValues = attrProductGroupByVo.getAttrValues().split(",");
            if (attrValues.length > 1) {
                ProductAttrItemVo productAttrItemVo = new ProductAttrItemVo();
                productAttrItemVo.setTitle(attrProductGroupByVo.getAttrName());
                for (String attrValue : attrValues) {
                    //一个完整的attr
                    String[] attr = attrValue.split("_");
                    //转成对象
                    ProductAttrItemTo productAttrItemTo = new ProductAttrItemTo();
                    productAttrItemTo.setAttrId(Long.parseLong(attr[0]));
                    productAttrItemTo.setAttrValue(attr[1]);
                    //添加到集合中
                    productAttrItemVo.getAttrValues().add(productAttrItemTo);
                }
                return productAttrItemVo;
            }
            return null;
        }).collect(Collectors.toList());
        return productAttrItemVos;
    }

    /**
     * 获取已经勾选的属性 id
     *
     * @return catelogId
     */
    private List<Long> getAddProductAttrs(Long catelogId) {
        //获取 redis map 中保存的属性实例
        BoundHashOperations<String, String, String> ops = RedisConsant.getCatelogAttrsInstant(stringRedisTemplate, catelogId);
        List<Long> ids = ops.keys().stream().map(key -> {
            //value
            String json = ops.get(key);
            //将json转成集合
            for (ProductAttrItemTo productAttrItemTo : JSON.parseArray(json, ProductAttrItemTo.class)) {
                if (productAttrItemTo.isChecked()) {
                    return productAttrItemTo.getAttrId();
                }
            }
            //ProductAttrItemTo productAttrItemTo = JSON.parseObject(json, ProductAttrItemTo.class);
            return null;
        }).collect(Collectors.toList());
        return ids;
    }

}
