package com.ruoyi.bsapi.service.impl;

import java.io.File;
import java.util.List;

import com.ruoyi.bsapi.utils.RedisConsant;
import com.ruoyi.common.config.RuoYiConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.BoundListOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import com.ruoyi.bsapi.mapper.ProductDetailMapper;
import com.ruoyi.bsapi.domain.ProductDetail;
import com.ruoyi.bsapi.service.IProductDetailService;

import javax.annotation.Resource;

/**
 * 详情图片Service业务层处理
 * 
 * @author lqg
 * @date 2023-03-13
 */
@Service
public class ProductDetailServiceImpl implements IProductDetailService 
{
    @Autowired
    private ProductDetailMapper productDetailMapper;

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    /**
     * 查询详情图片
     * 
     * @param productDetailId 详情图片主键
     * @return 详情图片
     */
    @Override
    public ProductDetail selectProductDetailByProductDetailId(Long productDetailId)
    {
        return productDetailMapper.selectProductDetailByProductDetailId(productDetailId);
    }

    /**
     * 查询详情图片列表
     * 
     * @param productDetail 详情图片
     * @return 详情图片
     */
    @Override
    public List<ProductDetail> selectProductDetailList(ProductDetail productDetail)
    {
        return productDetailMapper.selectProductDetailList(productDetail);
    }

    /**
     * 新增详情图片
     * 
     * @param productDetail 详情图片
     * @return 结果
     */
    @Override
    public int insertProductDetail(ProductDetail productDetail)
    {
        return productDetailMapper.insertProductDetail(productDetail);
    }

    /**
     * 修改详情图片
     * 
     * @param productDetail 详情图片
     * @return 结果
     */
    @Override
    public int updateProductDetail(ProductDetail productDetail)
    {
        return productDetailMapper.updateProductDetail(productDetail);
    }

    /**
     * 批量删除详情图片
     * 
     * @param productDetailIds 需要删除的详情图片主键
     * @return 结果
     */
    @Override
    public int deleteProductDetailByProductDetailIds(Long[] productDetailIds)
    {
        return productDetailMapper.deleteProductDetailByProductDetailIds(productDetailIds);
    }

    /**
     * 删除详情图片信息
     * 
     * @param productDetailId 详情图片主键
     * @return 结果
     */
    @Override
    public int deleteProductDetailByProductDetailId(Long productDetailId)
    {
        return productDetailMapper.deleteProductDetailByProductDetailId(productDetailId);
    }

    //保存 商品详情图
    @Override
    public void saveProductDetailImgs(Long productId) {
        BoundListOperations<String, String> ops = stringRedisTemplate.boundListOps(RedisConsant.UPLOAD_PRODUCT_DETAIL_URL);
        List<String> range = ops.range(0, -1);
        if (range.size() > 0) {
            this.deleteProductId(productId);
            for (String fileName : range) {
                ProductDetail productDetail = new ProductDetail();
                productDetail.setProductId(productId);
                productDetail.setProductDetailUrl(fileName);
                //保存
                this.insertProductDetail(productDetail);
            }
            stringRedisTemplate.delete(RedisConsant.UPLOAD_PRODUCT_DETAIL_URL);
        }
    }

    @Override
    public void deleteProductId(Long productId) {
        productDetailMapper.deleteProductId(productId);
    }

    @Override
    public void deleteProductImgUrl(String img) {
        String newName = img.substring(img.lastIndexOf("/") + 1);
        //修改数据库记录
        productDetailMapper.deleteProductImgUrl(newName);
        //删除本地图片
        new File(RuoYiConfig.getProfile()  + "/" + newName).delete();
    }
}
