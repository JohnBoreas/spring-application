#### Linux命令:修改文件权限命令

chmod、chgrp、chown

##### 1、基础知识

用户分类：文件所有者，同组用户、其他用户

权限：**r**(Read，读取，权限值为**4**)、**w**(Write,写入，权限值为**2**)、**x(**eXecute，执行，权限值为**1**)

##### 2、语法

 ```shell
**chmod 用于改变文件或目录的访问权限。用户用它控制文件或目录的访问权限
**chgrp 改变文件或目录所属的组
**chown 更改某个文件或目录的属主和属组
 
## who 可填 u“用户（user）”、g“同组（group）用户”、o“其他（others）用户”、a“所有（all）用户”
## + 添加某个权限 || – 取消某个权限 || = 赋予给定权限并取消其他所有权限（如果有的话）
## mode ： r 可读、w 可写、x 可执行......
## 文件名 多个用逗号隔开，支持通配符
chmod ［who］ ［+ | – | =］ ［mode］ 文件名¼
## mode ： 0表示没有权限，1表示可执行权限，2表示可写权限，4表示可读权限，顺序是（u）（g）（o）
chmod ［mode］ 文件名¼
## –R 递归处理、-v 显示指令执行过程、
chgrp ［选项］ group filename¼
## 
chown ［选项］ 用户或组 文件
 ```

##### 3、例子

```shell
chmod ug+w，o-x aa.txt ## 文件属主（u）增加写权限，同组用户（g）增加写权限，其他用户（o）删除执行权限
chmod 777 test.sh	## 给文件赋予权限777，ugo
chgrp - R test /opt/local /test		## 改变/opt/local /test/及其子目录下的所有文件的属组为test
chown - R user.group /test	## 把目录/test及其下的所有文件和子目录的属主改成user，属组改成group
chown user test.txt		## 把文件test.txt的所有者改为user
```



```powershell
## rz/sz命令的安装
wget http://www.ohse.de/uwe/releases/lrzsz-0.12.20.tar.gz
tar zxvf lrzsz-0.12.20.tar.gz && cd lrzsz-0.12.20
./configure && make && make install
## 上面安装过程默认把lsz和lrz安装到了/usr/local/bin/目录下，现在我们并不能直接使用，下面创建软链接，并命名为rz/sz
cd /usr/bin
ln -s /usr/local/bin/lrz rz
ln -s /usr/local/bin/lsz sz
```



#### find

```powershell
基本结构：find [paths] [expression] [actions]
默认递归方式检索（注意限制递归层数，不加文件多会慢）
# 查找 /usr 目录下所有文件名以 .txt 结尾的文件,限制递归层数为2
find /usr -maxdepth 2 -name '*.sh'
# 浏览当前目录及子目录下所有 1G 以上大小的文件的详细信息,限制递归层数为4,类型为文件
find . -type f -maxdepth 4 -size +1G -ls
# 检索 /usr 下两天前被修改过且 5 分钟前又读取过的文件
find /usr -type f -mtime 2 -amin 5
# 检索根目录下所有属主为 starky 的文件
find / -type f -user starky
# 搜索 /usr 目录下权限为 644（即 rwxr-xr-x）的文件
find /usr -perm 644
```

```powershell
-type	f: 文件；d: 目录；l: 符号链接
-size	c：字节；k：Kb；M：Mb；G：Gb
-mtime	修改时间；	-atime 访问时间；	-ctime	变更时间	（天）
-mmin	修改时间；	-amin 访问时间；		-cmin	变更时间	（分）
-mtime 2：2天前；	-mtime -2：2天以内；	-mtime +2：超过2天
```



#### Linux系统监控命令  

