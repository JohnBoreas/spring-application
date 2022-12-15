- **面向对象**：每一个变量都是一个类，有其自己的**属性（attribute）**与**方法（method）**。
- **语法块**：用缩进（四个空格）而不是分号、花括号等符号来标记。因此，行首的空格不能随意书写。
- **注释**：行内用“#”号，行间注释写在两组连续三单引号之间：’’’
- **续行**：行尾输入一个反斜杠加一个空格（’\ ‘），再换行。如果行尾语法明显未完成（比如以逗号结尾），可以直接续行。
- **打印与输入**： 函数 print() 与 input()，注意 print() 的 sep 与 end 参数。
- **变量**：无需指定变量类型，也不需要提前声明变量。
- 删除变量：del()
- 复制变量：直接将变量a赋值给b，有时仅仅复制了一个“引用”。此后 b 与 a 的改动仍会互相影响。必要时使用 `a is b` 来判断是否同址。
- **模块**：通过 `import pandas` 的方式加载模块（或者 `import pandas as pd`），并用形如 `pandas.DataFrame`（或 `pd.DataFrame`）的方式调用模块内的方法。也可以使用 `from pandas import DataFrame` 的方式，这样在下文可以直接使用 `DataFrame` 作为调用名。
- **帮助**：配合使用 dir() 与 help() 命令；其中前者是输出变量所有的成员



 Python 标识符，需要遵守如下规定：

- 第一个字符必须是字母或下划线（_）
- 剩下的字符可以是字母和数字或下划线
- 大小写敏感
- 不能是 Python 的关键字，例如 def、class 就不能作为标识符

单下划线开头 `_foo` 的代表不能直接访问的类属性，需通过类提供的接口进行访问，不能用 `from xxx import *` 而导入。

双下划线开头的 `__foo` 代表类的私有成员，以双下划线开头和结尾的 `__foo__` 代表 Python 里特殊方法专用的标识，如 `__init__()` 代表类的构造函数。





关键字

| and      | exec    | not    |
| :------- | :------ | :----- |
| assert   | finally | or     |
| break    | for     | pass   |
| class    | from    | print  |
| continue | global  | raise  |
| def      | if      | return |
| del      | import  | try    |
| elif     | in      | while  |
| else     | is      | with   |
| except   | lambda  | yield  |