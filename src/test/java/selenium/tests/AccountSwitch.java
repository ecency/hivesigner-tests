package selenium.tests;

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

public class AccountSwitch {
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
    public void switchToAccountNoPassword() {
        String username0 = ConfProperties.getProperty("userName");
        String privateKey0 = ConfProperties.getProperty("privateKey");
        String username1 = ConfProperties.getProperty("userNameAlt");
        String privateKey1 = ConfProperties.getProperty("privateKeyAlt");

        getStartedPage.getStartedBtnClick();
        loginPage.isPageLoaded();

        loginPage.loginAccount(username0, privateKey0, false);

        accountsPage.isPageLoaded();
        accountsPage.addAccountClick();
        loginPage.loginAccount(username1, privateKey1, false);
        accountsPage.chooseAccount(username0);
        accountsPage.isAccoutChoosen(username0);




    }
}
