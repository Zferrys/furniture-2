<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="x-ua-compatible" content="ie=edge"/>
    <title>-家居网购</title>
    <!-- 移动端适配 -->
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no"/>
    <base href="<%=request.getContextPath()+"/"%>">
    <link rel="stylesheet" href="assets/css/vendor/vendor.min.css"/>
    <link rel="stylesheet" href="assets/css/plugins/plugins.min.css"/>
    <link rel="stylesheet" href="assets/css/style.min.css">
    <script type="text/javascript" src="script/jquery-3.6.0.min.js"></script>
</head>

<body>
<!-- Header Area start  -->
<div class="header section">
    <!-- Header Top  End -->
    <!-- Header Bottom  Start -->
    <div class="header-bottom d-none d-lg-block">
        <div class="container position-relative">
            <div class="row align-self-center">
                <!-- Header Logo Start -->
                <div class="col-auto align-self-center">
                    <div class="header-logo">
                        <a href="index.jsp"><img src="assets/images/logo/logo.png" alt="Site Logo"/></a>
                    </div>
                </div>
                <!-- Header Logo End -->
                <!-- Header Action Start -->
                <div class="col align-self-center">
                    <div class="header-actions">
                        <div class="header-bottom-set dropdown">
                            <a>欢迎: ${sessionScope.member.username}</a>
                        </div>
                        <div class="header-bottom-set dropdown">
                            <a href="orderServlet?action=orderManager">订单管理</a>
                        </div>
                        <div class="header-bottom-set dropdown">
                            <a href="member?action=logout">安全退出</a>
                        </div>
                    </div>
                </div>
                <!-- Header Action End -->
            </div>
        </div>
    </div>
    <!-- Header Bottom  End -->
    <!-- Header Bottom  Start 手机端的header -->
    <div class="header-bottom d-lg-none sticky-nav bg-white">
        <div class="container position-relative">
            <div class="row align-self-center">
                <!-- Header Action Start -->
                <div class="col align-self-center">
                    <div class="header-actions">
                        <div class="header-bottom-set dropdown">
                            <c:if test="${empty sessionScope.member && empty sessionScope.admin }">
                                <a href="views/member/login.jsp">请先登录进行购物</a>
                            </c:if>
                            <c:if test="${not empty sessionScope.member || not empty sessionScope.admin}">
                                <a>欢迎: ${sessionScope.member.username}${sessionScope.admin.name}</a>
                            </c:if>
                        </div>
                        <div class="header-bottom-set dropdown">
                            <a href="orderServlet?action=orderManager">订单管理</a>
                        </div>
                        <!-- Single Wedge Start -->
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
                        <%--<!-- Single Wedge End -->--%>
                        <%--<a href="#offcanvas-cart"--%>
                        <%--   class="header-action-btn header-action-btn-cart offcanvas-toggle pr-0">--%>
                        <%--    <i class="icon-handbag"> 购物车</i>--%>
                        <%--    <span class="header-action-num">88</span>--%>
                        <%--</a>--%>
                        <%--<a href="#offcanvas-mobile-menu"--%>
                        <%--   class="header-action-btn header-action-btn-menu offcanvas-toggle d-lg-none">--%>
                        <%--    <i class="icon-menu"></i>--%>
                        <%--</a>--%>
                    </div>
                </div>
                <!-- Header Action End -->
            </div>
        </div>
    </div>
    <!-- Main Menu Start -->
    <div style="width: 100%;height: 50px;background-color: black"></div>
    <!-- Main Menu End -->
</div>
<!-- Cart Area Start -->
<div class="cart-main-area pt-70px pb-100px">
    <div class="container">
        <h3 class="cart-page-title">订单管理</h3>
        <div class="row">
            <div class="col-lg-12 col-md-12 col-sm-12 col-12">
                <form action="#">
                    <div class="table-content table-responsive cart-table-content">
                        <table>
                            <thead>
                            <tr>
                                <th>订单</th>
                                <th>日期</th>
                                <th>金额</th>
                                <th>状态</th>
                                <th>详情</th>
                            </tr>
                            </thead>
                            <tbody>
                            <c:forEach items="${requestScope.orders}" var="order">
                                <tr>
                                    <td class="product-name">${order.id}</td>
                                    <td class="product-name">${order.createTime}</td>
                                    <td class="product-price-cart"><span class="amount">${order.price}</span></td>
                                    <td class="product-name">
                                        <a href="#">
                                            <c:choose>
                                                <c:when test="${order.status==0}">未发货</c:when>
                                                <c:when test="${order.status==1}">已发货</c:when>
                                                <c:when test="${order.status==2}">已结账</c:when>
                                                <c:otherwise>异常状态</c:otherwise>
                                            </c:choose>
                                        </a>
                                    </td>
                                    <td class="product-remove">
                                        <a href="orderServlet?action=orderItemDetail&orderId=${order.id}"><i
                                                class="icon-eye"></i></a>
                                    </td>
                                </tr>
                            </c:forEach>

                            </tbody>
                        </table>
                    </div>
                </form>
            </div>
        </div>
    </div>
</div>
<!-- Cart Area End -->

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
                        <p class="copy-text">Copyright &copy; 2021 ~</p>
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