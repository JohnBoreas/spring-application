一、日志系统

relayLog，主从同步中的中继日志

binLog，用于主从同步

<img src="../resource/MySQL日志系统.png" style="zoom:80%;" />

二、redolog与binlog更新流程

redolog有prepare与commit阶段，binlog没有

<img src="../resource/redo与binlog更新流程.png" style="zoom:80%;" />