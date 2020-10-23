#### appium安装使用

##### 1、安装

```shell
（1）java环境
classpath：	.;%JAVA_HOME%\lib;%JAVA_HOME%\lib\tools.jar
path：		%JAVA_HOME%\bin
（2）sdk环境
path：		;%ANDROID_HOME%\platform-tools;%ANDROID_HOME%\tools
（3）node.js
（4）安装appuim
npm install -g appium@1.5
（5）安装appium-doctor
npm install -g appium-doctor
（6）验证
appium-doctor
```

##### 2、查找Activity包

```shell
###命令
adb shell dumpsys activity activities > C:\Users\xxx\Desktop\aa.txt
或者
adb shell
dumpsys activity | grep mFocusedActivity
###############################
realActivity=com.taobao.taobao/com.taobao.tao.welcome.Welcome
autoRemoveRecents=false isPersistable=true numFullscreen=1 taskType=0 mTaskToReturnTo=1
#############################
ps：
com.tencent.mm/.ui.LauncherUI
【/】不需要

##############################################
##查看包
adb shell pm list package -f taobao
##查看activity
adb shell
monkey -p 包名 -vvv 1
```

