package be.hcbgsystem.core.models;

public class InterruptableEvent {
    private String event;

    public InterruptableEvent(String event) {
        this.event = event;
    }

    public String getEvent() {
        return event;
    }

    public void setEvent(String event) {
        this.event = event;
    }

    @Override
    public String toString() {
        return event;
    }
}
