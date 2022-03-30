package be.hcbgsystem.notification.deliverers;

import be.hcbgsystem.core.models.emergencycontact.EmergencyContact;

import java.util.ArrayList;

public class StandardOutputDeliverer implements NotificationDeliverer {

    @Override
    public void deliverNotification(EmergencyContact contact, String message) {
        System.out.println("[Notification] Receiver: " + contact.getFirstName() + " " + contact.getLastName() + "\n" + "Message: " + message);
    }

    @Override
    public void deliverNotification(ArrayList<EmergencyContact> contacts, String message) {
        for (EmergencyContact contact : contacts) {
            deliverNotification(contact, message);
        }
    }
}
