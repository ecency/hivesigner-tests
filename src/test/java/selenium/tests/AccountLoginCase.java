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
    public static ConfProperties confProperties;

    @BeforeAll
    static void setupClass() {
        WebDriverManager.chromedriver().setup();
    }

    @BeforeEach
    public void setup() {
        confProperties = new ConfProperties();
        ChromeOptions options = new ChromeOptions();
        options.addArguments(confProperties.options);

        driver = new ChromeDriver(options);
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        driver.get(confProperties.getStartedPageUrl);

        getStartedPage = new GetStartedPage(driver);
        importPage = new ImportPage(driver);
        accountsPage = new AccountsPage(driver);
        loginPage = new LoginPage(driver);
        jsCodeHandler = new JsCodeHandler(driver);
        screenShotMaker = new ScreenshotsHandler(driver);
    }

    @Test
    public void noPasswordRequestForDecryptedAccount() {
        String username = confProperties.userName;
        String privateKey = confProperties.privateKey;
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
        Assertions.assertEquals(confProperties.loginPageUrl, loginPage.getPageUrl());
        Assertions.assertFalse(loginPage.isPasswordFieldPresent());
    }

    @Test
    public void passwordRequestForEncryptedAccount() {
        String username = confProperties.userName;
        String privateKey = confProperties.privateKey;
        String localPassword = confProperties.localPassword;
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
        Assertions.assertEquals(confProperties.loginPageUrl, loginPage.getPageUrl());
        Assertions.assertTrue(loginPage.isPasswordFieldPresent());
    }

    @Test
    public void addAnotherAccountBtnLeadsToImportPage() {
        String username = confProperties.userName;
        String privateKey = confProperties.privateKey;
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
        Assertions.assertEquals(confProperties.importPageUrl, importPage.getPageUrl());
        Assertions.assertTrue(importPage.isUserNameFieldPresent());
        Assertions.assertTrue(importPage.isPrivateKeyFieldPresent());
    }

    @Test
    public void passwordFieldDependsFromAccountInSelector() {
        String username0 = confProperties.userName;
        String privateKey0 = confProperties.privateKey;
        String username1 = confProperties.userNameAlt1;
        String privateKey1 = confProperties.privateKeyAlt1;
        String localPassword = confProperties.localPassword;
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

        Assertions.assertEquals(confProperties.loginPageUrl, loginPage.getPageUrl());
        loginPage.checkDropdownWithAccount(username0);
        Assertions.assertFalse(loginPage.isPasswordFieldPresent());
        loginPage.checkDropdownWithAccount(username1);
        Assertions.assertTrue(loginPage.isPasswordFieldPresent());
    }

    @Disabled("ToDo: Complete the test with JavaScript language usage or delete it")
    @Test
    public void userEncryptedSessionLives24Hours() {
        String username0 = confProperties.userName;
        String privateKey0 = confProperties.privateKey;

        String localPassword = confProperties.localPassword;
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
