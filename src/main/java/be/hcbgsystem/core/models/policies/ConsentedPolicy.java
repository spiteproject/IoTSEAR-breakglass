package be.hcbgsystem.core.models.policies;

public class ConsentedPolicy extends Policy {
    private boolean consented;

    public ConsentedPolicy(boolean consented, String id) {
        super(id);
    }

    public boolean isConsented() {
        return consented;
    }

    public void setConsented(boolean consented) {
        this.consented = consented;
    }

    @Override
    public String toString() {
        return getId();
    }
}
