package be.hcbgsystem.notification;

import be.hcbgsystem.core.models.policies.breakglass.BreakGlassLevel;

public interface BreakGlassNotificationPoint extends NotificationPoint {
    void deliverBreakGlassNotification(BreakGlassLevel level);
}
