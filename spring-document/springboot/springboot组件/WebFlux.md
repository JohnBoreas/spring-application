完全非阻塞的，支持 [Reactive Streams](https://links.jianshu.com/go?to=https%3A%2F%2Fwww.reactive-streams.org%2F)，并在Netty，Undertow和Servlet 3.1+容器等服务器上运行。

WebFlux需要Reactor作为核心依赖项，但是它可以通过Reactive Streams与其他React库进行互操作。



输入输出：

WebFlux API接受平原`Publisher`作为输入，在内部将其适应于`Reactor`类型，使用它，然后返回`Flux`或`Mono`作为输出。



