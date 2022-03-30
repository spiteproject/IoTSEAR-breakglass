package be.hcbgsystem.core.data;

import be.hcbgsystem.core.models.policies.breakglass.BreakGlassPolicy;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;

public class BreakGlassPoliciesDB implements BreakGlassPoliciesCRUD {
    private ArrayList<BreakGlassPolicy> policies;

    private Gson gson = new Gson();

    public void init(File file) {
        try {
            policies = gson.fromJson(new FileReader(file), new TypeToken<ArrayList<BreakGlassPolicy>>(){}.getType());
            System.out.println("[POLICY] Loaded " + policies.size() + " Break Glass policies");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void addBreakGlassPolicy(BreakGlassPolicy policy) {
        policies.add(policy);
    }

    @Override
    public void deleteBreakGlassPolicy(String policyId) {
        policies.removeIf((p) -> p.getId().equals(policyId));
    }

    @Override
    public void updateBreakGlassPolicy(BreakGlassPolicy newPolicy) {
        policies.set(policies.indexOf(newPolicy), newPolicy);
    }

    @Override
    public ArrayList<BreakGlassPolicy> getBreakGlassPolicies() {
        return new ArrayList<>(policies);
    }

    @Override
    public ArrayList<BreakGlassPolicy> getActiveBreakGlassPolicies() {
        ArrayList<BreakGlassPolicy> active = new ArrayList<>();

        policies.forEach((p) -> {
            if(p.isActive() && p.isConsented()) active.add(p);
        });

        return active;
    }

    @Override
    public void setActiveBreakGlassPolicies(ArrayList<BreakGlassPolicy> policies) {
        policies.forEach((p) -> {
            p.setActive(policies.contains(p));
        });
    }

    @Override
    public BreakGlassPolicy getBreakGlassPolicy(String id) {
        for (BreakGlassPolicy policy : policies) {
            if (policy.getId().equals(id)) {
                return policy;
            }
        }
        return null;
    }
}
