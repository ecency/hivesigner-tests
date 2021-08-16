package selenium.tests;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import selenium.ConfProperties;
import selenium.handlers.ScreenshotsHandler;
import selenium.pages.AccountsPage;
import selenium.pages.GetStartedPage;
import selenium.pages.ImportPage;

import java.util.concurrent.TimeUnit;


public class AccountImportCase {
    public static WebDriver driver;
    public static GetStartedPage getStartedPage;
    public static ImportPage importPage;
    public static AccountsPage accountsPage;
    public static ScreenshotsHandler screenShotMake;

    @BeforeEach
    public void setup() {
        System.setProperty("webdriver.chrome.driver", ConfProperties.getProperty("chromedriver"));

        ChromeOptions options = new ChromeOptions();
        options.addArguments(ConfProperties.getProperty("options.addArguments"));

        driver = new ChromeDriver(options);
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        driver.get(ConfProperties.getProperty("getStartedPageUrl"));

        getStartedPage = new GetStartedPage(driver);
        importPage = new ImportPage(driver);
        accountsPage = new AccountsPage(driver);
        screenShotMake = new ScreenshotsHandler(driver);

    }

    @Test
    public void accountImportNoEncrypt() {
        String username = ConfProperties.getProperty("userName");
        String privateKey = ConfProperties.getProperty("privateKey");

        getStartedPage.getStartedBtnClick();
        importPage.isPageLoaded();
        importPage.importAccount(username, privateKey, false);

        accountsPage.isPageLoaded();
        String curl = accountsPage.getPageUrl();
        Assertions.assertEquals(ConfProperties.getProperty("accountsPageUrl"), curl);
        accountsPage.isAccountChosen(username);
    }

    @Test
    public void accountImportEncrypt() {
        String username = ConfProperties.getProperty("userName");
        String privateKey = ConfProperties.getProperty("privateKey");
        String localPassword = ConfProperties.getProperty("localPassword");

        getStartedPage.getStartedBtnClick();
        importPage.isPageLoaded();
        importPage.importAccount(username, privateKey, true);
        importPage.setLocalPassword(localPassword);

        accountsPage.isPageLoaded();
        String curl = accountsPage.getPageUrl();
        Assertions.assertEquals(ConfProperties.getProperty("accountsPageUrl"), curl);
        accountsPage.isAccountChosen(username);
    }

    @Test
    public void addAccountWithSamePasswordInput(){
        String username0 = ConfProperties.getProperty("userName");
        String privateKey0 = ConfProperties.getProperty("privateKey");
        String username1 = ConfProperties.getProperty("userNameAlt1");
        String privateKey1 = ConfProperties.getProperty("privateKeyAlt1");
        String localPassword = ConfProperties.getProperty("localPassword");

        getStartedPage.getStartedBtnClick();
        importPage.isPageLoaded();

        importPage.importAccount(username0, privateKey0, true);
        importPage.setLocalPassword(localPassword);

        accountsPage.isPageLoaded();
        accountsPage.addAccountClick();

        importPage.importAccount(username1, privateKey1, true);
        importPage.userSamePassword(true, localPassword);

        accountsPage.isPageLoaded();
        String curl = accountsPage.getPageUrl();
        Assertions.assertEquals(ConfProperties.getProperty("accountsPageUrl"), curl);
        accountsPage.isAccountChosen(username1);
    }

    @Test
    public void addAccountWithOtherPasswordInput(){
        String username0 = ConfProperties.getProperty("userName");
        String privateKey0 = ConfProperties.getProperty("privateKey");
        String username1 = ConfProperties.getProperty("userNameAlt1");
        String privateKey1 = ConfProperties.getProperty("privateKeyAlt1");
        String localPassword = ConfProperties.getProperty("localPassword");
        String localPassword2 = ConfProperties.getProperty("localPasswordAlt");

        getStartedPage.getStartedBtnClick();
        importPage.isPageLoaded();

        importPage.importAccount(username0, privateKey0, true);
        importPage.setLocalPassword(localPassword);

        accountsPage.isPageLoaded();
        accountsPage.addAccountClick();

        importPage.importAccount(username1, privateKey1, true);
        importPage.userSamePassword(false, localPassword2);

        accountsPage.isPageLoaded();
        String curl = accountsPage.getPageUrl();
        Assertions.assertEquals(ConfProperties.getProperty("accountsPageUrl"), curl);
        accountsPage.isAccountChosen(username1);
    }


    @Test
    public void addAccountWithPasswordFromDropdownList(){
        String username0 = ConfProperties.getProperty("userName");
        String privateKey0 = ConfProperties.getProperty("privateKey");
        String username1 = ConfProperties.getProperty("userNameAlt1");
        String privateKey1 = ConfProperties.getProperty("privateKeyAlt1");
        String username2 = ConfProperties.getProperty("userNameAlt2");
        String privateKey2 = ConfProperties.getProperty("privateKeyAlt2");
        String localPassword = ConfProperties.getProperty("localPassword");

        getStartedPage.getStartedBtnClick();
        importPage.isPageLoaded();

        importPage.importAccount(username0, privateKey0, true);
        importPage.setLocalPassword(localPassword);

        accountsPage.isPageLoaded();
        accountsPage.addAccountClick();

        importPage.importAccount(username1, privateKey1, true);
        importPage.setLocalPassword(localPassword);

        accountsPage.isPageLoaded();
        accountsPage.addAccountClick();
        importPage.importAccount(username2, privateKey2, true);
        importPage.userPasswordFromAccountSelector(username0,localPassword);

        accountsPage.isPageLoaded();
        String curl = accountsPage.getPageUrl();
        Assertions.assertEquals(ConfProperties.getProperty("accountsPageUrl"), curl);
        accountsPage.isAccountChosen(username2);
    }

    @AfterEach
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

}
