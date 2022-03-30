package be.hcbgsystem.nonrepudiation;

import be.hcbgsystem.core.models.nonrepudiation.NonRepudiationEvidence;

public interface EvidenceCollectedCallback {
    void evidenceCollected(NonRepudiationEvidence evidence);
    void evidenceCollectionFailed();
}
