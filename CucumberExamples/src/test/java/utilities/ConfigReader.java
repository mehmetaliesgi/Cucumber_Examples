package utilities;

import java.io.FileInputStream;
import java.util.Properties;

public class ConfigReader {
    static Properties properties;

    static {
        String dosyaYolu = "configuration.properties";
        try {
            FileInputStream fis = new FileInputStream(dosyaYolu);
            properties = new Properties();
            properties.load(fis);
        } catch (Exception e) {
            System.out.println("Properties dosyası okunamadı.");
        }
    }
    public static String getProperty(String key) {

        return properties.getProperty(key);
    }
}
