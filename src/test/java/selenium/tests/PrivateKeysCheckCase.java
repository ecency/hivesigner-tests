package selenium.tests;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
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
        authoritiesPage = new AuthoritiesPage(driver);
        jsCodeHandler = new JsCodeHandler(driver);
        screenShotMaker = new ScreenshotsHandler(driver);
    }


    @Test
    public void memoPrivateKeyNotAcceptedImport() {
        String username = ConfProperties.getProperty("userName");
        String memoPrivateKey = ConfProperties.getProperty("memoPrivateKey");
        getStartedPage
                .getStartedBtnClick();
        importPage
                .isPageLoaded()
                .importAccount(username, memoPrivateKey, false);
        Assertions.assertTrue(importPage.isHigherKeyAlertIsPresent());
    }

    @Test
    public void postingPrivateKeyAcceptedImport() {
        String username = ConfProperties.getProperty("userName");
        String postingPrivateKey = ConfProperties.getProperty("postingPrivateKey");
        getStartedPage
                .getStartedBtnClick();
        importPage
                .isPageLoaded()
                .importAccount(username, postingPrivateKey, false);
        accountsPage
                .isPageLoaded()
                .authoritiesClick(username);
        authoritiesPage
                .keysCheck("posting");
    }


    @AfterEach
    public void tearDown() {
        jsCodeHandler.clearLocalStorage();
        if (driver != null) {
            driver.quit();
        }
    }
}
