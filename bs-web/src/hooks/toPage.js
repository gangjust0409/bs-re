export default function (router) {
    //跳转登录
    function toLogin() {
        console.log(router);
        router.push('/login');
    }

    //跳转注册
    function toRegister() {
        router.push('/register');
    }

    //跳转首页
    function toHome() {
        router.push('/home');
    }

    //跳转收藏
    function toCollect() {
        router.push('/my/collect')
    }

    //跳转我的足迹
    function toHistory() {
        router.push('/my/history')
    }

    //点击搜索
    function toSearch() {
        router.push('/search')
    }

    //点击详情
    function toDetail() {
        router.push('/detail')
    }
    //跳转支付成功页
    function toPaySuccess() {
        router.push('/pay/success')
    }

    //跳转支付失败页
    function toPayError() {
        router.push('/pay/error')
    }

    //跳转支付确认页
    function toPayConfirm() {
        router.push('/pay/result')
    }

    //跳转购物车
    function toCart() {
        router.push('/cart');
    }
}