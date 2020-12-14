（1）背景：

Android 7.0 以上机型 抓取 https 包时显示找不到证书，但是 Android 6.0 机型还是可以正常抓包。

（2）原因：

Android 6.0（API 23）及更低版本应用的默认网络安全性配置如下：

```xml
<!-- 默认允许所有明文通信 -->
<base-config cleartextTrafficPermitted="true">
    <trust-anchors>
        <!-- 信任系统预装 CA 证书 -->
        <certificates src="system" />
        <!-- 信任用户添加的 CA 证书，Charles 和 Fiddler 抓包工具安装的证书属于此类 -->
        <certificates src="user" />
    </trust-anchors>
</base-config>
```

Android 7.0（API 24）到 Android 8.1（API 27）的默认网络安全性配置如下：

```xml
<!-- 默认允许所有明文通信 -->
<base-config cleartextTrafficPermitted="true">
    <trust-anchors>
        <!-- 信任系统预装 CA 证书 -->
        <certificates src="system" />
    </trust-anchors>
</base-config>
```

Android 9.0（API 28）及更高版本的默认网络安全性配置如下：

```xml
<!-- 默认禁止所有明文通信 -->
<base-config cleartextTrafficPermitted="false">
    <trust-anchors>
        <!-- 信任系统预装 CA 证书 -->
        <certificates src="system" />
    </trust-anchors>
</base-config>
```

Android 7.0（API 24）到 Android 8.1（API 27），默认不再信任用户添加的 CA 证书，不再信任 Charles 和 Fiddler 抓包工具的证书，所以抓取 HTTPS 包时才会失败。

Android 9.0（API 28）及更高版本上，默认只系统预装的 CA 证书，还默认禁止所有明文通信（不允许 http 请求）。

