<p align="center">
    <img alt="logo" src="https://oscimg.oschina.net/oscnet/up-d3d0a9303e11d522a06cd263f3079027715.png">
</p>
<h1 align="center" style="margin: 30px 0 30px; font-weight: bold;">慧享养老服务平台</h1>
<h4 align="center">基于SpringBoot+Vue前后端分离的养老服务管理平台</h4>
<p align="center">
    <a href="#"><img src="https://img.shields.io/badge/Spring%20Boot-3.5.14-brightgreen.svg"></a>
    <a href="#"><img src="https://img.shields.io/badge/Vue-3.x-green.svg"></a>
    <a href="#"><img src="https://img.shields.io/badge/license-MIT-blue.svg"></a>
</p>

## 📋 项目简介

慧享养老服务平台是基于若依框架（RuoYi-Vue）二次开发的养老服务管理平台，为养老机构提供全面的信息化管理解决方案。

### 系统组成

| 组成部分 | 说明 | 管理方式 |
|---------|------|---------|
| **后台管理系统** | Spring Boot + Vue 3 前后端分离的管理平台 | 本仓库完整包含 |
| **微信小程序** | 供老人及家属使用的移动端应用 | 在微信开发者工具中独立管理 |

### 技术栈

- 后端采用 Spring Boot 3.5.x、Spring Security、Redis & Jwt
- 前端采用 Vue 2.x、Element UI、Vuex、Vue Router
- 权限认证使用 Jwt，支持多终端认证系统
- 支持加载动态权限菜单，多方式轻松权限控制
- 高效率开发，使用代码生成器可以一键生成前后端代码
- **新增测试管理模块**，实现测试用例、缺陷、测试计划的数字化管理
- **新增自动化测试框架**，基于Python + Selenium + pytest实现Web自动化测试
- **新增接口测试文档**，基于Apifox/OpenAPI规范的接口测试集合

> 💡 微信小程序代码在微信开发者工具中独立管理和维护，本仓库仅包含后端服务接口和管理后台前端。

## 🛠 技术栈

### 后端技术栈

| 技术 | 版本 | 说明 |
| :--- | :--- | :--- |
| Spring Boot | 3.5.14 | 应用框架 |
| Spring Security | 6.x | 安全框架 |
| MyBatis | 3.0.5 | ORM框架 |
| Redis | 7.x | 缓存中间件 |
| JWT | 0.9.1 | Token认证 |
| MySQL | 8.x | 关系型数据库 |
| Druid | 1.2.28 | 数据库连接池 |
| SpringDoc OpenAPI | 2.8.17 | API文档 |

### 前端技术栈

| 技术 | 版本 | 说明 |
| :--- | :--- | :--- |
| Vue | 2.6.12 | 前端框架 |
| Element UI | 2.15.14 | UI组件库 |
| Vuex | 3.6.0 | 状态管理 |
| Vue Router | 3.4.9 | 路由管理 |
| Axios | 0.30.3 | HTTP客户端 |
| ECharts | 5.4.0 | 图表库 |

### 测试技术栈

| 技术 | 说明 |
| :--- | :--- |
| Python + pytest | 自动化测试框架 |
| Selenium | Web自动化测试 |
| Apifox | 接口测试/API文档管理 |
| 禅道 (ZenTao) | 缺陷管理 |

## 📦 内置功能

1. **用户管理**：用户是系统操作者，该功能主要完成系统用户配置。
2. **部门管理**：配置系统组织机构（公司、部门、小组），树结构展现支持数据权限。
3. **岗位管理**：配置系统用户所属担任职务。
4. **菜单管理**：配置系统菜单，操作权限，按钮权限标识等。
5. **角色管理**：角色菜单权限分配、设置角色按机构进行数据范围权限划分。
6. **字典管理**：对系统中经常使用的一些较为固定的数据进行维护。
7. **参数管理**：对系统动态配置常用参数。
8. **通知公告**：系统通知公告信息发布维护。
9. **操作日志**：系统正常操作日志记录和查询；系统异常信息日志记录和查询。
10. **登录日志**：系统登录日志记录查询包含登录异常。
11. **在线用户**：当前系统中活跃用户状态监控。
12. **定时任务**：在线（添加、修改、删除）任务调度包含执行结果日志。
13. **代码生成**：前后端代码的生成（java、html、xml、sql）支持CRUD下载。
14. **系统接口**：根据业务代码自动生成相关的api接口文档。
15. **服务监控**：监视当前系统CPU、内存、磁盘、堆栈等相关信息。
16. **缓存监控**：对系统的缓存信息查询，命令统计等。
17. **在线构建器**：拖动表单元素生成相应的HTML代码。
18. **连接池监视**：监视当前系统数据库连接池状态，可进行分析SQL找出系统性能瓶颈。

## 🧪 新增测试管理功能

