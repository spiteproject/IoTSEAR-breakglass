package be.hcbgsystem.awareness.providers;

import be.hcbgsystem.core.models.awareness.AwarenessMessage;

public class StandardOutputAwarenessProvider implements AwarenessProvider {
    @Override
    public void provideAwareness(AwarenessMessage message) {
        String msg = "";
        msg += message.getPrelude() + "\n";
        msg += message.getBgPolicy().toString();

        System.out.println(msg);
    }
}
