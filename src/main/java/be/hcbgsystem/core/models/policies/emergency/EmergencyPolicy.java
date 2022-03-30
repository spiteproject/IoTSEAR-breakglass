package be.hcbgsystem.core.models.policies.emergency;

import be.hcbgsystem.core.models.policies.ConsentedPolicy;

public class EmergencyPolicy extends ConsentedPolicy {
    private EmergencyLevel emergencyLevel;
    private String description;
    private EmergencyCondition condition;
    private Integer freshness;
    private Integer cancelPeriod;
    private Integer emergencyPeriod;

    public EmergencyPolicy(String id, EmergencyLevel emergencyLevel, String description, EmergencyCondition condition, Integer freshness, Integer cancelPeriod, Integer emergencyPeriod, boolean consented) {
        super(consented, id);
        this.emergencyLevel = emergencyLevel;
        this.description = description;
        this.condition = condition;
        this.freshness = freshness;
        this.cancelPeriod = cancelPeriod;
        this.emergencyPeriod = emergencyPeriod;
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

    public EmergencyCondition getCondition() {
        return condition;
    }

    public void setCondition(EmergencyCondition condition) {
        this.condition = condition;
    }

    public Integer getFreshness() {
        return freshness;
    }

    public void setFreshness(Integer freshness) {
        this.freshness = freshness;
    }

    public Integer getCancelPeriod() {
        return cancelPeriod;
    }

    public void setCancelPeriod(Integer cancelPeriod) {
        this.cancelPeriod = cancelPeriod;
    }

    public Integer getEmergencyPeriod() {
        return emergencyPeriod;
    }

    public void setEmergencyPeriod(Integer emergencyPeriod) {
        this.emergencyPeriod = emergencyPeriod;
    }

    @Override
    public String toString() {
        return getId();
    }
}
