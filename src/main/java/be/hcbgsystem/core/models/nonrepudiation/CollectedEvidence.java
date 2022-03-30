package be.hcbgsystem.core.models.nonrepudiation;

public class CollectedEvidence {
    private String providerId;
    private Byte[] evidence;

    public CollectedEvidence(Byte[] evidence) {
        this.evidence = evidence;
    }

    public Byte[] getEvidence() {
        return evidence;
    }

    public void setEvidence(Byte[] evidence) {
        this.evidence = evidence;
    }
}
