package com.ruoyi.bsapi.to;

/**
 * 上传sku图片
 */
public class SkuUploadImgItemTo {

    private Long skuId;

    private String skuPic;

    public Long getSkuId() {
        return skuId;
    }

    public void setSkuId(Long skuId) {
        this.skuId = skuId;
    }

    public String getSkuPic() {
        return skuPic;
    }

    public void setSkuPic(String skuPic) {
        this.skuPic = skuPic;
    }

    @Override
    public String toString() {
        return "SkuUploadImgItemTo{" +
                "skuId=" + skuId +
                ", skuPic='" + skuPic + '\'' +
                '}';
    }
}
