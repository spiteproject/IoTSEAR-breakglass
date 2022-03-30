package be.hcbgsystem.core.models.policies;

public abstract class Policy {
    private String id;
    private boolean isActive = false;

    public Policy(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public abstract String toString();

    @Override
    public boolean equals(Object obj) {
        return id.equals(((Policy) obj).getId());
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }
}
