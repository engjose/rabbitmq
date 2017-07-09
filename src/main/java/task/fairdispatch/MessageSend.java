package task.fairdispatch;

import com.google.common.base.Strings;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * 公平分发任务: 公平分发任务下只有当任务处理完成后才会进行分配
 * 不像round-robin一次性分发好任务,所以这里可以动态的添加队列
 *
 * Created by panyuanyuan on 2017/6/29.
 */
public class MessageSend {

    private static final String QUEUE_NAME = "TASK_FAIR";

    public static void main(String[] args) throws IOException, TimeoutException {

        //创建连接
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("127.0.0.1");
        Connection connection = connectionFactory.newConnection();

        //声明队列
        Channel channel = connection.createChannel();
        channel.queueDeclare(QUEUE_NAME, false, false, false, null);

        //发送消息
        for (int i = 0; i < 5; i++) {
            // 发送的消息
            String message = "Hello World"+ Strings.repeat(".", i);
            // 往队列中发出一条消息,第三个参数是消息是否需要持久化,如果是MessageProperties.PERSISTENT_TEXT_PLAIN,则消息需要持久化
            channel.basicPublish("", QUEUE_NAME, null, message.getBytes());
            System.out.println(" [x] Sent '" + message + "'");
        }
        //关闭资源
        try {
            connection.close();
            channel.close();
        } catch (Exception e) {
            System.out.print("close stream err");
        }

    }
}
