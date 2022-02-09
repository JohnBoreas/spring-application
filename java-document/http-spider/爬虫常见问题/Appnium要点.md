**appnium工作原理：**

 （1）appnium使用了webdriver的json wire协议来驱动apple系统的uiautomation库,android系统的uiautomator框架。

 （2）appnium选择了client-server(c/s)的设计模式，

  在android端，appnium基于webdriver协议，利用bootstrap.jar，最后通过调用uiautomator命令，从而实现app的



#### 一丶测试过程中常见问题汇总：

 （1）手机连接电脑后，状态显示不正确：

  解:	1)确认开发者模式已开。

  		 2)确认usb连接方式为文件而非充电模式。

 （2）查看adb端口是否被占用：

  解:	1)可以设置环境变量Android_ADB_SERVER_PORT并修改测试配置文件端口调整。

 		  2)查看端口是否被占用：

```
netstat -ano|findstr 端口号      				
```

 （3）设置其他端口：

  解:	1)设置环境变量Android_ADB_SERVER_PORT.

   		2)修改自动化端口配置：

​      welink--config--config.py--修改ADB_PORT的设置。

 （4）pycharm界面右键列表上无git工具选项显示的解决方案：

   setting--version control--点击添加“+”--输入正确的路径。

 （5）如果在导包的时候出现全部报错的解决方案：

   settings--project:welinkAutoTest--android...--project structure--修改project路径。

 （6）出现“每个套接字地址只允许使用一次”报错提示的解决方案：

   (1)win+R打开运行窗口，输入cmd并回车。

   (2) 输入netstat -ano|findstr 并回车。

   (3)找到listening项，查看该进程的端口号。

   (4)输入tasklist/fi "pid eq端口号"。

   (5)输入taskkill/pid端口号/f来终止进程。

```shell
一般情况是可能4724被占用了, appnium的listener端口被占用
```

