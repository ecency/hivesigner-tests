package selenium.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;

public class LoginPage {
    public WebDriver driver;

    public LoginPage(WebDriver driver) {
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

    @FindBy(xpath = "//div/button[@class='button-primary w-full mb-2 mt-5']")
    private WebElement continueBtn;

    @FindBy(xpath = "//*[contains(text(), 'Don`t have an account?')]")
    private WebElement dontHaveAccount;

    @FindBy(id = "key")
    private WebElement hivesignerPasswordInput;

    @FindBy(id = "keyConfirmation")
    private WebElement hivesignerPasswordConfirmInput;

    @FindBy(xpath = "//div/div[@class='select text-lg relative text-black-500 z-20']")
    private WebElement accountDropdown;

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

    public void setLocalPassword(String localPassword) {
        inputHivesignerPassword(localPassword);
        inputHivesignerPasswordConfirm(localPassword);
        clickContinueButton();
    }

    public void dropdownSelect() {
        accountDropdown.click();
    }

    public void setAccount(String account){
        dropdownSelect();
        WebElement accountSelect = driver.findElement(By.xpath(String.format("//div//*[contains(text(), '%s')]", account)));
        accountSelect.click();
        clickContinueButton();
    }

    public void loginAccount(String username, String privateKey, Boolean encrypted) {
        inputUserName(username);
        inputPrivateKey(privateKey);
        encryptLoginCheckBoxEnabled(encrypted);
        clickLoginButton();
    }

    public void isPageLoaded() {
        dontHaveAccount.isDisplayed();
    }

    public String getLoginPageUrl() {
        String url = driver.getCurrentUrl();
        return url;
    }

}
