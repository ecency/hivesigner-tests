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
import selenium.pages.AuthoritiesPage;
import selenium.pages.GetStartedPage;
import selenium.pages.ImportPage;
import java.util.concurrent.TimeUnit;

public class PrivateKeysCheckCase {
    public static WebDriver driver;
    public static GetStartedPage getStartedPage;
    public static ImportPage importPage;
    public static AccountsPage accountsPage;
    public static AuthoritiesPage authoritiesPage;
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
        authoritiesPage = new AuthoritiesPage(driver);
        jsCodeHandler = new JsCodeHandler(driver);
        screenShotMaker = new ScreenshotsHandler(driver);
    }


    @Test
    public void memoPrivateKeyNotAcceptedImport() {
        String username = confProperties.userName;
        String privateKey = confProperties.memoPrivateKey;
        getStartedPage
                .getStartedBtnClick();
        importPage
                .isPageLoaded()
                .importAccount(username, privateKey, false);
        Assertions.assertTrue(importPage.isHigherKeyAlertIsPresent());
    }

    @Test
    public void postingPrivateKeyAcceptedImport() {
        String username = confProperties.userName;
        String privateKey = confProperties.postingPrivateKey;
        getStartedPage
                .getStartedBtnClick();
        importPage
                .isPageLoaded()
                .importAccount(username, privateKey, false);
        accountsPage
                .isPageLoaded()
                .authoritiesClick(username);
        authoritiesPage
                .keysCheck("posting");
    }

    @Test
    public void activePrivateKeyAcceptedImport() {
        String username = confProperties.userName;
        String privateKey = confProperties.activePrivateKey;
        getStartedPage
                .getStartedBtnClick();
        importPage
                .isPageLoaded()
                .importAccount(username, privateKey, false);
        accountsPage
                .isPageLoaded()
                .authoritiesClick(username);
        authoritiesPage
                .keysCheck("active");
    }

    @Test
    public void ownerPrivateKeyAcceptedImport() {
        String username = confProperties.userName;
        String privateKey = confProperties.ownerPrivateKey;
        getStartedPage
                .getStartedBtnClick();
        importPage
                .isPageLoaded()
                .importAccount(username, privateKey, false);
        accountsPage
                .isPageLoaded()
                .authoritiesClick(username);
        authoritiesPage
                .keysCheck("owner");
    }

    @Test
    public void masterPasswordAccessAllPrivateKeys() {
        String username =confProperties.userName;
        String masterPassword = confProperties.privateKey;
        getStartedPage
                .getStartedBtnClick();
        importPage
                .isPageLoaded()
                .importAccount(username, masterPassword, false);
        accountsPage
                .isPageLoaded()
                .authoritiesClick(username);
        authoritiesPage
                .keysCheck("masterPassword");
    }

    @AfterEach
    public void tearDown() {
        jsCodeHandler.clearLocalStorage();
        if (driver != null) {
            driver.quit();
        }
    }
}
