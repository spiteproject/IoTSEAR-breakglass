package be.hcbgsystem.core.models.nonrepudiation;

public class NoSuchNonRepudiationProvider extends Exception {
    public NoSuchNonRepudiationProvider(String provider) {
        super("No such non-repudiation provider: " + provider);
    }
}
