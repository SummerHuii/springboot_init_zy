# SpringBoot脚手架

## 项目介绍

这是一个功能齐全的SpringBoot脚手架项目，集成了常用的开发组件和功能模块，帮助开发者快速搭建企业级Java应用。

## 主要特性

### 1. 权限管理

- 基于注解的权限控制 (`@AuthCheck`)
- 灵活的角色权限校验机制
- 用户登录状态管理

### 2. 接口保护

- 接口限流控制 (`@RateLimit`)
- 防重复提交保护 (`@RepeatSubmit`)
- 请求响应日志记录

### 3. 文件存储

- 支持腾讯云COS对象存储
- 支持MinIO对象存储
- 灵活的存储配置

### 4. 实时通信

- WebSocket服务支持
- 心跳检测机制
- 消息确认机制

### 5. 缓存支持

- Redis缓存集成
- 统一的缓存管理配置
- 分布式锁支持

### 6. 数据库支持

- MyBatis-Plus集成
- 分页插件
- 数据库连接池配置

### 7. 通用功能

- 统一响应处理
- 全局跨域配置
- 线程池管理
- 统一异常处理

## 技术栈

- SpringBoot
- MyBatis-Plus
- Redis
- WebSocket
- MinIO
- 腾讯云COS
- AOP

## 快速开始

### 环境要求

- JDK 17+
- Maven 3.6+
- Redis
- MySQL

### 配置说明

1. 数据库配置
2. Redis配置
3. 文件存储配置（COS/MinIO）
4. 线程池配置

### 常用注解

```java
@AuthCheck          // 权限校验

@RateLimit          // 接口限流

@RepeatSubmit      // 防重复提交
```

## 项目结构

```
├── annotation // 自定义注解
├── aop // AOP切面
├── aspect // 切面实现
├── common // 通用类
├── config // 配置类
├── constant // 常量定义
├── controller // 控制器
├── service // 服务层
└── util // 工具类
```

## 特别说明

本项目提供了一套完整的企业级应用开发框架，开发者可以基于此快速构建自己的应用。项目集成了主流的技术组件，并提供了丰富的功能特性。

## 作者

- 作者：qiutuan
- 日期：2024年
