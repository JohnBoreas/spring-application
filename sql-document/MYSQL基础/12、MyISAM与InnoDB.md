一、MyISAM与InnoDb 数据存储

MyISAM 将数据保存在独立的文件中，

MyISAM 有三种类型的文件：

*.frm 用于存储表的定义，*

*.MYI 用于存放表索引，*

*.MYD 用于存放数据。*

MYD 文件中的数据是以堆表的形式存储的， MyISAM 以堆形式存储数据的，通常叫做 **堆组织表（Heap organized table，简称 HOT）**



 InnoDb 将数据保存在叶子节点中，叫做 **索引组织表（Index organized table，简称 IOT）**



二、索引结构

（1）MyISAM 索引结构

![index-myisam.png](..\resource\Myisam结构.png)

非叶子节点中保存 key，叶子节点中保存着数据的地址，指向数据文件中数据的位置；

必须通过两步才能拿到数据（先获取数据的地址，再读取数据文件）

（2）InnoDb 索引结构

![index-innodb.png](..\resource\innodb结构.png)

叶子节点直接保存数据