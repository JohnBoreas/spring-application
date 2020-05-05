选好服务器后进行

* Linux上操作

  > ```shell
  > ## 未安装wget命令需执行
  > yum install wget -y
  > 
  > wget --no-check-certificate https://raw.githubusercontent.com/teddysun/shadowsocks_install/master/shadowsocksR.sh
  > 
  > chmod +x shadowsocksR.sh
  > 
  > ./shadowsocksR.sh 2>&1 | tee shadowsocksR.log
  > 
  > ## 设置端口（port）和密码（password），加密方式,协议,混淆,端口
  > chacha20
  > auth_sha1_v4
  > 
  > Your Server IP        :  207.148.91.17 
  > Your Server Port      :  31280 
  > Your Password         :  myboreas 
  > Your Protocol         :  auth_sha1_v4 
  > Your obfs             :  http_simple 
  > Your Encryption Method:  chacha20 
  > 
  > 
  > ## 谷歌bbr加速
  > wget --no-check-certificate https://github.com/teddysun/across/raw/master/bbr.sh
  > 
  > chmod +x bbr.sh
  > 
  > ./bbr.sh
  > 
  > 
  > ```

