package be.hcbgsystem.core.configuration.system;

public class NoSuchSettingException extends Exception {
    public NoSuchSettingException(String key) {
        super("No such setting exists: " + key);
    }
}
