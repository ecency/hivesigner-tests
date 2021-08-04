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
import selenium.pages.AccountsPage;
import selenium.pages.GetStartedPage;
import selenium.pages.LoginPage;

import java.util.concurrent.TimeUnit;

public class GetStartedCase {
    public static WebDriver driver;
    public static GetStartedPage getStartedPage;
    public static LoginPage loginPage;
    public static AccountsPage accountsPage;
    public static ScreenshotsHandler screenShotMake;

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
        loginPage = new LoginPage(driver);
        accountsPage = new AccountsPage(driver);
        screenShotMake = new ScreenshotsHandler(driver);
    }

    @Test
    public void existedUserLoginNoEncrypt() {
        String username = ConfProperties.getProperty("userName");
        String privateKey = ConfProperties.getProperty("privateKey");

        getStartedPage.getStartedBtnClick();
        loginPage.isPageLoaded();

        loginPage.loginAccount(username, privateKey, false);

        accountsPage.isPageLoaded();
        String curl = accountsPage.getAccountsPageUrl();
        Assertions.assertEquals(ConfProperties.getProperty("accountsPageUrl"), curl);
    }

    @Test
    public void existedUserLoginEncrypt() {
        String username = ConfProperties.getProperty("userName");
        String privateKey = ConfProperties.getProperty("privateKey");
        String localPassword = ConfProperties.getProperty("localPassword");

        getStartedPage.getStartedBtnClick();
        loginPage.isPageLoaded();

        loginPage.loginAccount(username, privateKey, true);
        loginPage.setLocalPassword(localPassword);

        accountsPage.isPageLoaded();
        String curl = accountsPage.getAccountsPageUrl();
        Assertions.assertEquals(ConfProperties.getProperty("accountsPageUrl"), curl);
    }

    @Test
    public void loginUserFromDropdownList() {
        String username = ConfProperties.getProperty("userName");
        String privateKey = ConfProperties.getProperty("privateKey");

        getStartedPage.getStartedBtnClick();
        loginPage.isPageLoaded();
        loginPage.loginAccount(username, privateKey, false);
        accountsPage.isPageLoaded();
        accountsPage.returnToGetStartedPage();
        getStartedPage.getStartedBtnClick();
        loginPage.setAccount(username);


        accountsPage.isPageLoaded();
        String curl = accountsPage.getAccountsPageUrl();
        Assertions.assertEquals(ConfProperties.getProperty("accountsPageUrl"), curl);
    }


    @AfterEach
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}

