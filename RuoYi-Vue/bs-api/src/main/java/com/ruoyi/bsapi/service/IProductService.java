package com.ruoyi.bsapi.service;

import java.util.List;
import com.ruoyi.bsapi.domain.Product;
import com.ruoyi.bsapi.vo.*;
import org.springframework.web.multipart.MultipartFile;

/**
 * 商品Service接口
 * 
 * @author lqg
 * @date 2023-03-13
 */
public interface IProductService 
{
    /**
     * 查询商品
     * 
     * @param productId 商品主键
     * @return 商品
     */
    public Product selectProductByProductId(Long productId);

    /**
     * 查询商品列表
     * 
     * @param product 商品
     * @return 商品集合
     */
    public List<Product> selectProductList(Product product);

    /**
     * 新增商品
     * 
     * @param product 商品
     * @return 结果
     */
    public int insertProduct(Product product);

    /**
     * 修改商品
     * 
     * @param product 商品
     * @return 结果
     */
    public int updateProduct(Product product);

    /**
     * 批量删除商品
     * 
     * @param productIds 需要删除的商品主键集合
     * @return 结果
     */
    public int deleteProductByProductIds(Long[] productIds);

    /**
     * 删除商品信息
     * 
     * @param productId 商品主键
     * @return 结果
     */
    public int deleteProductByProductId(Long productId);

    /**
     * 查询商品属性和分类信息
     * @return
     */
    AttrProductVo searchAttrCatelogInfos(Long catelogId);

    /**
     * 上传商品的封面
     * @param file
     * @return
     */
    int uploadProductPic(MultipartFile file);

    /**
     * 新增商品
     *
     * @param json
     * @param token
     */
    void addProduct(String json, String token);

    /**
     * 上传商品时，新增详情图
     * @param files
     * @return
     */
    int uploadProductDetailImgs(MultipartFile files);

    /**
     * 上传商品时，新增缩略图
     * @param file
     * @return
     */
    int uploadProductThumbnailUrl(MultipartFile file);

    /**
     * 上传商品时，新增sku封面图
     *
     * @param file
     * @return
     */
    List<String> uploadProductSkuPicUrl(MultipartFile file);

    /**
     * 查询全部商品信息
     * @param productTitle
     * @param status
     * @return
     */
    List<ProductInfoVo> queryProductList(String productTitle, Long status);

    /**
     * 查询全部商品的总条数
     * @param productTitle
     * @param status
     * @return
     */
    Long selectCount(String productTitle, Long status);

    /**
     * 根据 商品获取商品详情
     * @param productId
     * @return
     */
    ProductDetailVo detail(Long productId);

    /**
     * 修改商品
     * @param json
     * @return
     */
    int editProduct(String json);

    /**
     * 点击删除修改时的图片
     * @param type
     * @param img
     */
    void deleteEditImg(String type, String img);

    /**
     * 点击删除修改时的图片
     * @param img
     */
    void deleteProductImgUrl(String img);

    /**
     * 根据商品状态进行查询数据
     * @return
     */
    List<ShopAddProductVo> queryProductListByStatus();

    /**
     * 查询已上架的商品
     * @return
     */
    List<ShopAddProductVo> queryProductListByUp();

    /**
     * 详情
     * @param productId
     * @return
     */
    ProductDetailVo menuDetail(Long productId);

    /**
     * 猜你喜欢
     * @return
     */
    List<Product> loveProducts(String token);

    /**
     * 商品检索
     * @param params
     * @return
     */
    List<Product> searchProduct(SearchProductParams params);

    /**
     * 将商品添加到购物车
     *
     * @param token
     * @param addProductCartVo
     */
    void addProductCart(String token, AddProductCartVo addProductCartVo);

    /**
     * 查询购物车中的商品信息
     * @param token
     * @return
     */
    AddCartResp carts(String token);

    /**
     * 选中商品和改变商品数量
     *
     * @param token
     * @param productId
     * @param count
     * @param isChecked
     * @param shopId
     */
    void changeCart(String token, Long productId, Long count, Boolean isChecked, Long shopId);

    /**
     * 全选和选中店铺
     * @param shopId
     * @param checkAll
     */
    void checkedAllAndShop(String token, Long shopId, Boolean checkAll);

    /**
     * 确认订单信息
     * @param token
     * @return
     */
    CartResultVo resultCart(String token);

    /**
     * 删除购物车
     *
     * @param token
     * @param cartId
     */
    void delCart(String token, Long cartId);

    /**
     * 刷新 sku 图片信息
     * @return
     */
    List<String> loadSkuImgInfo();
}
