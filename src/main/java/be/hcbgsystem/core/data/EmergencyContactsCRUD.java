package be.hcbgsystem.core.data;

import be.hcbgsystem.core.models.emergencycontact.EmergencyContact;

import java.util.ArrayList;

public interface EmergencyContactsCRUD {
    void addEmergencyContact(EmergencyContact contact);
    void deleteEmergencyContact(int id);
    void updateEmergencyContact(int id, EmergencyContact newContact);
    ArrayList<EmergencyContact> getEmergencyContacts();
    ArrayList<EmergencyContact> getPatientContacts();
}
