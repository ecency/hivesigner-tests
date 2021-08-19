package selenium.tests;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import selenium.ConfProperties;
import selenium.handlers.LocalStorageHandler;
import selenium.handlers.ScreenshotsHandler;
import selenium.pages.AccountsPage;
import selenium.pages.GetStartedPage;
import selenium.pages.ImportPage;
import selenium.pages.LoginPage;

import java.util.concurrent.TimeUnit;

public class AccountLoginCase {
    public static WebDriver driver;
    public static GetStartedPage getStartedPage;
    public static ImportPage importPage;
    public static AccountsPage accountsPage;
    public static LoginPage loginPage;
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
        localStorageHandler = new LocalStorageHandler(driver);
        screenShotMaker = new ScreenshotsHandler(driver);

    }

    @Test
    public void noPasswordRequestForDecryptedAccount() {
        String username = ConfProperties.getProperty("userName");
        String privateKey = ConfProperties.getProperty("privateKey");

        getStartedPage.getStartedBtnClick();
        importPage.isPageLoaded();
        importPage.importAccount(username, privateKey, false);
        accountsPage.isPageLoaded();
        accountsPage.returnToGetStartedPage();
        getStartedPage.getStartedBtnClick();
        loginPage.isPageLoaded();

        String curl = loginPage.getPageUrl();
        Assertions.assertEquals(ConfProperties.getProperty("loginPageUrl"), curl);
        loginPage.checkDropdownWithAccount(username);
        Assertions.assertEquals(false, loginPage.isPasswordFieldPresent());

    }

    @Test
    public void passwordRequestForEncryptedAccount() {
        String username = ConfProperties.getProperty("userName");
        String privateKey = ConfProperties.getProperty("privateKey");
        String localPassword = ConfProperties.getProperty("localPassword");

        getStartedPage.getStartedBtnClick();
        importPage.isPageLoaded();
        importPage.importAccount(username, privateKey, true);
        importPage.setLocalPassword(localPassword);
        accountsPage.isPageLoaded();
        accountsPage.returnToGetStartedPage();
        getStartedPage.getStartedBtnClick();
        loginPage.isPageLoaded();

        String curl = loginPage.getPageUrl();
        Assertions.assertEquals(ConfProperties.getProperty("loginPageUrl"), curl);
        loginPage.checkDropdownWithAccount(username);
        Assertions.assertEquals(true, loginPage.isPasswordFieldPresent());

    }

    @Test
    public void addAnotherAccountBtnLeadsToImportPage() {
        String username = ConfProperties.getProperty("userName");
        String privateKey = ConfProperties.getProperty("privateKey");

        getStartedPage.getStartedBtnClick();
        importPage.isPageLoaded();
        importPage.importAccount(username, privateKey, false);
        accountsPage.isPageLoaded();
        accountsPage.returnToGetStartedPage();
        getStartedPage.getStartedBtnClick();
        loginPage.isPageLoaded();
        loginPage.addAccountClick();

        String curl = importPage.getPageUrl();
        Assertions.assertEquals(ConfProperties.getProperty("importPageUrl"), curl);
        Assertions.assertEquals(true, importPage.isUserNameFieldPresent());
        Assertions.assertEquals(true, importPage.isPrivateKeyFieldPresent());
    }

    @Test
    public void passwordFieldDependsFromAccountInSelector() {
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
        importPage.userSamePassword(false, localPassword);
        accountsPage.isPageLoaded();
        accountsPage.returnToGetStartedPage();
        getStartedPage.getStartedBtnClick();

        loginPage.isPageLoaded();
        String curl = loginPage.getPageUrl();
        Assertions.assertEquals(ConfProperties.getProperty("loginPageUrl"), curl);

        loginPage.checkDropdownWithAccount(username0);
        Assertions.assertEquals(false, loginPage.isPasswordFieldPresent());
        loginPage.checkDropdownWithAccount(username1);
        Assertions.assertEquals(true, loginPage.isPasswordFieldPresent());

    }

    @AfterEach
    public void tearDown() {
        localStorageHandler.clearLocalStorage();
        if (driver != null) {
            driver.quit();
        }
    }

}
