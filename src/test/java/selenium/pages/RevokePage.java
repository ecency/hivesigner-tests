package selenium.pages;

import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import selenium.ConfProperties;

public class RevokePage {
    private WebDriver driver;

    public RevokePage(WebDriver driver) {
        PageFactory.initElements(driver, this);
        this.driver = driver;
    }

    public static RevokePage using(WebDriver driver) {
        return new RevokePage(driver);
    }


    @FindBy(xpath = "//div/p[@class=\"text-black-400 text-lg\"]")
    private WebElement successMessage;

    @FindBy(xpath = "//div//a//span[normalize-space()='Revoke']")
    private WebElement revokeImg;

    @FindBy(xpath = "//div//div//button[normalize-space()='Revoke']")
    private WebElement revokeBtn;

    public RevokePage revokeBtnClick(){
        this.revokeBtn.click();
        return this;
    }

    public RevokePage isPageLoaded() {
        this.revokeImg.isDisplayed();
        System.out.println("Revoke page is loaded...");
        return this;
    }

    public boolean isSuccessMessagePresent() {
        try {
            return successMessage.isDisplayed();
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    public String getPageUrl() {
        System.out.println("Check that current page url is correct...");
        return driver.getCurrentUrl();
    }
}