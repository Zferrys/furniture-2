<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="x-ua-compatible" content="ie=edge"/>
    <title>-家居网购</title>
    <base href="<%=request.getContextPath()+"/"%>">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no"/>
    <link rel="stylesheet" href="assets/css/vendor/vendor.min.css"/>
    <link rel="stylesheet" href="assets/css/plugins/plugins.min.css"/>
    <link rel="stylesheet" href="assets/css/style.min.css"/>
    <script type="text/javascript" src="script/jquery-3.6.0.min.js"></script>

</head>
<body>
<!-- Header Area start  -->
<div class="header section">
    <!-- Header Top Message Start -->
    <!-- Header Top  End -->
    <!-- Header Bottom  Start -->
    <div class="header-bottom d-none d-lg-block">
        <div class="container position-relative">
            <div class="row align-self-center">
                <!-- Header Logo Start -->
                <div class="col-auto align-self-center">
                    <div class="header-logo">
                        <a href="index.html"><img src="assets/images/logo/logo.png" alt="Site Logo"/></a>
                    </div>
                </div>
                <!-- Header Logo End -->
                <!-- Header Action Start -->
                <div class="col align-self-center">
                    <div class="header-actions">
                        <div class="header_account_list">
                            <a href="javascript:void(0)" class="header-action-btn search-btn"><i
                                    class="icon-magnifier"></i></a>
                            <div class="dropdown_search">
                                <form class="action-form" action="customer" method="post">
                                    <input type="hidden" name="action" value="searchByName">
                                    <input class="form-control" placeholder="请输入查找的关键字" type="text"
                                           name="name">
                                    <button class="submit" type="submit"><i class="icon-magnifier"></i></button>
                                </form>
                            </div>
                        </div>
                        <div class="header-bottom-set dropdown">
                            <c:if test="${empty sessionScope.member && empty sessionScope.admin }">
                                <a>请先登录</a>
                            </c:if>
                            <c:if test="${not empty sessionScope.member || not empty sessionScope.admin}">
                                <a>欢迎: ${sessionScope.member.username}${sessionScope.admin.name}</a>
                            </c:if>
                        </div>
                        <div class="header-bottom-set dropdown">
                            <a href="customer">首页</a>
                        </div>
                        <div class="header-bottom-set dropdown">
                            <a href="orderServlet?action=orderManager">订单管理</a>
                        </div>
                        <c:if test="${not empty sessionScope.admin}">
                            <div class="header-bottom-set dropdown">
                                <a href="views/manage/manage_menu.jsp">后台管理</a>
                            </div>
                        </c:if>
                        <c:if test="${not empty sessionScope.member }">
                            <div class="header-bottom-set dropdown">
                                <a href="member?action=logout">安全退出</a>
                            </div>
                        </c:if>
                        <c:if test="${not empty sessionScope.admin }">
                            <div class="header-bottom-set dropdown">
                                <a href="manage/admin?action=logout">安全退出</a>
                            </div>
                        </c:if>
                        <a href="views/cart/cart.jsp"
                           class="header-action-btn header-action-btn-cart pr-0">
                            <i class="icon-handbag"> 购物车</i>
                            <span class="header-action-num">${sessionScope.cart.totalCount}</span>
                        </a>
                    </div>
                </div>
                <!-- Header Action End -->
            </div>
        </div>
    </div>
    <!-- Header Bottom  Start 手机端的header -->
    <div class="header-bottom d-lg-none sticky-nav bg-white">
        <div class="container position-relative">
            <div class="row align-self-center">
                <!-- Header Logo Start -->
                <div class="col-auto align-self-center">
                    <div class="header-logo">
                        <a href="index.jsp"><img width="280px" src="assets/images/logo/logo.png"
                                                 alt="Site Logo"/></a>
                    </div>
                </div>
                <!-- Header Logo End -->
            </div>
        </div>
    </div>
    <!-- Main Menu Start -->
    <div style="width: 100%;height: 50px;background-color: black"></div>
    <!-- Main Menu End -->
