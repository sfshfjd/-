# Spring Boot 登录注册系统

这是一个基于Spring Boot的完整登录注册系统，包含JWT认证、用户管理、权限控制等功能。

## 技术栈

- **Spring Boot 3.1.0** - 主框架
- **Spring Security** - 安全框架
- **Spring Data JPA** - 数据访问层
- **MySQL** - 数据库
- **JWT** - 用户认证
- **Maven** - 项目管理工具

## 功能特性

- ✅ 用户注册
- ✅ 用户登录
- ✅ JWT令牌认证
- ✅ 密码加密存储
- ✅ 用户名/邮箱唯一性检查
- ✅ 权限控制（用户/管理员）
- ✅ 全局异常处理
- ✅ 参数验证
- ✅ RESTful API设计

## 项目结构

```
src/
├── main/
│   ├── java/com/example/loginregister/
│   │   ├── config/                    # 配置类
│   │   │   └── WebSecurityConfig.java # Spring Security配置
│   │   ├── controller/                # 控制器
│   │   │   ├── AuthController.java    # 认证相关API
│   │   │   └── TestController.java    # 测试API
│   │   ├── dto/                       # 数据传输对象
│   │   │   ├── ApiResponse.java       # 统一响应格式
│   │   │   ├── JwtResponse.java       # JWT响应
│   │   │   ├── LoginRequest.java      # 登录请求
│   │   │   └── RegisterRequest.java   # 注册请求
│   │   ├── entity/                    # 实体类
│   │   │   ├── Role.java             # 角色枚举
│   │   │   └── User.java             # 用户实体
│   │   ├── exception/                 # 异常处理
│   │   │   └── GlobalExceptionHandler.java
│   │   ├── repository/                # 数据访问层
│   │   │   └── UserRepository.java    # 用户Repository
│   │   ├── security/                  # 安全相关
│   │   │   ├── AuthEntryPointJwt.java # JWT认证入口
│   │   │   └── AuthTokenFilter.java   # JWT过滤器
│   │   ├── service/                   # 服务层
│   │   │   ├── AuthService.java       # 认证服务
│   │   │   └── UserDetailsServiceImpl.java # 用户详情服务
│   │   ├── util/                      # 工具类
│   │   │   └── JwtUtils.java         # JWT工具类
│   │   └── LoginRegisterApplication.java # 启动类
│   └── resources/
│       └── application.properties     # 配置文件
├── pom.xml                           # Maven配置
└── database.sql                      # 数据库脚本
```

## 环境要求

- JDK 17+
- Maven 3.6+
- MySQL 8.0+

## 快速开始

### 1. 准备数据库

1. 启动MySQL服务
2. 创建数据库：
   ```sql
   CREATE DATABASE login_register_db;
   ```
3. 执行 `database.sql` 脚本（可选，JPA会自动创建表）

### 2. 配置应用

修改 `src/main/resources/application.properties` 中的数据库连接信息：

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/login_register_db
spring.datasource.username=your_username
spring.datasource.password=your_password
```

### 3. 运行项目

```bash
# 编译项目
mvn clean compile

# 运行项目
mvn spring-boot:run
```

项目启动后，访问 http://localhost:8080

## API文档

### 认证相关接口

#### 用户注册
```
POST /api/auth/register
Content-Type: application/json

{
    "username": "testuser",
    "email": "test@example.com",
    "password": "123456",
    "confirmPassword": "123456"
}
```

#### 用户登录
```
POST /api/auth/login
Content-Type: application/json

{
    "username": "testuser",
    "password": "123456"
}
```

响应：
```json
{
    "success": true,
    "message": "登录成功",
    "data": {
        "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
        "type": "Bearer",
        "id": 1,
        "username": "testuser",
        "email": "test@example.com"
    },
    "code": 200
}
```

#### 检查用户名可用性
```
GET /api/auth/check-username?username=testuser
```

#### 检查邮箱可用性
```
GET /api/auth/check-email?email=test@example.com
```

### 测试接口

#### 公共接口（无需认证）
```
GET /api/test/public
```

#### 用户接口（需要认证）
```
GET /api/test/user
Authorization: Bearer <token>
```

#### 管理员接口（需要管理员权限）
```
GET /api/test/admin
Authorization: Bearer <token>
```

#### 获取用户信息
```
GET /api/test/profile
Authorization: Bearer <token>
```

## 使用说明

### 认证流程

1. 用户通过 `/api/auth/register` 注册账号
2. 用户通过 `/api/auth/login` 登录获取JWT令牌
3. 在需要认证的请求中，在Header中添加：`Authorization: Bearer <token>`

### 权限说明

- **USER**: 普通用户权限，可以访问用户相关接口
- **ADMIN**: 管理员权限，可以访问所有接口

### 安全特性

- 密码使用BCrypt加密存储
- JWT令牌有效期为24小时
- 支持跨域请求
- 全局异常处理
- 参数验证

## 测试

使用Postman或其他API测试工具测试各个接口。

默认测试账号（如果执行了database.sql脚本）：
- 管理员：username: `admin`, password: `password`
- 普通用户：username: `user`, password: `password`

## 许可证

本项目采用MIT许可证。

