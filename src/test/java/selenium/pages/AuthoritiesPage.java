package selenium.pages;

import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class AuthoritiesPage {
    public WebDriver driver;

    public AuthoritiesPage(WebDriver driver) {
        PageFactory.initElements(driver, this);
        this.driver = driver;
    }

    @FindBy(xpath = "//div[@class='text-xs tracking-wider uppercase text-gray']")
    private WebElement usernameTitle;

    @FindBy(xpath = "//div[normalize-space()='owner']")
    private WebElement ownerKey;

    @FindBy(xpath = "//div[normalize-space()='active']")
    private WebElement activeKey;

    @FindBy(xpath = "//div[normalize-space()='posting']")
    private WebElement postingKey;

    @FindBy(xpath = "//div[normalize-space()='memo']")
    private WebElement memoKey;


    public void isPageLoaded() {
        usernameTitle.isDisplayed();
    }

    public void isCorrectUser(String username) {
        WebElement pageUserName = driver.findElement(By.xpath(String.format("//div[@class='text-xs tracking-wider uppercase text-gray']//..//div[contains(string(), '%s')]", username)));
        Assertions.assertEquals(true, pageUserName.isDisplayed());
    }

    public void revokeBtnClick(String userName) {
        WebElement revokeBtn = driver.findElement(By.xpath(String.format("//div//div[@class='auths-table']//a[normalize-space()='%s']/../following::div[1]", userName)));
        revokeBtn.click();
    }

    public String getPageUrl() {
        String url = driver.getCurrentUrl();
        return url;
    }

    public void keysCheck(String userLevel) {
        switch (userLevel) {
            case "owner":
                ownerKey.isDisplayed();
                activeKey.isDisplayed();
                postingKey.isDisplayed();
                memoKey.isDisplayed();
                break;
            case "active": {
                activeKey.isDisplayed();
                postingKey.isDisplayed();
                memoKey.isDisplayed();
                break;
            }
            case "posting": {
                postingKey.isDisplayed();
                memoKey.isDisplayed();
                break;
            }


        }
    }


}
