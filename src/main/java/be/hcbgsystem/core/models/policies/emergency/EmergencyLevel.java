package be.hcbgsystem.core.models.policies.emergency;

public class EmergencyLevel {
    private int level;

    public EmergencyLevel(int level) {
        this.level = level;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }
}
