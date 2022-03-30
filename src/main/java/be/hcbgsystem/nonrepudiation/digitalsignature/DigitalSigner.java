package be.hcbgsystem.nonrepudiation.digitalsignature;

import be.hcbgsystem.core.models.DigitalSignature;

import java.security.KeyPair;

public interface DigitalSigner {
    KeyPair getNewKeyPair();
    DigitalSignature sign(KeyPair privateKey, byte[] toSign);
}
