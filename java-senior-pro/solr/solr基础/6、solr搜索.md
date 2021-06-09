1、q 

查询关键字，必须的，如果查询所有使用:。 

例如：q=id:1，默认为q=*:*，



2、fq （filter query）

过虑查询，作用：在q查询符合结果中同时是fq查询符合的，

示例：product_price:[1 TO 40]	过滤查询价格从1到40的记录。 

也可以在“q”查询条件中使用product_price:[1 TO 40]



3、sort

排序，格式：sort=<field name>+<desc|asc> [,<field name>+<desc|asc>]… 。

示例： product_price desc



4、start

分页显示使用，开始记录下标，从0开始 



5、rows

指定返回结果最多有多少条记录，配合`start`来实现分页。 

实际开发时，知道当前页码和每页显示的个数最后求出开始下标。



6、fl

指定返回那些字段内容，用逗号或空格分隔多个。

示例： product_price,product_name



7、df

指定一个搜索`Field`

示例： product_name

也可以在SolrCore目录 中conf/solrconfig.xml文件中指定默认搜索Field，指定后就可以直接在“q”查询条件中输入关键字。



8、wt  (writer type)

指定输出格式，可以有 xml, json, php, phps, 后面 solr 1.3增加的，要用通知我们，因为默认没有打开.



9、hl

是否高亮 ，设置高亮`Field`，设置格式前缀和后缀。

