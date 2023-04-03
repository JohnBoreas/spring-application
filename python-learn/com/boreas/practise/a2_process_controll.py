# if 语句
''' if 判断条件：
        执行语句……
    else：
        执行语句…… '''
a = '我是一个Java开发工作者'
if len(a) > 5:
    print(a)
else:
    print(len(a))

x = -5
if x < 0:
    x = 0
    print('Negative changed to zero')
elif x == 0:
    print('Zero')
elif x == 1:
    print('Single')
else:
    print('More')

# for 循环
'''
for 变量名 in 序列:
    执行语句

for 循环每次从序列中取一个值放到变量中
此处的序列主要指 列表  元组   字符串   文件
'''
for a_value in range(len(a)):
    print('当前 :', a[a_value])
for letter in 'Python':  # 第一个实例
    print('当前字母 :', letter)

# while 循环
'''
while 判断条件：
    执行语句
'''
b = len(a)
c = 0
while b > 0:
    print("current value: ", a[c], b, c)
    b = b - 2
    c = c + 2

# range() 函数
# 生成一个等差级数链表:语法： range (start， end， scan):
''' start: 计数从 start 开始。默认是从 0 开始。例如 range(5) 等价于 range(0, 5);
    end: 计数到 end 结束，但不包括 end.例如：range(0, 5) 是[0, 1, 2, 3, 4]没有 5
    scan：每次跳跃的间距，默认为1。例如：range(0, 5) 等价于 range(0, 5, 1)'''
for i in range(6, 12, 2):
    print(i)
print(range(6, 12, 2), 'finish')

# break 语句
for i in range(10):
    if i == 4:
        print("当前i:", i)
        break
print(range(10), 'finish')

# continue 语句
# 用来跳过当前循环块中的剩余语句，然后继续进行下一轮循环。
for i in range(6):
    if i > 2:
        continue
    print("当前 i : ", i)
print(range(6), 'finish')

# pass 语句
# 是空语句，是为了保持程序结构的完整性。它用于那些语法上必须要有什么语句，但程序什么也不做的场合.
while True:
    pass  # Busy-wait for keyboard interrupt (Ctrl+C)
print('end')
