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
├── entity/          # 实体类
│   ├── Furn.java       家居商品
│   ├── Order.java      订单
│   ├── OrderItem.java  订单项
│   ├── Cart.java       购物车
│   ├── CartItem.java   购物车项
│   ├── Member.java     会员
│   ├── Admin.java      管理员
│   └── Page.java       分页（泛型）
├── dao/             # 数据访问层接口
│   ├── BasicDAO.java       通用 DAO 基类（泛型 CRUD）
│   ├── AdminDao.java
│   ├── FurnDao.java
│   ├── MemberDao.java
│   ├── OrderDao.java       含分页查询
│   ├── OrderItemDao.java
│   └── impl/               DAO 实现类
├── service/         # 业务逻辑层
│   ├── AdminService.java
│   ├── FurnService.java    含分页/搜索
│   ├── MemberService.java
│   ├── OrderService.java   含分页/状态管理
│   └── impl/               Service 实现类
├── web/             # Servlet 控制器
│   ├── Basic_Servlet.java  反射路由基类（?action=xxx）
│   ├── memberServlet.java
│   ├── adminServlet.java
│   ├── customerServlet.java  首页/搜索/详情
│   ├── cartServlet.java      购物车 CRUD
│   ├── furnManageServlet.java 后台商品管理
│   └── orderServlet.java     下单/查单/状态
├── filter/          # 过滤器
│   ├── AuthFilter.java        权限拦截
│   └── TransactionFilter.java 事务管理
├── utils/           # 工具类
│   ├── JDBCUtilsByDruid.java  Druid 连接池
│   ├── DataUtils.java         数据转换
│   ├── WebUtils.java          Web 工具
│   ├── DealFileUtils.java     文件上传处理
│   └── Utility.java           通用工具
└── test/            # 单元测试
web/
├── index.jsp        # 入口（转发到客户首页）
├── WEB-INF/
│   ├── web.xml      # 部署描述符
│   └── lib/         # 第三方 jar
└── views/
    ├── customer/    # 客户页面（首页、详情）
    ├── cart/        # 购物车页面
    ├── order/       # 订单页面（结算、列表、详情）
    ├── member/      # 会员登录/注册
    ├── manage/      # 后台管理（登录、商品 CRUD、订单管理）
    └── error/       # 404/500 错误页面
```

## 数据库

**数据库名**: `furniture`，字符集 utf8，引擎 InnoDB。

**核心表**：

```sql
-- 家居商品
CREATE TABLE furn (
    id       INT UNSIGNED PRIMARY KEY AUTO_INCREMENT,
    name     VARCHAR(32)  NOT NULL,
    market   VARCHAR(32)  NOT NULL,
    price    DECIMAL(10,2),
    sales    INT UNSIGNED DEFAULT 0,
    store    INT UNSIGNED,            -- 库存
    img_path VARCHAR(256) NOT NULL
);

-- 订单
CREATE TABLE `order` (
    id          VARCHAR(64) PRIMARY KEY,  -- UUID
    create_time DATETIME NOT NULL,
    price       DECIMAL(10,2) NOT NULL,
    status      TINYINT NOT NULL,         -- 0未发货 1已发货 2已结账
    member_id   INT NOT NULL
);

-- 订单项
CREATE TABLE order_item (
    id        INT PRIMARY KEY AUTO_INCREMENT,
    name      VARCHAR(64),
    price     DECIMAL(10,2),
    count     INT,
    totalPrice DECIMAL(10,2),
    order_id  VARCHAR(64),                 -- 外键关联 order.id
    furn_id   INT
);
```

## Servlet 路由表

所有 Servlet 通过 `?action=xxx` 参数分发（`Basic_Servlet` 反射机制，无 action 时默认执行 `page` 方法）：

| URL | Servlet | 主要 action |
|-----|---------|------------|
| `/customer` | customerServlet | `page`(首页分页), `searchByName`(搜索), `detail`(商品详情) |
| `/cartServlet` | cartServlet | `addCartItem`, `addCartItemByAjax`, `updateCount`, `deleteCartItem`, `clearCartItem` |
| `/orderServlet` | orderServlet | `saveOrder`(下单), `orderItemDetail`(订单详情), `orderManager`(订单列表) |
| `/member` | memberServlet | `login`, `register` |
| `/manage/admin` | adminServlet | 管理员登录 |
| `/manage/furnManage` | furnManageServlet | 后台商品 CRUD（含图片上传） |
| `/kaptchaServlet` | KaptchaServlet | 生成验证码图片 |

## 权限控制

`AuthFilter` 拦截受保护路径，检查 Session 中的 `member` 或 `admin` 对象：
- `/views/manage/*` → 需要 admin 登录
- `/cartServlet`, `/orderServlet`, `/views/cart/*`, `/views/order/*`, `/views/member/*` → 需要 member 或 admin 登录
- 登录页面 `/views/manage/manage_login.jsp` 和 `/views/member/login.jsp` 为白名单，直接放行

`TransactionFilter` 对所有请求 (`/*`) 进行事务管理。

## 核心业务流程

### 1. 用户下单流程
```
购物车页面 → orderServlet?action=saveOrder
  → 校验登录状态（未登录跳转登录页）
  → 遍历购物车，逐项检查库存
  → 库存不足 → 返回购物车提示错误
  → 库存充足 → OrderServiceImpl.saveOrder() 生成订单
     → 保存 order 记录
     → 保存 order_item 记录（含商品名/单价/数量/小计）
     → 更新 furn 库存
     → 清空购物车
  → 转发到 checkout.jsp 显示订单号
```

### 2. 商品浏览
- 首页 (`/customer`) 默认展示全部商品，每页 4 条，支持分页导航
- 支持按名称模糊搜索，搜索结果也分页
- 点击商品进入详情页 (`/customer?action=detail&id=X`)

### 3. 后台管理
- 管理员登录后进入 `manage_menu.jsp`
- 商品管理：列表（分页）/ 添加（含图片上传）/ 修改 / 删除
- 订单管理：查看所有订单列表（分页），支持按订单状态筛选

## 运行方式

1. 用 IntelliJ IDEA 打开项目
2. 配置 Tomcat（Servlet 4.0）
3. 确保 MySQL 运行，数据库 `furniture` 已创建
4. 数据库连接配置在 `src/druid.properties`
5. 启动 Tomcat，访问 `http://localhost:8080/furniture-2/`

## 最近更新

### 2026-06-07 — 商品详情页 + 全站分页 + Bug 修复
- 新增商品详情页（首页点击商品 → 独立详情页）
- 前后台分页全覆盖（商品列表、订单列表、搜索结果）
- `Page.java` 泛型化支持复用
- 修复 NPE、404、死链、导航栏不一致等 10+ Bug
- 涉及 30 个文件
