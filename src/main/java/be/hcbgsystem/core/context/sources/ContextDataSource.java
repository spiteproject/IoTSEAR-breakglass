package be.hcbgsystem.core.context.sources;

import be.hcbgsystem.core.context.ContextProcessor;
import be.hcbgsystem.core.models.ContextData;

public abstract class ContextDataSource {
    private ContextProcessor processor;

    public ContextDataSource(ContextProcessor processor) {
        this.processor = processor;
    }

    protected void onReceived(ContextData data) {
        processor.processContextData(this, data);
    }

    public abstract void startSource();
    public abstract void stopSource();
}
