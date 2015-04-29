package com.jim;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.util.Scanner;

/**
 * JimQiao
 * 2015-04-29 17:23
 */
public class EmitLogTopic {

    private static final String EXCHANGE_NAME = "topic_logs";

    public static void main(String[] argv)
            throws Exception {

        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

        channel.exchangeDeclare(EXCHANGE_NAME, "topic");

        Scanner scanner = new Scanner(System.in);
        System.out.println("Waiting pres msg [level] [msg]");

        while (true) {
            String line = scanner.nextLine();
            if (line.equals("") || line.equals("exit")) break;

            argv = line.split(" ");

            String routingKey = getRouting(argv);
            String message = getMessage(argv);

            channel.basicPublish(EXCHANGE_NAME, routingKey, null, message.getBytes());
            System.out.println(" [x] Sent '" + routingKey + "':'" + message + "'");
        }
        connection.close();
    }

    private static String getMessage(String[] argv) {
        StringBuilder builder = new StringBuilder();
        for (int i = 1; i < argv.length; i++) {
            builder.append(argv[i]);
        }
        return builder.toString();
    }

    private static String getRouting(String[] argv) {
        return argv[0];
    }

}
