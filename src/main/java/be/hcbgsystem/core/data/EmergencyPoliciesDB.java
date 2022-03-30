package be.hcbgsystem.core.data;

import be.hcbgsystem.core.models.policies.emergency.EmergencyPolicy;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;

public class EmergencyPoliciesDB implements EmergencyPoliciesCRUD {

    private ArrayList<EmergencyPolicy> policies;

    private Gson gson = new Gson();

    public void init(File file) {
        try {
            policies = gson.fromJson(new FileReader(file), new TypeToken<ArrayList<EmergencyPolicy>>(){}.getType());
            System.out.println("[POLICY] Loaded " + policies.size() + " Emergency policies");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void addEmergencyPolicy(EmergencyPolicy policy) {
            policies.add(policy);
    }

    @Override
    public void deleteEmergencyPolicy(String id) {
        policies.removeIf((p) -> p.getId().equals(id));
    }

    @Override
    public void updateEmergencyPolicy(String oldId, EmergencyPolicy newPolicy) {
        policies.set(policies.indexOf(newPolicy), newPolicy);
    }

    @Override
    public ArrayList<EmergencyPolicy> getEmergencyPolicies() {
        return new ArrayList<>(policies);
    }

    @Override
    public EmergencyPolicy getEmergencyPolicy(String id) {
        for (EmergencyPolicy policy : policies) {
            if (policy.getId().equals(id)) {
                return policy;
            }
        }
        return null;
    }
}
