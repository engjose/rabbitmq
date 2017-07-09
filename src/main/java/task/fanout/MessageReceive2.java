package task.fanout;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.QueueingConsumer;
import com.rabbitmq.client.QueueingConsumer.Delivery;
import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * Created by panyuanyuan on 2017/7/4.
 */
public class MessageReceive2 {

    private static final String EX_NAME = "EX_FANOUT";

    public static void main(String[] args)
        throws IOException, TimeoutException, InterruptedException {

        //创建连接
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("127.0.0.1");
        Connection connection = connectionFactory.newConnection();

        //设置转换器
        Channel channel = connection.createChannel();
        channel.exchangeDeclare(EX_NAME, "fanout");

        //绑定队列
        String queueName = channel.queueDeclare().getQueue();
        channel.queueBind(queueName, EX_NAME, "");

        //消费消息
        QueueingConsumer consumer = new QueueingConsumer(channel);
        channel.basicConsume(queueName, true, consumer); //第一个参数:队列名称,第二个参数是否手动答复,第三个参数:消费者
        while (true) {
            Delivery delivery = consumer.nextDelivery();
            String message = new String(delivery.getBody());
            System.out.println("[X]: receive message:" + message);
        }

    }
}
