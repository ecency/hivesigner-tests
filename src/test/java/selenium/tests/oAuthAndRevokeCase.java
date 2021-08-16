package selenium.tests;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import selenium.ConfProperties;
import selenium.handlers.ScreenshotsHandler;
import selenium.pages.*;

import java.util.concurrent.TimeUnit;


public class oAuthAndRevokeCase {

    public static WebDriver driver;
    public static GetStartedPage getStartedPage;
    public static ImportPage importPage;
    public static AccountsPage accountsPage;
    public static OAuthPage oAuthPage;
    public static LoginPage loginPage;
    public static ScreenshotsHandler screenShotMake;


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
        screenShotMake = new ScreenshotsHandler(driver);

    }

    @Test
    public void oauthAndRevokeUserNewUserCase(){
        String username = ConfProperties.getProperty("userName");
        String privateKey = ConfProperties.getProperty("privateKey");

        String url =  ConfProperties.getProperty("oauth2Url");
        String oauthUrl = url+username;

        driver.get(oauthUrl);
        importPage.isPageLoaded();
        importPage.importAccount(username, privateKey, false);
        oAuthPage.isPageLoaded();
        oAuthPage.headerEmailCheck(username);
        oAuthPage.authorizeBtnClick();
        oAuthPage.isPageLoaded();

        Assertions.assertEquals(true, oAuthPage.isSuccessMessagePresent());

    }

    @Test
    public void oauthAndRevokeExistedUserCase(){
        String username = ConfProperties.getProperty("userName");
        String privateKey = ConfProperties.getProperty("privateKey");

        driver.get(ConfProperties.getProperty("getStartedPageUrl"));
        importPage.isPageLoaded();
        importPage.importAccount(username, privateKey, false);
        accountsPage.isPageLoaded();

        String url =  ConfProperties.getProperty("oauth2Url");
        String oauthUrl = url+username;
        driver.get(oauthUrl);

        oAuthPage.isPageLoaded();
        oAuthPage.headerEmailCheck(username);
        oAuthPage.authorizeBtnClick();

        loginPage.isPageLoaded();
        loginPage.chooseAccountFromSelect(username);



    }


    @AfterEach
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}