package playground.iotsear;

import be.distrinet.spite.iotsear.IoTSEAR;
import be.distrinet.spite.iotsear.pbms.ContextStorage;
import be.distrinet.spite.iotsear.pbms.PolicyRepository;

import java.io.File;

public class SearTest {
    public static void main(String[] args) {
        IoTSEAR ioTSEAR = IoTSEAR.getInstance();
        ioTSEAR.configure(new File(SearTest.class.getClassLoader().getResource("iotsear/config.json").getFile()));
        ContextStorage store = ioTSEAR.getContextStore();
        PolicyRepository repo = ioTSEAR.getPolicyRepository();
    }
}