* 常用命令

  1. free

     可以用来快速查看VPS主机的内存使用情况，包括了物理内存和虚拟内存。

     > ```shell
     > free -h
     > free -m
     > ```

     > 参数说明：
     >
     > - total：物理内存大小，就是机器实际的内存
     > - used：已使用的内存大小，这个值包括了 cached 和 应用程序实际使用的内存
     > - free：未被使用的内存大小
     > - shared：共享内存大小，是进程间通信的一种方式
     > - buffers：被缓冲区占用的内存大小
     > - cached：被缓存占用的内存大小

  2. vmstat（Virtual Meomory Statistics，虚拟内存统计）

     是对系统的整体情况进行统计，包括内核进程、虚拟内存、磁盘、陷阱和 CPU 活动的统计信息。

     > ```shell
     > vmstat 2 200   ## 2 刷新间隔， 200 输出次数
     > ```

     

  3. top命令

     top命令是Linux下常用的性能分析工具，能够实时显示系统中各个进程的资源占用状况及总体状况。

     > ```shell
     > 
     > ```

     

  4. mpstat（Multiprocessor Statistics，多处理器统计）

     是实时系统监控工具，它会报告与CPU相关的统计信息，这些信息存放在/proc/stat文件中。

     > ```shell
     > 
     > ```

  5. 


#### Linux磁盘工具

- Linux磁盘管理常用三个命令为df、du和fdisk。

  - df：列出文件系统的整体磁盘使用量
  - du：检查磁盘空间使用量
  - fdisk：用于磁盘分区

  1. df

     选项与参数：

     > - -a ：列出所有的文件系统，包括系统特有的 /proc 等文件系统；
     > - -k ：以 KBytes 的容量显示各文件系统；
     > - -m ：以 MBytes 的容量显示各文件系统；
     > - -h ：以人们较易阅读的 GBytes, MBytes, KBytes 等格式自行显示；
     > - -H ：以 M=1000K 取代 M=1024K 的进位方式；
     > - -T ：显示文件系统类型, 连同该 partition 的 filesystem 名称 (例如 ext3) 也列出；
     > - -i ：不用硬盘容量，而以 inode 的数量来显示

     > ```shell
     > df [-ahikHTm] [目录或文件名] ## [-ahikHTm] [目录或文件名]
     > df -aT   ## 将系统内的所有特殊文件格式及名称都列出来
     > ```

     

  2. du

     > ```shell
     > du [-ahskm] 文件或目录名称 ## [-ahskm] 文件或目录名称
     > ```

  3. fdisk

     > ```shell
     > fdisk [-l] 装置名称 ##  fdisk-l时，则系统将会把整个系统内能够搜寻到的装置的分区均列出来
     > ```

  

- Linux 的磁盘挂载使用 `mount` 命令，卸载使用 `umount` 命令。

  磁盘挂载语法：

  > ```shell
  > mount [-t 文件系统] [-L Label名] [-o 额外选项] [-n]  装置文件名  挂载点
  > umount [-fn] 装置文件名或挂载点
  > 
  > mount /dev/hdc6 /mnt/hdc6   ## 将/dev/hdc6 挂载到 /mnt/hdc6 上面
  > umount /dev/hdc6 ## 卸载/dev/hdc6
  > ```



#### Linux文件操作命令

##### 1. 几个常见的处理目录的命令：

- ls: 列出目录
- cd：切换目录
- pwd：显示目前的目录
- mkdir：创建一个新的目录
- rmdir：删除一个空的目录
- cp: 复制文件或目录
- rm: 移除文件或目录
- mv: 移动文件与目录，或修改文件与目录的名称

你可以使用 *man [命令]* 来查看各个命令的使用文档，如 ：man cp

##### 2. ls (列出目录)
* 语法

  > ```shell
  > [root@ ~]# ls [-aAdfFhilnrRSt] 目录名称
  > [root@ ~]# ls [--color={never,auto,always}] 目录名称
  > [root@ ~]# ls [--full-time] 目录名称
  > ```
* 选项与参数：

  * -a ：全部的文件，连同隐藏档( 开头为 . 的文件) 一起列出来(常用)


  * -d ：仅列出目录本身，而不是列出目录内的文件数据(常用)
  * -l ：长数据串列出，包含文件的属性与权限等等数据；(常用)
##### 3. cd (切换目录)

* 语法

  > ```shell
  > cd [相对路径或绝对路径]
  > ```

