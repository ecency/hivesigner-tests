package selenium.pages;

import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import selenium.ConfProperties;

public class AuthoritiesPage {
    private WebDriver driver;

    public AuthoritiesPage(WebDriver driver) {
        PageFactory.initElements(driver, this);
        this.driver = driver;
    }

    public static AuthoritiesPage using(WebDriver driver) {
        return new AuthoritiesPage(driver);
    }

    public AuthoritiesPage navigateToPage() {
        driver.get(ConfProperties.getProperty("authoritiesPageUrl"));
        return this;
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


    public AuthoritiesPage isPageLoaded() {
        this.usernameTitle.isDisplayed();
        return this;
    }

    public AuthoritiesPage isCorrectUser(String username) {
        WebElement pageUserName = driver.findElement(By.xpath(String.format("//div[@class='text-xs tracking-wider uppercase text-gray']//..//div[contains(string(), '%s')]", username)));
        Assertions.assertTrue(pageUserName.isDisplayed());
        return this;
    }

    public AuthoritiesPage revokeBtnClick(String userName) {
        WebElement revokeBtn = driver.findElement(By.xpath(String.format("//div//div[@class='auths-table']//a[normalize-space()='%s']/../following::div[1]", userName)));
        revokeBtn.click();
        return this;
    }

    public String getPageUrl() {
        return driver.getCurrentUrl();
    }

    public AuthoritiesPage keysCheck(String userLevel) {
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
        return this;
    }
}
