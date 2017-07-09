package helloworld;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.QueueingConsumer;
import com.rabbitmq.client.QueueingConsumer.Delivery;
import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * Created by panyuanyuan on 2017/6/28.
 */
public class MessageReceive {

    private static final String QUEUE_NAME = "HELLO_WORLD";

    public static void main(String[] args)
        throws IOException, TimeoutException, InterruptedException {

        //创建一个连接工厂
        ConnectionFactory connectionFactory = new ConnectionFactory();
        //设置mq主机ip
        connectionFactory.setHost("127.0.0.1");
        //创建链接
        Connection connection = connectionFactory.newConnection();

        //创建频道
        Channel channel = connection.createChannel();
        //创建队列--防止先启动消费者找不到队列
        channel.queueDeclare(QUEUE_NAME, false, false, false, null);

        //创建消费者
        QueueingConsumer consumer = new QueueingConsumer(channel);
        System.out.println(" [*] Waiting for messages. To exit press CTRL+C");
        //指定消费队列
        channel.basicConsume(QUEUE_NAME, true, consumer);

        while (true) {
            // nextDelivery是一个阻塞方法（内部实现其实是阻塞队列的take方法）
            Delivery delivery = consumer.nextDelivery();
            String message = new String(delivery.getBody());

            System.out.println("[X]receive message:" + message);
        }
    }
}
