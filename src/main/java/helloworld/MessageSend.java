package helloworld;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * Created by panyuanyuan on 2017/6/28.
 */
public class MessageSend {

    private static final String QUEUE_NAME = "HELLO_WORLD";

    public static void main(String[] args) throws IOException, TimeoutException {

        //创建一个连接工厂
        ConnectionFactory connectionFactory = new ConnectionFactory();
        //设置rabbitmq所在主机的ip
        connectionFactory.setHost("127.0.0.1");
        //创建一个连接
        Connection connection = connectionFactory.newConnection();

        //创建一个频道
        Channel channel = connection.createChannel();
        channel.queueDeclare(QUEUE_NAME, false, false, false, null);

        //发送消息
        String message = "你好啊!";
        channel.basicPublish("", QUEUE_NAME, null, message.getBytes());
        System.out.println("[X]send message:" + message);

        //关闭资源
        channel.close();
        connection.close();
    }
}
