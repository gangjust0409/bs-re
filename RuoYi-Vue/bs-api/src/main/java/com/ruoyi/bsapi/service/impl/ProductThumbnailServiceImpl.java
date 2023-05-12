package com.ruoyi.bsapi.service.impl;

import java.io.File;
import java.util.List;

import com.ruoyi.bsapi.utils.RedisConsant;
import com.ruoyi.common.config.RuoYiConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.BoundListOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import com.ruoyi.bsapi.mapper.ProductThumbnailMapper;
import com.ruoyi.bsapi.domain.ProductThumbnail;
import com.ruoyi.bsapi.service.IProductThumbnailService;

import javax.annotation.Resource;

/**
 * 缩略图Service业务层处理
 * 
 * @author lqg
 * @date 2023-03-13
 */
@Service
public class ProductThumbnailServiceImpl implements IProductThumbnailService 
{
    @Autowired
    private ProductThumbnailMapper productThumbnailMapper;

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    /**
     * 查询缩略图
     * 
     * @param productThumbnailId 缩略图主键
     * @return 缩略图
     */
    @Override
    public ProductThumbnail selectProductThumbnailByProductThumbnailId(Long productThumbnailId)
    {
        return productThumbnailMapper.selectProductThumbnailByProductThumbnailId(productThumbnailId);
    }

    /**
     * 查询缩略图列表
     * 
     * @param productThumbnail 缩略图
     * @return 缩略图
     */
    @Override
    public List<ProductThumbnail> selectProductThumbnailList(ProductThumbnail productThumbnail)
    {
        return productThumbnailMapper.selectProductThumbnailList(productThumbnail);
    }

    /**
     * 新增缩略图
     * 
     * @param productThumbnail 缩略图
     * @return 结果
     */
    @Override
    public int insertProductThumbnail(ProductThumbnail productThumbnail)
    {
        return productThumbnailMapper.insertProductThumbnail(productThumbnail);
    }

    /**
     * 修改缩略图
     * 
     * @param productThumbnail 缩略图
     * @return 结果
     */
    @Override
    public int updateProductThumbnail(ProductThumbnail productThumbnail)
    {
        return productThumbnailMapper.updateProductThumbnail(productThumbnail);
    }

    /**
     * 批量删除缩略图
     * 
     * @param productThumbnailIds 需要删除的缩略图主键
     * @return 结果
     */
    @Override
    public int deleteProductThumbnailByProductThumbnailIds(Long[] productThumbnailIds)
    {
        return productThumbnailMapper.deleteProductThumbnailByProductThumbnailIds(productThumbnailIds);
    }

    /**
     * 删除缩略图信息
     * 
     * @param productThumbnailId 缩略图主键
     * @return 结果
     */
    @Override
    public int deleteProductThumbnailByProductThumbnailId(Long productThumbnailId)
    {
        return productThumbnailMapper.deleteProductThumbnailByProductThumbnailId(productThumbnailId);
    }

    //保存商品缩略图
    @Override
    public void saveProductThumbnailImgs(Long productId) {
        BoundListOperations<String, String> ops = stringRedisTemplate.boundListOps(RedisConsant.UPLOAD_PRODUCT_THUMBNAI_URL);
        List<String> range = ops.range(0, -1);
        if (range.size() > 0) {
            this.deleteProductId(productId);
            for (String fileName : range) {
                ProductThumbnail productThumbnail = new ProductThumbnail();
                productThumbnail.setProductId(productId);
                productThumbnail.setProductThumbnailUrl(fileName);
                this.insertProductThumbnail(productThumbnail);
            }
            stringRedisTemplate.delete(RedisConsant.UPLOAD_PRODUCT_THUMBNAI_URL);
        }
    }

    @Override
    public void deleteProductId(Long productId) {
        productThumbnailMapper.deleteProductId(productId);
    }

    @Override
    public void deleteProductImgUrl(String img) {
        String newName = img.substring(img.lastIndexOf("/") + 1) ;;
        //删除数据库的记录
        productThumbnailMapper.deleteProductImgUrl(newName);
        //删除保存的图片
        String path = RuoYiConfig.getProfile() +"/"+ newName;
        File file = new File(path);
        file.delete();
    }
}
