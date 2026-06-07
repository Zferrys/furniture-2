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
    <script type="text/javascript">
        function updateStatus(orderId, status) {
            var statusText = status == 1 ? '发货' : '完成';
            if (!confirm('确定要标记订单【' + orderId + '】为' + statusText + '状态吗？')) {
                return;
            }
            location.href = 'orderServlet?action=updateOrderStatus&orderId=' + orderId + '&status=' + status;
        }
    </script>
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
                            <c:choose>
                                <c:when test="${not empty requestScope.page.items}">
                                    <c:forEach items="${requestScope.page.items}" var="order">
                                        <tr>
                                            <td class="product-name">${order.id}</td>
                                            <td class="product-name">${order.createTime}</td>
                                            <td class="product-price-cart"><span class="amount">¥${order.price}</span></td>
                                            <td class="product-name">
                                                <c:choose>
                                                    <c:when test="${order.status==0}"><span style="color: orange;">未发货</span></c:when>
                                                    <c:when test="${order.status==1}"><span style="color: blue;">已发货</span></c:when>
                                                    <c:when test="${order.status==2}"><span style="color: green;">已结账</span></c:when>
                                                    <c:otherwise><span style="color: red;">异常状态</span></c:otherwise>
                                                </c:choose>
                                            </td>
                                            <td class="product-remove">
                                                <a href="orderServlet?action=orderItemDetail&orderId=${order.id}" title="查看详情"><i
                                                        class="icon-eye"></i> 详情</a>
                                            </td>
                                            <c:if test="${not empty sessionScope.admin}">
                                                <td class="product-remove">
                                                    <c:if test="${order.status == 0}">
                                                        <a href="javascript:void(0);" onclick="updateStatus('${order.id}', 1)" title="标记发货">
                                                            <i class="icon-truck"></i> 发货
                                                        </a>
                                                    </c:if>
                                                    <c:if test="${order.status == 1}">
                                                        <a href="javascript:void(0);" onclick="updateStatus('${order.id}', 2)" title="标记完成">
                                                            <i class="icon-check"></i> 完成
                                                        </a>
                                                    </c:if>
                                                    <c:if test="${order.status == 2}">
                                                        <span style="color: gray;">已完成</span>
                                                    </c:if>
                                                </td>
                                            </c:if>
                                        </tr>
                                    </c:forEach>
                                </c:when>
                                <c:otherwise>
                                    <tr>
                                        <td colspan="${not empty sessionScope.admin ? 6 : 5}" style="text-align: center; padding: 40px;">
                                            暂无订单记录
                                        </td>
                                    </tr>
                                </c:otherwise>
                            </c:choose>

                            </tbody>
                        </table>
                    </div>
                </form>
            </div>
        </div>
        <!--  Pagination Area Start -->
        <c:if test="${not empty requestScope.page.items}">
        <div class="pro-pagination-style text-center mb-md-30px mb-lm-30px mt-6" data-aos="fade-up">
            <ul>
                <li><a href="${requestScope.page.url}pageNo=1">首页</a></li>
                <c:choose>
                    <c:when test="${requestScope.page.pageTotalCount<=5}">
                        <c:set var="begin" value="1"/>
                        <c:set var="end" value="${requestScope.page.pageTotalCount}"/>
                    </c:when>
                    <c:when test="${requestScope.page.pageTotalCount>5}">
                        <c:choose>
                            <c:when test="${requestScope.page.pageNo<=3}">
                                <c:set var="begin" value="1"/>
                                <c:set var="end" value="5"/>
                            </c:when>
                            <c:when test="${requestScope.page.pageNo>requestScope.page.pageTotalCount-3}">
                                <c:set var="begin" value="${requestScope.page.pageTotalCount-4}"/>
                                <c:set var="end" value="${requestScope.page.pageTotalCount}"/>
                            </c:when>
                            <c:otherwise>
                                <c:set var="begin" value="${requestScope.page.pageNo-2}"/>
                                <c:set var="end" value="${requestScope.page.pageNo+2}"/>
                            </c:otherwise>
                        </c:choose>
                    </c:when>
                </c:choose>
                <c:if test="${requestScope.page.pageNo > 1}">
                    <li><a href="${requestScope.page.url}pageNo=${requestScope.page.pageNo-1}">上一页</a></li>
                </c:if>
                <c:forEach begin="${begin}" end="${end}" var="i">
                    <c:if test="${i == requestScope.page.pageNo}">
                        <li><a href="${requestScope.page.url}pageNo=${i}" class="active">${i}</a></li>
                    </c:if>
                    <c:if test="${i != requestScope.page.pageNo}">
                        <li><a href="${requestScope.page.url}pageNo=${i}">${i}</a></li>
                    </c:if>
                </c:forEach>
                <c:if test="${requestScope.page.pageNo < requestScope.page.pageTotalCount}">
                    <li><a href="${requestScope.page.url}pageNo=${requestScope.page.pageNo+1}">下一页</a></li>
                </c:if>
                <li><a href="${requestScope.page.url}pageNo=${requestScope.page.pageTotalCount}">末页</a></li>
                <li><a style="white-space: nowrap;">共${requestScope.page.pageTotalCount}页(${requestScope.page.totalRow}条)</a></li>
            </ul>
        </div>
        </c:if>
        <!--  Pagination Area End -->
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