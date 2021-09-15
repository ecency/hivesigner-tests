package selenium;

public class ConfProperties {

    public String getStartedPageUrl = EnvProperties.getEnv("GETSTARTEDPAGEURL");
    public String importPageUrl = EnvProperties.getEnv("IMPORTPAGEURL");
    public String loginPageUrl = EnvProperties.getEnv("LOGINPAGEURL");
    public String accountsPageUrl = EnvProperties.getEnv("ACCOUNTSPAGEURL");
    public String authoritiesPageUrl = EnvProperties.getEnv("AUTHORITIESPAGEURL");
    public String oauth2Url = EnvProperties.getEnv("OAUTH2URL");

    public String userName = EnvProperties.getEnv("USERNAME");
    public String privateKey = EnvProperties.getEnv("PRIVATEKEY");
    public String userNameAlt1 = EnvProperties.getEnv("USERNAMEALT1");
    public String privateKeyAlt1 = EnvProperties.getEnv("PRIVATEKEYALT1");

    public String userNameAlt2 = EnvProperties.getEnv("USERNAMEALT2");
    public String privateKeyAlt2 = EnvProperties.getEnv("PRIVATEKEYALT2");


    public String localPassword = EnvProperties.getEnv("LOCALPASSWORD");
    public String localPasswordAlt = EnvProperties.getEnv("LOCALPASSWORDALT");

    public String screenShotPath = EnvProperties.getEnv("SCREENSHOTPATH");

    public String options = "\"--headless\"";

    public String ownerPrivateKey = EnvProperties.getEnv("OWNERPRIVATEKEY");
    public String activePrivateKey = EnvProperties.getEnv("ACTIVEPRIVATEKEY");
    public String postingPrivateKey = EnvProperties.getEnv("POSTINGPRIVATEKEY");
    public String memoPrivateKey = EnvProperties.getEnv("MEMOPRIVATEKEY");

}