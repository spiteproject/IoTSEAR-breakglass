package be.hcbgsystem.notification;

import be.hcbgsystem.core.data.EmergencyContactsCRUD;
import be.hcbgsystem.core.models.policies.breakglass.BreakGlassLevel;
import be.hcbgsystem.core.models.emergencycontact.EmergencyContact;
import be.hcbgsystem.core.models.policies.emergency.EmergencyLevel;
import be.hcbgsystem.notification.deliverers.NotificationDeliverer;

import java.util.ArrayList;

public class NotificationPointAll implements EmergencyNotificationPoint, BreakGlassNotificationPoint {
    private ArrayList<NotificationDeliverer> deliverers;
    private EmergencyContactsCRUD emergencyContactsCRUD;


    public NotificationPointAll(EmergencyContactsCRUD emergencyContactsCRUD) {
        this.emergencyContactsCRUD = emergencyContactsCRUD;
        deliverers = new ArrayList<>();
    }

    @Override
    public void registerDeliverer(NotificationDeliverer deliverer) {
        deliverers.add(deliverer);
    }

    @Override
    public void unregisterDeliverer(NotificationDeliverer deliverer) {
        deliverers.remove(deliverer);
    }

    private void deliver(ArrayList<EmergencyContact> contacts, String message) {
        for (NotificationDeliverer deliverer : deliverers) {
            new Thread(deliverer.toString()) {
                @Override
                public void run() {
                    deliverer.deliverNotification(contacts, message);
                }
            }.start();
        }
    }

    @Override
    public void deliverBreakGlassNotification(BreakGlassLevel level) {
        String msg = "Break Glass activated! Level: " + level.getLevel();

        deliver(emergencyContactsCRUD.getEmergencyContacts(), msg);
    }

    @Override
    public void deliverEmergencyNotification(EmergencyLevel level) {
        String msg = "Emergency detected! Level: " + level.getLevel();

        deliver(emergencyContactsCRUD.getEmergencyContacts(), msg);
    }

    @Override
    public void deliverEmergencyInterruptNotification(EmergencyLevel level, int cancelPeriod) {
        String msg = "Emergency detected! Time to cancel: " + cancelPeriod + " seconds";
        deliver(emergencyContactsCRUD.getPatientContacts(), msg);
    }
}
