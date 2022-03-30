package be.hcbgsystem.core.models;

import java.sql.Timestamp;
import java.time.Instant;

public class ContextData<T> {
    private String id;
    private String contextType;
    private T data;
    private Timestamp timestampCollected;

    public ContextData(String id, String contextType, T data, Timestamp timestampCollected) {
        this.id = id;
        this.contextType = contextType;
        this.data = data;
        this.timestampCollected = timestampCollected;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getContextType() {
        return contextType;
    }

    public void setContextType(String contextType) {
        this.contextType = contextType;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public Timestamp getTimestampCollected() {
        return timestampCollected;
    }

    public void setTimestampCollected(Timestamp timestampCollected) {
        this.timestampCollected = timestampCollected;
    }

    // MaxAge: seconds
    public boolean isFresh(int maxAge) {
       Timestamp current = new Timestamp(Instant.now().toEpochMilli());
       long milliDiff = current.getTime() - timestampCollected.getTime();
       return milliDiff < maxAge * 1000;
    }
}
