**一、安装证书fiddlercentmarker**

1、下载fiddlercentmarker.exe,安装好后点击确认，重启fiddler（记得重启）

![img](../../resource/fiddle插件下载.png)

下载地址：https://www.telerik.com/fiddler/add-ons

插件名称：CertMaker for iOS and Android

2、导出证书到本地电脑，在Toos–>HTTPS–>actions–>trust root certificate

![img](../../resource/fiddle导入证书.png)



3、如图操作

![img](../../resource/fiddle安装插件.png)





二、解决配置fiddler时信任证书

报：Unable to configure Windows to Trust the Fiddler Root certificate.The LOG tab may contain more infor

使用cmd命令，找到fiddler的安装路径，在该路径下执行如下命令：

makecert.exe -r -ss my -n "CN=DO_NOT_TRUST_FiddlerRoot, O=DO_NOT_TRUST, OU=Created by http://www.fiddler2.com" -sky signature -eku 1.3.6.1.5.5.7.3.1 -h 1 -cy authority -a sha1 -m 120 -b 10/12/2021

