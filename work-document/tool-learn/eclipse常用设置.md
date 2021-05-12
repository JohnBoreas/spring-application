##### 配置环境eclipse·：

**1. 在eclipse中，设置字符集**

　　Windows -> Preferences -> General -> Workspace -> Text file encoding -> Other  , 修改成 UTF-8

**2. 设置合适的字体大小**

　　Windows -> Preferences -> General -> Editors -> Text Editors -> Colors and Fonts  -> Text Font  , 个人习惯设置成 Consolas 11

　　或者 Window -> Preferences -> General -> Appearance -> Content Assist -> Colors and Fornts -> Text Font

**3. 设置Java格式化默认长度**

　　Windows -> Preferences -> Java -> Code Style -> Formatter -> Edit -> Line Wrapping -> Maxmum line width  , 大小可以自己设置，个人习惯设置成120.



##### 其他常用设置：

**1. 代码自动提示**

　　Window -> Preferences -> Java -> Editor -> Content Assist -> Auto Activation

　　把"."修改为"abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ"， 这样就会 输入每个字母都提示。



　　同样的方法，可以修改Javascript和HTML页面的代码提示：

　　Window -> Preferences -> JavaScript-> Editor -> Content Assist -> Auto-Activation

　　Window -> Preferences -> Web -> HTML Files -> Editor -> Content Assist -> Auto Activation

 

**2. 设置JSP文件编码**

　　Window -> Preferences -> Web -> JSP Files -> Encoding  (一般是设置为UTF-8)

 

**3. 设置JDK 本地JavaDOC API的路径 以及 源码**

　　设置JDk：  Window -> Preferences -> Java -> Installed JREs

　　点击右侧的 Edit；全选 JRE system libraries 下的所有jar 包， 点击右侧的 Source Attachment



 **4. Show View （****如果需要任何未显示的View界面，都可以通过Windows -> Show View打开****）**

　　Windows -> Show View 

 

 **5. 高亮显示选中的变量**

　　Windows -> Preferences -> Java -> Editor -> Mark Occurences -> 第一行有个“Annotations”点击进入，显示出列表，找到 Occurrence annotation 右边选择Color即可。



 **6. Eclipse中编辑代码，输入main或者syso有完整的代码提示**

Windows -> Preferences -> General -> Keys -> 搜索框输入 Content Assist,

　　看第一个的快捷键是否是 Alt + /

　　设置： 在when下拉列表中选中"Editing Java Source"或"Edition XML Source"或其它的选项，然后让Binding文本框获得焦点，键盘按Alt+/键，设置快捷键，最后点击Apply按钮，设置完成。



**7. 普通java项目，通过Build Path引入新的jar文件，展开在项目中，看起来比较凌乱 （正常情况下外部jar包是放在****Referenced Libraries**下面）

　　原因： 检查Eclipse中左侧，看View方式是不是**Project Explorer**，一般是用这个打开就会出现上面的情况。

　　设置：用Package Explorer 的方式显示， Windows -> Show View -> Package Explorer (如果没找到，在Other下面去找)



**8. 如何在Eclipse中调整package的上下顺序**

　　设置： 右键项目 -> Build Path -> Configure Build Path -> 右侧tab，Order and Export -> Up or Down ， 设置完，记得apply。