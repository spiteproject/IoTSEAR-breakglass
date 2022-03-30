package be.hcbgsystem.notification.deliverers;

import be.hcbgsystem.core.models.emergencycontact.EmergencyContact;

import java.util.ArrayList;

public interface NotificationDeliverer {
    void deliverNotification(EmergencyContact contact, String message);
    void deliverNotification(ArrayList<EmergencyContact> contacts, String message);
}
