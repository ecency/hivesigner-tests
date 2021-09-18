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

public class UserAuthoritiesCase {
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
    public void userAuthoritiesPageNavigate() {
        String username = ConfProperties.getProperty("USER_NAME");
        String privateKey = ConfProperties.getProperty("PRIVATE_KEY");
        getStartedPage
                .getStartedBtnClick();
        importPage
                .importAccount(username, privateKey, false);
        accountsPage
                .isPageLoaded()
                .authoritiesClick(username);
        authoritiesPage
                .isPageLoaded()
                .isCorrectUser(username);
        Assertions.assertEquals(ConfProperties.getProperty("AUTHORITIES_PAGE"), authoritiesPage.getPageUrl());
    }

    @AfterEach
    public void tearDown() {
        jsCodeHandler.clearLocalStorage();
        if (driver != null) {
            driver.quit();
        }
    }

}
