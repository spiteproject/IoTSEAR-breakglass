package be.hcbgsystem.core.models.nonrepudiation;

import java.util.HashMap;
import java.util.Map;

public class FileNonRepudiationEvidence extends RetainedNonRepudiationEvidence {
    Map<String, String> evidence; // <non repudiation provider ID, filename>

    public FileNonRepudiationEvidence() {
        evidence = new HashMap<>();
    }

    public Map<String, String> getEvidence() {
        return evidence;
    }

    public void setEvidence(Map<String, String> evidence) {
        this.evidence = evidence;
    }

    public void addEvidence(String nonRepudiationProviderId, String fileName) {
        evidence.put(nonRepudiationProviderId, fileName);
    }
}
