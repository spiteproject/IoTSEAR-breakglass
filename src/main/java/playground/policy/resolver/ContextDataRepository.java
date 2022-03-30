package playground.policy.resolver;

import be.hcbgsystem.core.models.ContextData;

import java.util.ArrayList;
import java.util.HashMap;

public class ContextDataRepository {
    private HashMap<String, ArrayList<ContextData>> repository;

    public ContextDataRepository() {
        repository = new HashMap<>();
    }

    public void addContextData(ContextData data) {
        if (!repository.containsKey(data.getId())) {
            repository.put(data.getId(), new ArrayList<>());
        }
        ArrayList<ContextData> contextData = repository.get(data.getId());
        contextData.add(data);
        repository.put(data.getId(), contextData);
    }

    public ArrayList<ContextData> getContextData(String providerId) {
        if (repository.containsKey(providerId)) {
            return repository.get(providerId);
        }
        return null;
    }
}
