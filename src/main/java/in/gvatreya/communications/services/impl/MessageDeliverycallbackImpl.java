package in.gvatreya.communications.services.impl;

import in.gvatreya.communications.services.MessageDeliveryCallback;
import in.gvatreya.communications.services.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MessageDeliverycallbackImpl implements MessageDeliveryCallback {

    @Autowired
    MessageService messageService;

    @Override
    public void updateDeliveryStatus(String uuid, String status) {
        messageService.updateStatus(uuid, status);
    }
}
