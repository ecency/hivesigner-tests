package selenium;

public class ConfProperties {

    public String getStartedPageUrl = EnvProperties.getEnv("TESTGETSTARTEDPAGEURL");
    public String importPageUrl = EnvProperties.getEnv("TESTIMPORTPAGEURL");
    public String loginPageUrl = EnvProperties.getEnv("TESTLOGINPAGEURL");
    public String accountsPageUrl = EnvProperties.getEnv("TESTACCOUNTSPAGEURL");
    public String authoritiesPageUrl = EnvProperties.getEnv("TESTAUTHORITIESPAGEURL");
    public String oauth2Url = EnvProperties.getEnv("TESTOAUTH2URL");

    public String userName = EnvProperties.getEnv("TESTUSERNAME");
    public String privateKey = EnvProperties.getEnv("TESTPRIVATEKEY");
    public String userNameAlt1 = EnvProperties.getEnv("TESTUSERNAMEALT1");
    public String privateKeyAlt1 = EnvProperties.getEnv("TESTPRIVATEKEYALT1");

    public String userNameAlt2 = EnvProperties.getEnv("TESTUSERNAMEALT2");
    public String privateKeyAlt2 = EnvProperties.getEnv("TESTPRIVATEKEYALT2");

    public String localPassword = EnvProperties.getEnv("TESTLOCALPASSWORD");
    public String localPasswordAlt = EnvProperties.getEnv("TESTLOCALPASSWORDALT");

    public String screenShotPath = EnvProperties.getEnv("TESTSCREENSHOTPATH");

    public String options = EnvProperties.getEnv("TESTBROWSEROPTIONS");

    public String ownerPrivateKey = EnvProperties.getEnv("TESTOWNERPRIVATEKEY");
    public String activePrivateKey = EnvProperties.getEnv("TESTACTIVEPRIVATEKEY");
    public String postingPrivateKey = EnvProperties.getEnv("TESTPOSTINGPRIVATEKEY");
    public String memoPrivateKey = EnvProperties.getEnv("TESTMEMOPRIVATEKEY");

}