* cd是Change Directory的缩写，这是用来变换工作目录的命令。

  > ```shell
  > mkdir runoob	#使用 mkdir 命令创建 runoob 目录
  > cd /root/runoob/#使用绝对路径切换到 runoob 目录
  > cd ./runoob/	#使用相对路径切换到 runoob 目录
  > cd ~			# 表示回到自己的家目录，亦即是 /root 这个目录
  > cd ..			# 表示去到目前的上一级目录，亦即是 /root 的上一级目录的意思；
  > ```

##### 4. pwd (显示目前所在的目录)

* 语法

  > ```shell
  > pwd [-P] ## -P ：显示出确实的路径，而非使用连结 (link) 路径。
  > ```

* pwd 是 Print Working Directory的缩写，也就是显示目前所在目录的命令


##### 5. mkdir (创建新目录)

* 创建新的目录使用mkdir (make directory)

  > ```shell
  > mkdir [-mp] 目录名称
  > mkdir test	## 创建一名为 test 的新目录
  > ```

* 选项与参数：

  - -m ：配置文件的权限喔！直接配置，不需要看默认权限 (umask) 的脸色～
  - -p ：帮助你直接将所需要的目录(包含上一级目录)递归创建起来！

  > ```shell
  > mkdir -m 711 test2	## 创建权限为 rwx--x--x 的目录
  > ```

##### 6. rmdir (删除空的目录)

* 语法

  > ```shell
  > rmdir [-p] 目录名称
  > ```

* 选项与参数：

  - **-p ：**连同上一级『空的』目录也一起删除


##### 7. cp (复制文件或目录)  

* 语法

  > ```shell
  > cp [-adfilprsu] 来源档(source) 目标档(destination)
  > cp [options] source1 source2 source3 .... directory
  > ```

* 选项与参数：

  - **-a：**相当於 -pdr 的意思，至於 pdr 请参考下列说明；(常用)
  - **-d：**若来源档为连结档的属性(link file)，则复制连结档属性而非文件本身；
  - **-f：**为强制(force)的意思，若目标文件已经存在且无法开启，则移除后再尝试一次；
  - **-i：**若目标档(destination)已经存在时，在覆盖时会先询问动作的进行(常用)
  - **-l：**进行硬式连结(hard link)的连结档创建，而非复制文件本身；
  - **-p：**连同文件的属性一起复制过去，而非使用默认属性(备份常用)；
  - **-r：**递归持续复制，用於目录的复制行为；(常用)
  - **-s：**复制成为符号连结档 (symbolic link)，亦即『捷径』文件；
  - **-u：**若 destination 比 source 旧才升级 destination ！

  > ```shell
  > cp -i ~/.bashrc /tmp/bashrc	## 以询问的方式从~/.bashrc移动到/tmp/bashrc
  > ```

##### 8. rm (移除文件或目录)

* 语法

  > ```shell
  > rm [-fir] 文件或目录
  > ```

* 选项与参数：

  - -f ：就是 force 的意思，忽略不存在的文件，不会出现警告信息；
  - -i ：互动模式，在删除前会询问使用者是否动作
  - -r ：递归删除啊！最常用在目录的删除了！这是非常危险的选项！！！

  > ```shell
  > rm -i bashrc	##  以询问方式删除bashrc！！！
  > ```

##### 9. mv (移动文件与目录，或修改名称)

* 语法：

  > ```shell
  > mv [-fiu] source destination
  > mv [options] source1 source2 source3 .... directory
  > ```

* 选项与参数：

  - -f ：force 强制的意思，如果目标文件已经存在，不会询问而直接覆盖；
  - -i ：若目标文件 (destination) 已经存在时，就会询问是否覆盖！
  - -u ：若目标文件已经存在，且 source 比较新，才会升级 (update)

  > ```shell
  > mv bashrc mvtest	## 某个文件移动到某个目录去
  > ```



#### Linux文件内容查看

##### 1. Linux系统中使用以下命令来查看文件的内容：

* cat  由第一行开始显示文件内容

