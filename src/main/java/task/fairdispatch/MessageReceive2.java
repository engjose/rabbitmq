package task.fairdispatch;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.QueueingConsumer;
import com.rabbitmq.client.QueueingConsumer.Delivery;
import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * Created by panyuanyuan on 2017/6/29.
 */
public class MessageReceive2 {

    private static final String QUEUE_NAME = "TASK_FAIR";

    public static void main(String[] args)
        throws IOException, TimeoutException, InterruptedException {

        //创建连接
        ConnectionFactory connectionFactory =  new ConnectionFactory();
        connectionFactory.setHost("127.0.0.1");
        Connection connection = connectionFactory.newConnection();

        //声明队列
        Channel channel = connection.createChannel();
        channel.queueDeclare(QUEUE_NAME, false, false, false, null);

        //设置消息转发数量
        int prefetchCount = 2;
        channel.basicQos(prefetchCount);

        //消费参数设置: 参数一:队列名称, 参数二:是否需要回复,false时需要手动回复, 参数三consumer
        QueueingConsumer consumer = new QueueingConsumer(channel);
        channel.basicConsume(QUEUE_NAME, false, consumer);

        while (true) {
            Delivery delivery = consumer.nextDelivery();
            String message = new String(delivery.getBody());
            System.out.println("[2]received message:" + message);

            //睡眠
            for (char ch : message.toCharArray()) {
                if(ch == '.') {
                    Thread.sleep(400);
                }
            }

            //回复应答消息
            channel.basicAck(delivery.getEnvelope().getDeliveryTag(), false);
        }


    }
}
