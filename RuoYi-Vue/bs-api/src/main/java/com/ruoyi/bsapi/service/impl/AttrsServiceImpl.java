package com.ruoyi.bsapi.service.impl;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import com.alibaba.fastjson2.JSON;
import com.ruoyi.bsapi.service.IAttrProductService;
import com.ruoyi.bsapi.to.ProductAttrItemTo;
import com.ruoyi.bsapi.utils.RedisConsant;
import com.ruoyi.bsapi.vo.AttrProductGroupByVo;
import com.ruoyi.bsapi.vo.ProductAttrItemVo;
import com.ruoyi.common.utils.my.Log;
import org.springframework.data.redis.core.BoundHashOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import com.ruoyi.bsapi.mapper.AttrsMapper;
import com.ruoyi.bsapi.domain.Attrs;
import com.ruoyi.bsapi.service.IAttrsService;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;

/**
 * 属性Service业务层处理
 *
 * @author lqg
 * @date 2023-03-13
 */
@Service
public class AttrsServiceImpl implements IAttrsService {

    private Log log = Log.getLogger(AttrsServiceImpl.class);

    @Resource
    private AttrsMapper attrsMapper;

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    @Resource
    private IAttrProductService attrProductService;

    /**
     * 查询属性
     *
     * @param attrId 属性主键
     * @return 属性
     */
    @Override
    public Attrs selectAttrsByAttrId(Long attrId) {
        return attrsMapper.selectAttrsByAttrId(attrId);
    }

    /**
     * 查询属性列表
     *
     * @param attrs 属性
     * @return 属性
     */
    @Override
    public List<Attrs> selectAttrsList(Attrs attrs) {
        return attrsMapper.selectAttrsList(attrs);
    }

    /**
     * 新增属性
     *
     * @param attrs 属性
     * @return 结果
     */
    @Override
    public int insertAttrs(Attrs attrs) {
        int result = 0;
        //保存进入数据库
        int i = attrsMapper.insertAttrs(attrs);
        //保存进入 redis
        BoundHashOperations<String, String, String> ops = RedisConsant.getCatelogAttrsInstant(stringRedisTemplate, attrs.getCatelogId());
        System.out.println(ops);
        //查询最新的数据，根据分类id获取信息
        List<ProductAttrItemVo> attrItemVos = attrsMapper.selectAttrsGroupByAttrNameByCatelogId(attrs.getCatelogId());
        if (!CollectionUtils.isEmpty(attrItemVos)) {
            for (ProductAttrItemVo productAttrItemVo : attrItemVos) {
                String attrValuesWithStr = productAttrItemVo.getAttrValuesWithStr();
                if (attrValuesWithStr.equals("")) {
                    log.info("查询到的 values 为 null");
                }
                String[] values = attrValuesWithStr.split(",");
                List<ProductAttrItemTo> vals = Arrays.stream(values).map(val -> {
                    String[] valu = val.split("-");
                    ProductAttrItemTo productAttrItemTo = new ProductAttrItemTo();
                    productAttrItemTo.setAttrId(Long.parseLong(valu[0]));
                    productAttrItemTo.setChecked(false);
                    productAttrItemTo.setAttrValue(valu[1]);
                    return productAttrItemTo;
                }).collect(Collectors.toList());
                String json = JSON.toJSONString(vals);
                ops.put(productAttrItemVo.getTitle(), json);
            }
            result = 1;
        }
        return result;
    }

    /**
     * 修改属性
     *
     * @param attrs 属性
     * @return 结果
     */
    @Override
    public int updateAttrs(Attrs attrs) {
        return attrsMapper.updateAttrs(attrs);
    }

    /**
     * 批量删除属性
     *
     * @param attrIds 需要删除的属性主键
     * @return 结果
     */
    @Override
    public int deleteAttrsByAttrIds(Long[] attrIds) {
        return attrsMapper.deleteAttrsByAttrIds(attrIds);
    }

    /**
     * 删除属性信息
     *
     * @param attrId 属性主键
     * @return 结果
     */
    @Override
    public int deleteAttrsByAttrId(Long attrId) {
        return attrsMapper.deleteAttrsByAttrId(attrId);
    }

    /**
     * 新增商品时，选择属性
     *
     * @return
     */
    @Override
    public List<ProductAttrItemVo> selectAttrs() {
        ////查询属性
        //List<ProductAttrItemVo> attrItemVos = attrsMapper.selectAttrsGroupByAttrName().stream()
        //        .map(attr -> {
        //            String[] values = attr.getAttrValuesWithStr().split(",");
        //            for (String value : values) {
        //                attr.getAttrValues().add(value);
        //            }
        //            return attr;
        //        }).collect(Collectors.toList());


        return null;
    }

    @Override
    public void updateAttrStatus(Long catelogId, List<ProductAttrItemVo> productAttrItemVo) {
        //获取redis中的 实例
        BoundHashOperations<String, String, String> ops = RedisConsant.getCatelogAttrsInstant(stringRedisTemplate,catelogId);
        for (ProductAttrItemVo attrItemVo : productAttrItemVo) {
            //key
            String key = attrItemVo.getTitle();
            //value
            List<ProductAttrItemTo> attrValues = attrItemVo.getAttrValues();
            String json = JSON.toJSONString(attrValues);
            ops.put(key, json);
            log.info("修改成功！");
        }
    }

    @Override
    public List<Attrs> queryAttrsByCatelogId(Long catelogId, Long templateProductId) {
        Attrs params = new Attrs();
        params.setCatelogId(catelogId);
        List<Attrs> attrs = this.selectAttrsList(params);
        //查询当前属性是否和商品关联
        List<Attrs> attrsReleaseProduct = attrProductService.getAttrsReleaseProduct(templateProductId);
        //查询到数据进行对比，属性id一样返回空，否则返回属性信息
        List<Attrs> attrsList = attrs.stream().map(attr -> {
            for (Attrs ap : attrsReleaseProduct) {
                if (ap.getAttrId().equals(attr.getAttrId())) {
                    return null;
                }
            }
            return attr;
        }).collect(Collectors.toList());

        return attrsList;
    }

    @Override
    public List<AttrProductGroupByVo> selectAttrsByGroupByProductId(List<Long> attrIds) {
        return attrsMapper.selectAttrsByGroupByProductId(attrIds);
    }

}
