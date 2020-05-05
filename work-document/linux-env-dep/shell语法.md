#### shell

##### 1、例子

```shell
#!/bin/bash

#while : ;
for str in "192.168.0.1:80" "192.168.0.2:80"
do
    current=$(date "+%Y-%m-%d %H-%M-%S")
    info="current curl>>>>>>>>>ip:${str}, time:${current}"
    time=$(date "+%Y-%m-%d")
    echo $str
    fileresponse=ip_response${time}.txt
    echo $info >> ${fileresponse}
    curl -I -x $str "https://detail.tmall.com/item.htm?id=557061371824" >> $fileresponse
    sleep 1
    filehtml=ip_html${time}.txt
    filereponse=ip_q_res${time}.txt
    curl -x $str "https://s.taobao.com/search?q=三生三世" >> $filereponse
    sleep 1
    curl -x $str "https://detail.tmall.com/item.htm?id=557061371824" >> $filehtml
    sleep 1
done

```

