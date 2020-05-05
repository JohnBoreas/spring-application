Maven

```powershell
1.下载maven

2.解压文件apache-maven-3.X.X

3.配置环境变量 复制maven路径
    a.创建环境变量MAVEN_HOME  D:\maven\apache-maven-3.5.2   （maven路径）
    b.系统变量path添加 %MAVEN_HOME%\bin

4.检查maven环境是否配置成功    Cmd输入 mvn -version

5.配置本地仓库:setting.xml
<localRepository>C:\Users\Administrator\.m2\repository</localRepository>

6.设置maven的远程仓库:setting.xml
<mirror>
    <id>alimaven</id>
    <name>aliyun maven</name>
    <url>http://maven.aliyun.com/nexus/content/groups/public/</url>
    <mirrorOf>central</mirrorOf>       
</mirror>
检验配置生效：cmd 输入  mvn help:system

```

JAVA

```powershell
1.下载解压文件jdk1.8.0_20

2.配置环境变量
    a.JAVA_HOME	变量值：D:\Program Files\Java\jdk1.8.0_20   // 根据自己的实际路径配置
    b.CLASSPATH	变量值：.;%JAVA_HOME%\lib\dt.jar;%JAVA_HOME%\lib\tools.jar;
    c.系统变量Path	变量值：%JAVA_HOME%\bin;%JAVA_HOME%\jre\bin;
```

