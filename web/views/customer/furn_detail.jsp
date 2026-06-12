<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="x-ua-compatible" content="ie=edge"/>
    <title><c:out value="${requestScope.furn.name}"/> - 商品详情</title>
    <base href="<%= request.getContextPath()+"/"%>">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no"/>
    <link rel="stylesheet" href="assets/css/vendor/vendor.min.css"/>
    <link rel="stylesheet" href="assets/css/plugins/plugins.min.css"/>
    <link rel="stylesheet" href="assets/css/style.min.css">
    <script type="text/javascript" src="script/jquery-3.6.0.min.js"></script>
    <style>
        .detail-page { padding-top: 40px; padding-bottom: 80px; background: #fff; }
        .detail-breadcrumb { margin-bottom: 30px; }
        .detail-breadcrumb li { display: inline-block; font-size: 14px; color: #999; }
        .detail-breadcrumb li + li::before { content: "›"; margin: 0 8px; color: #ccc; }
        .detail-breadcrumb li.active { color: #333; }
        .detail-breadcrumb a { color: #007bff; text-decoration: none; }
        .detail-breadcrumb a:hover { text-decoration: underline; }

        .detail-card { background: #fff; border-radius: 12px; overflow: hidden; box-shadow: 0 2px 20px rgba(0,0,0,0.08); border: 1px solid #f0f0f0; }
        .detail-img-wrap { padding: 24px; background: #fafafa; text-align: center; }
        .detail-img-wrap img { max-width: 100%; max-height: 480px; object-fit: contain; border-radius: 10px; }

        .detail-info { padding: 36px 36px 28px; }
        .detail-title { font-size: 26px; font-weight: 700; color: #222; margin-bottom: 18px; line-height: 1.3; }
        .detail-price-row { margin-bottom: 22px; padding: 16px 20px; background: linear-gradient(135deg, #fff5f5, #ffeaea); border-radius: 8px; border-left: 4px solid #e74c3c; }
        .detail-price-label { font-size: 15px; color: #666; }
        .detail-price-val { font-size: 34px; color: #e74c3c; font-weight: 800; margin-left: 6px; }

        .detail-specs { background: #fafbfc; border-radius: 10px; padding: 20px 24px; margin-bottom: 26px; }
        .spec-row { display: flex; align-items: center; padding: 11px 0; }
        .spec-row + .spec-row { border-top: 1px solid #eee; }
        .spec-key { width: 56px; font-size: 14px; color: #888; flex-shrink: 0; }
        .spec-val { font-size: 15px; color: #333; font-weight: 500; }
        .stock-ok { color: #27ae60; font-weight: 600; }
        .stock-warn { color: #f39c12; font-weight: 600; }
        .stock-none { color: #e74c3c; font-weight: 600; }

        .btn-detail-primary {
            background: linear-gradient(135deg, #ff6b6b, #ee5a52);
            color: #fff !important;
            border: none;
            padding: 13px 38px;
            border-radius: 8px;
            font-size: 15px;
            font-weight: 600;
            cursor: pointer;
            transition: all 0.25s;
            text-decoration: none;
            display: inline-flex; align-items: center; gap: 7px;
        }
        .btn-detail-primary:hover { transform: translateY(-2px); box-shadow: 0 6px 20px rgba(238,90,82,0.35); }
        .btn-detail-primary:disabled { background: #ccc; cursor: not-allowed; transform: none; box-shadow: none; }
        .btn-detail-secondary {
            background: #333; color: #fff !important;
            border: none; padding: 13px 28px; border-radius: 8px;
            font-size: 15px; font-weight: 500; text-decoration: none;
            display: inline-flex; align-items: center; gap: 7px;
            transition: all 0.25s;
        }
        .btn-detail-secondary:hover { background: #555; transform: translateY(-2px); }

        .service-bar { display: flex; gap: 32px; flex-wrap: wrap; margin-top: 28px; padding: 18px 22px; background: #f9fdfa; border: 1px solid #e8f5e9; border-radius: 10px; }
        .service-item { display: flex; align-items: center; gap: 8px; font-size: 13px; color: #555; }
        .service-item i { color: #27ae60; font-size: 17px; }
    </style>
    <script type="text/javascript">
        $(function () {
            $("button.add-to-cart").click(function () {
                var furnId = $(this).attr("furnId");
                $.ajax({
                    url: "cartServlet",
                    type: "POST",
                    data: {action: "addCartItemByAjax", id: furnId, csrfToken: "${sessionScope.csrfToken}"},
                    dataType: "json",
                    success: function (data) {
                        if (data.url === undefined) {
                            $(".header-action-num").text(data.totalCount);
                        } else {
                            location.href = data.url;
                        }
                    },
                    error: function () {
                        location.href = "views/member/login.jsp";
                    }
                });
            });
        });
    </script>
</head>

<body>
<!-- Header Area start -->
<div class="header section">
    <div class="header-bottom d-none d-lg-block">
        <div class="container position-relative">
            <div class="row align-self-center">
                <div class="col-auto align-self-center">
                    <div class="header-logo">
                        <a href="customer?pageNo=1"><img src="assets/images/logo/logo.png" alt="Site Logo"/></a>
                    </div>
                </div>
                <div class="col align-self-center">
                    <div class="header-actions">
                        <div class="header_account_list">
                            <a href="javascript:void(0)" class="header-action-btn search-btn"><i class="icon-magnifier"></i></a>
                            <div class="dropdown_search">
                                <form class="action-form" action="customer" method="post">
                                    <input type="hidden" name="action" value="searchByName">
                                    <input type="hidden" name="csrfToken" value="${sessionScope.csrfToken}"/>
                                    <input class="form-control" placeholder="请输入查找的关键字" type="text" name="name">
                                    <button class="submit" type="submit"><i class="icon-magnifier"></i></button>
                                </form>
                            </div>
                        </div>
                        <div class="header-bottom-set dropdown">
                            <c:if test="${empty sessionScope.member && empty sessionScope.admin}">
                                <a href="views/member/login.jsp">登录|注册</a>
                            </c:if>
                            <c:if test="${not empty sessionScope.member || not empty sessionScope.admin}">
                                <a>欢迎: <c:out value="${sessionScope.member.username}"/><c:out value="${sessionScope.admin.name}"/></a>
                            </c:if>
                        </div>
                        <div class="header-bottom-set dropdown"><a href="customer?pageNo=1">首页</a></div>
                        <div class="header-bottom-set dropdown">
                            <c:if test="${empty sessionScope.member && empty sessionScope.admin}">
                                <a href="views/manage/manage_login.jsp">后台管理</a>
                            </c:if>
                            <c:if test="${not empty sessionScope.member || not empty sessionScope.admin}">
                                <a href="orderServlet?action=orderManager">订单管理</a>
                            </c:if>
                        </div>
                        <c:if test="${not empty sessionScope.admin}">
                            <div class="header-bottom-set dropdown">
                                <a href="views/manage/manage_menu.jsp">后台管理</a>
                            </div>
                        </c:if>
                        <c:if test="${not empty sessionScope.member}">
                            <div class="header-bottom-set dropdown"><a href="member?action=logout">安全退出</a></div>
                        </c:if>
                        <c:if test="${not empty sessionScope.admin}">
                            <div class="header-bottom-set dropdown"><a href="manage/admin?action=logout">安全退出</a></div>
                        </c:if>
                        <a href="views/cart/cart.jsp" class="header-action-btn header-action-btn-cart pr-0">
                            <i class="icon-handbag"> 购物车</i><span class="header-action-num">${sessionScope.cart.totalCount}</span>
                        </a>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <div class="header-bottom d-lg-none sticky-nav bg-white">
        <div class="container position-relative">
            <div class="row align-self-center">
                <div class="col-auto align-self-center">
                    <div class="header-logo">
                        <a href="customer?pageNo=1"><img width="280px" src="assets/images/logo/logo.png" alt="Site Logo"/></a>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <div style="width:100%;height:50px;background-color:black"></div>
</div>

<c:set var="furn" value="${requestScope.furn}" scope="page"/>

<!-- Product Detail -->
<div class="detail-page">
    <div class="container">
        <!-- Breadcrumb -->
        <ul class="detail-breadcrumb">
            <li><a href="customer?pageNo=1">首页</a></li>
            <li class="active"><c:out value="${furn.name}"/></li>
        </ul>

        <div class="row">
            <!-- Image -->
            <div class="col-lg-6 col-md-6 mb-30px">
                <div class="detail-card detail-img-wrap">
                    <img src="<c:out value="${furn.imgPath}"/>" alt="<c:out value="${furn.name}"/>"/>
                </div>
            </div>

            <!-- Info -->
            <div class="col-lg-6 col-md-6">
                <div class="detail-card detail-info">
                    <h2 class="detail-title"><c:out value="${furn.name}"/></h2>

                    <div class="detail-price-row">
                        <span class="detail-price-label">售价</span>
                        <span class="detail-price-val">￥<c:out value="${furn.price}"/></span>
                    </div>

                    <div class="detail-specs">
                        <div class="spec-row">
                            <span class="spec-key">厂商</span>
                            <span class="spec-val"><c:out value="${furn.market}"/></span>
                        </div>
                        <div class="spec-row">
                            <span class="spec-key">销量</span>
                            <span class="spec-val" style="color:#e74c3c;font-weight:600"><c:out value="${furn.sales}"/> 件</span>
                        </div>
                        <div class="spec-row">
                            <span class="spec-key">库存</span>
                            <c:choose>
                                <c:when test="${furn.store > 10}"><span class="spec-val stock-ok"><c:out value="${furn.store}"/> 件（有货）</span></c:when>
                                <c:when test="${furn.store > 0}"><span class="spec-val stock-warn"><c:out value="${furn.store}"/> 件（库存紧张）</span></c:when>
                                <c:otherwise><span class="spec-val stock-none">已售罄</span></c:otherwise>
                            </c:choose>
                        </div>
                    </div>

                    <!-- Buttons -->
                    <div style="display:flex;gap:14px;align-items:center;flex-wrap:wrap;">
                        <c:choose>
                            <c:when test="${furn.store <= 0}">
                                <button class="btn-detail-primary" disabled><i class="icon-handbag"></i> 已售罄</button>
                            </c:when>
                            <c:otherwise>
                                <button furnId="${furn.id}" class="add-to-cart btn-detail-primary"><i class="icon-handbag"></i> 加入购物车</button>
                            </c:otherwise>
                        </c:choose>
                        <a href="customer?pageNo=1" class="btn-detail-secondary"><i class="icon-arrow-left"></i> 返回列表</a>
                    </div>

                    <!-- Service Bar -->
                    <div class="service-bar">
                        <div class="service-item"><i class="icon-refresh"></i> 30天退换货</div>
                        <div class="service-item"><i class="icon-lock"></i> 安全支付</div>
                        <div class="service-item"><i class="icon-plane"></i> 全国包邮</div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

<!-- Footer -->
<div class="footer-area">
    <div class="footer-container">
        <div class="footer-top">
            <div class="container">
                <div class="row">
                    <div class="col-md-6 col-sm-6 col-lg-3 mb-md-30px mb-lm-30px" data-aos="fade-up" data-aos-delay="400">
                        <div class="single-wedge">
                            <h4 class="footer-hearding">信息</h4>
                            <div class="footer-links">
                                <div class="footer-row">
                                    <ul class="align-items-center">
                                        <li class="li"><a class="single-link" href="#">关于我们</a></li>
                                        <li class="li"><a class="single-link" href="#">交货信息</a></li>
                                        <li class="li"><a class="single-link" href="#">隐私与政策</a></li>
                                        <li class="li"><a class="single-link" href="#">条款和条件</a></li>
                                    </ul>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="col-md-6 col-lg-2 col-sm-6 mb-lm-30px" data-aos="fade-up" data-aos-delay="600">
                        <div class="single-wedge">
                            <h4 class="footer-herading">我的账号</h4>
                            <div class="footer-links">
                                <div class="footer-row">
                                    <ul class="align-items-center">
                                        <li class="li"><a class="single-link" href="#">我的账号</a></li>
                                        <li class="li"><a class="single-link" href="#">购物车</a></li>
                                        <li class="li"><a class="single-link" href="#">登录</a></li>
                                    </ul>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <div class="footer-bottom">
            <div class="container">
                <div class="row flex-sm-row-reverse">
                    <div class="col-md-6 text-right"><div class="payment-link"><img src="#" alt=""></div></div>
                    <div class="col-md-6 text-left"><p class="copy-text">Copyright &copy; 2021 ~</p></div>
                </div>
            </div>
        </div>
    </div>
</div>

<script src="assets/js/vendor/vendor.min.js"></script>
<script src="assets/js/plugins/plugins.min.js"></script>
<script src="assets/js/main.js"></script>
</body>
</html>
