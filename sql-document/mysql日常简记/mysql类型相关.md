一、MySQL字段类型括号含义

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

