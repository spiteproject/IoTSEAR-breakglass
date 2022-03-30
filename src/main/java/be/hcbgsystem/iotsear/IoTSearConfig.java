package be.hcbgsystem.iotsear;

import be.distrinet.spite.iotsear.core.model.context.ContextSource;

import java.util.ArrayList;
import java.util.Iterator;

public class IoTSearConfig {
    private static ArrayList<ContextSource.ContextListener> contextListeners = new ArrayList<>();

    public static void add(ContextSource.ContextListener listener) {
        contextListeners.add(listener);
    }

    public static Iterator<ContextSource.ContextListener> getIterator() { return contextListeners.iterator(); }
}
