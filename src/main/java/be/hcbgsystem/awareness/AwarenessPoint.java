package be.hcbgsystem.awareness;

import be.hcbgsystem.awareness.providers.AwarenessProvider;
import be.hcbgsystem.core.models.policies.breakglass.BreakGlassPolicy;
import org.pf4j.ExtensionPoint;

public interface AwarenessPoint extends ExtensionPoint {
    void registerProvider(AwarenessProvider provider);
    void unregisterProvider(AwarenessProvider provider);
    void provideAwareness(BreakGlassPolicy policy);
}
