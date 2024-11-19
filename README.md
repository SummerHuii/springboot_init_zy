# Spring Boot 基础配置与通用功能项目文档

## 引言

在快速迭代和高效交付的软件开发环境中，简化新项目的配置并实现通用功能显得尤为关键。本文档旨在详细介绍一个基于 Spring Boot 的基础项目，该项目通过集成多种技术和工具，实现了用户登录、文件上传、全局异常处理、登录拦截、用户鉴权以及全局跨域等核心功能。该项目不仅提升了开发效率，还确保了代码的可靠性和可维护性。

## 项目概述

### 技术栈
技术栈用的非常朴实，没有什么高深的技术。简单但实用

- **Spring Boot**：作为项目的核心框架，提供了快速构建和部署应用的能力。
- **JUnit**：用于编写和运行测试，确保代码的质量和稳定性。
- **Knife4j**：基于 Swagger 的 API 文档生成工具，提供了友好的接口文档界面。
- **JJWT**：用于生成和解析 JSON Web Token（JWT），实现用户鉴权。
- **MySQL**：作为数据库存储用户信息和业务数据。
- **MyBatis & MyBatis Plus**：持久层框架，简化了数据库操作，提高了开发效率。
- **AOP**：面向切面编程，用于实现日志记录、事务管理等功能。
- **Redis**：用于缓存用户会话信息，提高系统性能。

### 功能模块

1. **用户登录**：通过用户名和密码进行身份验证，并生成 JWT Token，用于后续请求的鉴权。
2. **文件上传**：支持将文件上传到腾讯云或 MinIO 存储，提供了灵活的文件存储解决方案。
3. **全局异常处理**：统一捕获和处理应用中的异常，返回友好的错误信息。
4. **登录拦截**：通过 AOP 拦截器或 Spring Security 实现，确保未登录用户无法访问受保护的接口。
5. **用户鉴权**：基于 JWT Token 实现用户身份的验证和权限控制。
6. **全局跨域**：通过配置 Spring Boot 的 CORS（跨域资源共享）支持，允许前端应用跨域请求后端接口。

## 详细功能介绍

### 用户登录

用户通过 POST 请求向 `/auth/login` 接口提交用户名和密码，后端验证成功后生成 JWT Token 并返回给客户端。JWT Token 包含用户的基本信息和有效期，用于后续请求的鉴权。

### 文件上传

文件上传接口支持多种文件类型，用户可以选择将文件上传到腾讯云或 MinIO 存储。上传时，后端会验证文件类型和大小，确保文件符合存储要求。上传成功后，返回文件的存储路径或 URL，供前端展示或下载。

### 全局异常处理

项目通过 `@ControllerAdvice` 和 `@ExceptionHandler` 注解实现了全局异常处理。当应用中出现异常时，全局异常处理器会捕获异常并返回统一的错误信息格式，包括错误代码、错误信息和提示信息，提高了系统的可用性和用户体验。

### 登录拦截

项目通过 AOP 拦截器或 Spring Security 实现了登录拦截功能。未登录用户访问受保护的接口时，拦截器会拦截请求并返回未登录的错误信息。登录用户则通过 JWT Token 进行身份验证和权限控制，确保只有具备相应权限的用户才能访问特定接口。

### 用户鉴权

项目基于 JWT Token 实现了用户鉴权功能。用户登录成功后，后端生成 JWT Token 并返回给客户端。客户端在后续请求中携带 JWT Token，后端通过解析 JWT Token 验证用户身份和权限。根据用户的权限信息，后端会允许或拒绝请求的执行。

### 全局跨域

项目通过配置 Spring Boot 的 CORS 支持实现了全局跨域功能。在配置类中设置允许跨域的域名、请求方法和请求头等参数，确保前端应用能够跨域请求后端接口。这不仅提高了前后端分离的灵活性，还降低了跨域请求带来的安全风险。

## 总结

本项目基于 Spring Boot 框架，集成了多种技术和工具，实现了用户登录、文件上传、全局异常处理、登录拦截、用户鉴权和全局跨域等核心功能。通过简化新项目的配置和完成通用功能，项目提升了开发效率，确保了代码的可靠性和可维护性。未来，我们将继续优化和完善项目功能，为更多开发者提供高效、可靠的解决方案。

## 数据库

数据库就一个表 ， 所以写这里了

```sql
CREATE TABLE `user` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'id',
  `userAccount` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '账号',
  `userPassword` varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '密码',
  `userName` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '用户昵称',
  `userAvatar` varchar(1024) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '用户头像',
  `userProfile` varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '用户简介',
  `userRole` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT 'user' COMMENT '用户角色',
  `createTime` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updateTime` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `isDelete` tinyint NOT NULL DEFAULT '0' COMMENT '是否删除',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1857700903633555458 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='用户';
```

