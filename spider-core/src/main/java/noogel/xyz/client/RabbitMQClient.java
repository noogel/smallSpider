package noogel.xyz.client;

import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class RabbitMQClient {
    private static final Logger logger = LoggerFactory.getLogger(RabbitMQClient.class);
    private static ConnectionFactory connectionFactory = new ConnectionFactory();

    public static Connection getConnection(String host, Integer port, String userName, String password) {
        connectionFactory.setHost(host);
        if (port != null && port > 0) {
            connectionFactory.setPort(port);
        }
        connectionFactory.setUsername(userName);
        connectionFactory.setPassword(password);
        connectionFactory.setAutomaticRecoveryEnabled(true);
        try {
            return connectionFactory.newConnection();
        } catch (IOException | TimeoutException exc) {
            logger.error(exc.getMessage(), exc);
        }
        return null;
    }
}
