2 种过期删除策略

##### 惰性删除

当我们查询 key 的时候才对 key 进行检测，如果已经达到过期时间，则删除

缺点是如果这些过期的 key 没有被访问，那么他就一直无法被删除，而且一直占用内存。

![null](..\..\resource\惰性删除.jpg)



##### 定期删除

redis 每隔一段时间对数据库做一次检查，删除里面的过期 key。

由于不可能对所有 key 去做轮询来删除，所以 redis 会每次随机取一些 key 去做检查和删除。







内存淘汰机制:

1. volatile-lru：从已设置过期时间的 key 中，移出最近最少使用的 key 进行淘汰
2. volatile-ttl：从已设置过期时间的 key 中，移出将要过期的 key
3. volatile-random：从已设置过期时间的 key 中随机选择 key 淘汰
4. allkeys-lru：从 key 中选择最近最少使用的进行淘汰
5. allkeys-random：从 key 中随机选择 key 进行淘汰
6. noeviction：当内存达到阈值的时候，新写入操作报错