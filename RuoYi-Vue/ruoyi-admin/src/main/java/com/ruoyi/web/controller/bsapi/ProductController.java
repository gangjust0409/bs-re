package com.ruoyi.web.controller.bsapi;

import java.util.List;
import javax.servlet.http.HttpServletResponse;

import com.ruoyi.bsapi.domain.ProductDetail;
import com.ruoyi.bsapi.vo.AttrProductVo;
import com.ruoyi.bsapi.vo.ProductDetailVo;
import com.ruoyi.bsapi.vo.ProductInfoVo;
import com.ruoyi.bsapi.vo.ShopAddProductVo;
import com.ruoyi.common.core.domain.R;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.bsapi.domain.Product;
import com.ruoyi.bsapi.service.IProductService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 商品Controller
 * 
 * @author lqg
 * @date 2023-03-13
 */
@RestController
@RequestMapping("/bsapi/product")
public class ProductController extends BaseController
{
    @Autowired
    private IProductService productService;

    //查询已上架的商品
    @PreAuthorize("@ss.hasPermi('bsapi:query:up:products')")
    @GetMapping("/up")
    public AjaxResult queryProductListByUp(){
        List<ShopAddProductVo>  productVos = productService.queryProductListByUp();
        return success(productVos);
    }

    //根据商品状态进行查询数据
    @PreAuthorize("@ss.hasPermi('bsapi:query:products:by:status')")
    @GetMapping("/shop/query")
    public AjaxResult queryProductListByStatus() {
        List<ShopAddProductVo> productVos = productService.queryProductListByStatus();
        return success(productVos);
    }

    //点击删除修改时的图片
    @PreAuthorize("@ss.hasPermi('bsapi:delete:edit:img')")
    @Log(title = "点击删除修改时的图片", businessType = BusinessType.DELETE)
    @DeleteMapping("/delete/edit/img")
    public AjaxResult deleteEditImg(@RequestParam String img, @RequestParam String type) {
        productService.deleteEditImg(type, img);
        return AjaxResult.success();
    }


    //修改商品信息
    @PreAuthorize("@ss.hasPermi('bsapi:edit:product')")
    @Log(title = "修改商品信息", businessType = BusinessType.UPDATE)
    @PutMapping("/edit")
    public AjaxResult editProduct(@RequestBody String json){
        int res = productService.editProduct(json);
        return toAjax(res);
    }

    //查询全部商品信息
    @PreAuthorize("@ss.hasPermi('bsapi:query:all:products')")
    @GetMapping("/query/list")
    public TableDataInfo queryProductList(@RequestParam(required = false) String productTitle, @RequestParam(required = false) Long status) {
        List<ProductInfoVo> productInfoVos = productService.queryProductList(productTitle,status);
        TableDataInfo dataTable = getDataTable(productInfoVos);
        Long total = productService.selectCount(productTitle, status);
        dataTable.setTotal(total);
        return dataTable;
    }

    //根据商品id获取商品信息
    @PreAuthorize("@ss.hasPermi('bsapi:query:product:by:id')")
    @GetMapping("/get/{productId}")
    public AjaxResult getProductByProductId(@PathVariable Long productId) {
        ProductDetailVo detail = productService.detail(productId);
        return AjaxResult.success(detail);
    }

    //新增商品
    @PreAuthorize("@ss.hasPermi('bsapi:add:product')")
    @Log(title = "新增商品", businessType = BusinessType.INSERT)
    @PostMapping("/create")
    public AjaxResult addProduct(@RequestBody String json,@CookieValue("Admin-Token") String token){
        productService.addProduct(json, token);
        return AjaxResult.success();
    }


    //查询商品属性和分类信息
    @PreAuthorize("@ss.hasPermi('bsapi:query:product:attrs:catelog')")
    @GetMapping("/query/attr/catelog/{catelogId}")
    public R searchAttrCatelogInfos(@PathVariable Long catelogId){
        AttrProductVo attrProductVo = productService.searchAttrCatelogInfos(catelogId);
        return R.ok(attrProductVo);
    }

    /**
     * 查询商品列表
     */
    @PreAuthorize("@ss.hasPermi('bsapi:product:list')")
    @GetMapping("/list")
    public TableDataInfo list(Product product)
    {
        startPage();
        List<Product> list = productService.selectProductList(product);
        return getDataTable(list);
    }

    /**
     * 导出商品列表
     */
    @PreAuthorize("@ss.hasPermi('bsapi:product:export')")
    @Log(title = "商品", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, Product product)
    {
        List<Product> list = productService.selectProductList(product);
        ExcelUtil<Product> util = new ExcelUtil<Product>(Product.class);
        util.exportExcel(response, list, "商品数据");
    }

    /**
     * 获取商品详细信息
     */
    @PreAuthorize("@ss.hasPermi('bsapi:product:query')")
    @GetMapping(value = "/{productId}")
    public AjaxResult getInfo(@PathVariable("productId") Long productId)
    {
        return success(productService.selectProductByProductId(productId));
    }

    /**
     * 新增商品
     */
    @PreAuthorize("@ss.hasPermi('bsapi:product:add')")
    @Log(title = "商品", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult addProduct(@RequestBody Product product)
    {
        return toAjax(productService.insertProduct(product));
    }

    /**
     * 修改商品
     */
    @PreAuthorize("@ss.hasPermi('bsapi:product:edit')")
    @Log(title = "商品", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody Product product)
    {
        return toAjax(productService.updateProduct(product));
    }

    /**
     * 删除商品
     */
    @PreAuthorize("@ss.hasPermi('bsapi:product:remove')")
    @Log(title = "商品", businessType = BusinessType.DELETE)
	@DeleteMapping("/{productIds}")
    public AjaxResult remove(@PathVariable Long[] productIds)
    {
        return toAjax(productService.deleteProductByProductIds(productIds));
    }
}
