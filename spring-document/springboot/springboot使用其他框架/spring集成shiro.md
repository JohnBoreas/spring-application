一、shiro实现用户登录认证需要三个核心api

`Subject` 用户主体

`SecurityManager` 安全管理器

`Realm` 连接数据的桥梁

二、核心类

（1）Realm：

新建一个`UserRealm.java`类，该类要继承 `AuthorizationRealm`

（2）ShiroConfig

新建一个`ShiroConfig.java` 配置类， 在shiro的配置类中我们需要写三个东西

```java
 /**
     * 创建 ShiroFilterFactoryBean
     */
     @Bean
    public ShiroFilterFactoryBean getShiroFilterFactoryBean (@Qualifier("securityManager") DefaultWebSecurityManager securityManager) {
        ShiroFilterFactoryBean filterFactoryBean = new ShiroFilterFactoryBean();
        // 设置安全管理器
        filterFactoryBean.setSecurityManager(securityManager);
        return filterFactoryBean;
    }
    /**
     * 创建 DefaultWebSecurityManager
     * @Qualifier 注解
     * @Bean 注解里的 name 指定放到spring容器中的名字， 若不写， 默认为方法名
     */
    @Bean(name = "securityManager")
    public DefaultWebSecurityManager getDefaultWebSecurityManager(@Qualifier("userRealm") UserRealm userRealm) {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        // 这里要吧 userRealm 和 securityManager 关联
        securityManager.setRealm(userRealm);
        return securityManager;
    }
    /**
     * 创建 Realm
     * @Bean 的作用： 将该方法返回的对象放入spring容器， 以便给上边的方法使用
     */
    @Bean
    public UserRealm userRealm() {
        return new UserRealm();
    }
```



