package com.ruoyi.web.controller.bsapi;

import com.ruoyi.bsapi.service.IProductService;
import com.ruoyi.bsapi.service.IShopService;
import com.ruoyi.bsapi.service.ISwiperService;
import com.ruoyi.common.core.domain.AjaxResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;

import java.util.List;

import static com.ruoyi.common.core.domain.AjaxResult.success;

/**
 * 上传文件的控制
 */
@RestController
@RequestMapping("/product/upload")
public class UploadProductImgController {

    @Resource
    private IProductService productService;

    @Resource
    private IShopService shopService;

    @Resource
    private ISwiperService swiperService;

    //上传商品的封面
    @PostMapping("/product/pic")
    public AjaxResult uploadProductPic(@RequestPart("file") MultipartFile file) {
        //上传商品封面
        int state = productService.uploadProductPic(file);
        return success(state);
    }

    //上传商品时，新增详情图
    @PostMapping("/detail/imgs")
    public AjaxResult uploadProductDetailImgs(@RequestPart("file") MultipartFile files) {
        int state = productService.uploadProductDetailImgs(files);
        return success(state);
    }
    //上传商品时，新增缩略图
    @PostMapping("/thumbnai/imgs")
    public AjaxResult uploadProductThumbnailUrl(@RequestPart("file") MultipartFile file) {
        int state  = productService.uploadProductThumbnailUrl(file);
        return success(state);
    }
    //上传商品时，新增sku封面图
    @PostMapping("/sku/pic")
    public AjaxResult uploadProductSkuPicUrl(
            @RequestPart("file") MultipartFile file) {
        List<String> imgs = productService.uploadProductSkuPicUrl(file);
        return success(imgs);
    }

    //刷新 sku 图片信息
    @GetMapping("/load/sku/img")
    public AjaxResult loadSkuImgInfo(){
        List<String> imgs = productService.loadSkuImgInfo();
        return AjaxResult.success(imgs);
    }

    //上传店铺图标
    @PostMapping("/shop/icon")
    public AjaxResult uploadShopIcon(@RequestPart MultipartFile file) {
        shopService.uploadShopIcon(file);
        return success();
    }

    //上传轮播图
    @PostMapping("/swiper")
    public AjaxResult uploadSwiper(@RequestPart MultipartFile file) {
        swiperService.uploadSwiper(file);
        return success();
    }

}
