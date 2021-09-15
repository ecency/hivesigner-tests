package selenium;

public class ConfProperties {

    public static String getProperty(String key) {
        return System.getenv(key);
    }
}