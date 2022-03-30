package benchmarks;

import be.hcbgsystem.core.models.nonrepudiation.NonRepudiationRequirements;
import be.hcbgsystem.core.models.policies.breakglass.BreakGlassLevel;
import be.hcbgsystem.core.models.policies.breakglass.BreakGlassPolicy;
import be.hcbgsystem.core.models.policies.emergency.EmergencyLevel;
import com.google.gson.Gson;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Random;

public class Scaffold {
    public static void main(String[] args) throws IOException {
        int nBreakGlassPolicies = 100000;
        Random random = new Random();

        ArrayList<BreakGlassPolicy> breakGlassPolicies = new ArrayList<>();
        for (int i = 0; i < nBreakGlassPolicies; i++) {
            String id = "bg-policy-" + (i+1);
            EmergencyLevel emergencyLevel = new EmergencyLevel(random.nextInt(6) + 1); // [1; 7]
            NonRepudiationRequirements nonRepudiationRequirements = new NonRepudiationRequirements();
            nonRepudiationRequirements.addProvider("eid-reader-1");
            BreakGlassLevel breakGlassLevel = new BreakGlassLevel(random.nextInt(6) + 1); // [1; 7]
            int duration = random.nextInt(100) + 30; // [30; 130]
            BreakGlassPolicy policy = new BreakGlassPolicy(id, emergencyLevel, "the description", nonRepudiationRequirements, breakGlassLevel, duration, true);
            breakGlassPolicies.add(policy);
        }

        Files.write(Paths.get("./breakglasspolicies-100000"), new Gson().toJson(breakGlassPolicies).getBytes());
    }
}
