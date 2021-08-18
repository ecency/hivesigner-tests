package selenium.tests;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import selenium.ConfProperties;
import selenium.handlers.LocalStorageHandler;
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
    public static ScreenshotsHandler screenShotMake;
    public static LocalStorageHandler localStorageHandler;

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
        loginPage = new LoginPage(driver);
        screenShotMake = new ScreenshotsHandler(driver);
    }

    @Test
    public void getStartedRedirectToImportPage(){

        getStartedPage.getStartedBtnClick();
        importPage.isPageLoaded();
        String curl = importPage.getPageUrl();
        Assertions.assertEquals(ConfProperties.getProperty("importPageUrl"), curl);

    }

    @Test
    public void getStartedRedirectToLoginPage() {
        String username = ConfProperties.getProperty("userName");
        String privateKey = ConfProperties.getProperty("privateKey");

        getStartedPage.getStartedBtnClick();
        importPage.isPageLoaded();
        importPage.importAccount(username, privateKey, false);
        accountsPage.isPageLoaded();
        accountsPage.returnToGetStartedPage();
        getStartedPage.getStartedBtnClick();
        loginPage.checkDropdownWithAccount(username);
    }

    @AfterEach
    public void tearDown() {
        localStorageHandler.clearLocalStorage();
        if (driver != null) {
            driver.quit();
        }
    }
}

