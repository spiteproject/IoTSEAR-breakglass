package be.hcbgsystem.core.models.nonrepudiation;

import java.util.ArrayList;

public class NonRepudiationRequirements {
    private ArrayList<String> providers;

    public NonRepudiationRequirements() {
        providers = new ArrayList<>();
    }

    public ArrayList<String> getProviders() {
        return providers;
    }

    public void setProviders(ArrayList<String> providers) {
        this.providers = providers;
    }

    public void addProvider(String providerId) {
        providers.add(providerId);
    }

    public boolean containsProvider(String providerId) {
        return providers.contains(providerId);
    }
}
