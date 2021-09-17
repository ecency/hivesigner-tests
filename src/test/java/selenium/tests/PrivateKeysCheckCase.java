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
        authoritiesPage = new AuthoritiesPage(driver);
        jsCodeHandler = new JsCodeHandler(driver);
        screenShotMaker = new ScreenshotsHandler(driver);
    }


    @Test
    public void memoPrivateKeyNotAcceptedImport() {
        String username = ConfProperties.getProperty("USER_NAME");
        String privateKey = ConfProperties.getProperty("MEMO_PRIVATE_KEY");
        getStartedPage
                .getStartedBtnClick();
        importPage
                .isPageLoaded()
                .importAccount(username, privateKey, false);
        Assertions.assertTrue(importPage.isHigherKeyAlertIsPresent());
    }

    @Test
    public void postingPrivateKeyAcceptedImport() {
        String username = ConfProperties.getProperty("USER_NAME");
        String privateKey = ConfProperties.getProperty("POSTING_PRIVATE_KEY");
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
        String username = ConfProperties.getProperty("USER_NAME");
        String privateKey = ConfProperties.getProperty("ACTIVE_PRIVATE_KEY");
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
        String username = ConfProperties.getProperty("USER_NAME");
        String privateKey = ConfProperties.getProperty("OWNER_PRIVATE_KEY");
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
        String username = ConfProperties.getProperty("USER_NAME");
        String masterPassword = ConfProperties.getProperty("PRIVATE_KEY");
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
