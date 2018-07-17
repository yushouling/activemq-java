# activemq-java
描述：activemq java test helloworld

注意事项：
topic消息需要消费者提前订阅，否则将收不到监听之前发送的主题消息；
而队列可以接收到监听前的queue消息，如果有多个消费者同时监听队列消息，消息将被每个消费者抢占，且每条消息只会被一个消费者接收到。
