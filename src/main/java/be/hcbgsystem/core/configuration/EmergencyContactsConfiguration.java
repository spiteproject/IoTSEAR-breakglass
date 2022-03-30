package be.hcbgsystem.core.configuration;

import be.hcbgsystem.core.models.emergencycontact.EmergencyContact;

import java.util.ArrayList;

public interface EmergencyContactsConfiguration {
    void addEmergencyContact(EmergencyContact contact);
    void deleteEmergencyContact(int id);
    void updateEmergencyContact(int id, EmergencyContact newContact);
    ArrayList<EmergencyContact> getEmergencyContacts();
}
