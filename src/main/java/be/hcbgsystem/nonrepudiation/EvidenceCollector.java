package be.hcbgsystem.nonrepudiation;

import be.hcbgsystem.core.models.nonrepudiation.NonRepudiationRequirements;
import be.hcbgsystem.nonrepudiation.providers.NonRepudiationProvider;

public interface EvidenceCollector {
    void registerNonRepudiationProvider(NonRepudiationProvider provider);
    void unregisterNonRepudiationProvider(NonRepudiationProvider provider);
    void collectEvidence(NonRepudiationRequirements requirements, EvidenceCollectedCallback callback);
}
