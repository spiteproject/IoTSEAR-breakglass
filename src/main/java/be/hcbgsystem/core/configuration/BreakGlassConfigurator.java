package be.hcbgsystem.core.configuration;

import be.hcbgsystem.core.data.BreakGlassPoliciesCRUD;
import be.hcbgsystem.core.data.EmergencyContactsCRUD;
import be.hcbgsystem.core.data.EmergencyPoliciesCRUD;
import be.hcbgsystem.core.models.emergencycontact.EmergencyContact;
import be.hcbgsystem.core.models.policies.breakglass.BreakGlassPolicy;
import be.hcbgsystem.core.models.policies.emergency.EmergencyPolicy;

import java.util.ArrayList;

public class BreakGlassConfigurator implements BreakGlassPolicyConfiguration, EmergencyContactsConfiguration, EmergencyPolicyConfiguration, PolicyConsent {
    private BreakGlassPoliciesCRUD breakGlassPoliciesCRUD;
    private EmergencyContactsCRUD emergencyContactsCRUD;
    private EmergencyPoliciesCRUD emergencyPoliciesCRUD;

    public BreakGlassConfigurator(BreakGlassPoliciesCRUD breakGlassPoliciesCRUD, EmergencyContactsCRUD emergencyContactsCRUD, EmergencyPoliciesCRUD emergencyPoliciesCRUD) {
        this.breakGlassPoliciesCRUD = breakGlassPoliciesCRUD;
        this.emergencyContactsCRUD = emergencyContactsCRUD;
        this.emergencyPoliciesCRUD = emergencyPoliciesCRUD;
    }

    @Override
    public void addBreakGlassPolicy(BreakGlassPolicy policy) {
        breakGlassPoliciesCRUD.addBreakGlassPolicy(policy);
    }

    @Override
    public void deleteBreakGlassPolicy(String policyId) {
        breakGlassPoliciesCRUD.deleteBreakGlassPolicy(policyId);
    }

    @Override
    public void updateBreakGlassPolicy(BreakGlassPolicy newPolicy) {
        breakGlassPoliciesCRUD.updateBreakGlassPolicy(newPolicy);
    }

    @Override
    public ArrayList<BreakGlassPolicy> getBreakGlassPolicies() {
        return breakGlassPoliciesCRUD.getBreakGlassPolicies();
    }

    @Override
    public BreakGlassPolicy getBreakGlassPolicy(String id) {
        return breakGlassPoliciesCRUD.getBreakGlassPolicy(id);
    }

    @Override
    public void addEmergencyContact(EmergencyContact contact) {
        emergencyContactsCRUD.addEmergencyContact(contact);
    }

    @Override
    public void deleteEmergencyContact(int id) {
        emergencyContactsCRUD.deleteEmergencyContact(id);
    }

    @Override
    public void updateEmergencyContact(int id, EmergencyContact newContact) {
        emergencyContactsCRUD.updateEmergencyContact(id, newContact);
    }

    @Override
    public ArrayList<EmergencyContact> getEmergencyContacts() {
        return emergencyContactsCRUD.getEmergencyContacts();
    }

    @Override
    public void addEmergencyPolicy(EmergencyPolicy policy) {
        emergencyPoliciesCRUD.addEmergencyPolicy(policy);
    }

    @Override
    public void deleteEmergencyPolicy(String id) {
        emergencyPoliciesCRUD.deleteEmergencyPolicy(id);
    }

    @Override
    public void updateEmergencyPolicy(String oldId, EmergencyPolicy newPolicy) {
        emergencyPoliciesCRUD.updateEmergencyPolicy(oldId, newPolicy);
    }

    @Override
    public ArrayList<EmergencyPolicy> getEmergencyPolicies() {
        return emergencyPoliciesCRUD.getEmergencyPolicies();
    }

    @Override
    public EmergencyPolicy getEmergencyPolicy(String id) {
        return emergencyPoliciesCRUD.getEmergencyPolicy(id);
    }

    @Override
    public void giveConsent(BreakGlassPolicy policy, boolean consent) {
        policy.setConsented(consent);
        breakGlassPoliciesCRUD.updateBreakGlassPolicy(policy);
    }

    @Override
    public void giveConsent(EmergencyPolicy policy, boolean consent) {
        policy.setConsented(consent);
        emergencyPoliciesCRUD.updateEmergencyPolicy(policy.getId(), policy);
    }
}
