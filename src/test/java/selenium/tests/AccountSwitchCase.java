package selenium.tests;

import org.junit.jupiter.api.AfterEach;
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

public class AccountSwitchCase {
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
    public void switchToAccountNoPassword() {
        String username0 = ConfProperties.getProperty("userName");
        String privateKey0 = ConfProperties.getProperty("privateKey");
        String username1 = ConfProperties.getProperty("userNameAlt1");
        String privateKey1 = ConfProperties.getProperty("privateKeyAlt1");

        getStartedPage.getStartedBtnClick();
        importPage.isPageLoaded();

        importPage.importAccount(username0, privateKey0, false);

        accountsPage.isPageLoaded();
        accountsPage.addAccountClick();
        importPage.importAccount(username1, privateKey1, false);
        accountsPage.chooseAccount(username0);
        accountsPage.isAccountChosen(username0);
    }

    @Test
    public void switchToAccountWithPassword() {
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

        importPage.importAccount(username1, privateKey1, false);
        accountsPage.chooseAccount(username0);
        accountsPage.inputConfirmLocalPassword(localPassword);
        accountsPage.isAccountChosen(username0);
    }

    @Test
    public void switchToDecryptedAccountFromDropdownMenu(){
        String username0 = ConfProperties.getProperty("userName");
        String privateKey0 = ConfProperties.getProperty("privateKey");
        String username1 = ConfProperties.getProperty("userNameAlt1");
        String privateKey1 = ConfProperties.getProperty("privateKeyAlt1");
        String localPassword = ConfProperties.getProperty("localPassword");

        getStartedPage.getStartedBtnClick();
        importPage.isPageLoaded();
        importPage.importAccount(username0, privateKey0, false);
        accountsPage.isPageLoaded();
        accountsPage.addAccountClick();
        importPage.importAccount(username1, privateKey1, true);
        importPage.setLocalPassword(localPassword);

        accountsPage.isPageLoaded();
        accountsPage.chooseAccountFromDropDownList(username0, false,null);
        accountsPage.isAccountChosen(username0);
    }

    @Test
    public void switchToEncryptedAccountFromDropdownMenu(){
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
        importPage.importAccount(username1, privateKey1, false);

        accountsPage.isPageLoaded();
        accountsPage.chooseAccountFromDropDownList(username0,true, localPassword);
        accountsPage.isAccountChosen(username0);
    }

    @AfterEach
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