### 测试用例管理
- 测试用例的增删改查
- 支持用例导入/导出Excel
- 按模块、类型、状态、优先级等多维度筛选
- 用例与缺陷关联

### 缺陷管理
- 缺陷全生命周期管理
- 支持严重程度、优先级分类
- 缺陷与测试用例关联
- 统计分析

### 测试计划
- 测试计划制定和跟踪
- 计划关联测试用例
- 执行进度监控

### 测试仪表盘
- 测试数据可视化统计
- 用例通过率、缺陷趋势图
- 模块覆盖率分析

## 🔬 自动化测试

项目根目录下的 `自动化测试脚本/` 目录包含完整的Selenium自动化测试框架：

| 文件 | 说明 |
| :--- | :--- |
| `conftest.py` | pytest配置和浏览器fixture |
| `test_login.py` | 登录模块测试用例 |
| `test_checkin.py` | 入住管理测试用例 |
| `test_api.py` | API接口测试用例 |
| `requirements.txt` | Python依赖清单 |

运行方式：
```bash
cd 自动化测试脚本
pip install -r requirements.txt
pytest -v --html=report.html
```

## 📘 接口测试

`Apifox接口测试/` 目录包含完整的OpenAPI接口文档，可导入Apifox使用。

接口分类：
- 认证管理：登录、登出、获取用户信息
- 用户管理：用户CRUD
- 入住管理：入住登记、退住办理
- 护理管理：护理等级、计划、项目
- 床位管理：楼层、房间、床位
- 监测管理：设备告警、监测数据
- 系统管理：角色、菜单、字典
- 测试管理：测试用例、缺陷管理

## 📂 项目结构

```
zzyl-springboot/
├── zzyl-admin/              # 启动模块
├── zzyl-system/             # 系统核心模块
├── zzyl-framework/          # 框架核心模块
├── zzyl-quartz/             # 定时任务模块
├── zzyl-generator/          # 代码生成模块
├── zzyl-common/             # 通用工具模块
├── zzyl-oss/                # OSS存储模块
├── zzyl-nursing-platform/   # 护理平台业务模块
├── zzyl-ai-agent/           # AI智能体模块
├── zzyl-test-platform/      # 🆕 测试管理模块
├── sql/                     # SQL脚本
│   └── zzyl_test_init.sql   # 测试模块初始化脚本
├── 自动化测试脚本/           # 🆕 Selenium自动化测试
│   ├── conftest.py
│   ├── test_login.py
│   ├── test_checkin.py
│   ├── test_api.py
│   └── requirements.txt
├── Apifox接口测试/           # 🆕 API接口文档
│   └── 慧享养老服务平台-接口文档.yaml
├── 测试用例设计/              # 🆕 测试用例文档
├── 慧享养老服务平台-测试全流程操作手册.docx  # 🆕 测试操作手册
├── 慧享养老服务平台-项目总结.docx           # 🆕 项目总结
├── 慧享养老服务平台-面试题集.docx           # 🆕 面试题集
├── 苏凯峰-软件测试工程师简历.docx            # 🆕 个人简历
└── doc/                     # 文档目录
```

## 🚀 快速开始

### 环境要求

| 环境 | 版本要求 |
| :--- | :--- |
| JDK | 17+ |
| Maven | 3.8+ |
| Node.js | 8.9+ |
| MySQL | 8.0+ |
| Redis | 7.0+ |

### 数据库配置

1. 创建数据库 `zzyl`
2. 执行 `sql/` 目录下的初始化脚本
3. 执行 `sql/zzyl_test_init.sql` 初始化测试模块
4. 修改 `zzyl-admin/src/main/resources/application-druid.yml` 配置数据库连接信息

```yaml
spring:
    datasource:
        druid:
            master:
                url: jdbc:mysql://localhost:3306/zzyl?useUnicode=true&characterEncoding=utf8&zeroDateTimeBehavior=convertToNull&useSSL=true&serverTimezone=GMT%2B8
                username: root
                password: your_password
```

### Redis配置

修改 `zzyl-admin/src/main/resources/application.yml` 配置Redis连接信息：

```yaml
spring:
    data:
        redis:
            host: localhost
            port: 6380
            password: your_password
```

### 后端运行

```bash
# 进入项目目录
cd zzyl-springboot

# 编译项目
mvn clean package -DskipTests

# 运行项目
java -jar zzyl-admin/target/zzyl-admin.jar

# 或者使用Maven运行
mvn spring-boot:run -pl zzyl-admin
```

访问地址：http://localhost:8080

### 前端运行

```bash
# 进入前端目录
cd ruoyi-ui

# 安装依赖
npm install

# 启动开发服务器
npm run dev
```

访问地址：http://localhost:80

### 登录信息

- 用户名：admin
- 密码：admin123

## 📄 许可证

本项目基于若依框架（MIT License）二次开发，遵循 MIT 开源协议。
