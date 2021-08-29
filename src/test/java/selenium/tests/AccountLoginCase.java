package selenium.tests;

import org.junit.jupiter.api.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.logging.LogEntries;
import selenium.ConfProperties;
import selenium.handlers.JsCodeHandler;
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
    public static JsCodeHandler jsCodeHandler;

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
        jsCodeHandler = new JsCodeHandler(driver);
        screenShotMaker = new ScreenshotsHandler(driver);
    }

    @Test
    public void noPasswordRequestForDecryptedAccount() {
        String username = ConfProperties.getProperty("userName");
        String privateKey = ConfProperties.getProperty("privateKey");
        getStartedPage
                .getStartedBtnClick();
        importPage
                .isPageLoaded()
                .importAccount(username, privateKey, false);
        accountsPage
                .isPageLoaded()
                .returnToGetStartedPage();
        getStartedPage
                .getStartedBtnClick();
        loginPage
                .isPageLoaded()
                .checkDropdownWithAccount(username);
        Assertions.assertEquals(ConfProperties.getProperty("loginPageUrl"), loginPage.getPageUrl());
        Assertions.assertFalse(loginPage.isPasswordFieldPresent());
    }

    @Test
    public void passwordRequestForEncryptedAccount() {
        String username = ConfProperties.getProperty("userName");
        String privateKey = ConfProperties.getProperty("privateKey");
        String localPassword = ConfProperties.getProperty("localPassword");
        getStartedPage
                .getStartedBtnClick();
        importPage
                .isPageLoaded()
                .importAccount(username, privateKey, true)
                .setLocalPassword(localPassword);
        accountsPage
                .isPageLoaded()
                .returnToGetStartedPage();
        getStartedPage
                .getStartedBtnClick();
        loginPage
                .isPageLoaded()
                .checkDropdownWithAccount(username);
        Assertions.assertEquals(ConfProperties.getProperty("loginPageUrl"), loginPage.getPageUrl());
        Assertions.assertTrue(loginPage.isPasswordFieldPresent());
    }

    @Test
    public void addAnotherAccountBtnLeadsToImportPage() {
        String username = ConfProperties.getProperty("userName");
        String privateKey = ConfProperties.getProperty("privateKey");
        getStartedPage
                .getStartedBtnClick();
        importPage
                .isPageLoaded()
                .importAccount(username, privateKey, false);
        accountsPage
                .isPageLoaded()
                .returnToGetStartedPage();
        getStartedPage
                .getStartedBtnClick();
        loginPage
                .isPageLoaded()
                .addAccountClick();
        Assertions.assertEquals(ConfProperties.getProperty("importPageUrl"), importPage.getPageUrl());
        Assertions.assertTrue(importPage.isUserNameFieldPresent());
        Assertions.assertTrue(importPage.isPrivateKeyFieldPresent());
    }

    @Test
    public void passwordFieldDependsFromAccountInSelector() {
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
                .userSamePassword(false, localPassword);
        accountsPage
                .isPageLoaded()
                .returnToGetStartedPage();
        getStartedPage
                .getStartedBtnClick();
        loginPage
                .isPageLoaded();

        Assertions.assertEquals(ConfProperties.getProperty("loginPageUrl"), loginPage.getPageUrl());
        loginPage.checkDropdownWithAccount(username0);
        Assertions.assertFalse(loginPage.isPasswordFieldPresent());
        loginPage.checkDropdownWithAccount(username1);
        Assertions.assertTrue(loginPage.isPasswordFieldPresent());
    }

    @Disabled("ToDo: Complete the test with JavaScript language usage or delete it")
    @Test
    public void userEncryptedSessionLives24Hours() {
        String username0 = ConfProperties.getProperty("userName");
        String privateKey0 = ConfProperties.getProperty("privateKey");
        LogEntries outputLogs;

        String localPassword = ConfProperties.getProperty("localPassword");
        getStartedPage
                .getStartedBtnClick();
        importPage
                .isPageLoaded()
                .importAccount(username0, privateKey0, true)
                .userSamePassword(false, localPassword);
//        jsCodeHandler.getTimeZone();
//        accountsPage
//                .returnToGetStartedPage();
//        getStartedPage
//                .getStartedBtnClick();
//        loginPage
//                .isPageLoaded();
//
//        Assertions.assertEquals(ConfProperties.getProperty("loginPageUrl"), loginPage.getPageUrl());
//        loginPage.checkDropdownWithAccount(username0);
//        Assertions.assertFalse(loginPage.isPasswordFieldPresent());
//        loginPage.checkDropdownWithAccount(username1);
//        Assertions.assertTrue(loginPage.isPasswordFieldPresent());
    }

    @AfterEach
    public void tearDown() {
        jsCodeHandler.clearLocalStorage();
        if (driver != null) {
            driver.quit();
        }
    }

}
