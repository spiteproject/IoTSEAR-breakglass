package be.hcbgsystem.notification;

import be.hcbgsystem.core.models.policies.emergency.EmergencyLevel;

public interface EmergencyNotificationPoint extends NotificationPoint {
    void deliverEmergencyNotification(EmergencyLevel level);
    void deliverEmergencyInterruptNotification(EmergencyLevel level, int cancelPeriod);
}
