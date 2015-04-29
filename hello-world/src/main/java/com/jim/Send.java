package com.jim;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;

/**
 * JimQiao
 * 2015-04-29 10:46
 */
public class Send {
    private static final String QUEUE_NAME = "hello";

    public static void main(String[] args) throws IOException {
        Connection conn = null;
        Channel channel = null;
        try {
            ConnectionFactory factory = new ConnectionFactory();
            factory.setHost("localhost");
            conn = factory.newConnection();
            channel = conn.createChannel();

            channel.queueDeclare(QUEUE_NAME, false, false, false, null);
            String msg = "Hello World!";

            channel.basicPublish("", QUEUE_NAME, null, msg.getBytes());

            System.out.println("[x] Sent '" + msg + "'");
        } finally {
            if (channel != null)
                channel.close();
            if (conn != null)
                conn.close();
        }
    }
}
