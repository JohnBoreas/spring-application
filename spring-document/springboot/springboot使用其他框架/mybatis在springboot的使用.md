**使用：**

1、添加依赖

```
<dependency>
    <groupId>org.mybatis.spring.boot</groupId>
    <artifactId>mybatis-spring-boot-starter</artifactId>
    <version>2.0.0</version>
</dependency>
<dependency>
    <groupId>mysql</groupId>
    <artifactId>mysql-connector-java</artifactId>
</dependency>
```

2、java注解方式

```
1、application.properties 添加相关配置
mybatis.type-aliases-package=com.XXX.model

2、在启动类中添加对 mapper 包扫描`@MapperScan`
启动类中使用了@MapperScan，可以不用使用@Mapper
3、编写Entity

4、编写Mapper，并使用注释来写SQL
```

3、XML方式

```
1、application.properties新增以下配置
mybatis.config-location=classpath:mybatis/mybatis-config.xml
mybatis.mapper-locations=classpath:mybatis/mapper/*.xml

2、编写mapper.xml，编写SQL

3、mapper里只需要方法就行
```

mybatis-config.xml 配置

```xml
<configuration>
    <typeAliases>
        <typeAlias alias="Integer" type="java.lang.Integer" />
        <typeAlias alias="Long" type="java.lang.Long" />
        <typeAlias alias="HashMap" type="java.util.HashMap" />
        <typeAlias alias="LinkedHashMap" type="java.util.LinkedHashMap" />
        <typeAlias alias="ArrayList" type="java.util.ArrayList" />
        <typeAlias alias="LinkedList" type="java.util.LinkedList" />
    </typeAliases>
</configuration>
```

4、多数据源

mybatis多数据源的原理是根据不同包，调用不同的数据源，

```
 核心代码就这句@MapperScan(basePackages ="com.web.ds2.**.dao", sqlSessionTemplateRef = "ds2SqlSessionTemplate")
 用来指定包扫描指定sqlSessionTemplateRef和sqlSessionFactory.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath*:com/web/ds2/**/*.xml"));
```



自动生成xml插件

```
1、下载插件Free MyBatis Plugin
2、添加插件
<build>
  <plugins>
    <plugin>
      <groupId>org.mybatis.generator</groupId>
      <artifactId>mybatis-generator-maven-plugin</artifactId>
      <version>1.3.5</version>
      <configuration>
        <verbose>true</verbose>
        <overwrite>true</overwrite>
      </configuration>
    </plugin>
  </plugins>
</build>
3、在maven项目下的src/main/resources 目录下新增 generatorConfig.xml的配置文件
	配置数据库链接，mapper，dao，xml等路径
4、使用maven命令启动
	mybatis-generator:generate

```



Mapper开发

```
@Select 是查询类的注解，所有的查询均使用这个
@Result 修饰返回的结果集，关联实体类属性和数据库字段一一对应，如果实体类属性和数据库属性名保持一致，就不需要这个属性来修饰。
@Insert 插入数据库使用，直接传入实体类会自动解析属性到对应的值
@Update 负责修改，也可以直接传入对象
@delete 负责删除
```

**注意**

（1）**使用#符号和$符号的不同：**

```java
// This example creates a prepared statement, something like select * from teacher where name = ?;
@Select("Select * from teacher where name = #{name}")
Teacher selectTeachForGivenName(@Param("name") String name);

// This example creates n inlined statement, something like select * from teacher where name = 'someName';
@Select("Select * from teacher where name = '${name}'")
Teacher selectTeachForGivenName(@Param("name") String name);
```

（2）大小写区分

```xml
数据库是小写，这里大写，会不识别，估计是否还有其他配置
<sql id="table"> TABLE </sql>
```

