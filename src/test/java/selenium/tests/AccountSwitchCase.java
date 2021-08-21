package selenium.tests;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import selenium.ConfProperties;
import selenium.handlers.LocalStorageHandler;
import selenium.handlers.ScreenshotsHandler;
import selenium.pages.*;

import java.util.concurrent.TimeUnit;

public class AccountSwitchCase {
    public static WebDriver driver;
    public static GetStartedPage getStartedPage;
    public static ImportPage importPage;
    public static AccountsPage accountsPage;
    public static LoginPage loginPage;
    public static NavigationBottomLine navigation;
    public static ScreenshotsHandler screenShotMaker;
    public static LocalStorageHandler localStorageHandler;

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
        loginPage = new LoginPage(driver);
        navigation = new NavigationBottomLine(driver);
        localStorageHandler = new LocalStorageHandler(driver);
        screenShotMaker = new ScreenshotsHandler(driver);
    }

    @Test
    public void switchToAccountNoPassword() {
        String username0 = ConfProperties.getProperty("userName");
        String privateKey0 = ConfProperties.getProperty("privateKey");
        String username1 = ConfProperties.getProperty("userNameAlt1");
        String privateKey1 = ConfProperties.getProperty("privateKeyAlt1");
        getStartedPage
                .getStartedBtnClick();
        importPage
                .isPageLoaded()
                .importAccount(username0, privateKey0, false);
        accountsPage
                .isPageLoaded()
                .addAccountClick();
        importPage
                .importAccount(username1, privateKey1, false);
        accountsPage
                .chooseAccount(username0)
                .isAccountChosen(username0);
    }

    @Test
    public void switchToAccountWithPassword() {
        String username0 = ConfProperties.getProperty("userName");
        String privateKey0 = ConfProperties.getProperty("privateKey");
        String username1 = ConfProperties.getProperty("userNameAlt1");
        String privateKey1 = ConfProperties.getProperty("privateKeyAlt1");
        String localPassword = ConfProperties.getProperty("localPassword");
        getStartedPage
                .getStartedBtnClick();
        importPage
                .isPageLoaded()
                .importAccount(username0, privateKey0, true)
                .setLocalPassword(localPassword);
        accountsPage
                .isPageLoaded()
                .addAccountClick();
        importPage
                .importAccount(username1, privateKey1, false);
        accountsPage
                .chooseAccount(username0)
                .inputConfirmLocalPassword(localPassword)
                .isAccountChosen(username0);
    }

    @Test
    public void switchToDecryptedAccountFromDropdownMenu(){
        String username0 = ConfProperties.getProperty("userName");
        String privateKey0 = ConfProperties.getProperty("privateKey");
        String username1 = ConfProperties.getProperty("userNameAlt1");
        String privateKey1 = ConfProperties.getProperty("privateKeyAlt1");
        String localPassword = ConfProperties.getProperty("localPassword");
        getStartedPage
                .getStartedBtnClick();
        importPage
                .isPageLoaded()
                .importAccount(username0, privateKey0, false);
        accountsPage
                .isPageLoaded()
                .addAccountClick();
        importPage
                .importAccount(username1, privateKey1, true)
                .setLocalPassword(localPassword);
        accountsPage
                .isPageLoaded()
                .chooseAccountFromDropDownList(username0, false,null)
                .isAccountChosen(username0);
    }

    @Test
    public void switchToEncryptedAccountFromDropdownMenu(){
        String username0 = ConfProperties.getProperty("userName");
        String privateKey0 = ConfProperties.getProperty("privateKey");
        String username1 = ConfProperties.getProperty("userNameAlt1");
        String privateKey1 = ConfProperties.getProperty("privateKeyAlt1");
        String localPassword = ConfProperties.getProperty("localPassword");
        getStartedPage
                .getStartedBtnClick();
        importPage
                .isPageLoaded()
                .importAccount(username0, privateKey0, true)
                .setLocalPassword(localPassword);
        accountsPage
                .isPageLoaded()
                .addAccountClick();
        importPage
                .importAccount(username1, privateKey1, false);
        accountsPage
                .isPageLoaded()
                .chooseAccountFromDropDownList(username0,true, localPassword)
                .isAccountChosen(username0);
    }

    @Test
    public void switchToDecryptedAccountAfterCurrentAccRemoved(){
        String username0 = ConfProperties.getProperty("userName");
        String privateKey0 = ConfProperties.getProperty("privateKey");
        String username1 = ConfProperties.getProperty("userNameAlt1");
        String privateKey1 = ConfProperties.getProperty("privateKeyAlt1");
        getStartedPage
                .getStartedBtnClick();
        importPage
                .isPageLoaded()
                .importAccount(username0, privateKey0, false);
        accountsPage
                .isPageLoaded()
                .addAccountClick();
        importPage
                .importAccount(username1, privateKey1, false);
        accountsPage
                .isPageLoaded()
                .isAccountChosen(username1)
                .removeAccountClick(username1);
        getStartedPage
                .isPageLoaded();
        navigation
                .accountsClick();
        accountsPage
                .chooseAccount(username0)
                .isAccountChosen(username0);
    }

    @Test
    public void switchToEncryptedAccountAfterCurrentAccRemoved(){
        String username0 = ConfProperties.getProperty("userName");
        String privateKey0 = ConfProperties.getProperty("privateKey");
        String username1 = ConfProperties.getProperty("userNameAlt1");
        String privateKey1 = ConfProperties.getProperty("privateKeyAlt1");
        String localPassword = ConfProperties.getProperty("localPassword");
        getStartedPage
                .getStartedBtnClick();
        importPage
                .isPageLoaded()
                .importAccount(username0, privateKey0, true)
                .setLocalPassword(localPassword);
        accountsPage
                .isPageLoaded()
                .addAccountClick();
        importPage
                .importAccount(username1, privateKey1, false);
        accountsPage
                .isPageLoaded()
                .isAccountChosen(username1)
                .removeAccountClick(username1);
        getStartedPage
                .isPageLoaded();
        navigation
                .accountsClick();
        accountsPage
                .chooseAccount(username0)
                .inputConfirmLocalPassword(localPassword)
                .isAccountChosen(username0);
    }

    @AfterEach
    public void tearDown() {
        localStorageHandler.clearLocalStorage();
        if (driver != null) {
            driver.quit();
        }
    }
}
