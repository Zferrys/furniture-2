# Furniture-2 项目记忆

## 项目技术栈
- Java Web (Servlet + JSP + JSTL)
- Apache Commons FileUpload (文件上传)
- Druid 连接池 + MySQL
- Tomcat (APR 连接器, 端口 9998)
- 无 Spring 框架, 纯原生 Servlet

## 项目结构
- `src/com/furniture/web/` - Servlet 控制器
  - `Basic_Servlet.java` - 基类, 通过反射根据 `action` 参数分发请求
  - `customerServlet.java` - 客户首页/搜索
  - `cartServlet.java` - 购物车
  - `orderServlet.java` - 订单
  - `furnManageServlet.java` - 家具管理 (CRUD)
  - `adminServlet.java` - 管理员
- `src/com/furniture/service/` - 服务层
- `src/com/furniture/dao/` - 数据访问层
- `src/com/furniture/entity/` - 实体类 (Cart, CartItem, Furn, Order, User 等)
- `src/com/furniture/filter/` - 过滤器 (TransactionFilter)
- `src/com/furniture/utils/` - 工具类

## 重要修改历史
- 2026-06-07: 修复 Basic_Servlet action 为 null 时的 NPE 问题
- 2026-06-07: 完善家具 CRUD, 实现文件上传, 修复图片显示, CartItem 增加 imgPath 字段
