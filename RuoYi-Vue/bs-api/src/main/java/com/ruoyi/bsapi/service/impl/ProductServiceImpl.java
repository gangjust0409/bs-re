package com.ruoyi.bsapi.service.impl;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

import com.alibaba.fastjson2.JSON;
import com.ruoyi.bsapi.domain.*;
import com.ruoyi.bsapi.service.*;
import com.ruoyi.bsapi.to.AddCartItemTo;
import com.ruoyi.bsapi.to.AddCartShopItemTo;
import com.ruoyi.bsapi.to.ProductAttrItemTo;
import com.ruoyi.bsapi.to.SkuUploadImgItemTo;
import com.ruoyi.bsapi.utils.RedisConsant;
import com.ruoyi.bsapi.vo.*;
import com.ruoyi.common.config.RuoYiConfig;
import com.ruoyi.common.enums.ProductStatus;
import com.ruoyi.common.utils.my.FileUtil;
import com.ruoyi.common.utils.my.Log;
import com.ruoyi.common.utils.my.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.BoundHashOperations;
import org.springframework.data.redis.core.BoundListOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;
import com.ruoyi.bsapi.mapper.ProductMapper;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;

import static com.github.pagehelper.page.PageMethod.offsetPage;
import static com.ruoyi.common.utils.PageUtils.startPage;

/**
 * 商品Service业务层处理
 *
 * @author lqg
 * @date 2023-03-13
 */
@Service
public class ProductServiceImpl implements IProductService {

    private Log log = Log.getLogger(ProductAttrItemVo.class);

    @Resource
    private ProductMapper productMapper;

    @Autowired
    private IAttrsService attrsService;

    @Autowired
    private ICatelogService catelogService;

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Resource
    private IProductDetailService detailService;

    @Resource
    private IProductThumbnailService thumbnailService;

    @Resource
    private ISkuinfoService skuinfoService;

    @Resource
    private IAttrProductService attrProductService;

    @Resource
    private IShopService shopService;

    @Resource
    private IShopProductService shopProductService;

    @Resource
    private IMyCollectService myCollectService;

    List<SkuUploadImgItemTo> skus = new ArrayList<>();

    //属性
    private List<Attrs> attrs = new ArrayList<>();

    @Resource
    private ISysFileInfoService fileInfoService;

    @Autowired
    private ISwiperService swiperService;

    private final String LOCK_SKU = "lock-sku";


    /**
     * 查询商品
     *
     * @param productId 商品主键
     * @return 商品
     */
    @Override
    public Product selectProductByProductId(Long productId) {
        return productMapper.selectProductByProductId(productId);
    }

    /**
     * 查询商品列表
     *
     * @param product 商品
     * @return 商品
     */
    @Override
    public List<Product> selectProductList(Product product) {
        return productMapper.selectProductList(product).stream().map(pro -> {
            pro.setProductPic(com.ruoyi.bsapi.utils.FileUtil.LOCALHOST_SERVER_UPLOAD + pro.getProductPic());
            return pro;
        }).collect(Collectors.toList());
    }

    /**
     * 新增商品
     *
     * @param product 商品
     * @return 结果
     */
    @Override
    public int insertProduct(Product product) {
        return productMapper.insertProduct(product);
    }

    /**
     * 修改商品
     *
     * @param product 商品
     * @return 结果
     */
    @Override
    public int updateProduct(Product product) {
        return productMapper.updateProduct(product);
    }

    /**
     * 批量删除商品
     *
     * @param productIds 需要删除的商品主键
     * @return 结果
     */
    @Override
    public int deleteProductByProductIds(Long[] productIds) {
        //删除其他关联商品的信息
        List<Product> products = this.selectProductList(null);
        for (Long productId : productIds) {
            //删除详情图
            detailService.deleteProductId(productId);

            //删除缩略图
            thumbnailService.deleteProductId(productId);

            //店铺信息
            shopProductService.deleteProductId(productId);

            //sku 信息
            skuinfoService.deleteProductId(productId);

            //attr
            attrProductService.deleteProductId(productId);
        }
        return productMapper.deleteProductByProductIds(productIds);
    }

    /**
     * 删除商品信息
     *
     * @param productId 商品主键
     * @return 结果
     */
    @Override
    public int deleteProductByProductId(Long productId) {
        return productMapper.deleteProductByProductId(productId);
    }

