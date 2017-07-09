package task.spring;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageListener;

/**
 * Created by panyuanyuan on 2017/7/9.
 */
public class MessageConsumer3 implements MessageListener{

    public void onMessage(Message message) {
        System.out.println("****************" + new String(message.getBody()) + "******************");
    }
}
