package task.spring;

import java.io.IOException;
import javax.annotation.Resource;
import org.springframework.amqp.core.AmqpTemplate;

/**
 * Created by panyuanyuan on 2017/7/9.
 */
public class MessageProducer {

    @Resource(name="amqpTemplate")
    private AmqpTemplate amqpTemplate;

    @Resource(name="amqpTemplate2")
    private AmqpTemplate amqpTemplate2;

    public void sendMessage(Object message) throws IOException {
        amqpTemplate.convertAndSend("queueTestKey", message);
        amqpTemplate.convertAndSend("queueTestChris", message);

        amqpTemplate2.convertAndSend("wuxing.xxxx.wsdwd", message);
    }
}
