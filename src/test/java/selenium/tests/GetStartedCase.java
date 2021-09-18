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

public class GetStartedCase {
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
    public void getStartedRedirectToImportPage() {
        getStartedPage
                .getStartedBtnClick();
        importPage
                .isPageLoaded();
        Assertions.assertEquals(ConfProperties.getProperty("IMPORT_PAGE"), importPage.getPageUrl());
    }

    @Test
    public void getStartedRedirectToLoginPage() {
        String username = ConfProperties.getProperty("USER_NAME");
        String privateKey = ConfProperties.getProperty("PRIVATE_KEY");
        getStartedPage
                .getStartedBtnClick();
        importPage
                .isPageLoaded();
        importPage
                .importAccount(username, privateKey, false);
        accountsPage
                .isPageLoaded()
                .returnToGetStartedPage();
        getStartedPage
                .getStartedBtnClick();
        loginPage
                .checkDropdownWithAccount(username);
    }

    @AfterEach
    public void tearDown() {
        jsCodeHandler.clearLocalStorage();
        if (driver != null) {
            driver.quit();
        }
    }
}