</div>
<!-- Header Area End  -->
<!-- Checkout Success Area Start -->
<div class="checkout-main-area pt-70px pb-100px">
    <div class="container">
        <div class="row">
            <div class="col-lg-12 col-md-12 col-sm-12 col-12">
                <div class="checkout-wrapper">
                    <!-- Success Icon -->
                    <div class="checkout-success-icon" style="text-align: center; margin-bottom: 30px;">
                        <i class="icon-check" style="font-size: 60px; color: #28a745;"></i>
                    </div>
                    <div class="row">
                        <div class="col-lg-8 col-md-8 ml-auto mr-auto">
                            <div class="alert alert-success" style="background: #d4edda; border: 1px solid #c3e6cb; border-radius: 5px; padding: 20px; margin-bottom: 30px;">
                                <h3 style="color: #155724; margin-bottom: 15px;">
                                    <i class="icon-check-circle"></i> 订单提交成功！
                                </h3>
                                <p style="color: #155724; font-size: 16px;">
                                    您的订单号是：<strong style="font-size: 18px;">${requestScope.orderId}</strong>
                                </p>
                                <p style="color: #155724; font-size: 14px; margin-top: 10px;">
                                    我们已收到您的订单，将尽快为您处理。您可以在订单管理中查看订单状态。
                                </p>
                            </div>
                            
                            <!-- Order Info -->
                            <div class="checkout-order-info" style="background: #f8f9fa; border-radius: 5px; padding: 20px; margin-bottom: 20px;">
                                <h4 style="margin-bottom: 15px; color: #333;">订单信息</h4>
                                <div class="row">
                                    <div class="col-md-6">
                                        <p style="margin-bottom: 10px;">
                                            <strong>订单编号：</strong>${requestScope.orderId}
                                        </p>
                                        <p style="margin-bottom: 10px;">
                                            <strong>下单时间：</strong><span id="order-time"></span>
                                        </p>
                                    </div>
                                    <div class="col-md-6">
                                        <p style="margin-bottom: 10px;">
                                            <strong>订单状态：</strong><span style="color: orange;">未发货</span>
                                        </p>
                                        <p style="margin-bottom: 10px;">
                                            <strong>支付方式：</strong>货到付款
                                        </p>
                                    </div>
                                </div>
                            </div>
                            
                            <!-- Action Buttons -->
                            <div class="checkout-action-buttons" style="text-align: center; margin-top: 30px;">
                                <a href="orderServlet?action=orderManager" 
                                   style="display: inline-block; padding: 12px 30px; background: #007bff; color: white; text-decoration: none; border-radius: 5px; margin: 0 10px;">
                                    <i class="icon-list"></i> 查看订单
                                </a>
                                <c:choose>
                                    <c:when test="${not empty sessionScope.admin}">
                                        <a href="views/manage/manage_menu.jsp" 
                                           style="display: inline-block; padding: 12px 30px; background: #28a745; color: white; text-decoration: none; border-radius: 5px; margin: 0 10px;">
                                            <i class="icon-home"></i> 返回后台管理
                                        </a>
                                    </c:when>
                                    <c:otherwise>
                                        <a href="customer" 
                                           style="display: inline-block; padding: 12px 30px; background: #28a745; color: white; text-decoration: none; border-radius: 5px; margin: 0 10px;">
                                            <i class="icon-handbag"></i> 继续购物
                                        </a>
                                    </c:otherwise>
                                </c:choose>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<!-- Checkout Success Area End -->

<script type="text/javascript">
    // 显示下单时间
    document.getElementById('order-time').textContent = new Date().toLocaleString('zh-CN');
</script>

<!-- Footer Area Start -->
<div class="footer-area">
    <div class="footer-container">
        <div class="footer-top">
            <div class="container">
                <div class="row">
                    <!-- Start single blog -->
                    <!-- End single blog -->
                    <!-- Start single blog -->
                    <div class="col-md-6 col-sm-6 col-lg-3 mb-md-30px mb-lm-30px" data-aos="fade-up"
                         data-aos-delay="400">
                        <div class="single-wedge">
                            <h4 class="footer-herading">信息</h4>
                            <div class="footer-links">
                                <div class="footer-row">
                                    <ul class="align-items-center">
                                        <li class="li"><a class="single-link" href="about.html">关于我们</a></li>
                                        <li class="li"><a class="single-link" href="#">交货信息</a></li>
                                        <li class="li"><a class="single-link" href="privacy-policy.html">隐私与政策</a>
                                        </li>
                                        <li class="li"><a class="single-link" href="#">条款和条件</a></li>
                                        <li class="li"><a class="single-link" href="#">制造</a></li>
                                    </ul>
                                </div>
                            </div>
                        </div>
                    </div>
                    <!-- End single blog -->
                    <!-- Start single blog -->
                    <div class="col-md-6 col-lg-2 col-sm-6 mb-lm-30px" data-aos="fade-up" data-aos-delay="600">
                        <div class="single-wedge">
                            <h4 class="footer-herading">我的账号</h4>
                            <div class="footer-links">
                                <div class="footer-row">
                                    <ul class="align-items-center">
                                        <li class="li"><a class="single-link" href="my-account.html">我的账号</a>
                                        </li>
                                        <li class="li"><a class="single-link" href="cart.html">我的购物车</a></li>
                                        <li class="li"><a class="single-link" href="login.html">登录</a></li>
                                        <li class="li"><a class="single-link" href="wishlist.html">感兴趣的</a></li>
                                        <li class="li"><a class="single-link" href="checkout.html">结账</a></li>
                                    </ul>
                                </div>
                            </div>
                        </div>
                    </div>
                    <!-- End single blog -->
                    <!-- Start single blog -->
                    <div class="col-md-6 col-lg-3" data-aos="fade-up" data-aos-delay="800">

                    </div>
                    <!-- End single blog -->
                </div>
            </div>
        </div>
        <div class="footer-bottom">
            <div class="container">
                <div class="row flex-sm-row-reverse">
                    <div class="col-md-6 text-right">
                        <div class="payment-link">
                            <img src="#" alt="">
                        </div>
                    </div>
                    <div class="col-md-6 text-left">
                        <p class="copy-text">Copyright &copy; 2021 </p>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<!-- Footer Area End -->
<script src="assets/js/vendor/vendor.min.js"></script>
<script src="assets/js/plugins/plugins.min.js"></script>
<!-- Main Js -->
<script src="assets/js/main.js"></script>
</body>
</html>