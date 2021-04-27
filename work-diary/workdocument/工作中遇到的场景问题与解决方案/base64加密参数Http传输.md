
**base64加密参数Http传输**

* 场景：爬虫抓取二维码场景

​		在对Base64加密进行http传输时，后台收到的数据会出现空格的现象，导致传输的数据和接收的数据不一致，导致解密失败

* 原因：base64中，加号（+）是base64编码的一部分，如果将+号转变为空格，就会导致解密失败

* 解决：

  后台处理：JAVA 后端对字符串进行替换  url = url.replaceAll(" ","+");

  前端获取：imgSrc = document.querySelector('canvas').toDataURL("image/png");
