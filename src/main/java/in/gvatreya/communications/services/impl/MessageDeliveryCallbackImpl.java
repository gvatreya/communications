package in.gvatreya.communications.services.impl;

import in.gvatreya.communications.services.MessageDeliveryCallback;
import in.gvatreya.communications.services.MessageDeliveryTask;
import in.gvatreya.communications.services.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Component
public class MessageDeliveryCallbackImpl implements MessageDeliveryCallback {

    final MessageService messageService;

    public MessageDeliveryCallbackImpl(MessageService messageService) {
        this.messageService = messageService;
    }

    @Override
    public void updateDeliveryStatus(String uuid, String status) {
        messageService.updateStatus(uuid, status);
    }
}