- tac  从最后一行开始显示，可以看出 tac 是 cat 的倒著写！
- nl   显示的时候，顺道输出行号！
- more 一页一页的显示文件内容
- less 与 more 类似，但是比 more 更好的是，他可以往前翻页！
- head 只看头几行
- tail 只看尾巴几行



#### yum常用命令

##### 1. 语法

> ```shell
> yum [options] [command] [package ...]
> ```

##### 2. 常用命令

> ```shell
> yum check-update	## 1.列出所有可更新的软件清单命令
> yum update			## 2.更新所有软件命令
> yum install <package_name>	## 3.仅安装指定的软件命令
> yum update <package_name>	## 4.仅更新指定的软件命令
> yum list			## 5.列出所有可安装的软件清单命令
> yum remove <package_name>	## 6.删除软件包命令
> yum search <keyword>## 7.查找软件包命令
> ## 8.清除缓存命令：
> yum clean package	## 清除缓存目录下的软件包
> yum clean headers	## 清除缓存目录下的标题
> yum clean oldheaders## 清除缓存目录下旧的标题
> yum clean，yum clean all（= yum clean package; yum clean oldheaders）## 清除缓存目录下的软件包及旧的header
> 
> ```



#### vim

* 进入vim

  > ```shell
  > $ vim runoob.txt
  > ```

* 进入编辑模式

  在一般模式之中，只要按下 i, o, a 等字符就可以进入输入模式了

  左下角状态栏中会出现 –INSERT- 的字样，那就是可以输入任意字符的提示

* 常用命令

  + 搜索替换

    第 n1 与 n2 行之间寻找 word1 这个字符串，并将该字符串取代为 word2

    ```shell
    $ 1,2s/word1/word2/g
    $ 1,$s/word1/word2/g 或 :%s/word1/word2/g 
    $ 1,$s/word1/word2/gc 或 :%s/word1/word2/gc
    ```

  + 一般模式切换到指令行模式

    | :w                  | 将编辑的数据写入硬盘档案中(常用)                             |
    | ------------------- | :----------------------------------------------------------- |
    | :w!                 | 若文件属性为『只读』时，强制写入该档案。不过，到底能不能写入， 还是跟你对该档案的档案权限有关啊！ |
    | :q                  | 离开 vi (常用)                                               |
    | :q!                 | 若曾修改过档案，又不想储存，使用 ! 为强制离开不储存档案。    |
    | :wq                 | 储存后离开，若为 :wq! 则为强制储存后离开 (常用)              |
    | ZZ                  | 这是大写的 Z 喔！若档案没有更动，则不储存离开，若档案已经被更动过，则储存后离开！ |
    | :w [filename]       | 将编辑的数据储存成另一个档案（类似另存新档）                 |
    | :r [filename]       | 在编辑的数据中，读入另一个档案的数据。亦即将 『filename』 这个档案内容加到游标所在行后面 |
    | :n1,n2 w [filename] | 将 n1 到 n2 的内容储存成 filename 这个档案。                 |

  + 复制黏贴删除

    | ndd  | n 为数字。删除光标所在的向下 n 行，例如 20dd 则是删除 20 行 (常用) |
    | ---- | ------------------------------------------------------------ |
    | d1G  | 删除光标所在到第一行的所有数据                               |
    | dG   | 删除光标所在到最后一行的所有数据                             |
    | d$   | 删除游标所在处，到该行的最后一个字符                         |
    | d0   | 那个是数字的 0 ，删除游标所在处，到该行的最前面一个字符      |
    | yy   | 复制游标所在的那一行(常用)                                   |
    | nyy  | n 为数字。复制光标所在的向下 n 行，例如 20yy 则是复制 20 行(常用) |
    | y1G  | 复制游标所在行到第一行的所有数据                             |
    | yG   | 复制游标所在行到最后一行的所有数据                           |
    | y0   | 复制光标所在的那个字符到该行行首的所有数据                   |
    | y$   | 复制光标所在的那个字符到该行行尾的所有数据                   |





