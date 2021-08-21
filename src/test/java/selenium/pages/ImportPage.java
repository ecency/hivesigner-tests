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

    public ImportPage inputUserName(String login) {
        this.userNameInput.sendKeys(login);
        return this;
    }

    public ImportPage inputPrivateKey(String privateKey) {
        this.privateKeyInput.sendKeys(privateKey);
        return this;
    }

    public ImportPage clickLoginButton() {
        this.loginBtn.click();
        return this;
    }

    public ImportPage clickContinueButton() {
        this.continueBtn.click();
        return this;
    }

    public ImportPage encryptLoginCheckBoxEnabled(Boolean status) {
        if (status == false) {
            this.encryptYourLoginCheckBox.click();
        }
        return this;
    }

    public ImportPage inputHivesignerPassword(String password) {
        this.hivesignerPasswordInput.sendKeys(password);
        return this;
    }

    public ImportPage inputHivesignerPasswordConfirm(String password) {
        this.hivesignerPasswordConfirmInput.sendKeys(password);
        return this;
    }

    public ImportPage userSamePassword(Boolean status, String password) {
        if (status == true) {
            this.userSamePasswordCheckbox.click();
            inputHivesignerPasswordConfirm(password);
        } else {
            inputHivesignerPassword(password);
            inputHivesignerPasswordConfirm(password);
        }
        clickContinueButton();
        return this;
    }

    public ImportPage userPasswordFromAccountSelector(String account, String password) {
        this.userSamePasswordCheckbox.click();
        this.accountPasswordSelector.click();
        WebElement accountSelect = driver.findElement(By.xpath(String.format("//div[@class=\"dropdown relative\"]//a[normalize-space()='%s']", account)));
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
        return this;
    }

    public String getPageUrl() {
        return driver.getCurrentUrl();
    }

    public boolean isUserNameFieldPresent() {
        try {
            return this.userNameInput.isDisplayed();
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    public boolean isPrivateKeyFieldPresent() {
        try {
            return this.privateKeyInput.isDisplayed();
        } catch (NoSuchElementException e) {
            return false;
        }
    }
}