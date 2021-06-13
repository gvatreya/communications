package in.gvatreya.communications.services;

import in.gvatreya.communications.model.Message;
import in.gvatreya.communications.utils.Constants;
import org.apache.commons.lang3.RandomUtils;

public class MessageDeliveryTask implements Runnable {

    private MessageService messageService;
    private MessageDeliveryCallback callback;

    private final Message message;

    public MessageDeliveryTask(Message message, MessageDeliveryCallback callback) {
        this.message = message;
        this.callback = callback;
    }

    @Override
    public void run() {
        //FIXME: This thread will look at the channel and then will call appropriate
        // services to deliver the message by the specified channel
        // For now, this will wait for 1.5 seconds and then update
        // the message delivery status to either Delivered or Error (randomly)
        Thread.currentThread().setName("MSG_DLVRY_" + message.getId());
        try {
            Thread.sleep(1500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        final String status = RandomUtils.nextInt() % 2 == 0 ?
                Constants.DELIVERY_STATUS.COMPLETED.name() :
                Constants.DELIVERY_STATUS.ERROR.name();

        this.callback.updateDeliveryStatus(message.getUuid(), status);

    }
}
