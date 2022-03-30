package be.hcbgsystem.core.models.nonrepudiation;

import java.util.ArrayList;

public class NonRepudiationEvidence {
    private final String uid = String.valueOf(System.currentTimeMillis());
    ArrayList<NonRepudiationEvidenceRecord> evidence;

    public NonRepudiationEvidence() {
        evidence = new ArrayList<>();
    }

    public String getUid() {
        return uid;
    }

    public void addEvidence(NonRepudiationEvidenceRecord record) {
        evidence.add(record);
    }

    public ArrayList<NonRepudiationEvidenceRecord> getEvidence() {
        return evidence;
    }

    public void clear() {
        evidence.clear();
    }
}
