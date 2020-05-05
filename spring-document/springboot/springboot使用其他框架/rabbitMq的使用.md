

以 Configuration 的方式配置 RabbitMQ 并以 Bean 的方式显示注入 RabbitMQ 在发送接收处理消息时相关 Bean 组件配置其中典型的配置是 RabbitTemplate 以及 SimpleRabbitListenerContainerFactory，前者是充当消息的发送组件，后者是用于管理  RabbitMQ监听器listener 的容器工厂