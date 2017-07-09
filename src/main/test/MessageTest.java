import java.io.IOException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import task.spring.MessageProducer;


/**
 * Created by panyuanyuan on 2017/7/9.
 */
public class MessageTest {

    public static void main(String[] args) throws IOException {
        ApplicationContext context = new ClassPathXmlApplicationContext("classpath:applicationContext.xml");
        MessageProducer messageProducer = (MessageProducer) context.getBean("messageProducer");

        int a = 4;
        while (a > 0) {
            try {
                //暂停一下，好让消息消费者去取消息打印出来
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            messageProducer.sendMessage("发送消息 :" + a--);
            System.out.println("$$$$$$" + a);
        }
    }
}
