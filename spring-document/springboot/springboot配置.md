多数据源

1、@Primary

2、

```java
## yml配置文件
spring:
 # 数据源配置
  datasource:
    ds1: #数据源1
      driver-class-name: com.mysql.jdbc.Driver # mysql的驱动你可以配置别的关系型数据库
      url: jdbc:mysql://ip:3306/db1 #数据源地址
      username: root # 用户名
      password: root # 密码
    ds2: # 数据源2
      driver-class-name: com.mysql.jdbc.Driver # mysql的驱动你可以配置别的关系型数据库
      url: jdbc:mysql://ip:3307/db2#数据源地址
      username: root # 用户名
      password: root # 密码
## Config类
/**
 * 多数据源配置
 */
@Configuration
public class DataSourceConfig {

    //主数据源配置 ds1数据源
    @Primary
    @Bean(name = "ds1DataSourceProperties")
    @ConfigurationProperties(prefix = "spring.datasource.ds1")
    public DataSourceProperties ds1DataSourceProperties() {
        return new DataSourceProperties();
    }
    //主数据源 ds1数据源
    @Primary
    @Bean(name = "ds1DataSource")
    public DataSource ds1DataSource(@Qualifier("ds1DataSourceProperties") DataSourceProperties dataSourceProperties) {
        return dataSourceProperties.initializeDataSourceBuilder().build();
    }

    //第二个ds2数据源配置
    @Bean(name = "ds2DataSourceProperties")
    @ConfigurationProperties(prefix = "spring.datasource.ds2")
    public DataSourceProperties ds2DataSourceProperties() {
        return new DataSourceProperties();
    }
    //第二个ds2数据源
    @Bean("ds2DataSource")
    public DataSource ds2DataSource(@Qualifier("ds2DataSourceProperties") DataSourceProperties dataSourceProperties) {
        return dataSourceProperties.initializeDataSourceBuilder().build();
    }

}
## JdbcTemplate多数据源配置
/**
 * JdbcTemplate多数据源配置
 * 依赖于数据源配置
 *
 * @see DataSourceConfig
 */
@Configuration
public class JdbcTemplateDataSourceConfig {

    //JdbcTemplate主数据源ds1数据源
    @Primary
    @Bean(name = "ds1JdbcTemplate")
    public JdbcTemplate ds1JdbcTemplate(@Qualifier("ds1DataSource") DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }

    //JdbcTemplate第二个ds2数据源
    @Bean(name = "ds2JdbcTemplate")
    public JdbcTemplate ds2JdbcTemplate(@Qualifier("ds2DataSource") DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }
}
```

