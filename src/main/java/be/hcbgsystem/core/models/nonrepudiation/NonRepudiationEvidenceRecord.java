package be.hcbgsystem.core.models.nonrepudiation;

public class NonRepudiationEvidenceRecord {
    private String provider;
    private byte[] evidence;
    private String extension;

    public NonRepudiationEvidenceRecord(String provider, byte[] evidence, String extension) {
        this.provider = provider;
        this.evidence = evidence;
        this.extension = extension;
    }

    public String getProvider() {
        return provider;
    }

    public void setProvider(String provider) {
        this.provider = provider;
    }

    public byte[] getEvidence() {
        return evidence;
    }

    public void setEvidence(byte[] evidence) {
        this.evidence = evidence;
    }

    public String getExtension() {
        return extension;
    }

    public void setExtension(String extension) {
        this.extension = extension;
    }
}
