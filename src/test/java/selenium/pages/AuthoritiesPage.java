package selenium.pages;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
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

    @FindBy(xpath = "//div[@data-e2e=\"owner-key\"]")
    private WebElement ownerKey;

    @FindBy(xpath = "//div[@data-e2e=\"active-key\"]")
    private WebElement activeKey;

    @FindBy(xpath = "//div[@data-e2e=\"active-key\"]/following-sibling::div[@data-e2e=\"posting-key\"]")
    private WebElement postingKey;

    @FindBy(xpath = "//div[@data-e2e=\"memo-key\"]")
    private WebElement memoKey;

    @FindBy(css = "[data-e2e=\"owner-reveal-or-import\"]")
    private WebElement ownerKeyRevealButton;

    @FindBy(css = "[data-e2e=\"active-reveal-or-import\"]")
    private WebElement activeKeyRevealButton;

    @FindBy(xpath = "//div[@data-e2e=\"active-key\"]/following-sibling::div[@data-e2e=\"posting-key\"]/following-sibling::div//a[@data-e2e=\"posting-reveal-or-import\"]")
    private WebElement postingKeyRevealButton;

    @FindBy(css = "[data-e2e=\"memo-reveal-or-import\"]")
    private WebElement memoKeyRevealButton;

    public AuthoritiesPage isPageLoaded() {
        this.usernameTitle.isDisplayed();
        System.out.println("Authorities page is loaded...");
        return this;
    }

    public AuthoritiesPage isCorrectUser(String username) {
        WebElement pageUserName = driver.findElement(By.xpath(String.format("//div[@class='text-xs tracking-wider uppercase text-gray']//..//div[contains(string(), '%s')]", username)));
        System.out.println("Check that page is for correct user...");
        Assertions.assertTrue(pageUserName.isDisplayed());
        System.out.println("Checked...");
        return this;
    }

    public AuthoritiesPage revokeBtnClick(String username) {
        WebElement revokeBtn = driver.findElement(By.xpath(String.format("//div//div[@class='auths-table']//a[normalize-space()='%s']/../following::div[1]", username)));
        revokeBtn.click();
        System.out.println("Revoke access for user...");
        return this;
    }

    public String getPageUrl() {
        System.out.println("Check that current page url is correct...");
        return driver.getCurrentUrl();
    }

    public AuthoritiesPage keysCheck(String userLevel, String key) {
        switch (userLevel) {
            case "owner":
                System.out.println("Check access to Owner key...");
                ownerKey.isDisplayed();
                activeKey.isDisplayed();
                postingKey.isDisplayed();
                ownerKeyRevealButton.isDisplayed();
                Assertions.assertEquals(ownerKeyRevealButton.getText(), "Reveal private key");
                System.out.println("Checked...");
                break;
            case "active": {
                System.out.println("Check access to Active key...");
                ownerKey.isDisplayed();
                activeKey.isDisplayed();
                postingKey.isDisplayed();
                activeKeyRevealButton.isDisplayed();
                Assertions.assertEquals(activeKeyRevealButton.getText(), "Reveal private key");
                System.out.println("Checked...");
                break;
            }
            case "posting": {
                System.out.println("Check access to Posting key...");
                ownerKey.isDisplayed();
                activeKey.isDisplayed();
                postingKey.isDisplayed();
                postingKeyRevealButton.isDisplayed();
                Assertions.assertEquals(postingKeyRevealButton.getText(), "Reveal private key");
                System.out.println("Checked...");
                break;
            }
        }
        return this;
    }
}
