##### Windows下Android环境安装

```powershell
1、java -version
查看java版本，安装相应的java环境
## JAVA_HOME：
## path：%JAVA_HOME%\bin;%JAVA_HOME%\jre\bin;
## CLASSPATH：.;%JAVA_HOME%\lib\dt.jar;%JAVA_HOME%\lib\tools.jar;
检验：cmd：javac

2、安卓环境
## ANDROID_HOME：
## PATH：%ANDROID_HOME%\tools;%ANDROID_HOME%\platform-tools;
检验：cmd：adb

3、nodejs
## Path：C:\Program Files\nodejs;
检验：cmd：node -v

4、安装appuim
npm install -g appium@1.5
  安装appium-doctor
npm install -g appium-doctor
检验：cmd：appium-doctor

或者：npm install appium –g(国外很慢)

new:
## 使用国内镜像
npm install -g cnpm --registry=https://registry.npm.taobao.org
## 安装最新版本
cnpm install -g appium  # 安装指定版本 cnpm install appium@1.7.2 -g
## 查看版本
appium -v
## 安装Appium-Python-Client（不需要）
pip install Appium-Python-Client
## 安装 appium-doctor
cnpm install appium-doctor -g
## 检查appium-doctor
appium-doctor

5、安装Android Interface
（如果adb devices无法识别或者adb interface有黄色感叹号）
详见：win7连接Android设备失败的问题
```

