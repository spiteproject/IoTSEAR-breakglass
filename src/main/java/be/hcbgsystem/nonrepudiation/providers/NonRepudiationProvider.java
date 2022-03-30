package be.hcbgsystem.nonrepudiation.providers;

import org.pf4j.ExtensionPoint;

public interface NonRepudiationProvider extends ExtensionPoint  {
    void collectEvidence(EvidenceProducedCallback callback);
    String getId();
    String getDataExtension();
}