    /**
     * 存入 redis 中进行操作
     *
     * @return
     */
    @Override
    public AttrProductVo searchAttrCatelogInfos(Long catelogId) {
        AttrProductVo attrProductVo = new AttrProductVo();
        //TODO 查询属性 把属性做成 key list 类型, 如 颜色[红色、蓝色] 尺寸[1.23,1.34]
        //点击勾选时的样式
        AtomicBoolean ifIndeterminate = new AtomicBoolean(false);
        //List<Attrs> attrs = attrsService.selectAttrsList(null);
        //List<ProductAttrItemVo> productVos = attrsService.selectAttrs();
        //获取分类下的属性
        BoundHashOperations<String, String, String> ops = RedisConsant.getCatelogAttrsInstant(redisTemplate, catelogId);
        //key 属性值
        List<ProductAttrItemVo> productAttrItemVos = ops.keys().stream().map(key -> {
            ProductAttrItemVo productAttrItemVo = new ProductAttrItemVo();
            //是否全选
            boolean isAll = false;
            String json = ops.get(key);
            List<ProductAttrItemTo> attrItemTos = JSON.parseArray(json, ProductAttrItemTo.class);
            //判断有一个true，显示
            for (ProductAttrItemTo attrItemTo : attrItemTos) {
                if (attrItemTo.isChecked()) {
                    ifIndeterminate.set(true);
                    isAll = true;
                } else {
                    isAll = false;
                    break;
                }
            }
            if (isAll) {
                ifIndeterminate.set(false);
            }
            productAttrItemVo.setTitle(key);
            productAttrItemVo.setAttrValues(attrItemTos);
            productAttrItemVo.setIfIndeterminate(ifIndeterminate.get());
            productAttrItemVo.setIfAll(isAll);
            return productAttrItemVo;
        }).collect(Collectors.toList());
        //key 属性
        attrProductVo.setAttrItems(productAttrItemVos);
        return attrProductVo;
    }

    //上传商品的封面
    @Override
    public int uploadProductPic(MultipartFile file) {
        int result = 0;
        if (file.isEmpty()) return result;
        try {
            //获取当前源文件名称
            String originalFilename = file.getOriginalFilename();
            //新的文件名称
            String newName = StringUtils.randomUUID() + "." + FileUtil.getPrefix(originalFilename);
            result = uploadFile(file, newName, RedisConsant.UPLOAD_TEMPLATE_PRODUCT_PIC);
            log.info("商品图片保存成功！");
        } catch (Exception e) {
            log.error("上传文件出现异常：" + e.getMessage());
            result = 4;
        }
        return result;
    }

    /**
     * 保存文件
     *
     * @param file
     * @param newName
     * @return
     * @throws IOException
     */
    private int uploadFile(MultipartFile file, String newName, String key) throws IOException {
        int result;
        //保存的位置
        String profile = RuoYiConfig.getProfile();
        //封装一个文件对象
        File dirFile = new File(profile, newName);
        //获取输出流
        InputStream inputStream = file.getInputStream();
        //保存文件
        FileUtil.transformTo(inputStream, dirFile);
        //保存文件名进入 redis
        redisTemplate.opsForValue().setIfAbsent(key, newName, 2L, TimeUnit.MINUTES);
        result = 1;
        return result;
    }

    @Transactional
    @Override
    public void addProduct(String json, String token) {
        //睡上2s，防止redis存放文件名为空
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        Long productId = 0L;
        ValueOperations<String, String> ops = redisTemplate.opsForValue();
        //将前端传入的 json 转成 对象
        AddProductVo addProductVo = JSON.parseObject(json, AddProductVo.class);
        //封装一个商品对象
        Product product = new Product();
        product.setProductTitle(addProductVo.getProductTitle());
        product.setProductPrice(addProductVo.getProductPrice());
        product.setCatelogId(addProductVo.getCatelogId());
        product.setMonthPin(0L);
        product.setStatus(ProductStatus.CREATED.getId());
        String productPic = ops.get(RedisConsant.UPLOAD_TEMPLATE_PRODUCT_PIC);
        product.setProductPic(productPic);

        //保存商品
        this.insertProduct(product);
        //查询商品id
        Product paramsProduct = new Product();
        paramsProduct.setProductTitle(addProductVo.getProductTitle());
        List<Product> products = productMapper.selectProductList(paramsProduct);
        if (!CollectionUtils.isEmpty(products)) {
            productId = products.get(0).getProductId();
        }
        //保存 sku 信息
        skuinfoService.saveSkuInfo(productId, addProductVo.getSkus());

        //保存 商品详情图
        detailService.saveProductDetailImgs(productId);

        //保存商品缩略图
        thumbnailService.saveProductThumbnailImgs(productId);

        //获取已经勾选的属性，并保存
        //保存属性
        attrProductService.saveAttrs(productId, product.getCatelogId());
        //防止前一次保存的数据还在这个集合中
        skus = new ArrayList<>();
        //把sku的文件内容信息删除
        redisTemplate.delete(RedisConsant.UPLOAD_PRODUCT_SKU_PIC_ID);
    }

    //上传商品时，新增详情图
    @Override
    public int uploadProductDetailImgs(MultipartFile files) {
        int result = 0;
        if (files.isEmpty()) return 0;
        try {
            //获取每一个file的源文件名称
            String originalFilename = files.getOriginalFilename();
            //新的文件名称
            String newName = StringUtils.randomUUID() + "." + FileUtil.getPrefix(originalFilename);
            BoundListOperations<String, String> ops = RedisConsant.getFileMulitInstant(redisTemplate, "d");
            result = getMulitFile(files, newName, ops);
        } catch (IOException e) {
            result = 4;
            log.error("上传文件出现异常：" + e.getMessage());
        }
        return result;
    }

    /**
     * 上传多个文件
     *
     * @param files
     * @param newName
     * @return
     * @throws IOException
     */
    private int getMulitFile(MultipartFile files, String newName) throws IOException {
        int result;
        String profile = RuoYiConfig.getProfile();
        //封装一个文件对象
        File dirFile = new File(profile, newName);
        //获取输出流
        InputStream inputStream = files.getInputStream();
        //保存文件
        FileUtil.transformTo(inputStream, dirFile);
        result = 1;
        return result;
    }

