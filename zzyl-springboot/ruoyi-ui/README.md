# 中州养老管理系统 - 前端

## 项目简介

中州养老管理系统前端项目基于 Vue 2.x + Element UI 构建，为养老服务管理平台提供现代化的用户界面。

## 技术栈

| 技术 | 版本 | 说明 |
| :--- | :--- | :--- |
| Vue | 2.6.12 | 前端框架 |
| Element UI | 2.15.14 | UI组件库 |
| Vuex | 3.6.0 | 状态管理 |
| Vue Router | 3.4.9 | 路由管理 |
| Axios | 0.30.3 | HTTP客户端 |
| ECharts | 5.4.0 | 图表库 |
| Quill | 2.0.2 | 富文本编辑器 |
| NProgress | 0.2.0 | 进度条组件 |

## 环境要求

| 环境 | 版本要求 |
| :--- | :--- |
| Node.js | >= 8.9 |
| npm | >= 3.0.0 |

## 开发

```bash
# 克隆项目
git clone https://gitee.com/y_project/RuoYi-Vue

# 进入项目目录
cd ruoyi-ui

# 安装依赖
npm install

# 建议不要直接使用 cnpm 安装依赖，会有各种诡异的 bug。可以通过如下操作解决 npm 下载速度慢的问题
npm install --registry=https://registry.npmmirror.com

# 启动服务
npm run dev
```

浏览器访问 http://localhost:80

## 开发配置

### 环境配置文件

项目提供三种环境配置：

| 配置文件 | 环境 | 说明 |
| :--- | :--- | :--- |
| `.env.development` | 开发环境 | 本地开发使用 |
| `.env.staging` | 测试环境 | 测试服务器使用 |
| `.env.production` | 生产环境 | 生产服务器使用 |

### 代理配置

开发环境下，前端通过代理访问后端接口。代理配置在 `vue.config.js` 中：

```javascript
devServer: {
    proxy: {
        '/dev-api': {
            target: 'http://localhost:8080',
            changeOrigin: true,
            pathRewrite: {
                '^/dev-api': ''
            }
        }
    }
}
```

### API接口配置

API接口配置在 `src/api/` 目录下，每个模块对应一个API文件：

- `src/api/login.js` - 登录相关接口
- `src/api/system/user.js` - 用户管理接口
- `src/api/system/role.js` - 角色管理接口
- `src/api/system/menu.js` - 菜单管理接口
- `src/api/monitor/` - 监控相关接口
- `src/api/tool/gen.js` - 代码生成接口

## 项目结构

```
ruoyi-ui/
├── public/                 # 静态资源
│   ├── index.html          # HTML入口
│   ├── favicon.ico         # 网站图标
│   └── styles/             # 全局样式
├── src/
│   ├── api/                # API接口定义
│   │   ├── system/         # 系统管理接口
│   │   ├── monitor/        # 监控管理接口
│   │   └── tool/           # 工具管理接口
│   ├── assets/             # 静态资源
│   │   ├── icons/          # SVG图标
│   │   ├── images/         # 图片资源
│   │   └── styles/         # 全局样式文件
│   ├── components/         # 公共组件
│   │   ├── Breadcrumb/     # 面包屑导航
│   │   ├── Crontab/        # 定时任务表达式组件
│   │   ├── DictData/       # 字典数据组件
│   │   ├── Editor/         # 富文本编辑器
│   │   ├── FileUpload/     # 文件上传组件
│   │   ├── ImageUpload/    # 图片上传组件
│   │   ├── Pagination/     # 分页组件
│   │   ├── RightToolbar/   # 右侧工具栏
│   │   └── SvgIcon/        # SVG图标组件
│   ├── directive/          # 自定义指令
│   │   ├── dialog/         # 对话框拖拽指令
│   │   ├── module/         # 功能模块指令
│   │   └── permission/     # 权限指令
│   ├── layout/             # 布局组件
│   │   ├── components/     # 布局子组件
│   │   │   ├── Sidebar/    # 侧边栏
│   │   │   ├── Navbar/     # 顶部导航栏
│   │   │   └── AppMain/    # 主内容区
│   │   └── mixin/          # 布局混入
│   ├── plugins/            # 插件
│   │   ├── auth.js         # 认证插件
│   │   ├── cache.js        # 缓存插件
│   │   └── download.js     # 下载插件
│   ├── router/             # 路由配置
│   ├── store/              # 状态管理(Vuex)
│   ├── utils/              # 工具函数
│   ├── views/              # 页面视图
│   │   ├── dashboard/      # 首页仪表盘
│   │   ├── login/          # 登录页
│   │   ├── system/         # 系统管理页面
│   │   ├── monitor/        # 监控管理页面
│   │   └── tool/           # 工具管理页面
│   ├── App.vue             # 根组件
│   ├── main.js             # 入口文件
│   └── permission.js       # 权限控制
├── .env.development        # 开发环境配置
├── .env.production         # 生产环境配置
├── .env.staging            # 测试环境配置
├── babel.config.js         # Babel配置
├── package.json            # 项目依赖
├── vue.config.js           # Vue CLI配置
└── README.md               # 项目说明
```

## 代码规范

### 组件命名规范

- 组件名使用 PascalCase 格式
- 公共组件放在 `src/components/` 目录
- 页面组件放在 `src/views/` 目录

### API命名规范

- API方法使用驼峰命名
- 方法名格式：`getXxxList`, `getXxxById`, `addXxx`, `editXxx`, `delXxx`

### 状态管理规范

- 全局状态放在 `src/store/modules/` 目录
- 状态定义使用常量
- Mutations 使用大写常量命名

## 发布

```bash
# 构建测试环境
npm run build:stage

# 构建生产环境
npm run build:prod
```

构建产物会生成在 `dist` 目录。

## 部署

### Nginx部署示例

```nginx
server {
    listen 80;
    server_name your_domain.com;

    location / {
        root /path/to/dist;
        index index.html index.htm;
        try_files $uri $uri/ /index.html;
    }

    location /prod-api/ {
        proxy_pass http://localhost:8080/;
        proxy_set_header Host $host;
        proxy_set_header X-Real-IP $remote_addr;
        proxy_set_header X-Forwarded-For $proxy_add_x_forwarded_for;
        proxy_set_header REMOTE-HOST $remote_addr;
    }
}
```

### 配置说明

1. 将 `dist` 目录部署到 Nginx 的静态资源目录
2. 配置反向代理，将 `/prod-api/` 指向后端服务
3. 确保前端 `VUE_APP_BASE_API` 配置与后端接口路径一致

## 常见问题

### npm安装失败

```bash
# 清除缓存
npm cache clean --force

# 使用淘宝镜像
npm install --registry=https://registry.npmmirror.com
```

### 端口冲突

修改 `vue.config.js` 中的 `port` 配置：

```javascript
devServer: {
    port: 8081
}
```

### 代理配置

确保后端服务已启动，且代理地址配置正确。开发环境下，前端访问 `/dev-api/` 会被代理到后端服务。

## 许可证

本项目基于若依框架（MIT License）二次开发，遵循 MIT 开源协议。
