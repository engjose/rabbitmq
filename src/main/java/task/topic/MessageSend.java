package task.topic;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * 主题转发的形式是最强大的一种形式,会根据routingkey的不通进行路由,
 * routingkey可以有*和#表示:*表示一个标识符,#表示0个或者多个标识符
 *
 * Created by panyuanyuan on 2017/7/5.
 */
public class MessageSend {

    private static final String TOPIC_NAME = "TOPIC_NAME";
    private static final String[] ROUTING_KEY = {"rabbit.red.fast", "fox.blue.fast", "rabbit.red.slow", "rabbit.blue.fast"};

    public static void main(String[] args) throws IOException, TimeoutException {

        //创建连接
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("127.0.0.1");
        Connection connection = connectionFactory.newConnection();

        //设置exchange
        Channel channel = connection.createChannel();
        channel.exchangeDeclare(TOPIC_NAME, "topic");

        for (String key : ROUTING_KEY) {
            channel.basicPublish(TOPIC_NAME, key, null, key.getBytes());
            System.out.println("[X]send message:" + key);
        }

        //关闭资源
        channel.close();
        connection.close();
    }
}