    /**
     * 上传多个文件
     *
     * @param files
     * @param newName
     * @param ops
     * @return
     * @throws IOException
     */
    private static int getMulitFile(MultipartFile files, String newName, BoundListOperations<String, String> ops) throws IOException {
        int result;
        ops.rightPush(newName);
        String profile = RuoYiConfig.getProfile();
        //封装一个文件对象
        File dirFile = new File(profile, newName);
        //获取输出流
        InputStream inputStream = files.getInputStream();
        ////保存文件
        FileUtil.transformTo(inputStream, dirFile);
        //files.transferTo(dirFile);
        result = 1;
        return result;
    }

    /**
     * 上传商品时，新增缩略图
     *
     * @param file
     * @return
     */
    @Override
    public int uploadProductThumbnailUrl(MultipartFile file) {
        int result = 0;
        if (file.isEmpty()) return 0;
        try {
            //获取每一个file的源文件名称
            String originalFilename = file.getOriginalFilename();
            //新的文件名称
            String newName = StringUtils.randomUUID() + "." + FileUtil.getPrefix(originalFilename);
            BoundListOperations<String, String> ops = RedisConsant.getFileMulitInstant(redisTemplate, "t");
            result = getMulitFile(file, newName, ops);
        } catch (IOException e) {
            result = 4;
            log.error("上传文件出现异常：" + e.getMessage());
        }
        return result;
    }

    /**
     * 上传商品时，新增sku封面图
     *
     * @param file
     * @return
     */
    @Override
    public List<String> uploadProductSkuPicUrl(MultipartFile file) {
        if (file.isEmpty()) return null;
        try {
            //获取每一个file的源文件名称
            String originalFilename = file.getOriginalFilename();
            BoundListOperations<String, String> skuOps = RedisConsant.getSkuUploadImg(redisTemplate);
            //新的文件名称
            String newName = StringUtils.randomUUID() + "." + FileUtil.getPrefix(originalFilename);
            //redis
            getMulitFile(file, newName, skuOps);
            List<String> range = skuOps.range(0, -1).stream().map(img -> com.ruoyi.bsapi.utils.FileUtil.LOCALHOST_SERVER_UPLOAD+img).collect(Collectors.toList());
            return range;
        } catch (IOException e) {
            log.error("上传文件出现异常：" + e.getMessage());
        }
        return null;
    }

    @Override
    public List<ProductInfoVo> queryProductList(String productTitle, Long status) {
        //查询条件
        Product paramsProduct = new Product();
        paramsProduct.setProductTitle(productTitle);
        paramsProduct.setStatus(status);
        startPage();
        //查询
        List<ProductInfoVo> productInfoVos = this.selectProductList(paramsProduct).stream()
                .map(product -> {
                    ProductInfoVo productInfoVo = new ProductInfoVo();
                    productInfoVo.setProductId(product.getProductId());
                    productInfoVo.setProductTitle(product.getProductTitle());
                    productInfoVo.setProductPic(product.getProductPic());
                    productInfoVo.setStatus(product.getStatus());
                    productInfoVo.setProductPrice(product.getProductPrice());
                    //分类名称
                    //1 根据分类ID获取分类信息
                    Catelog catelog = catelogService.getCatelogByCatelogId(product.getCatelogId());
                    //2 设置分类名称
                    productInfoVo.setCatelgoId(catelog.getCatelogId());
                    productInfoVo.setCatelogName(catelog.getCatelogName());
                    //商店名称，可以为空
                    Shop shop = shopService.getShopById(product.getShopId());
                    if (StringUtils.isNotNull(shop)) {
                        productInfoVo.setShopName(shop.getShopName());
                    }
                    //属性
                    List<Attrs> attrs = attrProductService.getAttrsReleaseProduct(product.getProductId());
                    productInfoVo.setAttrs(attrs);
                    return productInfoVo;
                }).collect(Collectors.toList());
        return productInfoVos;
    }

    @Override
    public Long selectCount(String productTitle, Long status) {
        return productMapper.selectCount(productTitle, status);
    }

