package task.fanout;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * 分发:创建一个分发消息的队列模型:发布===>订阅
 *
 * Created by panyuanyuan on 2017/7/4.
 */
public class MessageSend {

    private static final String EX_NAME = "EX_FANOUT";

    public static void main(String[] args) throws IOException, TimeoutException {

        //创建连接
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("127.0.0.1");
        Connection connection = connectionFactory.newConnection();

        //声明转发器
        Channel channel = connection.createChannel();
        channel.exchangeDeclare(EX_NAME, "fanout");

        //发送消息:参数一:转换器,参数二:队列名称,参数三:是否需要持久化,参数四:消息
        String message = "HELLO REACT";
        channel.basicPublish(EX_NAME, "", null, message.getBytes());

        System.out.println("[X]send message:" + message);

        channel.close();
        connection.close();
    }
}
