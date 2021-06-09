**每个集合都有一份属于自己的 schema 文件**



一、模式（2种模式）

Solr中提供了两种方式来配置schema，但两者只能选其一。

（1）托管模式（5.5以后默认）

未声明<schemaFactory/>时使用该模式，

```xml
<schemaFactory class="ManagedIndexSchemaFactory">
    <bool name="mutable">true</bool>
    <str name="managedSchemaResourceName">managed-schema</str>       // schema文件名
</schemaFactory>
```

（2）schema.xml模式

```xml
<schemaFactory class="ClassicIndexSchemaFactory"/>
```



二、标签

（1）field

表示字段，相当于 Java 中的类成员变量

```xml
<field name="id" type="string" multiValued="false" indexed="true" required="true" stored="true"/>

<!-- 属性 -->
name		字段名称
type		字段类型，对应 fieldType
default		字段默认值
indexed		该字段数据是否进行索引，默认为 false
stored		该字段数据是否进行存储，默认为 false
multiValued	该字段是否有多个值，默认为 false
required	该字段是否为必填项，默认为 false
```



（2）fieldType

表示字段类型，相当于 Java 中的数据类型

`field` 标签的 `type` 属性对应 `fieldType` 标签的 `name` 属性。

```xml
<fieldType name="string" class="solr.StrField" sortMissingLast="true" docValues="true"/>
```



属性：

```less
name：	字段类型名称，用于Field定义中的type属性引用

class：	存放该类型的值来进行索引的字段类名（同lucene中Field的子类）。注意，应以 *solr.*为前缀，这样solr就可以很快定位到该到哪个包中去查找类，如 solr.TextField 。如果使用的是第三方包的类，则需要用全限定名。solr.TextField 的全限定名为：org.apache.solr.schema.TextField。

positionIncrementGap：用于多值字段，定义多值间的间隔，来阻止假的短语匹配

autoGeneratePhraseQueries：用于文本字段，如果设为true，solr会自动对该字段的查询生成短语查询，即使搜索文本没带“”

synonymQueryStyle：同义词查询分值计算方式

enableGraphQueries：是否支持图表查询

docValuesFormat：docValues字段的存储格式化器：schema-aware codec，配置在solrconfig.xml中的

postingsFormat：词条格式器：schema-aware codec，配置在solrconfig.xml中的
```



（3）FieldType的Analyzer

```xml
<!-- 分词器 -->
<fieldType name="text_ik" class="solr.TextField">
   <!-- 索引 -->
   <analyzer type="index">
      <tokenizer class="org.wltea.analyzer.lucene.IKTokenizerFactory" conf="ik.conf" useSmart="false"/>
      <filter class="solr.LowerCaseFilterFactory"/>
   </analyzer>
   <!-- 查询 --> 
   <analyzer type="query">
      <tokenizer class="org.wltea.analyzer.lucene.IKTokenizerFactory" conf="ik.conf" useSmart="true"/>
      <filter class="solr.LowerCaseFilterFactory"/>
   </analyzer>
</fieldType>

<!-- 组合analyzer -->
<fieldType name="nametext" class="solr.TextField">
  <analyzer type="index">
    <tokenizer class="solr.StandardTokenizerFactory"/>
    <filter class="solr.LowerCaseFilterFactory"/>
    <filter class="solr.KeepWordFilterFactory" words="keepwords.txt"/>
    <filter class="solr.SynonymFilterFactory" synonyms="syns.txt"/>
  </analyzer>
  <analyzer type="query">
    <tokenizer class="solr.StandardTokenizerFactory"/>
    <filter class="solr.LowerCaseFilterFactory"/>
  </analyzer>
</fieldType>
```



（4）dynamicField

表示动态字段

```xml
借助通配符 *，实现字段的动态匹配。
<dynamicField name="*_i" type="int" indexed="true"  stored="true"/>
```



（5）copyField

作用：将一个 `field` 的数据复制到另一个 `field` 中

```xml
<schema name="example" version="1.6">
    <!-- field -->
    <field name="title" type="text" indexed="true" stored="false"/>
    <field name="description" type="text" indexed="true" stored="false"/>
    <field name="text" type="text" indexed="true" stored="false"  multiValued="true"/>
    <!-- copyField -->
    <!-- title 和 description 的内容复制合并后赋予 text 字段 -->
    <copyField source="title" dest="text"/>
    <copyField source="description" dest="text"/>
</schema>
```



（6）UniqueKey 唯一键

```xml
<!--指定商品ID为唯一键，类似于传统数据库的主键ID-->
<uniqueKey>productId<uniqueKey>
```