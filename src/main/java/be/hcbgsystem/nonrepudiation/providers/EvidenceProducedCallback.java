package be.hcbgsystem.nonrepudiation.providers;

import be.hcbgsystem.core.models.nonrepudiation.NonRepudiationEvidenceRecord;

public interface EvidenceProducedCallback {
    void onEvidenceCollected(NonRepudiationEvidenceRecord evidence);
    void onEvidenceCollectionFailed();
}
