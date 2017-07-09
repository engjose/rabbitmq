package task.roundrobin;

import com.google.common.base.Strings;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * 轮询分发: 轮询分发是将消息轮询分发给多个消费者
 *
 * Created by panyuanyuan on 2017/6/29.
 */
public class MessageSend {

    private static final String QUEUE_NAME = "HELLO_TASK";

    public static void main(String[] args) throws IOException, TimeoutException {

        //创建连接工厂
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("127.0.0.1");
        //创建链接
        Connection connection = connectionFactory.newConnection();
        //创建一个频道
        Channel channel = connection.createChannel();
        //声明一个队列: 第二个参数是是否需要持久化队列,true的话持久化队列
        channel.queueDeclare(QUEUE_NAME, false, false, false, null);

        //发送消息
        for (int i = 0; i < 5; i++) {
            // 发送的消息
            String message = "Hello World"+ Strings.repeat(".", i);
            // 往队列中发出一条消息,第三个参数是消息是否需要持久化,如果是MessageProperties.PERSISTENT_TEXT_PLAIN,则消息需要持久化
            channel.basicPublish("", QUEUE_NAME, null, message.getBytes());
            System.out.println(" [x] Sent '" + message + "'");
        }

        channel.close();
        connection.close();
    }
}
