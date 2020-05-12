ServletContextListener

​		Servlet API 中有一个 ServletContextListener 接口，它能够监听 ServletContext 对象的生命周期，实际上就是监听 Web 应用的生命周期。

　　当Servlet 容器启动或终止Web 应用时，会触发ServletContextEvent 事件，该事件由ServletContextListener 来处理。在 ServletContextListener 接口中定义了处理ServletContextEvent 事件的两个方法。

```java
/**
  * 当Servlet 容器终止Web 应用时调用该方法。在调用该方法之前，容器会先销毁所有的Servlet和Filter过滤器。
  * @param arg0
  */
@Override
public void contextDestroyed(ServletContextEvent arg0) {}
/**
  * 当Servlet 容器启动Web 应用时调用该方法。在调用完该方法之后，容器再对Filter 初始化，
  * 并且对那些在Web 应用启动时就需要被初始化的Servlet 进行初始化。
  * @param arg0
  */
@Override
public void contextInitialized(ServletContextEvent arg0) {}
```

使用：

web.xml文件的web-app节点里添加

```xml
<listener>
    <listener-class>com.projects.listener.MyServletContextListener</listener-class>
</listener>
```

