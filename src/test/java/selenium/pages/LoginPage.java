package selenium.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

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


    @FindBy(xpath = "//*[contains(text(), 'Don`t have an account?')]")
    private WebElement haveAccountQ;


    public void inputUserName(String login) {
        userNameInput.sendKeys(login);
    }

    public void inputPrivateKey(String password) {
        privateKeyInput.sendKeys(password);
    }

    public void clickLoginButton() {
        loginBtn.click();
    }

    public void encryptLoginCheckBoxEnabled(Boolean status) {
        if (status == false) {
            encryptYourLoginCheckBox.click();
        }

    }

    public void isPageLoaded(){
        haveAccountQ.isDisplayed();
    }

    public String getLoginPageUrl() {
        String url = driver.getCurrentUrl();
        return url;
    }


}
