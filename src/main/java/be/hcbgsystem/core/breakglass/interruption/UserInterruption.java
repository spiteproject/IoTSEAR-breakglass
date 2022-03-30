package be.hcbgsystem.core.breakglass.interruption;

import be.hcbgsystem.core.models.InterruptableEvent;

public interface UserInterruption {
    UserInterruptionResult interruptActivation();
    InterruptableEvent getInterruptableEvents();
}
