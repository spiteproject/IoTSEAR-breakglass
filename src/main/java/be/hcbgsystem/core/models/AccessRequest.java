package be.hcbgsystem.core.models;

public class AccessRequest {
    private String subject;
    private String resource;
    private String action;

    public AccessRequest(String subject, String resource, String action) {
        this.subject = subject;
        this.resource = resource;
        this.action = action;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getResource() {
        return resource;
    }

    public void setResource(String resource) {
        this.resource = resource;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }
}
