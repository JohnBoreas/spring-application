快捷键

```haskell
生成get/set有2种方式：alt+enter、alt+insert
调用层次：ctrl+alt+h
查看方法在哪里被调用：右键 + find usages选项 or Ctrl + G
Ctrl+N	按名字搜索类
Ctrl+H	查看类的继承关系
Ctrl+Alt+B	查看接口的实现类

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



IDEA中的`.iml`文件是项目标识文件，缺少了这个文件，IDEA就无法识别项目。跟Eclipse的`.project`文件性质是一样的。并且这些文件不同的设备上的内容也会有差异，所以我们在管理项目的时候，`.project和.iml文件都需要忽略掉`。