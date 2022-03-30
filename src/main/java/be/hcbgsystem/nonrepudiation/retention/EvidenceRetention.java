package be.hcbgsystem.nonrepudiation.retention;

import be.hcbgsystem.core.models.nonrepudiation.NonRepudiationEvidence;
import be.hcbgsystem.core.models.nonrepudiation.RetainedNonRepudiationEvidence;

public interface EvidenceRetention {
    RetainedNonRepudiationEvidence retainEvidence(NonRepudiationEvidence evidence);
}
