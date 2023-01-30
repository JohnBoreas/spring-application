一、如何实现的？

简单的取模，亦或加上位移

```c
#  构造散列函数
def hash(a):
    return (a + 2 + (a << 1)) % 8 ^ 5
```



二、Hash 算法包括 MD5、SHA-1 和 SHA-2。

（1）MD4（RFC 1320）是 MIT 的 Ronald L. Rivest 在 1990 年设计的，MD 是 Message Digest 的缩写。其输出为 128 位。MD4 已证明不够安全。

（2）MD5（RFC 1321）是 Rivest 于1991年对 MD4 的改进版本。它对输入仍以 512 位分组，其输出是 128 位。MD5 比 MD4 复杂，并且计算速度要慢一点，更安全一些。MD5 已被证明不具备”强抗碰撞性”。

（3）SHA （Secure Hash Algorithm）是一个 Hash 函数族，由 NIST（National Institute of Standards and Technology）于 1993 年发布第一个算法。目前知名的 SHA-1 在 1995 年面世，它的输出为长度 160 位的 hash 值，因此抗穷举性更好。SHA-1 设计时基于和 MD4 相同原理，并且模仿了该算法。SHA-1 已被证明不具”强抗碰撞性”。

为了提高安全性，NIST 还设计出了 SHA-224、SHA-256、SHA-384，和 SHA-512 算法（统称为 SHA-2），跟 SHA-1 算法原理类似。SHA-3 相关算法也已被提出。



三、Hash 碰撞（Collision）

若散列算法得到一个不大于8的自然数，如果我们随便拿9个数去计算，肯定至少会得到两个相同的值