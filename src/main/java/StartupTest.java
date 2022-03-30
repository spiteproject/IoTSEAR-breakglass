import be.distrinet.spite.iotsear.core.exceptions.ProviderNotFoundException;
import be.hcbgsystem.BreakGlassSystem;

public class StartupTest {
    private void startSystem() throws ProviderNotFoundException {
        BreakGlassSystem breakGlassSystem = new BreakGlassSystem();
        breakGlassSystem.configure("breakglasspolicies.json", "emergencypolicies.json");
        breakGlassSystem.start();
    }

    public void start() {
        try {
            startSystem();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        StartupTest startup = new StartupTest();
        startup.start();
    }
}
