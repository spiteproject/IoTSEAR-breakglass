package be.hcbgsystem.core.context;

import be.hcbgsystem.core.context.sinks.ContextDataSink;
import be.hcbgsystem.core.context.sources.ContextDataSource;
import be.hcbgsystem.core.models.ContextData;

public interface ContextProcessor {
    void registerSink(ContextDataSink sink);
    void unregisterSink(ContextDataSink sink);
    void processContextData(ContextDataSource source, ContextData data);
}
