package be.hcbgsystem.notification;

import be.hcbgsystem.notification.deliverers.NotificationDeliverer;

public interface NotificationPoint {
    void registerDeliverer(NotificationDeliverer deliverer);
    void unregisterDeliverer(NotificationDeliverer deliverer);
}
