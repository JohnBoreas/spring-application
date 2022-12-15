1、生产环境调用Https接口出现这个问题javax.net.ssl.SSLHandshakeException: Received fatal alert: handshake_failure

（1）解决：将jdk版本从1.7升至1.8

【需要设置Java客户端https.protocols环境变量，使用服务端支持的SSL协议版本。】

```java
System.setProperty("https.protocols", "TLSv1.2,TLSv1.1,TLSv1.0,SSLv3");
```

（2）原因：客户端和服务端SSL协议版本不一致。

JDK 8 默认TLSv1.2

JDK 7 默认TLSv1

![img](../..\resource\tlsv.png)

（3）查看网站的ssl

使用火狐浏览器，F12看请求的安全性



2、javax.net.ssl.SSLException: Received fatal alert: internal_error