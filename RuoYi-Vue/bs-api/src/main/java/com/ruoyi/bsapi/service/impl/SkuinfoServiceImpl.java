package com.ruoyi.bsapi.service.impl;

import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import com.alibaba.fastjson2.JSON;
import com.ruoyi.bsapi.domain.SysFileInfo;
import com.ruoyi.bsapi.service.ISysFileInfoService;
import com.ruoyi.bsapi.to.SkuUploadImgItemTo;
import com.ruoyi.bsapi.utils.RedisConsant;
import com.ruoyi.bsapi.vo.AddProductVo;
import com.ruoyi.common.config.RuoYiConfig;
import com.ruoyi.common.utils.my.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.BoundListOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;
import com.ruoyi.bsapi.mapper.SkuinfoMapper;
import com.ruoyi.bsapi.domain.Skuinfo;
import com.ruoyi.bsapi.service.ISkuinfoService;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;

/**
 * sku infoService业务层处理
 *
 * @author lqg
 * @date 2023-03-17
 */
@Service
public class SkuinfoServiceImpl implements ISkuinfoService {
    @Resource
    private SkuinfoMapper skuinfoMapper;

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    @Resource
    private ISysFileInfoService fileInfoService;


    int i = 0;

    /**
     * 查询sku info
     *
     * @param skuId sku info主键
     * @return sku info
     */
    @Override
    public Skuinfo selectSkuinfoBySkuId(Long skuId) {
        return skuinfoMapper.selectSkuinfoBySkuId(skuId);
    }

    /**
     * 查询sku info列表
     *
     * @param skuinfo sku info
     * @return sku info
     */
    @Override
    public List<Skuinfo> selectSkuinfoList(Skuinfo skuinfo) {
        return skuinfoMapper.selectSkuinfoList(skuinfo);
    }

    /**
     * 新增sku info
     *
     * @param skuinfo sku info
     * @return 结果
     */
    @Override
    public int insertSkuinfo(Skuinfo skuinfo) {
        return skuinfoMapper.insertSkuinfo(skuinfo);
    }

    /**
     * 修改sku info
     *
     * @param skuinfo sku info
     * @return 结果
     */
    @Override
    public int updateSkuinfo(Skuinfo skuinfo) {
        return skuinfoMapper.updateSkuinfo(skuinfo);
    }

    /**
     * 批量删除sku info
     *
     * @param skuIds 需要删除的sku info主键
     * @return 结果
     */
    @Override
    public int deleteSkuinfoBySkuIds(Long[] skuIds) {
        return skuinfoMapper.deleteSkuinfoBySkuIds(skuIds);
    }

    /**
     * 删除sku info信息
     *
     * @param skuId sku info主键
     * @return 结果
     */
    @Override
    public int deleteSkuinfoBySkuId(Long skuId) {
        return skuinfoMapper.deleteSkuinfoBySkuId(skuId);
    }

    @Override
    public void saveSkuInfo(Long productId, AddProductVo.AddProductItem[] skus) {
        //获取 redis 中的图片
        BoundListOperations<String, String> ops = RedisConsant.getSkuUploadImg(stringRedisTemplate);
        ////TODO redis
        //List<String> skuImgs = ops.range(0, -1);
        //List<SkuUploadImgItemTo> skuUploadImgItemTos = JSON.parseArray(json, SkuUploadImgItemTo.class);
        //TODO 数据库
        //SysFileInfo fileInfoParams = new SysFileInfo();
        //fileInfoParams.setFilePath(RedisConsant.UPLOAD_PRODUCT_SKU_PIC_ID);
        //查询sku的图片信息
        //List<SysFileInfo> sysFileInfos = fileInfoService.selectSysFileInfoList(fileInfoParams);
        for (AddProductVo.AddProductItem addProductItem : skus) {
            //sku 文本框写入数据时才可以添加
            if (!(StringUtils.isNotNull(addProductItem.getSkuName())
                            && StringUtils.isNotNull(addProductItem.getSkuPrice()))) {
                Skuinfo skuinfo = new Skuinfo();
                skuinfo.setProductId(productId);
                skuinfo.setSkuName(addProductItem.getSkuName());
                skuinfo.setSkuPrice(addProductItem.getSkuPrice());
                String skuPic = addProductItem.getSkuPic();
                //只有上传图片才进行添加图片操作
                if(StringUtils.isNotNull(skuPic)) {
                    String newPic = skuPic.substring(skuPic.lastIndexOf("/") + 1);
                    skuinfo.setSkuPic(newPic);
                }
               /* //TODO redis
                while (i < skuImgs.size()) {
                    skuinfo.setSkuPic(skuImgs.get(i));
                    i++;
                    break;
                }*/
                //保存
                this.insertSkuinfo(skuinfo);
            }
        }
        //保存成功后，删除
        stringRedisTemplate.delete(RedisConsant.UPLOAD_PRODUCT_SKU_PIC_ID);
        i = 0;
    }

    @Override
    public void deleteProductId(Long productId) {
        skuinfoMapper.deleteProductId(productId);
    }

    @Override
    public void deleteSkuInfoByPrimaryKey() {
        fileInfoService.deleteSkuInfoByPrimaryKey(RedisConsant.UPLOAD_PRODUCT_SKU_PIC_ID);
    }

    @Override
    public void deleteProductImgUrl(String img) {
        String newName = img.substring(img.lastIndexOf("/") + 1);
        skuinfoMapper.deleteProductImgUrl(newName);
        new File(RuoYiConfig.getProfile() + "/" + newName).delete();
    }
}
