package selenium.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import selenium.ConfProperties;

public class ImportPage {
    private WebDriver driver;

    public ImportPage(WebDriver driver) {
        PageFactory.initElements(driver, this);
        this.driver = driver;
    }

    public static ImportPage using(WebDriver driver) {
        return new ImportPage(driver);
    }

    public ImportPage navigateToPage() {
        driver.get(ConfProperties.getProperty("importPageUrl"));
        return this;
    }

    @FindBy(xpath = "//*[@id=\"username\"]")
    private WebElement userNameInput;

    @FindBy(xpath = "//*[@id=\"password\"]")
    private WebElement privateKeyInput;

    @FindBy(xpath = "//div/div[2]//form/div/div[3]/label/span")
    private WebElement encryptYourLoginCheckBox;

    @FindBy(xpath = "//div/button[@class='button-primary w-full block mb-2']")
    private WebElement loginBtn;

    @FindBy(xpath = "//div//button[@type=\"submit\"]")
    private WebElement continueBtn;

    @FindBy(xpath = "//div//div[@class=\"mb-2\"]//div[@class='text-gray text-lg pt-4']/a[normalize-space()='Signup here']")
    private WebElement dontHaveAccount;

    @FindBy(id = "key")
    private WebElement hivesignerPasswordInput;

    @FindBy(id = "keyConfirmation")
    private WebElement hivesignerPasswordConfirmInput;

    @FindBy(xpath = "//span[@class='checkbox mr-2']")
    private WebElement userSamePasswordCheckbox;

    @FindBy(xpath = "//span[@class='text-lg']/..")
    private WebElement accountPasswordSelector;

    @FindBy(xpath = "//div//form//div[normalize-space()=\"You need to use master or at least posting key to login.\"]")
    private WebElement needHigherKeyAlert;

    public ImportPage inputUserName(String username) {
        this.userNameInput.sendKeys(username);
        System.out.println("Input username...");
        return this;
    }

    public ImportPage inputPrivateKey(String privateKey) {
        this.privateKeyInput.sendKeys(privateKey);
        System.out.println("Input private key...");
        return this;
    }

    public ImportPage clickLoginButton() {
        this.loginBtn.click();
        System.out.println("Click on 'Login' button...");
        return this;
    }

    public ImportPage clickContinueButton() {
        this.continueBtn.click();
        System.out.println("Click on 'Continue' button...");
        return this;
    }

    public ImportPage encryptLoginCheckBoxEnabled(Boolean status) {
        if (status == false) {
            System.out.println("'Encrypt your login' checkbox removed...");
            this.encryptYourLoginCheckBox.click();
        } else {
            System.out.println("'Encrypt your login' checkbox checked...");
        }
        return this;
    }

    public ImportPage inputHivesignerPassword(String password) {
        this.hivesignerPasswordInput.sendKeys(password);
        System.out.println("Input local password...");
        return this;
    }

    public ImportPage inputHivesignerPasswordConfirm(String password) {
        this.hivesignerPasswordConfirmInput.sendKeys(password);
        System.out.println("Input local confirm password...");
        return this;
    }

    public ImportPage userSamePassword(Boolean status, String password) {
        if (status == true) {
            System.out.println("New User use the same local password...");
            this.userSamePasswordCheckbox.click();
            inputHivesignerPasswordConfirm(password);
        } else {
            System.out.println("New User use the new local password...");
            inputHivesignerPassword(password);
            inputHivesignerPasswordConfirm(password);
        }
        clickContinueButton();
        return this;
    }

    public ImportPage userPasswordFromAccountSelector(String account, String password) {
        System.out.println("Click on checkbox 'Use the same password'...");
        this.userSamePasswordCheckbox.click();
        this.accountPasswordSelector.click();
        WebElement accountSelect = driver.findElement(By.xpath(String.format("//div[@class=\"dropdown relative\"]//a[normalize-space()='%s']", account)));
        System.out.println("Choose password from account selector...");
        accountSelect.click();
        inputHivesignerPasswordConfirm(password);
        clickContinueButton();
        return this;
    }

    public ImportPage importAccount(String username, String privateKey, Boolean encrypted) {
        inputUserName(username);
        inputPrivateKey(privateKey);
        encryptLoginCheckBoxEnabled(encrypted);
        clickLoginButton();
        return this;
    }

    public ImportPage setLocalPassword(String localPassword) {
        inputHivesignerPassword(localPassword);
        inputHivesignerPasswordConfirm(localPassword);
        clickContinueButton();
        return this;
    }

    public ImportPage isPageLoaded() {
        this.dontHaveAccount.isDisplayed();
        System.out.println("Import page is loaded...");
        return this;
    }

    public String getPageUrl() {
        System.out.println("Check that current page url is correct...");
        return driver.getCurrentUrl();
    }

    public boolean isUserNameFieldPresent() {
        try {
            userNameInput.isDisplayed();
            System.out.println("Username input field is displayed...");
            return true;
        } catch (NoSuchElementException e) {
            System.out.println("Username input field is not displayed...");
            return false;
        }
    }

    public boolean isPrivateKeyFieldPresent() {
        try {
            privateKeyInput.isDisplayed();
            System.out.println("Private key input field is displayed...");
            return true;
        } catch (NoSuchElementException e) {
            System.out.println("Private key input field is not displayed...");
            return false;
        }
    }

    public boolean isHigherKeyAlertIsPresent() {
        try {
            needHigherKeyAlert.isDisplayed();
            System.out.println("Higher key needed alert is displayed...");
            return true;
        } catch (NoSuchElementException e) {
            System.out.println("Higher key needed alert is not displayed...");
            return false;
        }
    }
}