

### 1.查看所有数据库容量大小

```sql
SELECT
table_schema AS '数据库',
SUM(table_rows) AS '记录数',
SUM(TRUNCATE(data_length/1024/1024, 2)) AS '数据容量(MB)',
SUM(TRUNCATE(index_length/1024/1024, 2)) AS '索引容量(MB)'
FROM information_schema.tables
GROUP BY table_schema
ORDER BY SUM(data_length) DESC, SUM(index_length) DESC;
```

### 2.查看所有数据库各表容量大小

```sql
select table_schema as '数据库', table_name as '表名', table_rows as '记录数',
truncate(data_length/1024/1024/1024, 2) as '数据容量(GB)',
truncate(index_length/1024/1024/1024, 2) as '索引容量(GB)'
from information_schema.tables
order by data_length desc, index_length desc;
```

### 3.查看指定数据库容量大小

```sql
select
table_schema ``as` `'数据库'``,
sum(table_rows) ``as` `'记录数'``,
sum(truncate(data_length/1024/1024, 2)) ``as` `'数据容量(MB)'``,
sum(truncate(index_length/1024/1024, 2)) ``as` `'索引容量(MB)'
from` `information_schema.tables
where` `table_schema=``'mysql'``;
```

### 4.查看指定数据库各表容量大小

```sql
select
table_schema ``as` `'数据库'``,
table_name ``as` `'表名'``,
table_rows ``as` `'记录数'``,
truncate(data_length/1024/1024, 2) ``as` `'数据容量(MB)'``,
truncate(index_length/1024/1024, 2) ``as` `'索引容量(MB)'
from` `information_schema.tables
where` `table_schema=``'mysql'
order ``by` `data_length desc, index_length desc
```