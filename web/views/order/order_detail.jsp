<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <meta http-equiv="x-ua-compatible" content="ie=edge"/>
    <title>-家居网购</title>
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
                                <a>欢迎: <c:out value="${sessionScope.member.username}"/><c:out value="${sessionScope.admin.name}"/></a>
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
                        <a href="index.html"><img width="280px" src="assets/images/logo/logo.png"
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

<!-- OffCanvas Cart Start -->

<!-- OffCanvas Cart End -->

<!-- OffCanvas Menu Start -->

<!-- OffCanvas Menu End -->


<!-- breadcrumb-area start -->


<!-- breadcrumb-area end -->

<!-- Cart Area Start -->
<div class="cart-main-area pt-100px pb-100px">
    <div class="container">
        <!-- Order Info Header -->
        <h3 class="cart-page-title" style="margin-bottom: 20px;">订单详情</h3>
        
        <c:if test="${not empty requestScope.order}">
        <div class="row" style="margin-bottom: 20px;">
            <div class="col-lg-12">
                <div class="alert alert-info" style="background: #d1ecf1; border: 1px solid #bee5eb; border-radius: 5px; padding: 15px;">
                    <div class="row">
                        <div class="col-md-3">
                            <strong>订单编号：</strong><c:out value="${requestScope.order.id}"/>
                        </div>
                        <div class="col-md-3">
                            <strong>下单时间：</strong><fmt:formatDate value="${requestScope.order.createTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
                        </div>
                        <div class="col-md-3">
                            <strong>订单状态：</strong>
                            <c:choose>
                                <c:when test="${requestScope.order.status==0}"><span style="color: orange;">未发货</span></c:when>
                                <c:when test="${requestScope.order.status==1}"><span style="color: blue;">已发货</span></c:when>
                                <c:when test="${requestScope.order.status==2}"><span style="color: green;">已结账</span></c:when>
                                <c:otherwise><span style="color: red;">异常状态</span></c:otherwise>
                            </c:choose>
                        </div>
                        <div class="col-md-3">
                            <strong>订单总价：</strong><span style="color: red; font-size: 18px;">¥<c:out value="${requestScope.totalPrice}"/></span>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        </c:if>
        
        <div class="row">
            <div class="col-lg-12 col-md-12 col-sm-12 col-12">
                <form action="#">
                    <div class="table-content table-responsive cart-table-content">
                        <table>
                            <thead>
                            <tr>
                                <th>商品名称</th>
                                <th>单价</th>
                                <th>数量</th>
                                <th>金额</th>
                            </tr>
                            </thead>
                            <tbody>
                            <c:if test="${not empty requestScope.orderItems}">
                                <c:forEach var="orderItem" items="${requestScope.orderItems}">
                                <tr>
                                    <td class="product-name"><a href="#"><c:out value="${orderItem.name}"/></a></td>
                                    <td class="product-price-cart"><span class="amount">¥${orderItem.price}</span></td>
                                    <td class="product-quantity">${orderItem.count} 件</td>
                                    <td class="product-subtotal"><span class="amount">¥${orderItem.totalPrice}</span></td>
                                </tr>
                                </c:forEach>
                            </c:if>
                            </tbody>
                            <tfoot>
                            <tr>
                                <td colspan="3" style="text-align: right; font-weight: bold; padding-right: 20px;">
                                    共 <c:out value="${requestScope.totalCount}"/> 件商品，总计：
                                </td>
                                <td style="text-align: right; font-size: 18px; color: red; font-weight: bold;">
                                    ¥<c:out value="${requestScope.totalPrice}"/>
                                </td>
                            </tr>
                            </tfoot>
                        </table>
                    </div>
                    <div class="row">
                        <div class="col-lg-12">
                            <div class="cart-shiping-update-wrapper" style="text-align: center;">
                                <c:choose>
                                    <c:when test="${not empty sessionScope.admin}">
                                        <a href="views/manage/manage_menu.jsp" style="display: inline-block; padding: 12px 30px; background: #007bff; color: white; text-decoration: none; border-radius: 5px; margin: 0 10px;">
                                            <i class="icon-home"></i> 返回后台管理
                                        </a>
                                    </c:when>
                                    <c:otherwise>
                                        <a href="customer" style="display: inline-block; padding: 12px 30px; background: #007bff; color: white; text-decoration: none; border-radius: 5px; margin: 0 10px;">
                                            <i class="icon-handbag"></i> 继续购物
                                        </a>
                                    </c:otherwise>
                                </c:choose>
                                <a href="orderServlet?action=orderManager" style="display: inline-block; padding: 12px 30px; background: #6c757d; color: white; text-decoration: none; border-radius: 5px; margin: 0 10px;">
                                    <i class="icon-list"></i> 返回订单列表
                                </a>
                            </div>
                        </div>
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
                                        <li class="li"><a class="single-link" href="privacy-policy.html">隐私与政策</a></li>
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