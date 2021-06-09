Solr中强烈推荐使用Schema API来管理集合/内核的模式信息，可以读、写模式信息。

通过API来更新模式信息，solr将自动重载内核。

**注意：模式修改并不会自动重索引已索引的文档，只会对后续的文档起作用，如果必要，你需要手动重索引（删除原来的，重新提交文档）**



一、更新SCHEMA

（1）接口

请求地址：V1老版本的api，V2新版本的API

​	V1： http://localhost:8983/solr/mycore/schema

​	V2： http://localhost:8983/api/cores/mycore/schema

请求方式：post

头信息：Content-type:application/json

（2）相关操作

```
add-field: 添加一个新字段.
delete-field: 删除一个字段.
replace-field: 替换一个字段，修改.

add-dynamic-field: 添加一个新动态字段.
delete-dynamic-field: 删除一个动态字段
replace-dynamic-field: 替换一个已存在的动态字段

add-field-type: 添加一个fieldType.
delete-field-type: 删除一个fieldType.
replace-field-type: 更新一个存在的fieldType

add-copy-field: 添加一个复制字段规则.
delete-copy-field: 删除一个复制字段规则
```

（3）json格式的参数

```json
// 添加一个字段类别 add-field-type
{
    "add-field-type": {
        "name": "myNewTxtField",
        "class": "solr.TextField",
        "positionIncrementGap": "100",
        "analyzer": {
            "tokenizer": {
                "class": "solr.WhitespaceTokenizerFactory"
            },
            "filters": [
                {
                    "class": "solr.WordDelimiterFilterFactory",
                    "preserveOriginal": "0"
                }
            ]
        }
    }
}
// 带索引分析器和查询分析器
{
    "add-field-type": {
        "name": "myNewTextField",
        "class": "solr.TextField",
        "indexAnalyzer": {
            "tokenizer": {
                "class": "solr.PathHierarchyTokenizerFactory",
                "delimiter": "/"
            }
        },
        "queryAnalyzer": {
            "tokenizer": {
                "class": "solr.KeywordTokenizerFactory"
            }
        }
    }
}
// 删除一个字段类别 delete-field-type
{
    "delete-field-type": {
        "name": "myNewTxtField"
    }
}
// 替换一个字段类别 replace-field-type
{
    "replace-field-type": {
        "name": "myNewTxtField",
        "class": "solr.TextField",
        "positionIncrementGap": "100",
        "analyzer": {
            "tokenizer": {
                "class": "solr.StandardTokenizerFactory"
            }
        }
    }
}
// 添加一个字段 add-field
{
    "add-field": {
        "name": "sell_by",
        "type": "myNewTxtField",
        "stored": true
    }
}
// 删除一个字段 delete-field
{
    "delete-field": {
        "name": "sell_by"
    }
}
// 替换一个字段 replace-field
{
    "replace-field": {
        "name": "sell_by",
        "type": "date",
        "stored": false
    }
}
// 添加一个动态字段 add-dynamic-field
{
    "add-dynamic-field": {
        "name": "*_s",
        "type": "string",
        "stored": true
    }
}
// 添加复制字段 add-copy-field
{
    "add-copy-field": {
        "source": "shelf",
        "dest": [
            "location",
            "catchall"
        ]
    }
}
// 删除复制字段 delete-copy-field
{
    "delete-copy-field": {
        "source": "shelf",
        "dest": "location"
    }
}
```





二、获取schema信息

（1）获取整个schema

接口：http://localhost:8983/solr/mycore/schema?wt=xml

请求方式：GET

参数wt：json，xml

（2）获取字段

```json
GET /collection/schema/fields
GET /collection/schema/fields/fieldname

请求参数有：
wt:   json/xml            
fl：指定需要返回的字段名，以逗号或空格间隔
showDefaults：true/false ，是否返回字段的默认属性
includeDynamic：true/false，在path中带有fieldname  或指定了 fl的情况下才有用。


## 获取所有字段： 
http://localhost:8983/api/cores/mycore/schema/fields
## 获取指定字段： 
http://localhost:8983/api/cores/mycore/schema/fields/_root_
```



（3）获取动态字段

```powershell
GET /collection/schema/dynamicfields
GET /collection/schema/dynamicfields/name

请求参数：wt、showDefaults
```



（4）获取字段类别

```powershell
GET /collection/schema/fieldtypes
GET /collection/schema/fieldtypes/name

请求参数：wt、showDefaults
```



（5）获取其他信息

```powershell
GET /collection/schema/name       获取schema的name
GET /collection/schema/version    获取schema的版本
GET /collection/schema/uniquekey  获取唯一键字段
GET /collection/schema/similarity 获取全局相关性计算类

请求参数：wt
```



（6）获取复制字段

```powershell
GET /collection/schema/copyfields

请求参数：wt、 source.fl、 dest.fl
```

