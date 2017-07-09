package task.direct;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.QueueingConsumer;
import com.rabbitmq.client.QueueingConsumer.Delivery;
import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * direct接收消息进行选择
 *
 * Created by panyuanyuan on 2017/7/5.
 */
public class MessageReceive2 {

    private static final String EXCHANGE_DIRECT = "EXCHANGE_DIRECT";
    private static final String[] SEVERITIES = {"info","warning"};

    public static void main(String[] args)
        throws IOException, TimeoutException, InterruptedException {

        //创建连接
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("127.0.0.1");
        Connection connection = connectionFactory.newConnection();

        //设置exchange
        Channel channel = connection.createChannel();
        channel.exchangeDeclare(EXCHANGE_DIRECT, "direct");
        String queueName = channel.queueDeclare().getQueue();
        channel.queueBind(queueName, EXCHANGE_DIRECT, "info");
        channel.queueBind(queueName, EXCHANGE_DIRECT, "warning");

        //消费消息
        QueueingConsumer consumer = new QueueingConsumer(channel);
        channel.basicConsume(queueName, true, consumer);
        while (true) {
            Delivery delivery = consumer.nextDelivery();
            String message = new String(delivery.getBody());
            System.out.println("[2] receive message:" + message);
        }
    }
}
