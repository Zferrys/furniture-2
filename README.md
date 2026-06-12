# 家居电商平台 (Furniture-2)

基于 Java Servlet + JSP 的家居商品电商平台，采用经典 MVC 三层架构。

## 技术栈

| 层次 | 技术 |
|------|------|
| 后端 | Java Servlet, JSP |
| 数据库 | MySQL 5.x |
| 连接池 | Druid 1.1.10 |
| ORM 工具 | Apache Commons DbUtils 1.3 |
| JSON | Gson 2.2.4 |
| 验证码 | Kaptcha 2.3.2 |
| 前端 | jQuery + Bootstrap 模板 |

## 项目结构

```
src/com/furniture/
├── entity/          # 实体类（Furn, Order, OrderItem, Cart, Member, Admin, Page）
├── dao/             # 数据访问层（BasicDAO 泛型基类 + 各 DAO 接口与实现）
├── service/         # 业务逻辑层（Admin/Furn/Member/Order 服务接口与实现）
├── web/             # Servlet 控制器（Basic_Servlet 反射路由 + 7 个业务 Servlet）
├── filter/          # 过滤器（AuthFilter 权限 + TransactionFilter 事务）
├── utils/           # 工具类（Druid连接池、数据转换、文件上传等）
└── test/            # 单元测试
web/
├── index.jsp        # 入口，转发到客户首页
├── WEB-INF/
│   ├── web.xml      # 部署描述符
│   └── lib/         # 第三方 jar
└── views/
    ├── customer/    # 客户页面（首页、详情）
    ├── cart/        # 购物车
    ├── order/       # 订单（结算、列表、详情）
    ├── member/      # 会员登录/注册
    ├── manage/      # 后台管理（登录、商品CRUD、订单管理）
    └── error/       # 404/500
```

## 数据库

数据库名 `furniture`，utf8 + InnoDB。

核心表：`furn`（商品）、`order`（订单）、`order_item`（订单项）。

## Servlet 路由

所有 Servlet 继承 `Basic_Servlet`，通过 `?action=xxx` 反射分发，无 action 默认走 `page`。

| URL | Servlet | 主要功能 |
|-----|---------|---------|
| `/customer` | customerServlet | 首页分页、模糊搜索、商品详情 |
| `/cartServlet` | cartServlet | 购物车增删改查 |
| `/orderServlet` | orderServlet | 下单、订单详情、订单列表 |
| `/member` | memberServlet | 会员登录、注册 |
| `/manage/admin` | adminServlet | 管理员登录 |
| `/manage/furnManage` | furnManageServlet | 后台商品CRUD（含图片上传） |
| `/kaptchaServlet` | KaptchaServlet | 验证码 |

## 权限控制

- `AuthFilter`：拦截受保护路径，检查 Session 中 `member`/`admin`；登录页白名单放行
- `TransactionFilter`：全局事务管理（请求成功 commit，异常 rollback）

## 核心业务流程

**下单**：购物车 → 校验登录 → 检查库存 → 生成订单 + 订单项 → 更新库存/销量 → 清空购物车 → 结算页

**浏览**：首页分页展示（每页4条）、名称搜索、点击进入详情页

**后台**：商品 CRUD（分页 + 图片上传）、订单管理（分页 + 状态筛选）

## 快速开始

1. IntelliJ IDEA 打开项目，配置 Tomcat（Servlet 4.0）
2. MySQL 创建数据库 `furniture`
3. 修改 `src/druid.properties` 数据库连接信息
4. 启动 Tomcat，访问 `http://localhost:8080/furniture-2/`

## 更新日志

### 2026-06-07 — 商品详情页 + 全站分页 + Bug 修复
- 新增商品详情页、前后台分页全覆盖、`Page` 泛型化
- 修复 NPE、404、死链、导航栏不一致等 10+ Bug，涉及 30 个文件
