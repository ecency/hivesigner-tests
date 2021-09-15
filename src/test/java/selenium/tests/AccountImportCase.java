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
import java.util.concurrent.TimeUnit;

public class AccountImportCase {
    public static WebDriver driver;
    public static GetStartedPage getStartedPage;
    public static ImportPage importPage;
    public static AccountsPage accountsPage;
    public static ScreenshotsHandler screenShotMaker;
    public static JsCodeHandler jsCodeHandler;
    public static ConfProperties confProperties;

    @BeforeAll
    static void setupClass() {
        WebDriverManager.chromedriver().setup();
    }

    @BeforeEach
    public void setup() {
        confProperties = new ConfProperties();
        ChromeOptions options = new ChromeOptions();
        options.addArguments(confProperties.options);

        driver = new ChromeDriver(options);
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        driver.get(confProperties.getStartedPageUrl);

        getStartedPage = new GetStartedPage(driver);
        importPage = new ImportPage(driver);
        accountsPage = new AccountsPage(driver);
        jsCodeHandler = new JsCodeHandler(driver);
        screenShotMaker = new ScreenshotsHandler(driver);
    }

    @Test
    public void accountImportNoEncrypt() {
        String username =confProperties.userName;
        String privateKey = confProperties.privateKey;
        getStartedPage
                .getStartedBtnClick();
        importPage
                .isPageLoaded()
                .importAccount(username, privateKey, false);
        accountsPage
                .isPageLoaded()
                .isAccountChosen(username);
        Assertions.assertEquals(confProperties.accountsPageUrl, accountsPage.getPageUrl());
    }

    @Test
    public void accountImportEncrypt() {
        String username = confProperties.userName;
        String privateKey = confProperties.privateKey;
        String localPassword = confProperties.localPassword;
        getStartedPage.getStartedBtnClick();
        importPage
                .isPageLoaded()
                .importAccount(username, privateKey, true)
                .setLocalPassword(localPassword);
        accountsPage
                .isPageLoaded()
                .isAccountChosen(username);
        Assertions.assertEquals(confProperties.accountsPageUrl, accountsPage.getPageUrl());
    }

    @Test
    public void addAccountWithSamePasswordInput() {
        String username0 = confProperties.userName;
        String privateKey0 = confProperties.privateKey;
        String username1 = confProperties.userNameAlt1;
        String privateKey1 = confProperties.privateKeyAlt1;
        String localPassword = confProperties.localPassword;
        getStartedPage
                .getStartedBtnClick();
        importPage
                .isPageLoaded()
                .importAccount(username0, privateKey0, true)
                .setLocalPassword(localPassword);
        accountsPage
                .isPageLoaded()
                .addAccountClick();
        importPage
                .importAccount(username1, privateKey1, true)
                .userSamePassword(true, localPassword);
        accountsPage
                .isPageLoaded()
                .isAccountChosen(username1);
        Assertions.assertEquals(confProperties.accountsPageUrl, accountsPage.getPageUrl());
    }

    @Test
    public void addAccountWithOtherPasswordInput() {
        String username0 = confProperties.userName;
        String privateKey0 = confProperties.privateKey;
        String username1 = confProperties.userNameAlt1;
        String privateKey1 = confProperties.privateKeyAlt1;
        String localPassword = confProperties.localPassword;
        String localPassword2 = confProperties.localPasswordAlt;
        getStartedPage
                .getStartedBtnClick();
        importPage
                .isPageLoaded()
                .importAccount(username0, privateKey0, true)
                .setLocalPassword(localPassword);
        accountsPage
                .isPageLoaded()
                .addAccountClick();
        importPage
                .importAccount(username1, privateKey1, true)
                .userSamePassword(false, localPassword2);
        accountsPage
                .isPageLoaded()
                .isAccountChosen(username1);
        Assertions.assertEquals(confProperties.accountsPageUrl, accountsPage.getPageUrl());
    }

    @Test
    public void addAccountWithPasswordFromDropdownList() {
        String username0 = confProperties.userName;
        String privateKey0 = confProperties.privateKey;
        String username1 = confProperties.userNameAlt1;
        String privateKey1 = confProperties.privateKeyAlt1;
        String username2 = confProperties.userNameAlt2;
        String privateKey2 = confProperties.privateKeyAlt2;
        String localPassword = confProperties.localPassword;
        getStartedPage
                .getStartedBtnClick();
        importPage
                .isPageLoaded()
                .importAccount(username0, privateKey0, true)
                .setLocalPassword(localPassword);
        accountsPage
                .isPageLoaded()
                .addAccountClick();
        importPage
                .importAccount(username1, privateKey1, true)
                .setLocalPassword(localPassword);
        accountsPage
                .isPageLoaded()
                .addAccountClick();
        importPage
                .importAccount(username2, privateKey2, true)
                .userPasswordFromAccountSelector(username0, localPassword);
        accountsPage
                .isPageLoaded()
                .isAccountChosen(username2);
        Assertions.assertEquals(confProperties.accountsPageUrl, accountsPage.getPageUrl());
    }

    @AfterEach
    public void tearDown() {
        jsCodeHandler.clearLocalStorage();
        if (driver != null) {
            driver.quit();
        }
    }

}
