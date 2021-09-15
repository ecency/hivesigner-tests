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

    @BeforeAll
    static void setupClass() {
        WebDriverManager.chromedriver().setup();
    }

    @BeforeEach
    public void setup() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments(ConfProperties.getProperty("optionsBrowser"));

        driver = new ChromeDriver(options);
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        driver.get(ConfProperties.getProperty("getStartedPageUrl"));

        getStartedPage = new GetStartedPage(driver);
        importPage = new ImportPage(driver);
        accountsPage = new AccountsPage(driver);
        jsCodeHandler = new JsCodeHandler(driver);
        screenShotMaker = new ScreenshotsHandler(driver);

    }

    @Test
    public void accountImportNoEncrypt() {
        String username = ConfProperties.getProperty("userName");
        String privateKey = ConfProperties.getProperty("privateKey");
        getStartedPage
                .getStartedBtnClick();
        importPage
                .isPageLoaded()
                .importAccount(username, privateKey, false);
        accountsPage
                .isPageLoaded()
                .isAccountChosen(username);
        Assertions.assertEquals(ConfProperties.getProperty("accountsPageUrl"), accountsPage.getPageUrl());
    }

    @Test
    public void accountImportEncrypt() {
        String username = ConfProperties.getProperty("userName");
        String privateKey = ConfProperties.getProperty("privateKey");
        String localPassword = ConfProperties.getProperty("localPassword");
        getStartedPage.getStartedBtnClick();
        importPage
                .isPageLoaded()
                .importAccount(username, privateKey, true)
                .setLocalPassword(localPassword);
        accountsPage
                .isPageLoaded()
                .isAccountChosen(username);
        Assertions.assertEquals(ConfProperties.getProperty("accountsPageUrl"), accountsPage.getPageUrl());
    }

    @Test
    public void addAccountWithSamePasswordInput() {
        String username0 = ConfProperties.getProperty("userName");
        String privateKey0 = ConfProperties.getProperty("privateKey");
        String username1 = ConfProperties.getProperty("userNameAlt1");
        String privateKey1 = ConfProperties.getProperty("privateKeyAlt1");
        String localPassword = ConfProperties.getProperty("localPassword");
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
        Assertions.assertEquals(ConfProperties.getProperty("accountsPageUrl"), accountsPage.getPageUrl());
    }

    @Test
    public void addAccountWithOtherPasswordInput() {
        String username0 = ConfProperties.getProperty("userName");
        String privateKey0 = ConfProperties.getProperty("privateKey");
        String username1 = ConfProperties.getProperty("userNameAlt1");
        String privateKey1 = ConfProperties.getProperty("privateKeyAlt1");
        String localPassword = ConfProperties.getProperty("localPassword");
        String localPassword2 = ConfProperties.getProperty("localPasswordAlt");
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
        Assertions.assertEquals(ConfProperties.getProperty("accountsPageUrl"), accountsPage.getPageUrl());
    }

    @Test
    public void addAccountWithPasswordFromDropdownList() {
        String username0 = ConfProperties.getProperty("userName");
        String privateKey0 = ConfProperties.getProperty("privateKey");
        String username1 = ConfProperties.getProperty("userNameAlt1");
        String privateKey1 = ConfProperties.getProperty("privateKeyAlt1");
        String username2 = ConfProperties.getProperty("userNameAlt2");
        String privateKey2 = ConfProperties.getProperty("privateKeyAlt2");
        String localPassword = ConfProperties.getProperty("localPassword");
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
        Assertions.assertEquals(ConfProperties.getProperty("accountsPageUrl"), accountsPage.getPageUrl());
    }

    @AfterEach
    public void tearDown() {
        jsCodeHandler.clearLocalStorage();
        if (driver != null) {
            driver.quit();
        }
    }

}
