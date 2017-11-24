# spring 生命周期、注解、代理、类扫描综合实践
参考feign使用spring模拟实现一个rpc框架，服务端定义接口和实现类，客户端通过接口代理实现访问服务端

## @MyService
被注解的类,包装成rest接口

## @MyReferer
被注解的字段或者方法，生成代理对象注入

## @EnableMyClient
声明需要扫描的client接口

## @MyClient
注解在接口上，会生成改接口的代理对象注入到spring context中，通过代理对象能访问远程服务

## MyClientsRegistrar
扫描@EnableMyClient中的basePackage中被@MyClient注解的接口，生成代理对象注入到springcontext中  
处理@MyReferer注解的属性，用代理的bean赋值

## MyBeanPostProcessor
处理@MyService注解的bean，注册成RestController

