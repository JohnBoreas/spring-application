# 函数
'''
定义一个函数有如下几个步骤:

1、函数代码块以 def 关键词开头，后接函数标识符名称和圆括号()。
2、任何传入参数和自变量必须放在圆括号中间。圆括号之间可以用于定义参数。
3、函数的第一行语句可以选择性地使用文档字符串—用于存放函数说明。
4、函数内容以冒号起始，并且缩进。
5、return [表达式] 结束函数，选择性地返回一个值给调用方。不带表达式的return相当于返回 None。
'''


def function_one(a, b):
    if a > 6:
        print('a 大于 6 : a = ', a)
    elif b < 8:
        print('b 小于8 : b = ', b)


# 单个返回值
def divide(a, b):
    return a / b


# 定义多个返回值函数
def more(x, y):
    nx = x + 2
    ny = y - 2
    return nx, ny


# 递归
def fact(n):
    if n == 1:
        return 1
    return n * fact(n - 1)


# 调用函数
function_one(7, 4)
function_one(4, 3)
print(divide(12, 6))
x, y = more(10, 10)
print(x, y)
print('fact : ', fact(6))
