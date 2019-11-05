package noogel.xyz.queue;

import noogel.xyz.config.SpiderQueueConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class QueueFactoryImpl implements QueueFactory {
    private static final Logger logger = LoggerFactory.getLogger(QueueFactory.class);

    @Autowired
    private SpiderQueueConfig config;

    private SpiderQueue spiderQueue = null;

    @Override
    public SpiderQueue getQueue() {
        if (spiderQueue == null) {
            try {
                spiderQueue = (SpiderQueue) Class.forName(config.getLoader()).newInstance();
            } catch (ClassNotFoundException | IllegalAccessException | InstantiationException exc) {
                logger.warn(exc.getMessage(), exc);
                spiderQueue = new SpiderQueueConcurrentQueue();
            }
            spiderQueue.setQueueConfig(config);
        }

        return spiderQueue;
    }
}
