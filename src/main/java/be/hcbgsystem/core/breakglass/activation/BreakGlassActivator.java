package be.hcbgsystem.core.breakglass.activation;

public interface BreakGlassActivator {
    void startActivator(BreakGlassActivationCallback callback);
    void onCollectingEvidence();
    void onCollectedEvidence();
    void onGlassBroken();
    void onRestored();
}
