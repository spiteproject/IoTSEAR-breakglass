package be.hcbgsystem.core.models.policies.breakglass;

import be.hcbgsystem.core.models.nonrepudiation.NonRepudiationRequirements;
import be.hcbgsystem.core.models.policies.ConsentedPolicy;
import be.hcbgsystem.core.models.policies.emergency.EmergencyLevel;

public class BreakGlassPolicy extends ConsentedPolicy {
    private EmergencyLevel emergencyLevel;
    private String description;
    private NonRepudiationRequirements nonRepudiationRequirements;
    private BreakGlassLevel breakGlassLevel;
    private int breakGlassDuration;

    public BreakGlassPolicy(String id, EmergencyLevel emergencyLevel, String description, NonRepudiationRequirements nonRepudiationRequirements, BreakGlassLevel breakGlassLevel, int breakGlassDuration, boolean consented) {
        super(consented, id);
        this.emergencyLevel = emergencyLevel;
        this.description = description;
        this.nonRepudiationRequirements = nonRepudiationRequirements;
        this.breakGlassLevel = breakGlassLevel;
        this.breakGlassDuration = breakGlassDuration;
    }

    public EmergencyLevel getEmergencyLevel() {
        return emergencyLevel;
    }

    public void setEmergencyLevel(EmergencyLevel emergencyLevel) {
        this.emergencyLevel = emergencyLevel;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public NonRepudiationRequirements getNonRepudiationRequirements() {
        return nonRepudiationRequirements;
    }

    public void setNonRepudiationRequirements(NonRepudiationRequirements nonRepudiationRequirements) {
        this.nonRepudiationRequirements = nonRepudiationRequirements;
    }

    public BreakGlassLevel getBreakGlassLevel() {
        return breakGlassLevel;
    }

    public void setBreakGlassLevel(BreakGlassLevel breakGlassLevel) {
        this.breakGlassLevel = breakGlassLevel;
    }

    public int getBreakGlassDuration() {
        return breakGlassDuration;
    }

    public void setBreakGlassDuration(int breakGlassDuration) {
        this.breakGlassDuration = breakGlassDuration;
    }

    @Override
    public String toString() {
        return "BG POLICY";
    }
}