    @Override
    public ProductDetailVo detail(Long productId) {
        ProductDetailVo detail = new ProductDetailVo();
        //1 查询当前商品信息
        Product product = this.selectProductByProductId(productId);
        //封装商品信息
        detail.setProductId(product.getProductId());
        detail.setProductTitle(product.getProductTitle());
        detail.setProductPrice(product.getProductPrice());
        detail.setMonthPin(product.getMonthPin());
        detail.setActivePic(product.getProductPic() == null ? null : com.ruoyi.bsapi.utils.FileUtil.LOCALHOST_SERVER_UPLOAD
                + product.getProductPic());
        //2 查询缩略图列表
        ProductThumbnail thumbnailParams = new ProductThumbnail();
        thumbnailParams.setProductId(productId);
        List<String> thumbnailUrls = thumbnailService.selectProductThumbnailList(thumbnailParams)
                .stream().map(productThumbnail -> {
                    if (productThumbnail.getProductThumbnailUrl() != null) {
                        return com.ruoyi.bsapi.utils.FileUtil.LOCALHOST_SERVER_UPLOAD + productThumbnail.getProductThumbnailUrl();
                    }
                    return null;
                })
                .collect(Collectors.toList());
        //设置选中在第一个图片
        thumbnailUrls.add(0, detail.getActivePic());
        //设置缩略图列表
        detail.setSmailPics(thumbnailUrls);
        // 3 查询属性列表
        AttrProduct attrProductParams = new AttrProduct();
        attrProductParams.setProductId(productId);
        List<AttrProduct> attrProducts = attrProductService.selectAttrProductList(attrProductParams);
        //查询所有的属性信息
        List<String> attrs = attrsService.selectAttrsList(null).stream()
                .map(attr -> {
                    for (AttrProduct attrProduct : attrProducts) {
                        if (attrProduct.getAttrId().equals(attr.getAttrId())) {
                            return attr.getAttrName() + ":" + attr.getAttrValue();
                        }
                    }
                    return null;
                }).collect(Collectors.toList());
        detail.setAttrs(attrs.stream().filter(e -> e != null).collect(Collectors.toList()));
        //查询 sku 信息
        Skuinfo skuinfoParams = new Skuinfo();
        skuinfoParams.setProductId(productId);
        List<ProductDetailSkuVo> productDetailSkuVos = skuinfoService.selectSkuinfoList(skuinfoParams).stream()
                .map(skuinfo -> {
                    ProductDetailSkuVo productDetailSkuVo = new ProductDetailSkuVo();
                    productDetailSkuVo.setSkuId(skuinfo.getSkuId());
                    productDetailSkuVo.setSkuPrice(skuinfo.getSkuPrice());
                    productDetailSkuVo.setSkuName(skuinfo.getSkuName());
                    productDetailSkuVo.setSkuPic(skuinfo.getSkuPic() == null ? null : com.ruoyi.bsapi.utils.FileUtil.LOCALHOST_SERVER_UPLOAD + skuinfo.getSkuPic());
                    return productDetailSkuVo;
                }).collect(Collectors.toList());
        detail.setSkus(productDetailSkuVos);
        //4 查询详情图
        ProductDetail productDetailParams = new ProductDetail();
        productDetailParams.setProductId(productId);
        List<String> detailImages = detailService.selectProductDetailList(productDetailParams).stream()
                .map(d -> com.ruoyi.bsapi.utils.FileUtil.LOCALHOST_SERVER_UPLOAD + d.getProductDetailUrl()).collect(Collectors.toList());
        detail.setDetailImages(detailImages);
        //5分类
        List<Long> catelogIds = catelogService.selectCatelogs(product.getCatelogId());
        detail.setCatelogIds(catelogIds);
        return detail;
    }

