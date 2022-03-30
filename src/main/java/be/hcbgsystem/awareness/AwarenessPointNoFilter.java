package be.hcbgsystem.awareness;

import be.hcbgsystem.awareness.providers.AwarenessProvider;
import be.hcbgsystem.core.models.awareness.AwarenessMessage;
import be.hcbgsystem.core.models.policies.breakglass.BreakGlassPolicy;
import java.util.ArrayList;

public class AwarenessPointNoFilter implements AwarenessPoint {
    private ArrayList<AwarenessProvider> providers;

    public AwarenessPointNoFilter() {
        providers = new ArrayList<>();
    }

    @Override
    public void registerProvider(AwarenessProvider provider) {
        providers.add(provider);
    }

    @Override
    public void unregisterProvider(AwarenessProvider provider) {
        providers.remove(provider);
    }

    @Override
    public void provideAwareness(BreakGlassPolicy policy) {
        for (AwarenessProvider provider : providers) {
            AwarenessMessage msg = new AwarenessMessage("PRIVACY STATEMENT AND GDPR APPLY <br> By breaking the glass you agree with.......", policy); // TODO Make privacy stmt configurable
            provider.provideAwareness(msg);
        }
    }
}
