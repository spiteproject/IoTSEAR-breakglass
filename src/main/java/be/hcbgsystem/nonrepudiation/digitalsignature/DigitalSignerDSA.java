package be.hcbgsystem.nonrepudiation.digitalsignature;

import be.hcbgsystem.core.models.DigitalSignature;

import java.security.*;

public class DigitalSignerDSA implements  DigitalSigner{
    @Override
    public KeyPair getNewKeyPair() {
        KeyPairGenerator keyPairGen;
        try {
            keyPairGen = KeyPairGenerator.getInstance("DSA");
        } catch (NoSuchAlgorithmException e) {
           return null;
        }
        keyPairGen.initialize(2048);

        return keyPairGen.generateKeyPair();
    }

    @Override
    public DigitalSignature sign(KeyPair keyPair, byte[] toSign) {
        Signature sign = null;
        try {
            sign = Signature.getInstance("SHA256withDSA");
            sign.initSign(keyPair.getPrivate());
            sign.update(toSign);

            return new DigitalSignature(sign.sign(), keyPair.getPublic().getEncoded());
        } catch (NoSuchAlgorithmException | InvalidKeyException | SignatureException e) {
            return null;
        }
    }
}
