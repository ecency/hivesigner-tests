package selenium.tests;

import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import selenium.ConfProperties;
import selenium.handlers.LocalStorageHandler;
import selenium.handlers.ScreenshotsHandler;
import selenium.handlers.URLhandler;
import selenium.pages.*;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
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
    public static LocalStorageHandler localStorageHandler;
    public static URLhandler urlHandler;


    @BeforeEach
    public void setup() {
        System.setProperty("webdriver.chrome.driver", ConfProperties.getProperty("chromedriver"));

        ChromeOptions options = new ChromeOptions();
        options.addArguments(ConfProperties.getProperty("options.addArguments"));

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
        localStorageHandler = new LocalStorageHandler(driver);
        screenShotMaker = new ScreenshotsHandler(driver);
        urlHandler = new URLhandler("https://www.google.com", "code", ConfProperties.getProperty("userName"));
    }

    @Disabled("Flaky. Test depends from account balance")
    @Test
    public void oauthAndRevokeNewUserCase() throws MalformedURLException {
        String username = ConfProperties.getProperty("userName");
        String privateKey = ConfProperties.getProperty("privateKey");
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

        driver.get(ConfProperties.getProperty("accountsPageUrl"));
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
        String username = ConfProperties.getProperty("userName");
        String privateKey = ConfProperties.getProperty("privateKey");

        driver.get(ConfProperties.getProperty("getStartedPageUrl"));
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
        localStorageHandler.clearLocalStorage();
        if (driver != null) {
            driver.quit();
        }
    }
}