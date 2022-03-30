package be.hcbgsystem.awareness.providers;

import be.hcbgsystem.core.models.awareness.AwarenessMessage;

public interface AwarenessProvider {
    void provideAwareness(AwarenessMessage message);
}
