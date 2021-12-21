1、通过继承WebMvcConfigurer实现相应的方法，对静态资源进行拦截

WebMvcConfigurer

```java
/**
     * 配置静态访问资源
     * @param registry
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        /** 本地文件上传路径 */
        registry.addResourceHandler(Constants.RESOURCE_PREFIX + "/**").addResourceLocations("file:" + Global.getProfile() + "/");

        /** swagger配置 */
        registry.addResourceHandler("swagger-ui.html").addResourceLocations("classpath:/META-INF/resources/");
        registry.addResourceHandler("/webjars/**").addResourceLocations("classpath:/META-INF/resources/webjars/");

        // addResourceHandler：指的是对外暴露的访问路径 addResourceLocations：指的是内部文件放置的目录
        // ***加入如下路径，才能正常访问到放在static文件夹下的静态资源***
        registry.addResourceHandler("/**").addResourceLocations("classpath:/static/");
    }

    /**
     * 自定义拦截规则
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(localeChangeInterceptor());
        registry.addInterceptor(repeatSubmitInterceptor).addPathPatterns("/**");
    }
```

2、server.servlet.context-path的配置对资源拦截影响

```
server.servlet.context-path=/path
```

（1）application.yml 中配置了 server.servlet.context-path=/path，那么我访问服务端资源的时候就必须加上 /path

（2）必须下面一句才能对静态资源进行访问，否则前端带相对路径时候无法访问

registry.addResourceHandler("/path/**").addResourceLocations("classpath:/static/");



3、HandlerInterceptor

最常用的登录拦截、或是权限校验、或是防重复提交

```java
// 有三个实现方法

// preHandle：在业务处理器处理请求之前被调用。预处理，可以进行编码、安全控制、权限校验等处理；
// 返回false会将静态资源拦截掉
boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception;
// postHandle：在业务处理器处理请求执行完成后，生成视图之前执行。后处理（调用了Service并返回ModelAndView，但未进行页面渲染）
void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception;
// afterCompletion：在DispatcherServlet完全处理完请求后被调用，可用于清理资源等。返回处理（已经渲染了页面）；
void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception;
```

