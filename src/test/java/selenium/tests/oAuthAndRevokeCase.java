package selenium.tests;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import selenium.ConfProperties;
import selenium.handlers.ScreenshotsHandler;
import selenium.handlers.URLhandler;
import selenium.pages.*;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;


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
    public void oauthAndRevokeUserNewUserCase() throws URISyntaxException, MalformedURLException {
        String username = ConfProperties.getProperty("userName");
        String privateKey = ConfProperties.getProperty("privateKey");

        Map<String, String> queryParams = new HashMap<>();
        queryParams.put("redirect_uri", "https://google.ru");
        queryParams.put("response_type", "code");
        queryParams.put("client_id", "demo.com");

        String url = ConfProperties.getProperty("oauth2Url");
        Collection<String> queryParamsCollection = queryParams.keySet()
                .stream()
                .map(queryName -> queryName + "=" + queryParams.get(queryName))
                .collect(Collectors.toList());
        String query = String.join("&", queryParamsCollection);
        String oauthUrl = url + "?" + query;

        driver.get(oauthUrl);
        importPage.isPageLoaded();
        importPage.importAccount(username, privateKey, false);

        oAuthPage.authorizeBtnClick();

        WebElement googleinput = driver.findElement(By.xpath("//div//input[@class='gLFyf gsfi']"));
        googleinput.isDisplayed();

        URL newURL = new URL(driver.getCurrentUrl());

        Assertions.assertEquals(queryParams.get("redirect_uri"), newURL.getHost() + newURL.getPath());
        Assertions.assertTrue(newURL.getQuery().contains("code="));
        Assertions.assertEquals(true, oAuthPage.isSuccessMessagePresent());

    }

    @Test
    public void oauthAndRevokeExistedUserCase() {
        String username = ConfProperties.getProperty("userName");
        String privateKey = ConfProperties.getProperty("privateKey");

        driver.get(ConfProperties.getProperty("getStartedPageUrl"));
        importPage.isPageLoaded();
        importPage.importAccount(username, privateKey, false);
        accountsPage.isPageLoaded();

        String url = ConfProperties.getProperty("oauth2Url");
        String oauthUrl = url + username;
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