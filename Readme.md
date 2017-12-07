# 自定义代码集成

## 底层依赖
```
<dependency>
    <groupId>com.github.fartherp</groupId>
    <artifactId>code-generator-core</artifactId>
</dependency>
```
## 目录结构
```
comment: 代码注解
db: 对应数据库字段/类包名基本信息
java.element: 类元素
java.file: java类集合
xml.mybatis.element: mybatis的xml文件元素
xml.mybatis.mapper: mybatis的xml文件集合
xml.spring.element: spring的xml文件元素
xml.spring.document: spring的xml文件集合
META-INF.generator: key(自定义)=生成代码入口类
```
## 核心
```
项目的核心*Attributes，使用泛型可以在编码时控制错误，核心贯穿整个项目
```
