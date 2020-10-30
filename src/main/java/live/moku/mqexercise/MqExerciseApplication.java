package live.moku.mqexercise;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;

@SpringBootApplication
@Component
@Slf4j
public class MqExerciseApplication {

//    @KafkaListener(topics = "${spring.kafka.template.default-topic}",groupId = "save-to-db")
//    public void consumeTest(ConsumerRecord consumerRecord) {
//        log.info("==========================开始消费==================================");
//        log.info("*************** 消费消息 *************get from topic {}, partition {}, value {}", consumerRecord.topic(), consumerRecord.partition(), consumerRecord.value());
//    }

    public static void main(String[] args) throws IOException {

//        ApplicationContext applicationContext = SpringApplication.run(MqExerciseApplication.class, args);

        ServerSocket serverSocket = new ServerSocket();
        serverSocket.bind(new InetSocketAddress(8088));
        Ldy ldy = new Ldy();
        Socket socket = null;
        byte[] b = new byte[100];
        while (true) {
            socket = serverSocket.accept();
            OutputStream os = socket.getOutputStream();
            InputStream is = socket.getInputStream();
            try {
                ldy.listen(b, is, os);
            } catch (Exception e) {
                System.out.println("客户端连接已断开 " + e.getCause());
            }
        }
    }

//        KafkaTemplate kafkaTemplate = applicationContext.getBean(KafkaTemplate.class);
//
//        Scanner scanner = new Scanner(System.in);
//        System.out.println("请输入，回车发送");
//        String input = "";
//        while(scanner.hasNext()) {
//            input = scanner.next();
//            kafkaTemplate.sendDefault(input);
//        }


}
