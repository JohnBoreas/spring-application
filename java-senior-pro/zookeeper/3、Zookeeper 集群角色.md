**Leader** **角色**：Leader 服务器是整个 zookeeper 集群的核心，主要的工作任务有两项:

1、事务请求的唯一调度和处理者，保证集群事务处理的顺序性

2、集群内部各服务器的调度者

**Follower** **角色**：Follower 角色的主要职责是

1、 处理客户端非事务请求、转发事务请求给 leader 服务器

2、参与事物请求 Proposal 的投票（需要半数以上服务器通过才能通知 leader commit 数据;

Leader 发起的提案，要求 Follower 投票）

3、参与 Leader 选举的投票

**Observer** **角色**：Observer 是 zookeeper3.3 开始引入的一个全新的服务器角色，从字面来理解，

该角色充当了观察者的角色。观察 zookeeper 集群中的最新状态变化并将这些状态变化同步到

observer 服务器上。Observer 的工作原理与follower 角色基本一致，而它和 follower 角色唯一的不同

在于 observer 不参与任何形式的投票，包括事务请求Proposal的投票和leader选举的投票。简单来

说，observer服务器只提供非事务请求服务，通常在于不影响集群事务处理能力的前提下提升集群非事

务处理的能力