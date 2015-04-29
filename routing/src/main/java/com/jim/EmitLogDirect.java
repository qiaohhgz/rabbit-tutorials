package com.jim;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.util.Scanner;

/**
 * JimQiao
 * 2015-04-29 16:48
 */
public class EmitLogDirect {
    private static final String EXCHANGE_NAME = "direct_logs";

    public static void main(String[] argv)
            throws java.io.IOException {

        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

        channel.exchangeDeclare(EXCHANGE_NAME, "direct");

        Scanner scanner = new Scanner(System.in);
        System.out.println("Waiting pres msg [level] [msg]");
        while (true) {
            String line = scanner.nextLine();
            if (line.equals("") || line.equals("exit")) break;

            argv = line.split(" ");
            
            /* 日志级别 error,warn,info,debug */
            String severity = getSeverity(argv);
            /* 日志内容 */
            String message = getMessage(argv);

            channel.basicPublish(EXCHANGE_NAME, severity, null, message.getBytes());
            System.out.println(" [x] Sent '" + severity + "':'" + message + "'");
        }

        channel.close();
        connection.close();
    }

    private static String getMessage(String[] argv) {
        return argv[1];
    }

    private static String getSeverity(String[] argv) {
        return argv[0];
    }
}
