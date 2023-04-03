1. solr.cmd start

2. solr.cmd -c create xc_core

##### Solr运算符 

    (1) “:” 指定字段查指定值，如返回所有值*:* 
    
    (2) “?” 表示单个任意字符的通配 
    
    (3) “*” 表示多个任意字符的通配（不能在检索的项开始使用*或者?符号）
    
    (4) “~” 表示模糊检索，如检索拼写类似于”roam”的项这样写：roam~将找到形如foam和roams的单词；roam~0.8，检索返回相似度在0.8以上的记录。
    
    (5) 邻近检索，如检索相隔10个单词的”apache”和”jakarta”，”jakarta apache”~10
    
    (6) “^” 控制相关度检索，如检索jakarta apache，同时希望去让”jakarta”的相关度更加好，那么在其后加上”^”符号和增量值，即jakarta^4 apache 
    
    (7) 布尔操作符AND、|| 
    
    (8) 布尔操作符OR、&& 
    
    (9) 布尔操作符NOT、!、- （排除操作符不能单独与项使用构成查询） 
    
    (10) “+” 存在操作符，要求符号”+”后的项必须在文档相应的域中存在
    
    (11) ( ) 用于构成子查询 
    
    (12) [] 包含范围检索，如检索某时间段记录，包含头尾，date:[200707 TO 200710] 
    
    (13) {} 不包含范围检索，如检索某时间段记录，不包含头尾，date:{200707 TO 200710}
    
    (14) / 转义操作符，特殊字符包括+ - && || ! ( ) { } [ ] ^ ” ~ * ? : / 
    
    *注：① “+”和”-“表示对单个查询单元的修饰，and 、or 、 not 是对两个查询单元是否做交集或者做差集还是取反的操作的符号* 　　 
        比如:AB:china +AB:america ,表示的是AB:china忽略不计可有可无，必须满足第二个条件才是对的,而不是你所认为的必须满足这两个搜索条件 　　 
        如果输入:AB:china AND AB:america ,解析出来的结果是两个条件同时满足，即+AB:china AND +AB:america或+AB:china +AB:america 　　 
    
    总而言之，查询语法： 修饰符 字段名:查询关键词 AND/OR/NOT 修饰符 字段名:查询关键词 

###### Solr查询语法

    (1) 普通的查询，
    
        比如查询姓张的人（ Name:张）,如果是精准性搜索相当于SQL SERVER中的LIKE搜索这需要带引号（""）,比如查询含有北京的（Address:"北京"）
    
    (2) 多条件查询，
    
        如果是针对单个字段进行搜索的可以用（Name:搜索条件加运算符(OR、AND、NOT) Name：搜索条件）, 
        比如模糊查询（ Name:张 OR Name:李 ）单个字段多条件搜索不建议这样写，一般建议是在单个字段里进行条件筛选，
        如（ Name:张 OR 李），多个字段查询（Name:张 + Address:北京 ）
    
    (3) 排序 
        
        比如根据姓名升序（Name asc）,降序（Name desc）


​        
###### 参数

    q	            查询字符串，这个是必须的。如果查询所有*:* ，根据指定字段查询（Name:张三 AND Address:北京）
                    可以采取solr的自带机制双引号（""，注意是英文引号）来将关键字括起来查询，如果有多个关键字待查询


