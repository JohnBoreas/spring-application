一、Sql分组查询后取每组的前N条记录

```sql
// 方法1
SELECT * FROM student_grade t WHERE (
	SELECT COUNT(1) FROM student_grade b 
	WHERE b.subId = t.subId AND b.grade >= t.grade
) <= 10
ORDER BY subId, grade DESC
// 方法2
SELECT e.`subId`, e.`grade`
FROM student_grade e
INNER JOIN student_grade et
ON e.`subId` = et.`subId` AND e.`grade` <= et.`grade`
GROUP BY e.`subId`, e.`grade`;
```

（1）数据分组(group by ): 

select 列a,聚合函数（聚合函数规范） from 表名 where 过滤条件 group by 列a 
group by 字句也和where条件语句结合在一起使用。当结合在一起时，where在前，group by 在后。即先对select xx from xx的记录集合用where进行筛选，然后再使用group by 对筛选后的结果进行分组。 

（2）使用having字句对分组后的结果进行筛选，语法和where差不多:having 条件表达式 

需要注意having和where的用法区别： 
1.having只能用在group by之后，对分组后的结果进行筛选(即使用having的前提条件是分组)。 
2.where肯定在group by 之前，即也在having之前。 
3.where后的条件表达式里不允许使用聚合函数，而having可以。 

（3）当一个查询语句同时出现了where,group by,having,order by的时候，执行顺序和编写顺序是： 

1.执行where xx对全表数据做筛选，返回第1个结果集。 
2.针对第1个结果集使用group by分组，返回第2个结果集。 
3.针对第2个结果集中的每1组数据执行select xx，有几组就执行几次，返回第3个结果集。 
4.针对第3个结集执行having xx进行筛选，返回第4个结果集。 5.针对第4个结果集排序。 