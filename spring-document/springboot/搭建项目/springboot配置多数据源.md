多数据源

1、@Primary只能有一个（主数据库）

 'javax.sql.DataSource' available: more than one 'primary' bean found among candidates

2、以下配置会导致无法读到url配置

Failed to configure a DataSource: 'url' attribute is not specified and no embedded datasource could be configured.

需要配置默认的数据源

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
}
## JdbcTemplate多数据源配置
/**
 * JdbcTemplate多数据源配置
 * 依赖于数据源配置
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
}
```

3、常用的配置

```java
@Configuration
public class WealthPasswordConfig {
    /**
     * 配置数据源
     */
    @Bean(name = "wealthPasswordDataSource")
    @ConfigurationProperties(prefix = "spring.datasource.wealth-password")
    public DataSource wealthPasswordDataSource() {
        // 通过HikariDataSource来获取
        return new HikariDataSource();
    }
}
```

