package be.hcbgsystem.core.breakglass.activation;

public interface BreakGlassActivationHandler {
    void registerBreakGlassActivator(BreakGlassActivator activator);
    void unregisterBreakGlassActivator(BreakGlassActivator activator);
}
