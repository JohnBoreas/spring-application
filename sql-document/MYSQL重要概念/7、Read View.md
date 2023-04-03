#### Read View

就是事务进行`快照读`操作的时候生产的`读视图`(Read View)，在该事务执行的快照读的那一刻，会生成数据库系统当前的一个快照，记录并维护系统当前活跃事务的ID（当每个事务开启时，都会被分配一个ID, 这个ID是递增的，所以最新的事务，ID值越大）

![](..\resource\readview.png)



![](..\resource\可见性算法.png)



<img src="..\resource\rc与rr下的读视图.png" style="zoom:80%;" />

