package noogel.xyz.queue;

import com.rabbitmq.client.*;
import noogel.xyz.client.RabbitMQClient;
import noogel.xyz.config.SpiderQueueConfig;
import noogel.xyz.dto.QueueTask;
import noogel.xyz.dto.QueueTaskHolder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.function.Consumer;

public class SpiderQueueRabbitMq implements SpiderQueue {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private SpiderQueueConfig config = null;
    private Connection connection = null;
    private Channel producerChannel = null;
    private Channel consumerChannel = null;

    private void createProducerChannels() {
        try {
            producerChannel = connection.createChannel();
            producerChannel.queueDeclare(config.getQueueName(), false, false, false, null);
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
        }
    }

    private void createConsumerChannels() {
        try {
            consumerChannel = connection.createChannel();
            consumerChannel.basicQos(1);
            consumerChannel.queueDeclare(config.getQueueName(), false, false, false, null);
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
        }
    }

    private Connection getConnection() {
        if (connection == null) {
            connection = RabbitMQClient.getConnection(config.getHost(), config.getPort(), config.getUserName(), config.getPassword());
            createProducerChannels();
            createConsumerChannels();
        }
        return connection;
    }

    @Override
    public void setQueueConfig(SpiderQueueConfig config) {
        this.config = config;
        getConnection();
    }

    @Override
    public <T extends QueueTask> boolean add(T t) {
        QueueTaskHolder holder = new QueueTaskHolder(t);
        try {
            this.producerChannel.basicPublish("", config.getQueueName(), null, holder.getBytesTask());
            logger.info("add url: " + t.getUrl());
            return true;
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
            return false;
        }
    }

    @Override
    public <T extends QueueTask> void consume(Consumer<T> consumer) {
        try {

            DefaultConsumer defaultConsumer = new DefaultConsumer(this.consumerChannel) {
                @Override
                public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                    super.handleDelivery(consumerTag, envelope, properties, body);
                    QueueTaskHolder holder = new QueueTaskHolder(body);
                    T queueTask = holder.getQueueTask();
                    logger.info("get url: " + queueTask.getUrl());
                    consumer.accept(queueTask);
                    consumerChannel.basicAck(envelope.getDeliveryTag(), false);
                }
            };

            String basicConsume = consumerChannel.basicConsume(config.getQueueName(), false, defaultConsumer);
            Thread.sleep(100);
        } catch (IOException | InterruptedException e) {
            logger.error(e.getMessage(), e);
        }
    }

    @Override
    public long size() {
        return 0;
    }
}
