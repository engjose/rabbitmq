package task.topic;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.QueueingConsumer;
import com.rabbitmq.client.QueueingConsumer.Delivery;
import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * Created by panyuanyuan on 2017/7/5.
 */
public class MessageReceive2 {

    private static final String TOPIC_NAME = "TOPIC_NAME";

    public static void main(String[] args)
        throws IOException, TimeoutException, InterruptedException {

        //创建连接
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("127.0.0.1");
        Connection connection = connectionFactory.newConnection();

        //设置exchange
        Channel channel = connection.createChannel();
        channel.exchangeDeclare(TOPIC_NAME, "topic");

        //设置队列
        String queueName = channel.queueDeclare().getQueue();
        channel.queueBind(queueName, TOPIC_NAME, "#.slow");

        //消费消息
        QueueingConsumer consumer = new QueueingConsumer(channel);
        channel.basicConsume(queueName, true, consumer);
        while (true) {
            Delivery delivery = consumer.nextDelivery();
            String message = new String(delivery.getBody());
            String routingKey = delivery.getEnvelope().getRoutingKey();
            System.out.println("[2] receive message:" + message);
            System.out.println("[2] receive routingKey:" + routingKey);
        }
    }
}
