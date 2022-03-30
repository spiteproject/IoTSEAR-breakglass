package be.hcbgsystem.core.context.sources;

import be.distrinet.spite.iotsear.core.model.context.ContextAttribute;
import be.distrinet.spite.iotsear.core.model.context.ContextSource;
import be.hcbgsystem.core.context.ContextProcessor;
import be.hcbgsystem.core.models.ContextData;

import java.sql.Timestamp;
import java.util.List;

public class IoTSEARDataSource extends ContextDataSource implements ContextSource.ContextListener {
    private List<ContextSource> contextSources;

    public IoTSEARDataSource(ContextProcessor processor, List<ContextSource> contextSources) {
        super(processor);
        this.contextSources = contextSources;
    }

    @Override
    public void processContext(ContextAttribute contextAttribute) {
      //  new Thread(() -> {
            ContextData newData = new ContextData<String>(contextAttribute.getSource().getProviderID(), contextAttribute.getType(), contextAttribute.getValue(), new Timestamp(contextAttribute.getTimestamp()));
            onReceived(newData);
      // }).start();
    }

    @Override
    public void startSource() {
        contextSources.forEach((source) -> source.addContextListener(this));
    }

    @Override
    public void stopSource() {

    }
}
