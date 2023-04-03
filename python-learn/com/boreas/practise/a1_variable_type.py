# 在 Python 中 变量命名规定，必须是大小写英文，数字和 下划线(_)的组合，并且不能用数字开头。
#
# 变量命名规则：
#
# (1)变量名只能是字母，数字和下划线的任意组合
# (2)变量名第一个字符不能是数字
# (3)变量名区分大小写，大小写字母被认为是两个不同的字符
# (4)特殊关键字不能命名为变量名

# 声明变量
a = '1'
b = "2"
c = 3
# 变量赋值
d, e, f = 1, 2, 'v'
h = i = j = 3

# Python3 中有六个标准的数据类型：Number（数字）、String（字符串）、List（列表）、Tuple（元组）、Sets（集合）、Dictionary（字典）。

# Number（数字）Python3 支持 int、float、bool、complex（复数）。
counter = 100  # 整型变量
miles = 1000.0  # 浮点型变量
name = "test"  # 字符串

# 数字类型转换

# int(x) 将x转换为一个整数。
# float(x) 将x转换到一个浮点数。
# complex(x) 将x转换到一个复数，实数部分为 x，虚数部分为 0。
# complex(x, y) 将 x 和 y 转换到一个复数，实数部分为 x，虚数部分为 y。x 和 y 是数字表达式。

# 运算
print(5 + 4)  # 加法   输出 9
print(4.3 - 2)  # 减法   输出 2.3
print(3 * 7)  # 乘法  输出 21
print(2 / 4)  # 除法，得到一个浮点数    输出 0.5
print(2 // 4)  # 除法，得到一个整数 输出 0
print(17 % 3)  # 取余   输出 2
print(2 ** 5)  # 乘方  输出 32

# String（字符串）

s = '学习Python'
# 切片
s[0], s[-1], s[3:], s[::-1]  # '学', 'n', 'Python', 'nohtyP的习学'
# 替换，还可以使用正则表达式替换
s.replace('Python', 'Java')  # '学习Java'
# 查找，find()、index()、rfind()、rindex()
s.find('P')  # 3, 返回第一次出现的子串的下标
s.find('h', 2)  # 6, 设定下标2开始查找
s.find('23333')  # -1, 查找不到返回-1
s.index('y')  # 4, 返回第一次出现的子串的下标
s.index('P')  # 不同与find(), 查找不到会抛出异常
# 转大小写, upper()、lower()、swapcase()、capitalize()、istitle()、isupper()、islower()
s.upper()  # '学习PYTHON'
s.swapcase()  # '学习pYTHON', 大小写互换
s.istitle()  # True
s.islower()  # False
# 去空格,strip()、lstrip()、rstrip()
# 格式化
s1 = '%s %s' % ('Windrivder', 21)  # 'Windrivder 21'
s2 = '{}, {}'.format(21, 'Windridver')  # 推荐使用format格式化字符串
s3 = '{0}, {1}, {0}'.format('Windrivder', 21)
print('s1 : {0}; {1}; {2}'.format(s1, s2, s3))
s4 = '{name}: {age}'.format(age=21, name='Windrivder')
# 连接与分割，使用 + 连接字符串，每次操作会重新计算、开辟、释放内存，效率很低，所以推荐使用join
l = ['2017', '03', '29', '22:00']
s5 = '-'.join(l)  # '2017-03-29-22:00'
s6 = s5.split('-')  # ['2017', '03', '29', '22:00']

# encode 将字符转换为字节
str = '学习Python'
print(str.encode())  # 默认编码是 UTF-8  输出：b'\xe5\xad\xa6\xe4\xb9\xa0Python'
print(str.encode('gbk'))  # 输出  b'\xd1\xa7\xcf\xb0Python'
# decode 将字节转换为字符
print(str.encode().decode('utf8'))  # 输出 '学习Python'
print(str.encode('gbk').decode('gbk'))  # 输出 '学习Python'

# List（列表）
# 操作类似于Java的list
Weekday = ['Monday', 'Tuesday', 'Wednesday', 'Thursday', 'Friday']
print(Weekday[0])  # 输出 Monday
print(Weekday.index("Wednesday"))  # list 搜索
Weekday.append("new")  # list 增加元素
print(Weekday)
Weekday.remove("Thursday")  # list 删除
print(Weekday)
print(Weekday[0:4])

# Tuple（元组）
# 元素不可修改
letters = ('a', 'b', 'c', 'd', 'e', 'f', 'g')
print(letters[0])  # 输出 'a'
print(letters[0:3])  # 输出一组 ('a', 'b', 'c')

# Sets（集合）
# 无序不重复， 使用大括号 {} 或者 set() 函数创建集合，注意：创建一个空集合必须用 set() 而不是 {} ，因为 {} 是用来创建一个空字典。
a_set = {1, 2, 3, 4}
# 添加
a_set.add(5)
print(a_set.add(5))
print(a_set)  # 输出{1, 2, 3, 4, 5}
# 删除
a_set.discard(5)
print(a_set)  # 输出{1, 2, 3, 4}

# Dictionary（字典）
# 映射类型，它的元素是键值对，字典的关键字必须为不可变类型，且不能重复。创建空字典使用 {} 。
D_dic = {}
print(len(D_dic))
Logo_code = {
    'BIDU': 'Baidu',
    'SINA': 'Sina',
    'YOKU': 'Youku'
}
print(Logo_code)
# 输出{'BIDU': 'Baidu', 'YOKU': 'Youku', 'SINA': 'Sina'}
print(Logo_code['SINA'])  # 输出键为 'one' 的值
print(Logo_code.keys())  # 输出所有键
print(Logo_code.values())  # 输出所有值
print(len(Logo_code))  # 输出字段长度
