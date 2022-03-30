package be.hcbgsystem.core.context;

import be.hcbgsystem.core.context.sinks.ContextDataSink;
import be.hcbgsystem.core.context.sources.ContextDataSource;
import be.hcbgsystem.core.models.ContextData;

import java.util.ArrayList;

public class ContextProcessorNoFilter implements ContextProcessor {
    private ArrayList<ContextDataSink> sinks;

    public ContextProcessorNoFilter() {
        sinks = new ArrayList<>();
    }

    @Override
    public void registerSink(ContextDataSink sink) {
        sinks.add(sink);
    }

    @Override
    public void unregisterSink(ContextDataSink sink) {
        sinks.remove(sink);
    }

    @Override
    public void processContextData(ContextDataSource source, ContextData data) {
        sinks.forEach((sink) -> sink.receiveData(data));
    }
}
