# python学习
#
#
# python语言合法命名规则
#
# 变量和函数的命名规则
#
# 以大小写字母、下划线开头均可，但不可以使用特殊字符，如$,%,*等，也不可以和关键字(keyword)重复。
#
#
# 一，包名、模块名、局部变量名、函数名
#
# 全小写+下划线式驼峰
#
# example：this_is_var
#
# 二，全局变量
#
# 全大写+下划线式驼峰
#
# example：GLOBAL_VAR
#
# 三，类名
#
# 首字母大写式驼峰
#
# example：ClassName()
#
# 四，关于下划线
#
# 以单下划线开头，是弱内部使用标识，from M import * 时，将不会导入该对象（python 一切皆对象）。
# 以双下划线开头的变量名，主要用于类内部标识类私有，不能直接访问。模块中使用见上一条。
# 双下划线开头且双下划线截尾的命名方法尽量不要用，这是标识
#
#
#
#
# python怎样命名变量的
#
# 1、模块名：小写字母，单词之间用_分割
#
# ad_stats.py
#
# 2、包名：和模块名一样
#
# ad_stats.py
#
# 3、类名：单词首字母大写
#
# AdStats
#
# ConfigUtil
#
# 4、全局变量名（类变量，在java中相当于static变量）：大写字母，单词之间用_分割
#
# NUMBER
#
# COLOR_WRITE
#
# 5、普通变量：小写字母，单词之间用_分割
#
# this_is_a_var
#
# 6、实例变量：以_开头，其他和普通变量一样
#
# _price
#
# _instance_var
#
# 7、私有实例变量（外部访问会报错）：以__开头（2个下划线），其他和普通变量一样
#
# __private_var
#
# 8、专有变量：__开头，__结尾，一般为python的自有变量，不要以这种方式命名
#
# __doc__
#
# __class__
#
# 9、普通函数：和普通变量一样：
#
# get_name()
#
# count_number()
#
# ad_stat()
#
# 10、私有函数（外部访问会报错）：以__开头（2个下划线），其他和普通函数一样
#
# __get_name()