ActiveMq

一、启动报错

Cannot send, channel has already failed: tcp://192.168.xxx.xxx:8161

原因：8161(管理端口)
			61616(服务端口)

解决：将端口配置改成61616