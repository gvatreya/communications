package in.gvatreya.communications.services;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;

public class MessageThreadsManager {

    private static final MessageThreadsManager INSTANCE = new MessageThreadsManager();

    private static ExecutorService messageThreads;

    private MessageThreadsManager() {

        //FIXME: Get threads count from properties
        messageThreads = Executors.newFixedThreadPool(10, new ThreadFactory() {

            Integer counter = 1;

            @Override
            public Thread newThread(Runnable r) {
                Thread messageDeliveryThread = new Thread(r);
                messageDeliveryThread.setDaemon(true);
                messageDeliveryThread.setName("MSG-DELIVERY-"+ counter++);
                return messageDeliveryThread;
            }

        });

    }

    public static MessageThreadsManager getInstance() {
        return INSTANCE;
    }

    public static void stopThreads() throws InterruptedException {
        messageThreads.awaitTermination(2, TimeUnit.MINUTES);
    }

    public ExecutorService getMessageThreadPool() {
        return messageThreads;
    }
}