​        
    fq	            （filter query）过虑查询，作用：在q查询符合结果中同时是fq查询符合的，
                    例如：q=Name:张三&fq=CreateDate:[20081001 TO 20091031],找关键字mm，并且CreateDate是20081001
        
    fl	            指定返回那些字段内容，用逗号或空格分隔多个。
        
    start	        返回第一条记录在完整找到结果中的偏移位置，0开始，一般分页用。
        
    rows	        指定返回结果最多有多少条记录，配合start来实现分页。
        
    sort	        排序，格式：sort=+<desc|asc>[,+<desc|asc>]… 。
                    示例：（score desc, price asc）表示先 “score” 降序, 再 “price” 升序，默认是相关性降序。
        
    wt	            (writer type)指定输出格式，可以有 xml, json, php, phps, python、ruby、csv
        
    indent	        是否需要将请求响应的结果以缩进的方式显示出来，使结果更具可读性，类似格式化显示 xml、json 等功能
        
    debugQuery	    是否需要显示调试信息，包括每个文档返回的“解释信息”，以便理解和管理程序的执行过程
        
    dismax	        是否启用 Dismax 查询解析器
        
    edismax	        是否启用扩展查询解析器
    
    df              default search field，默认查询field。
        
    fl	            表示索引显示那些field( *表示所有field,如果想查询指定字段用逗号或空格隔开
                    如：Name,SKU,ShortDescription或Name SKU ShortDescription【注：字段是严格区分大小写的】）
        
    q.op	        表示 q 中查询语句的各条件的逻辑操作 AND(与) OR(或)
        
    hl	            是否高亮 ,如hl=true
        
    hl.fl	        高亮field ,hl.fl=Name,SKU
        
    hl.snippets	    默认是1,这里设置为3个片段
    
    hl.simple.pre	高亮前面的格式
    
    hl.simple.post	高亮后面的格式
    
    facet	        是否启动统计
        
    facet.field	    统计field
        
    spatial 	    是否启用位置数据，该功能在地理或者空间搜索中使用
        
    spellcheck	    是否启用拼写检查，提供在线的模糊匹配或者专业术语匹配等建议
    
    q.alt:          当q字段为空时，用于设置缺省的query，通常设置q.alt为*:*。 如q.alt = title:计算机
    
    qf:query fields，指定solr从哪些field中搜索。 如qf=title mm: Minimum ‘Should’ Match。 
                     Solr支持三种查询clause，即“必须出现”， “不能出现”和“可以出现”，分别对应于AND, -, OR  
    
    pf: boosting phrases over words。
                    用于指定一组field，当query完全匹配pf指定的某一个field时，来进行boost，给搜索匹配到的字段打分  如pf =字段1^0.5 字段2^0.2     
    
    ps: Phrase Slop. 短语坡度。短语查询的坡度量用在pf字段，影响boost
    
    qs:Query Phrase Slop。查询短语坡度。查询短语坡度是指短语查询明确包含用户查询的字符串(在qf字段，影响匹配)    
    
    tie：tie breaker。float值作为决胜局中DisjunctionMaxQueries使用（应该是远小于1）    
    
    bq: Boost Query。对某个field的value进行boost，例如brand:xq^5.0    
    
    bf :Boost Functions。用函数的方式计算boost   
    
    uf:User Fields。用户字段。制定模式的字段可以被用户显示的查询。此参数支持通配符  
    
    pf2：Phrase bigram fields。短语两字母字段。e.g. “the brown fox jumped” is queried as “the brown” “brown fox” “fox jumped” 
    
    pf3：Phrase trigram fields。短语三字母字段。e.g. “the brown fox jumped” is queried as “the brown fox” “brown fox jumped”  
    
    ps2：短语两字母坡度。如果未指定，将使用”ps”   
    
    ps3：短语三字母坡度。如果未指定，将使用”ps”   
    
    boost：Boost Function, multiplicative。作为bf，score=bf*score。bf =sum(div(字段,100),1) 
    
    stopwords：单词停用，true 或false  
    
    lowercaseOperators:此参数用于控制小写单词作为布尔运算符，如”and” and “or”。设置与lowercaseOperators= true来允许此。默认为true   

###### 检索运算符(补充说明)

    : 指定字段查指定值，如返回所有值:
    
    ? 表示单个任意字符的通配
    
    * 表示多个任意字符的通配（不能在检索的项开始使用*或者?符号）
    
    ~ 表示模糊检索，如检索拼写类似于"roam"的项这样写：roam~将找到形如foam和roams的单词；roam~0.8，
      检索返回相似度在0.8以上的记录。 邻近检索，如检索相隔10个单词的"apache"和"jakarta"，"jakarta apache"~10
    
    ^ 控制相关度检索，如检索jakarta apache，同时希望去让"jakarta"的相关度更加好，那么在其后加上""符号和增量值，即jakarta4 apache
    
    布尔操作符 AND、||
    
    布尔操作符 OR、&&
    
    布尔操作符 NOT、!、-（排除操作符不能单独与项使用构成查询）
    
    + 存在操作符，要求符号"+"后的项必须在文档相应的域中存在
    
    () 用于构成子查询
    
    [] 包含范围检索，如检索某时间段记录，包含头尾，date:[200707 TO 200710]
    
    {}不包含范围检索，如检索某时间段记录，不包含头尾，date:{200707 TO 200710}
    
    " 转义操作符，特殊字符包括+ - && || ! ( ) { } [ ] ^ " ~ * ? : "748/

   ######## 注意
     + - && || ! ( ) { } [ ] ^ " ~ * ? : /
     这些字符在solr中具有特殊的含义，如果要使用这么字符本身含义，需要利用反斜杠进行转义，比如： \(1\+1\):2
     
   ####### 示例

     *号可以用在范围查询的开始或结束
         field:[* TO 100] field值小于等于100
         field:[100 TO *] field值大于等于100
         field:[* TO *] 匹配包含field字段的所有文档
         
     支持单独出现的否定查询
         -inStock:false field值inStack是false
         -field:[* TO *] 匹配field无值的所有文档
         
     支持函数查询
         val:myfield
         val:”recip(rord(myfield),1,2,3)”
         
     支持多种类型的查询解析器
        inStock:true OR {!dismax qf=’name manu’ v=’ipod’}
        
     支持过滤器缓存
         inStock:true会被缓存并在以下三个查询中重用。
         q=features:songs OR filter(inStock:true)
         q=+manu:Apple +filter(inStock:true)
         q=+manu:Apple & fq=inStock:true
         
     范围查询、前缀查询和通配符查询都可以指定文档的score是常量
    
     solr支持在基本查询语句中加入局部参数，
        比如可以在 q=solr rocks 中加入参数以修改操作符和默认的查找字段： q={!q.op=AND df=title}solr rocks
        语法规定如下：
         (1)以{!开始
         (2)后跟任意数量的用空格分隔的key=value对
         (3)以}结束，后跟查询语法


######  Solr5及其之后的版本(内置了jetty)被发布成了一个独立的应用，通过bin目录的脚本直接启动

    (1) SolrCore的管理功能。
        
        一个 solr服务器 可以有 多个SolrCore，每个SolrCore 就是一个 独立的 索引库
    
    (2) Core Admin中主要有主要有: 
    
        > Add Core (添加核心) 
        
        > Unload  （卸载核心）
         
        > Rename  （重命名核心）
        
        > Reload  (重新加载核心) 
        
        > Optimize（优化索引库）


##### Solr8 配置分词

    （1）Solr8自带分词器
        切换到 /data/solr-8.8.2/server/solr-webapp/webapp/WEB-INF/lib/ 然后执行将分词jar拷贝到当前目录
        <1> cp /data/solr-8.8.2/contrib/analysis-extras/lucene-libs/lucene-analyzers-smartcn-8.8.2.jar .
        
        <2> 然后对应core的conf目录下修改配置文件managed-schema添加以下配置
            <!-- 配置自定义内置中文分词器 -->
        	<fieldType name="solr_cnAnalyzer" class="solr.TextField" positionIncrementGap="100">
              <analyzer type="index">
                <tokenizer class="org.apache.lucene.analysis.cn.smart.HMMChineseTokenizerFactory"/>
              </analyzer>
        	  <analyzer type="query">
                <tokenizer class="org.apache.lucene.analysis.cn.smart.HMMChineseTokenizerFactory"/>
              </analyzer>
            </fieldType>
            
            <!-- 配置自定义三方中文分词器 IkAnalyzer 自行百度 -->
            
            下载地址：https://search.maven.org/search?q=com.github.magese （8.X版本）
            
            <fieldType name="text_ik" class="solr.TextField">
                <analyzer type="index">
                    <tokenizer class="org.wltea.analyzer.lucene.IKTokenizerFactory" conf="ik.conf" useSmart="false"/>
                    <filter class="solr.LowerCaseFilterFactory"/>
                </analyzer>
                <analyzer type="query">
                    <tokenizer class="org.wltea.analyzer.lucene.IKTokenizerFactory" conf="ik.conf" useSmart="true"/>
                    <filter class="solr.LowerCaseFilterFactory"/>
                </analyzer>
            </fieldType>




































