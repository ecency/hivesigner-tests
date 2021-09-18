package selenium.tests;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import selenium.ConfProperties;
import selenium.handlers.JsCodeHandler;
import selenium.handlers.ScreenshotsHandler;
import selenium.pages.*;
import java.util.concurrent.TimeUnit;

public class AccountSwitchCase {
    public static WebDriver driver;
    public static GetStartedPage getStartedPage;
    public static ImportPage importPage;
    public static AccountsPage accountsPage;
    public static LoginPage loginPage;
    public static NavigationBottomLine navigation;
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
        loginPage = new LoginPage(driver);
        navigation = new NavigationBottomLine(driver);
        jsCodeHandler = new JsCodeHandler(driver);
        screenShotMaker = new ScreenshotsHandler(driver);
    }

    @Test
    public void switchToAccountNoPassword() {
        String username0 = ConfProperties.getProperty("USER_NAME");
        String privateKey0 = ConfProperties.getProperty("PRIVATE_KEY");
        String username1 = ConfProperties.getProperty("USER_NAME_ALT1");
        String privateKey1 = ConfProperties.getProperty("PRIVATE_KEY_ALT1");
        getStartedPage
                .getStartedBtnClick();
        importPage
                .isPageLoaded()
                .importAccount(username0, privateKey0, false);
        accountsPage
                .isPageLoaded()
                .addAccountClick();
        importPage
                .importAccount(username1, privateKey1, false);
        accountsPage
                .chooseAccount(username0)
                .isAccountChosen(username0);
    }

    @Test
    public void switchToAccountWithPassword() {
        String username0 = ConfProperties.getProperty("USER_NAME");
        String privateKey0 = ConfProperties.getProperty("PRIVATE_KEY");
        String username1 = ConfProperties.getProperty("USER_NAME_ALT1");
        String privateKey1 = ConfProperties.getProperty("PRIVATE_KEY_ALT1");
        String localPassword = ConfProperties.getProperty("LOCAL_PASSWORD");
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
                .importAccount(username1, privateKey1, false);
        accountsPage
                .chooseAccount(username0)
                .inputConfirmLocalPassword(localPassword)
                .isAccountChosen(username0);
    }

    @Test
    public void switchToDecryptedAccountFromDropdownMenu(){
        String username0 = ConfProperties.getProperty("USER_NAME");
        String privateKey0 = ConfProperties.getProperty("PRIVATE_KEY");
        String username1 = ConfProperties.getProperty("USER_NAME_ALT1");
        String privateKey1 = ConfProperties.getProperty("PRIVATE_KEY_ALT1");
        String localPassword = ConfProperties.getProperty("LOCAL_PASSWORD");
        getStartedPage
                .getStartedBtnClick();
        importPage
                .isPageLoaded()
                .importAccount(username0, privateKey0, false);
        accountsPage
                .isPageLoaded()
                .addAccountClick();
        importPage
                .importAccount(username1, privateKey1, true)
                .setLocalPassword(localPassword);
        accountsPage
                .isPageLoaded()
                .chooseAccountFromDropDownList(username0, false,null)
                .isAccountChosen(username0);
    }

    @Test
    public void switchToEncryptedAccountFromDropdownMenu(){
        String username0 = ConfProperties.getProperty("USER_NAME");
        String privateKey0 = ConfProperties.getProperty("PRIVATE_KEY");
        String username1 = ConfProperties.getProperty("USER_NAME_ALT1");
        String privateKey1 = ConfProperties.getProperty("PRIVATE_KEY_ALT1");
        String localPassword = ConfProperties.getProperty("LOCAL_PASSWORD");
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
                .importAccount(username1, privateKey1, false);
        accountsPage
                .isPageLoaded()
                .chooseAccountFromDropDownList(username0,true, localPassword)
                .isAccountChosen(username0);
    }

    @Test
    public void switchToDecryptedAccountAfterCurrentAccRemoved(){
        String username0 = ConfProperties.getProperty("USER_NAME");
        String privateKey0 = ConfProperties.getProperty("PRIVATE_KEY");
        String username1 = ConfProperties.getProperty("USER_NAME_ALT1");
        String privateKey1 = ConfProperties.getProperty("PRIVATE_KEY_ALT1");
        getStartedPage
                .getStartedBtnClick();
        importPage
                .isPageLoaded()
                .importAccount(username0, privateKey0, false);
        accountsPage
                .isPageLoaded()
                .addAccountClick();
        importPage
                .importAccount(username1, privateKey1, false);
        accountsPage
                .isPageLoaded()
                .isAccountChosen(username1)
                .removeAccountClick(username1)
                .chooseAccount(username0)
                .isAccountChosen(username0);
    }

    @Test
    public void switchToEncryptedAccountAfterCurrentAccRemoved(){
        String username0 = ConfProperties.getProperty("USER_NAME");
        String privateKey0 = ConfProperties.getProperty("PRIVATE_KEY");
        String username1 = ConfProperties.getProperty("USER_NAME_ALT1");
        String privateKey1 = ConfProperties.getProperty("PRIVATE_KEY_ALT1");
        String localPassword = ConfProperties.getProperty("LOCAL_PASSWORD");
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
                .importAccount(username1, privateKey1, false);
        accountsPage
                .isPageLoaded()
                .isAccountChosen(username1)
                .removeAccountClick(username1)
                .chooseAccount(username0)
                .inputConfirmLocalPassword(localPassword)
                .isAccountChosen(username0);
    }

    @AfterEach
    public void tearDown() {
        jsCodeHandler.clearLocalStorage();
        if (driver != null) {
            driver.quit();
        }
    }
}
