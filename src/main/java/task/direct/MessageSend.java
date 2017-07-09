package task.direct;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * Exchange方式的direct
 * Created by panyuanyuan on 2017/7/5.
 */
public class MessageSend {

    private static final String EXCHANGE_DIRECT = "EXCHANGE_DIRECT";
    private static final String[] SEVERITIES = {"info","warning"};

    public static void main(String[] args) throws IOException, TimeoutException {

        //创建连接
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("127.0.0.1");
        Connection connection = connectionFactory.newConnection();

        //设置Exchange
        Channel channel = connection.createChannel();
        channel.exchangeDeclare(EXCHANGE_DIRECT, "direct");

        //发送消息
        channel.basicPublish(EXCHANGE_DIRECT, SEVERITIES[0], null, "info".getBytes());
        System.out.println("[info] send message:" + "info");
        channel.basicPublish(EXCHANGE_DIRECT, SEVERITIES[1], null, "warning".getBytes());
        System.out.println("[warning] send message:" + "warning");
        channel.basicPublish(EXCHANGE_DIRECT, SEVERITIES[1], null, "err".getBytes());
        System.out.println("[err] send message:" + "err");

        channel.close();
        connection.close();
    }
}
