快捷键

```haskell
生成get/set有2种方式：alt+enter、alt+insert
调用层次：ctrl+alt+h
查看方法在哪里被调用：右键 + find usages选项 or Ctrl + G

Ctrl+N	按名字搜索类
Ctrl+Shift+N	这个安装是使用文件名来进行搜索
Ctrl+H	查看类的继承关系
Ctrl+Alt+H	查看一个类调用与被调用的关系
Ctrl+Alt+B	查看接口的实现类
Ctrl+F/Ctrl+Shift+F	全局中查找
Shift+Shift	查看其中的任意文件

Ctrl + Alt +O 删除无用import

Ctrl+Alt+L	格式化代码快捷键
Ctrl+Alt+shift+C	复制包名类名

Ctrl+Alt+shift+U	查看图形形式的继承链

sout	System.out.println();
fori	自动创建一个for循环
psvm	public static void main
```

Intellij IDEA 将工程转换成maven工程

```
1> 右键工程，点击 Add Framework Support
2> 选中 Maven，再点击 OK
3> 同理可以转成其他项目或者加入其他模块
```

IDEA配置

```shell
## 查看类全路径或包名称
File–> settings --> Editor --> General --> 勾选show quick documentation on mouse move


```



Web项目运行

```
参考：https://blog.csdn.net/helihongzhizhuo/article/details/80347475

1、配置Modules --> Artifacts 点击＋号，选中 Web Application:Exploded,配置一下即可

2、配置Tomcat --> Configurations  菜单栏【run】-【Edit Configurations】或 右上角有个向下的小箭头

3、新建Tomcat Server 弹出的页面中，点击下面圈中的加号，然后Tomcat Server 然后local

4、配置Tomcat  --> Deployment， 加一个Artifact ,配置 Application context 
```



IDEA运行maven项目报错：包不存在，运行mvn idea:idea命令



IDEA中的`.iml`文件是项目标识文件，缺少了这个文件，IDEA就无法识别项目。跟Eclipse的`.project`文件性质是一样的。并且这些文件不同的设备上的内容也会有差异，所以我们在管理项目的时候，`.project和.iml文件都需要忽略掉`。



**IDEA2020使用爬坑**

```shell
1、运行maven项目，找不到程序包和符号，其他版本idea可以
maven自己加的localRepository删掉，用默认的，如果不想下在C盘，就在idea手动修改本地仓库的配置。
2、
```



**IDEA实用插件**

1、jclasslib ByteCode Viewer：用于查看字节码



