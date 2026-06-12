<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="x-ua-compatible" content="ie=edge"/>
    <title>-家居网购</title>
    <!-- 移动端适配 -->
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no"/>
    <base href="<%= request.getContextPath()+"/"%>">
    <link rel="stylesheet" href="assets/css/vendor/vendor.min.css"/>
    <link rel="stylesheet" href="assets/css/plugins/plugins.min.css"/>
    <link rel="stylesheet" href="assets/css/style.min.css">
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
                        <a href="index.html"><img src="assets/images/logo/logo.png" alt="Site Logo"/></a>
                    </div>
                </div>
                <!-- Header Logo End -->

                <!-- Header Action Start -->
                <div class="col align-self-center">
                    <div class="header-actions">
                        <!-- Single Wedge Start -->
                        <div class="header-bottom-set dropdown">
                            <a href="<%=request.getContextPath()+"/manage/furnManage?action=page&pageNo=1"%>">家居管理</a>
                        </div>
                        <div class="header-bottom-set dropdown">
                            <a href="orderServlet?action=orderManager">订单管理</a>
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
                <!-- Header Logo Start -->
                <div class="col-auto align-self-center">
                    <div class="header-logo">
                        <a href="index.html"><img width="280px" src="assets/images/logo/logo.png" alt="Site Logo"/></a>
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
<!-- Cart Area Start -->
<div class="cart-main-area pt-100px pb-100px">
    <div class="container">
        <h3 class="cart-page-title" style="margin-bottom: 30px;">家居后台管理控制台</h3>
        
        <!-- Admin Info -->
        <div class="row" style="margin-bottom: 30px;">
            <div class="col-lg-12">
                <div class="alert alert-info" style="background: #d1ecf1; border: 1px solid #bee5eb; border-radius: 5px; padding: 15px;">
                    <div class="row">
                        <div class="col-md-6">
                            <strong><i class="icon-user"></i> 欢迎管理员：</strong><c:out value="${sessionScope.admin.name}"/>
                        </div>
                        <div class="col-md-6" style="text-align: right;">
                            <a href="manage/admin?action=logout" style="color: #721c24; text-decoration: none; font-weight: bold;">
                                <i class="icon-off"></i> 退出登录
                            </a>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        
        <div class="row">
            <!-- 家居管理 -->
            <div class="col-lg-4 col-md-6 mb-30px">
                <div class="card" style="border: 1px solid #ddd; border-radius: 8px; padding: 30px; text-align: center; height: 100%;">
                    <div class="card-body">
                        <i class="icon-handbag" style="font-size: 50px; color: #007bff; margin-bottom: 15px;"></i>
                        <h4 style="margin-bottom: 15px;">家居管理</h4>
                        <p style="margin-bottom: 20px; color: #666;">添加、修改、删除家居商品</p>
                        <a href="<%=request.getContextPath()%>/manage/furnManage?action=page&pageNo=1" 
                           class="btn btn-primary" style="text-decoration: none;">
                            进入管理
                        </a>
                    </div>
                </div>
            </div>
            
            <!-- 订单管理 -->
            <div class="col-lg-4 col-md-6 mb-30px">
                <div class="card" style="border: 1px solid #ddd; border-radius: 8px; padding: 30px; text-align: center; height: 100%;">
                    <div class="card-body">
                        <i class="icon-list" style="font-size: 50px; color: #28a745; margin-bottom: 15px;"></i>
                        <h4 style="margin-bottom: 15px;">订单管理</h4>
                        <p style="margin-bottom: 20px; color: #666;">查看所有订单，处理发货</p>
                        <a href="<%=request.getContextPath()%>/orderServlet?action=orderManager" 
                           class="btn btn-success" style="text-decoration: none;">
                            进入管理
                        </a>
                    </div>
                </div>
            </div>
            
            <!-- 返回首页 -->
            <div class="col-lg-4 col-md-6 mb-30px">
                <div class="card" style="border: 1px solid #ddd; border-radius: 8px; padding: 30px; text-align: center; height: 100%;">
                    <div class="card-body">
                        <i class="icon-home" style="font-size: 50px; color: #ffc107; margin-bottom: 15px;"></i>
                        <h4 style="margin-bottom: 15px;">返回首页</h4>
                        <p style="margin-bottom: 20px; color: #666;">返回前台商城页面</p>
                        <a href="<%=request.getContextPath()%>/customer" 
                           class="btn btn-warning" style="text-decoration: none;">
                            返回首页
                        </a>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<!-- Cart Area End -->

<!-- Add fmt taglib for date formatting -->
<script src="assets/js/vendor/vendor.min.js"></script>
<script src="assets/js/plugins/plugins.min.js"></script>
<!-- Main Js -->
<script src="assets/js/main.js"></script>
</body>
</html>