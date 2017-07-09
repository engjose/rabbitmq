package task.roundrobin;

import com.rabbitmq.client.AMQP.BasicProperties;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Consumer;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;
import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * Created by panyuanyuan on 2017/6/29.
 */
public class MessageReceive2 {

    private static final String QUEUE_NAME = "HELLO_TASK";

    public static void main(String[] args) throws IOException, TimeoutException {

        //创建一个连接工厂
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("127.0.0.1");
        //创建一个连接
        Connection connection = connectionFactory.newConnection();
        //创建一个channel
        Channel channel = connection.createChannel();
        //声明队列
        channel.queueDeclare(QUEUE_NAME, false, false, false, null);

        //创建消费者
        final Consumer consumer = new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, BasicProperties properties, byte[] body) throws IOException {

                String message = new String(body, "UTF-8");
                System.out.println("[x] Received" + message);

                try {
                    for (char ch : message.toCharArray()) {
                        if(ch == '.') {
                            Thread.sleep(1000);
                        }
                    }
                } catch (Exception e) {
                }

            }
        };

        //第二个参数是是否需要自动回复:默认需要手动回复,设置为true是表示不需要回复
        channel.basicConsume(QUEUE_NAME, true, consumer);

    }
}
