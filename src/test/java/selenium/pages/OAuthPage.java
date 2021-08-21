package selenium.pages;

import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class OAuthPage {
    private WebDriver driver;

    public OAuthPage(WebDriver driver) {
        PageFactory.initElements(driver, this);
        this.driver = driver;
    }

    public static OAuthPage using(WebDriver driver) {
        return new OAuthPage(driver);
    }

    public OAuthPage navigateToPage(String url) {
        driver.get(url);
        return this;
    }

    @FindBy(xpath = "//div//button[contains(text(), 'Authorize')]")
    private WebElement authorizeBtn;

    @FindBy(xpath = "//div//h4[@class='mt-2 text-xl font-bold text-black-500']")
    private WebElement headerEmail;

    @FindBy(xpath = "//div//a[contains(text(), 'Accounts')]")
    private WebElement accountLink;

    @FindBy(xpath = "//div[contains(text(),'Transaction has been successfully broadcasted')]")
    private WebElement successMessage;

    public OAuthPage authorizeBtnClick() {
        this.authorizeBtn.click();
        return this;
    }

    public OAuthPage headerEmailCheck(String username) {
        Assertions.assertEquals(this.headerEmail.getText(), username);
        return this;
    }

    public OAuthPage isPageLoaded() {
        this.authorizeBtn.isDisplayed();
        return this;
    }

    public String getPageUrl() {
        return driver.getCurrentUrl();
    }

    public boolean isSuccessMessagePresent() {
        try {
            return successMessage.isDisplayed();
        } catch (NoSuchElementException e) {
            return false;
        }
    }
}