    @Override
    public int editProduct(String json) {
        //睡上2s，防止redis存放文件名为空
        try {
            Thread.sleep(4000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        //将 json转成对象
        AddProductVo productVo = JSON.parseObject(json, AddProductVo.class);
        //查询当前信息
        Product currentProduct = this.selectProductByProductId(productVo.getProductId());
        //保存product
        Product product = new Product();
        product.setProductId(productVo.getProductId());
        product.setProductTitle(productVo.getProductTitle());
        product.setProductOldPrice(currentProduct.getProductPrice());
        product.setProductPrice(productVo.getProductPrice());
        product.setCatelogId(productVo.getCatelogId());
        //设置
        String productPic = redisTemplate.opsForValue().get(RedisConsant.UPLOAD_TEMPLATE_PRODUCT_PIC);
        product.setProductPic(productPic);
        //执行修改操作
        this.updateProduct(product);
        //属性修改
        attrProductService.deleteProductId(product.getProductId());
        attrProductService.saveAttrs(product.getProductId(), product.getCatelogId());
        //保存 sku 信息
        skuinfoService.deleteProductId(product.getProductId());
        skuinfoService.saveSkuInfo(product.getProductId(), productVo.getSkus());

        //保存 商品详情图
        detailService.saveProductDetailImgs(product.getProductId());

        //保存商品缩略图
        thumbnailService.saveProductThumbnailImgs(product.getProductId());
        //防止前一次保存的数据还在这个集合中
        skus = new ArrayList<>();
        //把sku的文件内容信息删除
        redisTemplate.delete(RedisConsant.UPLOAD_PRODUCT_SKU_PIC_ID);
        return 1;
    }

    @Override
    public void deleteEditImg(String type, String img) {
        if ("thumbnail".equals(type)) {
            thumbnailService.deleteProductImgUrl(img);
        } else if ("productPic".equals(type)) {
            this.deleteProductImgUrl(img);
        } else if ("sku".equals(type)) {
            skuinfoService.deleteProductImgUrl(img);
        } else if ("detail".equals(type)) {
            detailService.deleteProductImgUrl(img);
        }
    }

    @Override
    public void deleteProductImgUrl(String img) {
        String newName = img.substring(img.lastIndexOf("/") + 1);
        productMapper.deleteProductImgUrl(newName);
        new File(RuoYiConfig.getProfile() + "/" + newName).delete();
    }

    @Override
    public List<ShopAddProductVo> queryProductListByStatus() {
        //TODO 只查询已创建的商品，其他的不可用再分配了
        Product productParams = new Product();
        productParams.setStatus(ProductStatus.CREATED.getId());
        return this.selectProductList(productParams).stream().map(product -> {
            ShopAddProductVo shopAddProductVo = new ShopAddProductVo();
            shopAddProductVo.setProductId(product.getProductId());
            shopAddProductVo.setProductTitle(product.getProductTitle());
            shopAddProductVo.setProductPrice(product.getProductPrice());
            return shopAddProductVo;
        }).collect(Collectors.toList());
    }

    @Override
    public List<ShopAddProductVo> queryProductListByUp() {
        Product productParams = new Product();
        productParams.setStatus(ProductStatus.PUT_SHELVES.getId());
        //查询全部轮播图的商品id
        List<Long> swiperProductIds = swiperService.selectProductReleaseSwiper(null).stream().map(SwiperProductVo::getProductId).collect(Collectors.toList());
        return this.selectProductList(productParams).stream().filter(product -> {
            for (Long swiperProductId : swiperProductIds) {
                if (swiperProductId.equals(product.getProductId())) {
                    return false;
                }
            }
            return true;
        }).map(product -> {
            ShopAddProductVo shopAddProductVo = new ShopAddProductVo();
            shopAddProductVo.setProductId(product.getProductId());
            shopAddProductVo.setProductTitle(product.getProductTitle());
            return shopAddProductVo;
        }).collect(Collectors.toList());
    }

    @Override
    public ProductDetailVo menuDetail(Long productId) {
        ProductDetailVo detail = this.detail(productId);
        List<ProductAttrItemVo> productAttrItemVos = attrProductService.selectAttrsByGroupByProductId(productId);
        //查询店铺和商品关联的信息
        ShopProduct shopProductParams = new ShopProduct();
        shopProductParams.setProductId(productId);
        List<ShopProduct> shopProducts = shopProductService.selectShopProductList(shopProductParams);
        if (!CollectionUtils.isEmpty(shopProducts)) {
            Shop shop = shopService.selectShopByShopId(shopProducts.get(0).getShopId());
            detail.setShop(shop);
        }
        if (!CollectionUtils.isEmpty(productAttrItemVos)) {
            detail.setAttrItems(productAttrItemVos.stream().filter(e -> e != null).collect(Collectors.toList()));
        }
        //查询店铺信息
        return detail;
    }

    @Override
    public List<Product> loveProducts(String token) {
        Product productParams = new Product();
        productParams.setStatus(ProductStatus.PUT_SHELVES.getId());
        return this.selectProductList(productParams).stream().
                filter(pro -> pro.getStatus().equals(ProductStatus.PUT_SHELVES.getId()))
                .map(pro -> {
                    if (StringUtils.isNotNull(token)) {
                        User user = RedisConsant.getUser(redisTemplate, token);
                        if (StringUtils.isNotNull(user)) {
                            MyCollect myCollectParams = new MyCollect();
                            myCollectParams.setProductId(pro.getProductId());
                            myCollectParams.setUserId(user.getUserId());
                            List<MyCollect> myCollects = myCollectService.selectMyCollectList(myCollectParams);
                            if (myCollects.size() == 1) {
                                pro.setIfCollected(true);
                            } else {
                                pro.setIfCollected(false);
                            }
                        }
                    }
                    return pro;
                })
                .collect(Collectors.toList());
    }

    @Override
    public List<Product> searchProduct(SearchProductParams params) {
        BigDecimal minPrice = null;
        BigDecimal maxPrice = null;
        Product productParams = new Product();
        productParams.setProductTitle(params.getKeyword());
        Catelog catelogParams = new Catelog();
        catelogParams.setCatelogName(params.getKeyword());
        //价格
        String priceStr = params.getPriceStr();
        if (StringUtils.isNotNull(priceStr)) {
            System.out.println(priceStr);
            String[] prices = priceStr.split("-");
            //TODO 当填写一个价格时，下标为0的为null
            minPrice = new BigDecimal(prices[0] == null ? "0" : prices[0]);
            maxPrice = new BigDecimal(prices[1] == null ? "0" : prices[1]);
        }
        //根据点击分类得到选择的分类信息
        List<Long> catelogIds = catelogService.selectCatelogList(catelogParams).stream().map(Catelog::getCatelogId).collect(Collectors.toList());
        List<Product> products = productMapper.queryProductList(productParams, catelogIds, minPrice, maxPrice)
                .stream().map(pro -> {
                    String oldTitle = null;
                    String newProductTitle = "";
                    pro.setProductPic(com.ruoyi.bsapi.utils.FileUtil.LOCALHOST_SERVER_UPLOAD + pro.getProductPic());
                    //把关键字变换颜色
                    String keyword = "<b style='color:red;'>" + params.getKeyword() + "</b>";
                    String productTitle = pro.getProductTitle();
                    int index = productTitle.indexOf(params.getKeyword());
                    //搜索名称
                    if (index != -1) {
                        newProductTitle = productTitle.replace(params.getKeyword(), keyword);
                        pro.setProductTitle(newProductTitle);
                    }
                    //否则就是查询的分类
                    return pro;
                }).collect(Collectors.toList());

        return products;
    }

    /**
     * 添加购物车实现步骤
     * 1 购物车下每个商品都有店铺，
     * 2 店铺下也有很多商品信息
     *
     * @param token
     * @param addProductCartVo
     */
    @Override
    public void addProductCart(String token, AddProductCartVo addProductCartVo) {
        //商品信息
        Product product = productMapper.selectProductByProductId(addProductCartVo.getProductId());
        //店铺信息
        ShopProduct shopProductParams = new ShopProduct();
        shopProductParams.setProductId(addProductCartVo.getProductId());
        Long shopId = shopProductService.selectShopProductList(shopProductParams).stream()
                .map(ShopProduct::getShopId).collect(Collectors.toList()).get(0);
        //Shop shop = shopService.selectShopByShopId(shopId);
        //TODO 商品属性信息
        //获取当前对象
        //User currentUser = RedisConsant.getUser(redisTemplate, token);
        //创建redis实例，存放 key 店铺id，value List<购物车每一项>
        // List<购物车每一项> 每一次添加购物车都有读取一下 redis 中的list
        BoundHashOperations<String, String, String> userOps = RedisConsant.getCurrentUserCart(redisTemplate, token);
        //查询当前用户已经选择的sku信息
        Skuinfo skuinfo = skuinfoService.selectSkuinfoBySkuId(addProductCartVo.getSkuId());
        //获取购物车sku信息在redis的实例
        final List<AddCartItemTo>[] addCartItemTos = new List[]{new ArrayList<>()};
        //BoundListOperations<String, String> skuOps = RedisConsant.getCurrentUserCartSkuList(redisTemplate, shopId);
        userOps.keys().forEach(key -> {
            String json = userOps.get(key);
            if (addProductCartVo.getShopId().equals(Long.parseLong(key)))
                addCartItemTos[0] = JSON.parseArray(json, AddCartItemTo.class);
        });
        Attrs attr = new Attrs();
        attr.setAttrName("款式");
        attr.setAttrValue(skuinfo.getSkuName());
        //添加进入属性
        attrs.add(attr);
        //将原有数据放到这里重新组合数据
        //if (cartsStr.size() > 0) {
        //    for (String json : cartsStr) {
        //        AddCartItemTo addCartItemTo = JSON.parseObject(json, AddCartItemTo.class);
        //        addCartItemTos.add(addCartItemTo);
        //    }
        //}
        //开始添加新的对象
        AddCartItemTo addCartItemTo = new AddCartItemTo();
        //控制购物车中是否已经存在过
        boolean isActiveCart = false;
        if (addCartItemTos[0].size() > 0) {
            Iterator<AddCartItemTo> itemToIterator = addCartItemTos[0].iterator();
            while (itemToIterator.hasNext()) {
                AddCartItemTo act = itemToIterator.next();
                System.out.println("商品id " + act.getProductId() + "\t" + addProductCartVo.getSkuId() + "\t" + act.getProductId().equals(addProductCartVo.getSkuId()));
                if (act.getProductId().equals(addProductCartVo.getSkuId())) {
                    BeanUtils.copyProperties(act, addCartItemTo);
                    addCartItemTo.setCount(act.getCount() + addProductCartVo.getCount());
                    //删除原来的数据
                    addCartItemTos[0].remove(act);
                    isActiveCart = true;
                    break;
                }
            }
        }
        if (!isActiveCart){
            //读取redis中用户关联的商品id
            addCartItem(addProductCartVo, product, skuinfo, addCartItemTo);
        }
        addCartItemTos[0].add(addCartItemTo);
        String newJson = JSON.toJSONString(addCartItemTos[0]);
        userOps.put(shopId.toString(), newJson);
        //保存完毕后，属性列表重置
        attrs = new ArrayList<>();
    }

    /**
     * 添加一个购物车
     *
     * @param addProductCartVo
     * @param product
     * @param skuinfo
     * @param addCartItemTo
     */
    private void addCartItem(AddProductCartVo addProductCartVo, Product product, Skuinfo skuinfo, AddCartItemTo addCartItemTo) {
        addCartItemTo.setProductId(skuinfo.getSkuId());
        addCartItemTo.setChecked(true);
        addCartItemTo.setCount(addProductCartVo.getCount());
        addCartItemTo.setProductTitle(product.getProductTitle() + " " + skuinfo.getSkuName());
        addCartItemTo.setProductPic(skuinfo.getSkuPic() == null ? com.ruoyi.bsapi.utils.FileUtil.LOCALHOST_SERVER_UPLOAD + product.getProductPic() : com.ruoyi.bsapi.utils.FileUtil.LOCALHOST_SERVER_UPLOAD + skuinfo.getSkuPic());
        addCartItemTo.setPrice(skuinfo.getSkuPrice());
        //设置属性
        addCartItemTo.setAttrs(attrs);
        //计算当前商品总价格
        BigDecimal totalMoney = skuinfo.getSkuPrice().multiply(new BigDecimal(addProductCartVo.getCount()));
        addCartItemTo.setTotalMoney(totalMoney);
    }

    @Override
    public AddCartResp carts(String token) {
        AddCartResp addCartResp = new AddCartResp();
        //店铺id
        final Long[] shopId = {null};
        //总数量
        AtomicLong count = new AtomicLong(0L);
        //计算总价格
        final BigDecimal[] totalPrice = {new BigDecimal("0")};
        //计算选中的数量
        AtomicLong checkedCount = new AtomicLong(0L);
        //店铺信息
        List<Shop> shops = shopService.selectShopList(null);
        //获取当前用户的购物车
        BoundHashOperations<String, String, String> userOps = RedisConsant.getCurrentUserCart(redisTemplate, token);
        //获取所有的key
        List<AddCartShopItemTo> addCartResps = userOps.keys().stream().map(key -> {
            //设置店铺信息
            AddCartShopItemTo addCartShopItemTo = new AddCartShopItemTo();
            shopId[0] = Long.parseLong(key);
            //计算购物车下的不同店铺的数量
            Long shopCount = 0L;
            Long shopCheckedCount = 0L;
            //获取当前店铺下的商品信息
            String valueJson = userOps.get(key);
            List<AddCartItemTo> addCartItemTos = JSON.parseArray(valueJson, AddCartItemTo.class);
            for (AddCartItemTo addCartItemTo : addCartItemTos) {
                //数量
                count.getAndIncrement();
                shopCount++;
                //当全是true
                if (addCartItemTo.isChecked()) {
                    //计算总价格
                    totalPrice[0] = totalPrice[0].add(addCartItemTo.getTotalMoney());
                    //选中的数量
                    checkedCount.getAndIncrement();
                    //选中的商品++
                    shopCheckedCount++;
                }
            }
            addCartShopItemTo.setCount(shopCount);
            addCartShopItemTo.setCheckedCount(shopCheckedCount);
            addCartShopItemTo.setCarts(addCartItemTos);
            addCartShopItemTo.setShopId(shopId[0]);
            //设置店铺名称
            for (Shop shop : shops) {
                if (shop.getShopId().equals(shopId[0])) {
                    addCartShopItemTo.setShopName(shop.getShopName());
                }
            }
            return addCartShopItemTo;
        }).collect(Collectors.toList());

        //设置返回数据
        addCartResp.setCount(count.get());
        addCartResp.setTotalPrice(totalPrice[0]);
        addCartResp.setCheckedCount(checkedCount.get());
        //把当前店铺添加到购物车中
        addCartResp.setShopItemTos(addCartResps);
        return addCartResp;
    }

    @Override
    public void changeCart(String token, Long productId, Long count, Boolean isChecked, Long shopId) {
        //获取当前用户的购物车
        BoundHashOperations<String, String, String> userOps = RedisConsant.getCurrentUserCart(redisTemplate, token);
        //获取对应的 map value，改变数量
        String prosJSON = userOps.get(shopId.toString());
        List<AddCartItemTo> addCartItemTos = JSON.parseArray(prosJSON, AddCartItemTo.class);
        List<AddCartItemTo> carts = addCartItemTos.stream().map(to -> {
            if (to.getProductId().equals(productId)) {
                //改变数量
                if (StringUtils.isNotNull(count) && count > 0) {
                    to.setCount(count);
                    //计算价格
                    BigDecimal totalPrice = to.getPrice().multiply(new BigDecimal(to.getCount()));
                    to.setTotalMoney(totalPrice);
                } else if (count == null && isChecked != null) {
                    //改变选中状态
                    to.setChecked(isChecked);
                }
            }
            return to;
        }).collect(Collectors.toList());
        userOps.put(shopId.toString(), JSON.toJSONString(carts));
    }

    @Override
    public void checkedAllAndShop(String token, Long shopId, Boolean checkAll) {
        //获取购物车
        BoundHashOperations<String, String, String> userOps = RedisConsant.getCurrentUserCart(redisTemplate, token);
        for (String key : userOps.keys()) {
            //判断是全选还是选中店铺
            if (StringUtils.isNotNull(shopId)) {
                if (shopId.equals(Long.parseLong(key))) {
                    List<AddCartItemTo> cartItemTos = getAddCartItemTos(checkAll, userOps, key);
                    //保存
                    userOps.put(shopId.toString(), JSON.toJSONString(cartItemTos));
                    break;
                }
            } else {
                List<AddCartItemTo> cartItemTos = getAddCartItemTos(checkAll, userOps, key);
                userOps.put(key, JSON.toJSONString(cartItemTos));
            }
        }

    }

    @Override
    public CartResultVo resultCart(String token) {
        CartResultVo cartResultVo = new CartResultVo();
        //店铺信息
        List<Shop> shops = shopService.selectShopList(null);
        //实际支付
        final BigDecimal[] payPrice = {new BigDecimal("0")};
        //sku info
        List<Skuinfo> skuinfos = skuinfoService.selectSkuinfoList(null);
        //获取当前购物车
        BoundHashOperations<String, String, String> userOps = RedisConsant.getCurrentUserCart(redisTemplate, token);
        List<CartResultShopItemVo> shopList = userOps.keys().stream().map(key -> {
            String valJson = userOps.get(key);
            List<AddCartItemTo> addCartItemTos = JSON.parseArray(valJson, AddCartItemTo.class);
            Iterator<Shop> it = shops.iterator();
            while (it.hasNext()) {
                CartResultShopItemVo shopItemVo = new CartResultShopItemVo();
                //计算当前店铺的商品信息
                final BigDecimal[] shopTotalPrice = {new BigDecimal("0")};
                Shop shop = it.next();
                //店铺展示
                if (Long.parseLong(key) == shop.getShopId()) {
                    shopItemVo.setShopId(shop.getShopId());
                    shopItemVo.setShopName(shop.getShopName());
                    shopItemVo.setFare(new BigDecimal("0.0"));
                    //查询商品信息
                    List<CartResultShopItemVo.CartResultProductItemVo> products = addCartItemTos.stream().map(addCartItemTo -> {
                        for (Skuinfo skuinfo : skuinfos) {
                            //判断购物车是否选中，查询商品信息
                            if (addCartItemTo.isChecked() && skuinfo.getSkuId()
                                    .equals(addCartItemTo.getProductId())) {
                                CartResultShopItemVo.CartResultProductItemVo itemVo = new CartResultShopItemVo.CartResultProductItemVo();
                                itemVo.setProductId(skuinfo.getSkuId());
                                itemVo.setProductPic(addCartItemTo.getProductPic());
                                itemVo.setProductTitle(addCartItemTo.getProductTitle());
                                itemVo.setProductCount(addCartItemTo.getCount());
                                itemVo.setProductPrice(skuinfo.getSkuPrice());
                                itemVo.setAttrs(addCartItemTo.getAttrs());
                                //数量 * 单价
                                BigDecimal subTotal = skuinfo.getSkuPrice().multiply(new BigDecimal(addCartItemTo.getCount()));
                                payPrice[0] = payPrice[0].add(subTotal);
                                shopTotalPrice[0] = shopTotalPrice[0].add(subTotal);
                                itemVo.setSubtotal(subTotal);
                                return itemVo;
                            }
                        }
                        return null;
                    }).collect(Collectors.toList());
                    //如果这个店没有商品信息，那么就不添加
                    //System.out.println("size " + products + "\t" + products.size());
                    List<CartResultShopItemVo.CartResultProductItemVo> productItemVos = products.stream().filter(e -> e != null).collect(Collectors.toList());
                    if (productItemVos.size() > 0) {
                        //设置当前店铺的总价格
                        shopItemVo.setTotalMoney(shopTotalPrice[0]);
                        //设置商品信息
                        shopItemVo.setProducts(productItemVos);
                        return shopItemVo;
                    }
                }
            }
            return null;
        }).collect(Collectors.toList());
        //店铺信息
        //设置店铺信息
        cartResultVo.setShops(shopList.stream().filter(Objects::nonNull).collect(Collectors.toList()));
        cartResultVo.setPayPrice(payPrice[0]);
        return cartResultVo;
    }

    @Override
    public void delCart(String token, Long cartId) {
        final String[] shopKey = {""};
        List<AddCartItemTo> newCarts = new ArrayList<>();
        BoundHashOperations<String, String, String> userOps = RedisConsant.getCurrentUserCart(redisTemplate, token);
        for (String key : userOps.keys()) {
            String valJson = userOps.get(key);
            List<AddCartItemTo> addCartItemTos = JSON.parseArray(valJson, AddCartItemTo.class)
                    .stream().filter(e -> e != null).collect(Collectors.toList());
            //TODO 使用原始的remove方法时，会抛出 java.util.ConcurrentModificationException: null 异常
            //TODO 1、使用迭代器 2、同步调整索引 3、倒序排列 list集合
            Iterator<AddCartItemTo> iterator = addCartItemTos.iterator();
            while (iterator.hasNext()) {
                AddCartItemTo addCartItemTo = iterator.next();
                shopKey[0] = key;
                //判断是单个删除还是多个删除
                if (StringUtils.isNotNull(cartId)) {
                    if (addCartItemTo.getProductId().equals(cartId)) {
                        iterator.remove();
                    }
                } else if (addCartItemTo.isChecked()) {
                    //多选
                    iterator.remove();
                }
            }
            //重新保存到 redis
            if (!valJson.equals("[]") || !(addCartItemTos.size() > 1)) {
                //还有数据
                userOps.put(shopKey[0], JSON.toJSONString(addCartItemTos));
            } else {
                //没有数据
                System.out.println(valJson + "\t" + shopKey[0]);
                userOps.delete(shopKey[0]);
            }
        }

    }

    @Override
    public List<String> loadSkuImgInfo() {
        //获取信息
        BoundListOperations<String, String> ops = RedisConsant.getSkuUploadImg(redisTemplate);
        List<String> range = ops.range(0, -1);
        List<String> urls = range.stream().map(url -> com.ruoyi.bsapi.utils.FileUtil.LOCALHOST_SERVER_UPLOAD + url).collect(Collectors.toList());
        return urls;
    }

    /**
     * 根据用户购物车中 key返回数据
     *
     * @param checkAll
     * @param userOps
     * @param key
     * @return
     */
    private static List<AddCartItemTo> getAddCartItemTos(Boolean checkAll, BoundHashOperations<String, String, String> userOps, String key) {
        String valJson = userOps.get(key);
        List<AddCartItemTo> addCartItemTos = JSON.parseArray(valJson, AddCartItemTo.class);
        List<AddCartItemTo> cartItemTos = addCartItemTos.stream().map(addCartItemTo -> {
            addCartItemTo.setChecked(checkAll);
            return addCartItemTo;
        }).collect(Collectors.toList());
        return cartItemTos;
    }
}
