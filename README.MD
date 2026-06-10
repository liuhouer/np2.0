# NorthPark 5 (np5/) - Modern Rebuild

## Overview
northpark.cn 完整重构，所有新工作隔离在 np5/ 目录。np2/ 和 np-webx（JSP+Servlet/Spring Boot 2.x + MyBatis + JSP）保持完全不动。

## Tech Stack
- Backend: Java 8 + Spring Boot 2.7.x + MyBatis-Plus + Spring Security
- Frontend: Thymeleaf fragments（layout + nav + header + footer）+ Tailwind CSS 4 + Alpine.js
- Design: Apple 风格最小化（大留白、系统字体、柔和阴影/圆角、响应式网格、懒加载图片、蓝灰色系）
- Security: 公开页面 permitAll()，下载链接、评论、个人中心等需登录（ROLE_ADMIN 用于后台）

## Navigation
影视 / 软件 / 课程 / 下载链接（登录后可见） / 关于 / 捐赠

## Key Flows
- 列表页（带分页 + 侧边栏）→ 详情页（保留 /movies、/movies/post-XXXX.html 等原始 URL 做 SEO）
- 下载字段仅登录后展示
- 完整保留并重写注册/登录/找回密码/评论/个人资料等用户功能

## Startup
1. 在 IDE 中把 np5/ 作为 Spring Boot 项目导入（或加入父 pom）
2. 配置 application.yml 中的数据库信息
3. 运行主 Application 类
4. 访问 http://localhost:8080/movies 等

## Removal List
- bruce-quiet-listen.jsp 及所有 Electron 音乐播放器相关（不再有播放跳转）
- 旧 Bootstrap 3 + jQuery + JSP includes
- 旧 static/css/qinco.css 等

详见 ARCHITECTURE.md。当前状态：基础框架初始化完成，接下来迁移影视模块，然后用户模块。




[浏览器/客户端请求] 
       │
       ▼ (作用域: 安全过滤链)
[SecurityConfig] ──(未登录且访问下载)──> [登录页面]
       │
       ▼ (作用域: 控制层)
[MoviesController] (处理 /movies 列表、标签分类、日期归档及 /movies/post-{id}.html SEO 路由)
       │
       ├─(侧边栏数据加载)─> [TagsService] & 随机热门 [MoviesService]
       │
       ▼ (作用域: 服务与持久层)
[MoviesService] ──> [MoviesMapper] ──> [bc_movies 数据库表]
       │
       ▼ (作用域: 视图渲染)
[Thymeleaf 模板引擎] (整合 Tailwind CSS 4 + Alpine.js)
       ├─ fragments (公用头、导航、页脚组件)
       ├─ movies/list.html (自适应网格列表与侧边栏)
       └─ movies/detail.html (登录状态控制下载显示)