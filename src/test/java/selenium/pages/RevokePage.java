package selenium.pages;

import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class RevokePage {
    public WebDriver driver;

    public RevokePage(WebDriver driver) {
        PageFactory.initElements(driver, this);
        this.driver = driver;
    }

    @FindBy(xpath = "//div/p[@class=\"text-black-400 text-lg\"]")
    private WebElement successMessage;

    @FindBy(xpath = "//div//a//span[normalize-space()='Revoke']")
    private WebElement revokeImg;

    @FindBy(xpath = "//div//div//button[normalize-space()='Revoke']")
    private WebElement revokeBtn;

    public void revokeBtnClick(){
        revokeBtn.click();

    }

    public void isPageLoaded() {
       revokeImg.isDisplayed();
    }

    public boolean isSuccessMessagePresent() {
        try {
            return successMessage.isDisplayed();
        } catch (NoSuchElementException e) {
            return false;
        }
    }



    public String getPageUrl() {
        String url = driver.getCurrentUrl();
        return url;
    }

}
