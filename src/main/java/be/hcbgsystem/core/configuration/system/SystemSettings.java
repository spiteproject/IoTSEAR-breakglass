package be.hcbgsystem.core.configuration.system;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class SystemSettings {
    private Properties properties;
    private static final String CONFIG_FILENAME = "config.properties";

    public SystemSettings() {
        properties = new Properties();
        loadProperties();
    }

    public void reload() {
        loadProperties();
    }

    private void loadProperties() {
        ClassLoader classLoader = SystemSettings.class.getClassLoader();

        try (InputStream stream = classLoader.getResourceAsStream(CONFIG_FILENAME)) {
            if (stream == null) {
                throw new FileNotFoundException();
            }
            properties.load(stream);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Object getProperty(String key) throws NoSuchSettingException {
        Object val = properties.getProperty(key);
        if (val == null) {
            throw new NoSuchSettingException(key);
        }
        return val;
    }
}
