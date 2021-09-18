package selenium.tests;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
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

    @BeforeAll
    static void setupClass() {
        WebDriverManager.chromedriver().setup();
    }

    @BeforeEach
    public void setup() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments(ConfProperties.getProperty("BROWSER_HEADLESS_MODE"), ConfProperties.getProperty("BROWSER_WINDOW_SIZE"));

        driver = new ChromeDriver(options);
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        driver.get(ConfProperties.getProperty("GET_STARTED_PAGE"));

        getStartedPage = new GetStartedPage(driver);
        importPage = new ImportPage(driver);
        accountsPage = new AccountsPage(driver);
        loginPage = new LoginPage(driver);
        jsCodeHandler = new JsCodeHandler(driver);
        screenShotMaker = new ScreenshotsHandler(driver);
    }

    @Test
    public void noPasswordRequestForDecryptedAccount() {
        String username = ConfProperties.getProperty("USER_NAME");
        String privateKey = ConfProperties.getProperty("PRIVATE_KEY");
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
        Assertions.assertEquals(ConfProperties.getProperty("LOGIN_PAGE"), loginPage.getPageUrl());
        Assertions.assertFalse(loginPage.isPasswordFieldPresent());
    }

    @Test
    public void passwordRequestForEncryptedAccount() {
        String username = ConfProperties.getProperty("USER_NAME");
        String privateKey = ConfProperties.getProperty("PRIVATE_KEY");
        String localPassword = ConfProperties.getProperty("LOCAL_PASSWORD");
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
        Assertions.assertEquals(ConfProperties.getProperty("LOGIN_PAGE"), loginPage.getPageUrl());
        Assertions.assertTrue(loginPage.isPasswordFieldPresent());
    }

    @Test
    public void addAnotherAccountBtnLeadsToImportPage() {
        String username = ConfProperties.getProperty("USER_NAME");
        String privateKey = ConfProperties.getProperty("PRIVATE_KEY");
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
        Assertions.assertEquals(ConfProperties.getProperty("IMPORT_PAGE"), importPage.getPageUrl());
        Assertions.assertTrue(importPage.isUserNameFieldPresent());
        Assertions.assertTrue(importPage.isPrivateKeyFieldPresent());
    }

    @Test
    public void passwordFieldDependsFromAccountInSelector() {
        String username0 = ConfProperties.getProperty("USER_NAME");
        String privateKey0 = ConfProperties.getProperty("PRIVATE_KEY");
        String username1 = ConfProperties.getProperty("USER_NAME_ALT1");
        String privateKey1 = ConfProperties.getProperty("PRIVATE_KEY_ALT1");
        String localPassword = ConfProperties.getProperty("LOCAL_PASSWORD");
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

        Assertions.assertEquals(ConfProperties.getProperty("LOGIN_PAGE"), loginPage.getPageUrl());
        loginPage.checkDropdownWithAccount(username0);
        Assertions.assertFalse(loginPage.isPasswordFieldPresent());
        loginPage.checkDropdownWithAccount(username1);
        Assertions.assertTrue(loginPage.isPasswordFieldPresent());
    }

    @Disabled("ToDo: Complete the test with JavaScript language usage or delete it")
    @Test
    public void userEncryptedSessionLives24Hours() {
        String username0 = ConfProperties.getProperty("USER_NAME");
        String privateKey0 = ConfProperties.getProperty("PRIVATE_KEY");

        String localPassword = ConfProperties.getProperty("LOCAL_PASSWORD");
        getStartedPage
                .getStartedBtnClick();
        importPage
                .isPageLoaded()
                .importAccount(username0, privateKey0, true)
                .userSamePassword(false, localPassword);
        jsCodeHandler.increaseDateJs();
        accountsPage.navigateToPage();
        accountsPage.isPageLoaded();
    }

    @AfterEach
    public void tearDown() {
        jsCodeHandler.clearLocalStorage();
        if (driver != null) {
            driver.quit();
        }
    }

}
