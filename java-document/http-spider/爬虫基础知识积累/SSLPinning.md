#### 什么是SSLPinning？

SSL Pinning是一种防止中间人攻击（MITM）的技术，

主要机制是

在客户端发起请求–>收到服务器发来的证书进行校验，如果收到的证书不被客户端信任，就直接断开连接不继续求情。



#### 原理

使用抓包工具抓包时，抓包工具在拦截了服务端返回的内容并重新发给客户端的时候使用证书不是服务器端原来的证书，而是抓包工具自己的，抓包工具原来的证书并不是APP开发者设定的服务端原本的证书，于是就构成了中间人攻击，触发SSL Pinning机制导致链接中断，所以我们无法直接抓到包。



常见的开启了SSL Pinning的APP大致分为两种操作：

1、服务端使用了某个权威证书颁发机构（CA）颁发的证书，并且在APP中校验证书是否正常；

2、服务端使用了CA颁发的证书或者自己给自己颁发证书，并且在APP中校验证书本身是否正常的，需要将证书与APP本体一同下发。有把证书混淆在代码里面藏起来的，也有直接放在资源目录下的。



#### 解决方案

1、直接使用低版本系统化的安卓手机（低于7.0）

2、使用Xposed或兼容Xposed的框架 + JustTrustMe（32 位应用的版本使用0.18.2 版本）

3、将抓包工具的证书直接安装到系统根目录

4、VirtualXposed（VirtualAPP）



针对IOS的App绕过SSL Pinning使用 [SSL Kill Switch 2](https://github.com/nabla-c0d3/ssl-kill-switch2)

需要准备的有：

- 一部越狱的IOS手机
- Debian Packager插件
- Cydia Substrate插件
- PreferenceLoader插件
- SSL Kill Switch2的deb包