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

存放和web相关的
控制层(controller)
拦截器(interceptor)

### tool
工具层, 暂时只包括自动代码生成的逻辑, 可独立运行, 与其他层没有关系

### todo:
1. 使用shiro控制权限
2. role/auth/menu管理
3. 引入EhCache或者Redis作为缓存

    

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
 