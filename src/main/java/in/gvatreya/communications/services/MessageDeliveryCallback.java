package in.gvatreya.communications.services;

public interface MessageDeliveryCallback {

    void updateDeliveryStatus(final String uuid, final String status);
}
