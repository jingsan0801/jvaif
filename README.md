# Note

## 分层说明

### common

底层, 集合常用的工具类, 供inf和web调用, 以jar包形式提供.

工具类(util)


### inf

接口层,提供service,属于业务的核心层,供web层的controller或者其他类型的前端调用, 以jar包形式提供.

存放:
配置类(config)
常量(constant)
Dao
实体类(domain)
服务类(service)
Vo(vo)

### web

web层, 服务入口, 包括给外部提供Restful接口, 展示页面等.

controller调用service时, controller一般返回Result, 但Service一般不要直接返回Result,而是返回有意义的dto或者其他类型的业务参数, 这样方便controller自己做封装.

controller不应该保护业务逻辑, 而应该只包含展示逻辑.

存放和web相关的
控制层(controller)
拦截器(interceptor)

### tool
工具层, 暂时只包括自动代码生成的逻辑, 可独立运行, 与其他层没有关系

### todo:
1. 多种验证方式共存
    1. token 
    2. username/password
2. role/auth/menu管理
3. 忘记密码
4. shiro的记住我
5. 重置密码
6. controller传入的参数, 统一加校验, 空格什么的要去掉
7. sql注入漏洞
    

### note
2019-08-01:
1. 完成基于mybatis-plus的scyUser的CRUD
2. 把前端代码分离出去

2019-08-05:
1. 完成新增用户/用户登录的功能 
2. 完善restApi返回信息 
3. 完善login登录鉴权逻辑

2019-08-11:
1. 更新token以后,旧的token应该置为失效
2. 引入swagger
3. 引入redis

2019-08-14:
1. 优化代码: 完善返回给接口的异常信息显示
2. 完成更新token功能,更新后旧的token置为失效
 
2019-08-16:
1. mybatis 使用xml配置sql, ok
2. 使用shiro控制权限注解ok

2019-08-17:
1. 在线修改用户权限后, 在用户退出登录后生效

2019-08-18:
1. 引入自动生成代码工具

2019-08-20:
1. 完成图形验证码功能