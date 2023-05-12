<template>
  <footer class="menu_footer">
    <div class="row">
      <a href="">联系客服</a>
      <a href="">开放平台</a> <a href="">法律声明</a> <a href="">廉正举报</a>
      <span>Taobao.com版权所有 2003-现在</span>
      <a href="">增值电信业务经营许可证：浙B2-20070195</a>
      <a href="">浙公网安备 33010002000075号</a>
    </div>
    <div class="row sp">
      <a href="">阿里巴巴集团</a> <span>|</span> <a href="">淘宝网</a>
      <span>|</span> <a href="">天猫</a> <span>|</span> <a href="">聚划算</a>
      <span>|</span> <a href="">全球速卖通</a> <span>|</span>
      <a href="">阿里巴巴国际交易市场</a> <span>|</span><a href="">1688</a>
      <span>|</span> <a href="">阿里妈妈</a> <span>|</span><a href="">飞猪</a>
      <span>|</span> <a href="">阿里云计算</a> <span>|</span
      ><a href="">AliOS</a> <span>|</span> <a href="">阿里通信</a>
      <span>|</span> <a href="">高德</a> <span>|</span>
      <a href="">UC</a>
    </div>
    <div class="row">
      <a href="">友盟</a> <span>|</span> <a href="">虾米</a> <span>|</span
      ><a href="">钉钉</a> <span>|</span> <a href="">支付宝</a> <span>|</span>
      <a href="">达摩院</a>
    </div>
    <div class="row img">
      <a href="">
        <img src="@/assets/images/T1C3z7FudfXXcsE9Te-40-42.png" alt="" />
      </a>
      <a href="">
        <img
          src="@/assets/images/TB1HxCbreL2gK0jSZPhXXahvXXa-65-70.gif"
          alt=""
        />
      </a>
      <a href="">
        <img src="@/assets/images/T1VVv9FABeXXbtCInf-38-42.png" alt="" />
      </a>
    </div>
  </footer>
</template>

<script setup>
</script>

<style scoped lang="scss">
.menu_footer {
  margin: 30px 0;
  padding: 0 155px;
  .row {
    line-height: 2;
    &.sp {
      a {
        margin-right: 0px;
      }
    }
    &.img {
      display: flex;
      align-items: center;
      a {
        display: inline-block;
        width: 40px;
        height: 40px;
        border-radius: 50%;
        opacity: 1;
        img {
          width: 100%;
          height: 100%;
          //把图片变成灰度图片
          filter: grayscale(100%);
          -webkit-filter: grayscale(100%); //谷歌浏览器
          -moz-filter: grayscale(100%); //火狐浏览器
          -ms-filter: grayscale(100%); //IE核心浏览器
          -o-filter: grayscale(100%); //Oprea浏览器
          -webkit-transition: all 0.3s ease-in-out; //这是设置过渡时间效果，有一个缓冲时间
          -o-transition: all 0.3s ease-in-out;
          -moz-transition: all 0.3s ease-in-out;
          -ms-transition: all 0.3s ease-in-out;
          &:hover {
            filter: grayscale(0%);
            -webkit-filter: grayscale(0%); //谷歌浏览器
            -moz-filter: grayscale(0%); //火狐浏览器
            -ms-filter: grayscale(0%); //IE核心浏览器
            -o-filter: grayscale(0%); //Oprea浏览器
            -webkit-transition: all 0.3s ease-in; //这是设置过渡时间效果，有一个缓冲时间
            -o-transition: all 0.3s ease-in;
            -moz-transition: all 0.3s ease-in;
            -ms-transition: all 0.3s ease-in;
          }
        }
      }
    }
    a {
      color: #333;
      opacity: 0.5;
      margin-right: 20px;
      font-size: 12px;
      &:last-of-type {
        margin-right: 0px;
      }
      &:hover {
        opacity: 1;
      }
    }
    span {
      color: #333;
      opacity: 0.5;
      margin: 10px;
    }
  }
}
</style>