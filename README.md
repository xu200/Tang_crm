<div  align="center">


唐三藏CRM后台管理系统Java版

</div>

<div align="center">

[在线体验](http://43.143.97.202:9001/main) |
[帮助文档](https://gitee.com/Skr_Alex/xqy-final-project/blob/master/readme.md) |
[Gitee社区]([Alex001 (Skr_Alex) - Gitee.com](https://gitee.com/Skr_Alex))

<div align="center" >
<a href="https://gitee.com/Skr_Alex/xqy-final-project/blob/Alex/README.md">宽屏预览</a>
</div>
</div>


---

### 📋 更新说明

```
v1.2.0 更新说明【更新时间2023年12月13日】
1、后台ui 从原始静态页面更新成动态可与后端交互的页面，UI样式的改变
2、数据统计查询优化
3、优化服务器站点配置，分类对应的端口项
4、修复了营销管理中的客户开发管理页面的可能出现新增联系人错误的功能
5、新增了redis进行各个模块之间的CRUD的性能问题，提高了读写性能，同时防止数据丢失
```

### 用心做开源，我很需要你的鼓励！右上角Star🌟，等你点亮！

---

### 📝 介绍

本项目是基于Spring Boot的唐三藏CRM后台管理系统，用于实现客户关系管理。CRM（Customer Relationship
Management）是指企业与客户之间建立和维护良好关系的管理方法和策略。该系统提供了一套完整的功能，帮助企业有效地管理客户信息、跟进销售机会、提升客户满意度等。

---

### 🫧 系统亮点

~~~
1.简化开发流程：采用Spring Boot框架，简化了Java后端应用程序的配置和部署过程，提高开发效率。
2.友好的前端界面：使用layui框架构建Web界面，具有美观、易用的特点，提供良好的用户体验。
3.高效的数据库访问：基于MyBatis-Plus，简化了数据库访问操作，提供了强大的CRUD功能，减少了开发工作量。
4.动态模板生成：利用Freemarker模板引擎生成动态的HTML、XML、JSON等文档，便于定制化和扩展。
5.交互式树形结构：使用Z-tree插件生成交互式的树形结构，方便展示和操作层级关系的数据。
6.高性能的数据库连接池：采用阿里巴巴开源的Druid连接池，提供了稳定可靠的数据库连接管理，提高了系统的性能和可伸缩性。
7.便捷的API文档生成：集成了Swagger2工具，自动生成和展示API文档，简化了接口的调试和测试过程。
8.灵活的分页处理：引入PageHelper分页插件，简化了数据库查询结果的分页操作，提供了灵活的分页功能。
9.开发辅助工具：使用MybatisX工具，提供了代码生成、语法检查等功能，加速了开发过程。
10.简化Java开发：采用Lombok库，通过注解自动生成代码，减少了Java开发中的样板代码。
11.高效的JSON处理：使用Jackson库，实现了JSON的序列化和反序列化，提供了高效的数据传输和处理方式。
12.快速部署和更新：利用Spring Boot的DevTools热部署工具，在开发阶段实现代码的快速重启和更新，提高了开发效率。
~~~

---

### 🖥 运行环境

```
Java Development Kit (JDK)：版本 1.8。
Apache Maven：3.6.5。
数据库：MySQL 8.30,redis进行缓存
Web浏览器：推荐使用Chrome、Firefox等现代浏览器。
数据库工具：Navicat Premium
java代码编辑器：IDEA
```

### 📱 系统演示

![输入图片说明](src\main\resources\public\images\readme\登录.png)

管理后台： http://43.143.97.202:9001/main

账号：xqy 密码：123456

> 听说，大神你想看看CRM开源项目的完整框架？<a href="https://gitee.com/Skr_Alex/xqy-final-project/tree/master/">
> 戳这儿，轻松获取！</a>

---

### 🔐 安装教程

1.新建一个名为 **crm** 的数据库，然后导入本项目中的sql文件 2.IDEA安装lombok插件（在插件库中直接搜索即可下载） 3.运行启动访问
**http://localhost:8080/** 即可 4.可在swagger（ **localhost:8080/swagger-ui.html** ）中查看本项目中的所有接口，有个总体了解

**登录页：**

![登录页](src\main\resources\public\images\readme\登录.png)

**首页：**

![首页](\src\main\resources\public\images\readme\首页.png)


---

### 📲 核心功能

1. 营销机会管理：帮助用户跟踪和管理销售机会的全过程，包括创建销售机会、跟进销售机会、分配销售机会和销售机会报表分析。
2. 客户开发计划：支持制定和执行客户开发计划，包括制定计划、分配任务、记录开发进展和开发计划报表分析。
3. 客户信息管理：提供全面的客户信息管理功能，包括添加客户信息、编辑客户信息、搜索和筛选客户、查看客户历史记录和导出客户信息。
4. 流失客户管理：帮助企业识别和挽回潜在的流失客户，包括识别潜在流失客户、跟踪流失客户活动、制定挽回计划和流失客户报表分析。
5. 服务创建：支持创建和管理客户的服务请求和工单，包括创建服务请求、分配服务工单、跟进服务工单和服务工单报表分析。
6. 生成订单：提供生成订单的功能，用于将销售机会转化为实际的销售订单，确保订单的准确性和及时性。
7. 新增客户分析和重要数据数量的统计分析：以Echarts折线图表的形式展示新增客户数量和重要数据数量的统计分析，帮助用户了解客户增长趋势和关键数据的变化。
8. 系统设置管理：提供了用户管理、角色管理和资源管理功能，用于管理系统的用户、角色和资源权限，确保系统安全和权限控制的有效性。
9. 管理员和其他不同角色的用户身份的登录所显示的页面和权限也不同。
10. 根据营销管理，服务管理，流失客户管理，系统设置管理，客户管理，数据报表管理分为了管理员，销售，客户经理，技术经理，人事，法律顾问的数据权限。

### 📖 UI界面展示

![首页](src\main\resources\public\images\readme\首页.png)
![营销计划管理页面](src\main\resources\public\images\readme\营销计划管理.png)
![客户信息管理页面](src\main\resources\public\images\readme\客户开发计划.png)
![流失客户管理页面](src\main\resources\public\images\readme\流失客户管理.png)

![服务创建页面](src\main\resources\public\images\readme\服务创建.png)

![生成订单页面](src\main\resources\public\images\readme\生成订单.png)

![新增客户分析页面](src\main\resources\public\images\readme\新增客户分析.png)

![重要数据数量的统计页面](src\main\resources\public\images\readme\重要数据.png)

![用户管理页面](src\main\resources\public\images\readme\用户管理.png)

![角色管理页面](src\main\resources\public\images\readme\角色管理.png)

![资源管理页面](src\main\resources\public\images\readme\资源管理.png)



---

### 💎 捐赠

赠人玫瑰，手留余香！CRM诚挚地邀请大家积极参与捐赠，我会将捐赠获得的费用，悉数用于支持公益项目，让善意无限传递下去！
在此深表感谢~

---

### 📸 特别鸣谢

排名不分先后，感谢这些软件的开发者：java、ftl、redis、mysql、maven、js、echarts、swagger、echats、page-Helper等，如有遗漏请联系我！

---

### 🎬 核心开发团队

产品：Alex

技术：Alex

UI：Alex

测试：Alex


---

### 📺 使用须知

1、允许用于个人学习、毕业设计、教学案例、公益事业、商业使用；

2、如果商用必须保留版权信息，请自觉遵守；

3、禁止将本项目的代码和资源进行任何形式的出售，产生的一切任何后果责任由侵权者自负。


---

### 💾 版权信息

本项目包含的第三方源码和二进制文件之版权信息另行标注。

版权所有Copyright © 2023-2023 by Alex(http://43.143.97.202:9001/main)

All rights reserved。

---

[返回顶部 :fa-arrow-circle-up: ](https://gitee.com/Skr_Alex/xqy-final-project/tree/master/)

