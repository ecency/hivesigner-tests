package selenium.pages;

import com.sun.source.util.SourcePositions;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import selenium.ConfProperties;

public class AccountsPage {
    private WebDriver driver;

    public AccountsPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public static AccountsPage using(WebDriver driver) {
        return new AccountsPage(driver);
    }

    public AccountsPage navigateToPage() {
        driver.get(ConfProperties.getProperty("accountsPageUrl"));
        return this;
    }

    @FindBy(css = ".accounts-list")
    private WebElement accountsList;

    @FindBy(xpath = "//div//span[text()='Accounts']")
    private WebElement logoImg;

    @FindBy(xpath = "//div//div[@class='flex justify-center mt-6 md:mt-20 xl:pt-10']/a")
    private WebElement addAnotherAccBtn;

    @FindBy(xpath = "//div[@class='modal flex items-center justify-center top-0 left-0 bottom-0 right-0 fixed mobile-full']//input[@id=\"importKey\"]")
    private WebElement confirmLocalPassword;

    @FindBy(xpath = "//div[@class='modal-content overflow-y-auto w-full relative bg-white duration-300 h-full xl:h-auto flex flex-col justify-center']")
    private WebElement confirmPasswordPanel;

    @FindBy(xpath = "//button[normalize-space()='Login']")
    private WebElement getConfirmLocalPasswordLogin;

    @FindBy(xpath = "//a[normalize-space()='Authorities']")
    private WebElement authorities;

    @FindBy(xpath = "//div[@class='hidden md:block']")
    private WebElement accountDropdownListBottom;

    @FindBy(xpath = "//div/div[@class='hidden md:block']//div[@class='icon absolute right-4 top-4 cursor-pointer text-gray hover:text-black']")
    private WebElement accountDropdownListBottomClose;

    @FindBy(xpath = "//div//a[normalize-space()='Remove from Hivesigner']")
    private WebElement removeBtn;

    @FindBy(xpath = "//div//a[normalize-space()='Remove']")
    private WebElement removeConfirmBtn;

    public AccountsPage isPageLoaded() {
        accountsList.isDisplayed();
        System.out.println("Account page is loaded...");
        return this;
    }

    public String getPageUrl() {
        System.out.println("Current page url copied...");
        return driver.getCurrentUrl();
    }

    public AccountsPage addAccountClick() {
        System.out.println("Click on 'Add another account' button...");
        this.addAnotherAccBtn.click();
        return this;
    }

    public AccountsPage chooseAccount(String username) {
        WebElement account = driver.findElement(By.xpath(String.format("//span[normalize-space()='%s']", username)));
        account.click();
        System.out.println("Chose account with username: " + username + " ...");
        return this;
    }

    public AccountsPage isAccountChosen(String username) {
        WebElement confirmIcon = driver.findElement(By.xpath(String.format("//div[@class='account-item flex flex-col items-center cursor-pointer']//span[normalize-space()='%s']//..//*[local-name()='svg']", username)));
        System.out.println("Check that account with username: " + username + " chosen...");
        Assertions.assertTrue(confirmIcon.isDisplayed());
        System.out.println("Checked...");
        return this;
    }

    public AccountsPage returnToGetStartedPage() {
        this.logoImg.click();
        System.out.println("Return to started page...");
        return this;
    }

    public AccountsPage inputConfirmLocalPassword(String password) {
        System.out.println("Check that confirm password is displayed...");
        Assertions.assertTrue(confirmPasswordPanel.isDisplayed());
        System.out.println("Checked...");
        this.confirmLocalPassword.sendKeys(password);
        this.getConfirmLocalPasswordLogin.click();
        System.out.println("Input local password confirm...");
        return this;
    }

    public AccountsPage authoritiesClick(String username) {
        WebElement confirmIcon = driver.findElement(By.xpath(String.format("//div[@class='account-item flex flex-col items-center cursor-pointer']//span[normalize-space()='%s']/..", username)));
        confirmIcon.click();
        this.authorities.click();
        System.out.println("Go to " + username + " Authorities page...");
        return this;
    }

    public AccountsPage removeAccountClick(String username) {
        WebElement confirmIcon = driver.findElement(By.xpath(String.format("//div[@class='account-item flex flex-col items-center cursor-pointer']//span[normalize-space()='%s']/..", username)));
        confirmIcon.click();
        this.removeBtn.click();
        this.removeConfirmBtn.click();
        System.out.println("Remove account with username: " + username + " from local storage...");
        return this;
    }

    public AccountsPage checkEncryptedOrNotIcon(String username, boolean encryptedStatus) {
        String title;
        if (encryptedStatus == true) {
            title = "Encrypted";
            System.out.println("Check that encrypted account has encrypted icon...");
        } else {
            title = "Decrypted";
            System.out.println("Check that decrypted account has no encrypted icon...");
        }
        this.accountDropdownListBottom.click();
        WebElement encryptedOrNotIcon = driver.findElement(By.xpath(String.format("//div/div[@class='hidden md:block']//div[normalize-space()='%s']/../../a[@title='%s']", username, title)));
        Assertions.assertTrue(encryptedOrNotIcon.isDisplayed());
        System.out.println("Checked...");
        return this;
    }

    public AccountsPage chooseAccountFromDropDownList(String username, boolean encrypted, String password) {
        this.accountDropdownListBottom.click();
        WebElement account = driver.findElement(By.xpath(String.format("//div/div[@class='hidden md:block']//div[@class='overflow-y-auto']//div[normalize-space()='%s']", username)));
        if (encrypted == false) {
            checkEncryptedOrNotIcon(username, false);
            account.click();
            this.accountDropdownListBottomClose.click();
        } else {
            checkEncryptedOrNotIcon(username, true);
            account.click();
            inputConfirmLocalPassword(password);
        }
        System.out.println("Account with username: " + username + "chosen from dropdown list...");
        return this;
    }
}