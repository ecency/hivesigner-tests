package selenium.pages;

import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class OAuthPage {
    public WebDriver driver;

    public OAuthPage(WebDriver driver) {
        PageFactory.initElements(driver, this);
        this.driver = driver;
    }


    @FindBy(xpath = "//div//button[contains(text(), 'Authorize')]")
    private WebElement authorizeBtn;

    @FindBy(xpath = "//div//h4[@class=\'mt-2 text-xl font-bold text-black-500\']")
    private WebElement headerEmail;

    @FindBy(xpath = "//div//a[contains(text(), 'Accounts')]")
    private WebElement accountLink;

    @FindBy(xpath = "//div[contains(text(),'Transaction has been successfully broadcasted')]")
    private WebElement successMessage;


    public void authorizeBtnClick() {
        authorizeBtn.click();
    }

    public void headerEmailCheck(String username) {
        Assertions.assertEquals(headerEmail.getText(), username);

    }

    public void isPageLoaded() {
        authorizeBtn.isDisplayed();
    }

    public String getPageUrl() {
        String url = driver.getCurrentUrl();
        return url;
    }


    public boolean isSuccessMessagePresent() {
        try {
            return successMessage.isDisplayed();
        } catch (NoSuchElementException e) {
            return false;
        }
    }
}