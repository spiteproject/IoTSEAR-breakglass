package be.hcbgsystem.core.models;

public class DigitalSignature {
    private byte[] signature;
    private byte[] publicKey;

    public DigitalSignature(byte[] signature, byte[] publicKey) {
        this.signature = signature;
        this.publicKey = publicKey;
    }

    public byte[] getSignature() {
        return signature;
    }

    public void setSignature(byte[] signature) {
        this.signature = signature;
    }

    public byte[] getPublicKey() {
        return publicKey;
    }

    public void setPublicKey(byte[] publicKey) {
        this.publicKey = publicKey;
    }
}
