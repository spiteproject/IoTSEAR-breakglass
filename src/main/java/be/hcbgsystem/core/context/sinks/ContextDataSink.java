package be.hcbgsystem.core.context.sinks;

import be.hcbgsystem.core.models.ContextData;

public interface ContextDataSink {
    void receiveData(ContextData data);
}
