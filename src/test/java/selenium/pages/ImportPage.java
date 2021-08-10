package selenium.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class ImportPage {
    public WebDriver driver;

    public ImportPage(WebDriver driver) {
        PageFactory.initElements(driver, this);
        this.driver = driver;
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

    public void inputUserName(String login) {
        userNameInput.sendKeys(login);
    }

    public void inputPrivateKey(String privateKey) {
        privateKeyInput.sendKeys(privateKey);
    }

    public void clickLoginButton() {
        loginBtn.click();
    }

    public void clickContinueButton() {
        continueBtn.click();
    }

    public void encryptLoginCheckBoxEnabled(Boolean status) {
        if (status == false) {
            encryptYourLoginCheckBox.click();
        }

    }

    public void inputHivesignerPassword(String password) {
        hivesignerPasswordInput.sendKeys(password);
    }

    public void inputHivesignerPasswordConfirm(String password) {
        hivesignerPasswordConfirmInput.sendKeys(password);
    }

    public void userSamePassword(Boolean status, String password) {
        if (status == true) {
            userSamePasswordCheckbox.click();
            inputHivesignerPasswordConfirm(password);
        } else {
            inputHivesignerPassword(password);
            inputHivesignerPasswordConfirm(password);
        }
        clickContinueButton();

    }

    public void userPasswordFromAccountSelector(String account, String password) {
        userSamePasswordCheckbox.click();
        accountPasswordSelector.click();
        WebElement accountSelect = driver.findElement(By.xpath(String.format("//div[@class=\"dropdown relative\"]//a[normalize-space()='%s']", account)));
        accountSelect.click();
        inputHivesignerPasswordConfirm(password);
        clickContinueButton();
    }

    public void importAccount(String username, String privateKey, Boolean encrypted) {
        inputUserName(username);
        inputPrivateKey(privateKey);
        encryptLoginCheckBoxEnabled(encrypted);
        clickLoginButton();
    }

    public void setLocalPassword(String localPassword) {
        inputHivesignerPassword(localPassword);
        inputHivesignerPasswordConfirm(localPassword);
        clickContinueButton();
    }

    public void isPageLoaded() {
        dontHaveAccount.isDisplayed();
    }

    public String getPageUrl() {
        String url = driver.getCurrentUrl();
        return url;
    }

    public boolean isUserNameFieldPresent() {
        try {
            return userNameInput.isDisplayed();
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    public boolean isPrivateKeyFieldPresent() {
        try {
            return privateKeyInput.isDisplayed();
        } catch (NoSuchElementException e) {
            return false;
        }
    }

}
