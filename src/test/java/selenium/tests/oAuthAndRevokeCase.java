package selenium.tests;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import selenium.ConfProperties;
import selenium.handlers.JsCodeHandler;
import selenium.handlers.ScreenshotsHandler;
import selenium.handlers.URLhandler;
import selenium.pages.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

public class oAuthAndRevokeCase {

    public static WebDriver driver;
    public static GetStartedPage getStartedPage;
    public static ImportPage importPage;
    public static AccountsPage accountsPage;
    public static OAuthPage oAuthPage;
    public static LoginPage loginPage;
    public static AuthoritiesPage authoritiesPage;
    public static RevokePage revokePage;
    public static ScreenshotsHandler screenShotMaker;
    public static JsCodeHandler jsCodeHandler;
    public static URLhandler urlHandler;
    public static ConfProperties confProperties;

    @BeforeAll
    static void setupClass() {
        WebDriverManager.chromedriver().setup();
    }

    @BeforeEach
    public void setup() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments(confProperties.options);

        driver = new ChromeDriver(options);
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

        getStartedPage = new GetStartedPage(driver);
        importPage = new ImportPage(driver);
        accountsPage = new AccountsPage(driver);
        loginPage = new LoginPage(driver);
        oAuthPage = new OAuthPage(driver);
        revokePage = new RevokePage(driver);
        authoritiesPage = new AuthoritiesPage(driver);
        jsCodeHandler = new JsCodeHandler(driver);
        screenShotMaker = new ScreenshotsHandler(driver);
        urlHandler = new URLhandler("https://www.google.com", "code", confProperties.userName);
    }

    @Disabled("Flaky. Test depends from account balance")
    @Test
    public void oauthAndRevokeNewUserCase() throws MalformedURLException {
        String username = confProperties.userName;
        String privateKey = confProperties.privateKey;
        String oauthUrl = urlHandler.getOAuthUrl();
        oAuthPage
                .navigateToPage(oauthUrl);
        importPage
                .isPageLoaded()
                .importAccount(username, privateKey, false);
        oAuthPage
                .isPageLoaded()
                .authorizeBtnClick();
        WebElement googleInput = driver.findElement(By.xpath("//div//input[@class='gLFyf gsfi']"));
        googleInput.isDisplayed();
        URL newURL = new URL(driver.getCurrentUrl());
        Assertions.assertTrue(newURL.getQuery().contains("code="));
        Assertions.assertEquals(urlHandler.getRedirectUri(), newURL.getProtocol()+ "://" + newURL.getHost());

        driver.get(confProperties.accountsPageUrl);
        accountsPage
                .isPageLoaded()
                .authoritiesClick(username);
        authoritiesPage
                .revokeBtnClick(username);
        revokePage
                .isPageLoaded()
                .revokeBtnClick();
        Assertions.assertTrue(revokePage.isSuccessMessagePresent());

    }
    @Disabled("Flaky. Test depends from account balance")
    @Test
    public void oauthAndRevokeExistedUserCase() throws MalformedURLException {
        String username = confProperties.userName;
        String privateKey = confProperties.privateKey;

        driver.get(confProperties.getStartedPageUrl);
        importPage.isPageLoaded();
        importPage.importAccount(username, privateKey, false);
        accountsPage.isPageLoaded();

        String oauthUrl = urlHandler.getOAuthUrl();
        oAuthPage
                .navigateToPage(oauthUrl)
                .isPageLoaded()
                .headerEmailCheck(username)
                .authorizeBtnClick();
        loginPage
                .isPageLoaded()
                .chooseAccountFromSelect(username);
        WebElement googleInput = driver.findElement(By.xpath("//div//input[@class='gLFyf gsfi']"));
        googleInput.isDisplayed();

        URL newURL = new URL(driver.getCurrentUrl());
        Assertions.assertTrue(newURL.getQuery().contains("code="));
        Assertions.assertEquals(urlHandler.getRedirectUri(), newURL.getProtocol()+ "://" + newURL.getHost());
        accountsPage
                .navigateToPage()
                .isPageLoaded()
                .authoritiesClick(username);
        authoritiesPage
                .revokeBtnClick(username);
        revokePage
                .isPageLoaded()
                .revokeBtnClick();
        Assertions.assertTrue(revokePage.isSuccessMessagePresent());
    }

    @AfterEach
    public void tearDown() {
        jsCodeHandler.clearLocalStorage();
        if (driver != null) {
            driver.quit();
        }
    }
}