#### 一、MySQL中varchar与char的区别以及varchar(50)中的50代表的涵义

**（1）varchar与char的区别**

char是一种固定长度的类型，varchar则是一种可变长度的类型

**（2）varchar(50)中50的涵义**

最多存放50个字符，varchar(50)和(200)存储，硬盘存储空间一样，后者在排序时会消耗更多内存，因为order by col采用fixed_length计算col长度(memory引擎也一样)



**（3）对比char、varchar、text**

按照查询速度：char>varchar>text

vachar：存储文本波动较大的数据，可变长度，varchar每次存储都要有额外的计算，得到长度等工作

char：最大255，自动删除末尾空格，长度不可变



#### 二、MySQL字段类型括号含义：int(11)，bigint(20)

1、int(11)中，11代表的并不是长度，而是字符的显示宽度

2、显示宽度设置为多少，int类型能存储的最大值和最小值永远都是固定的

```sql
/* 首先创建一张表：*/
CREATE TABLE int_demo (
    id INT(11) NOT NULL AUTO_INCREMENT,
    a INT(11) NOT NULL,
    b INT(11) UNSIGNED ZEROFILL NOT NULL,
    c INT(5) DEFAULT NULL,
    d INT(5) UNSIGNED ZEROFILL NOT NULL,
    e INT(15) DEFAULT NULL,
    PRIMARY KEY (`id`)
)
/* 插入两条数据:*/
INSERT INTO int_demo (a, b, c, d, e) VALUES (1, 1, 1, 1, 1);
INSERT INTO int_demo (a, b, c, d, e) VALUES (1234567890, 1234567890, 1234567890, 1234567890, 1234567890);
```

查询结果：

| id   | a          | b           | c          | d          | e          |
| ---- | ---------- | ----------- | ---------- | ---------- | ---------- |
| 1    | 1          | 00000000001 | 1          | 00001      | 1          |
| 2    | 1234567890 | 01234567890 | 1234567890 | 1234567890 | 1234567890 |

`注释`：如果用 navicat 软件查询出来并不会显示左边的 0，但把数据导出时可看到真实的数据，猜测是软件对数据格式进行了处理。



#### 三、时间

datetime：8字节，毫秒

timestamp：4字节，1970至1938

date：3字节，1000至9999，秒